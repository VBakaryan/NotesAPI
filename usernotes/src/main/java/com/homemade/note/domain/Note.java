package com.homemade.note.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends AbstractDomainModel {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String note;

    private Long userId;

}
