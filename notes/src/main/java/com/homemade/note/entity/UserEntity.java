package com.homemade.note.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_user")
public class UserEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty
    @Size(min = 8)
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "userEntity",
            targetEntity = NoteEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<NoteEntity> noteEntities;

}
