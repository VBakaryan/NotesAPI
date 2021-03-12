package com.homemade.note.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;


@Getter
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_last_modified")
    private Date dateModified;

    @Version
    @Column(name = "version")
    private int version;

    public void setVersion(int version) {
        this.version = version;
    }

    @PrePersist
    protected void prePersist() {
        dateCreated = new Date();
        dateModified = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        dateModified = new Date();
    }

    @PreRemove
    public void preRemove() { }

}
