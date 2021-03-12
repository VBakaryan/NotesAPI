package com.homemade.note.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public abstract class AbstractDomainModel implements Serializable {
    protected Map<String, Object> customData = new HashMap<>();
}
