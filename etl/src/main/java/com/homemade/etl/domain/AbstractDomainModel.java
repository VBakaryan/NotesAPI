package com.homemade.etl.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.homemade.etl.common.utils.Formats;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
abstract class AbstractDomainModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Formats.DATE_TIME_FORMAT, timezone = Formats.TIME_ZONE)
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Formats.DATE_TIME_FORMAT, timezone = Formats.TIME_ZONE)
    private Date dateLastModified;

}
