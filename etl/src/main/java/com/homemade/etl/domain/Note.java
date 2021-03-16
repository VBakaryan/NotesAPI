package com.homemade.etl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends AbstractDomainModel {

    private Long id;

    private String title;

    private String note;

    private Long userId;
}
