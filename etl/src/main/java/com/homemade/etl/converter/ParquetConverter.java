package com.homemade.etl.converter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class ParquetConverter {
    private static final String FILE_DIR = "./convertedfiles/parquet/";
    private static final String FILE_EXTENSION = ".parquet";

    /**
     * @param objList
     * @return temp file
     *@Conver the input json data to parquet format
     */
    public static <T> void convertAndStoreParquetFile(List<T> objList, String fileName) {
        JavaSparkContext sparkContext = null;
        File tempFile;
        try (SparkSession spark = SparkSession.builder()
                .master("local[4]")
                .appName("ConvertorApp")
                .getOrCreate()) {
            tempFile = createTempFile();
            Gson gson = new Gson();
            List<String> data = Arrays.asList(gson.toJson(objList));
            sparkContext = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate());
            sparkContext.setLogLevel("WARN");
            Dataset<String> stringDataSet = spark.createDataset(data, Encoders.STRING());
            Dataset<Row> parquetDataSet = spark.read().json(stringDataSet);

            if (tempFile != null) {
                parquetDataSet.write().parquet(tempFile.getPath());

                FileUtils.copyFile(retrieveParquetFileFromPath(tempFile), new File(composeFilePath(fileName)));
                FileUtils.deleteDirectory(new File(tempFile.toURI()));
            }
        } catch (Exception ex) {
            log.error("Stack Trace: {}", ex);
        } finally {
            if (sparkContext != null) {
                sparkContext.close();
            }
        }
    }

    //Create the temp file path to copy converted parquet data
    private static File createTempFile() throws IOException {
        Path tempPath = Files.createTempDirectory("");
        File tempFile = tempPath.toFile();
        if (tempFile != null && tempFile.exists()) {
            String tempFilePath = tempFile.getAbsolutePath();
            tempFile.deleteOnExit();
            Files.deleteIfExists(tempFile.toPath());
            log.debug("Deleted tempFile[ {} ]}", tempFilePath);
        }

        return tempFile;
    }

    //Retrieve the parquet file path
    private static File retrieveParquetFileFromPath(File tempFilePath) {
        List<File> files = Arrays.asList(tempFilePath.listFiles());
        return files.stream()
                .filter(
                        tmpFile -> tmpFile.getPath().contains(FILE_EXTENSION) && tmpFile.getPath().endsWith(FILE_EXTENSION))
                .findAny()
                .orElse(null);
    }

    private static String composeFilePath(String fileName) {
        return FILE_DIR + fileName + FILE_EXTENSION;
    }

}
