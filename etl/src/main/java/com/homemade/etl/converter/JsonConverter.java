package com.homemade.etl.converter;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;


public class JsonConverter {
    private static final String FILE_DIR = "./convertedfiles/json/";
    private static final String FILE_EXTENSION = ".json";

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> void convertAndStoreJsonFile(List<T> users, String fileName) throws Exception {
        mapper.writeValue(new File(FILE_DIR + fileName + FILE_EXTENSION), users);
    }

}
