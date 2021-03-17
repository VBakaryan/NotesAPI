package com.homemade.etl.reader;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class ReaderInputData {

    private Date startTime;
    private Date endTime;
    private int rowsSize;

}
