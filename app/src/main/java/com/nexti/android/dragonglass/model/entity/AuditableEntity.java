package com.nexti.android.dragonglass.model.entity;

import java.util.Date;

/**
 * Created by ISCesar on 29/10/2017.
 */
public abstract class AuditableEntity implements IEntity{

    protected int id;
    protected Date createTimestamp;
    protected Date updateTimestamp;
    protected int version;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    @Override
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    @Override
    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AuditableEntity{" +
                "id=" + id +
                ", createTimestamp=" + createTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                ", version=" + version +
                '}';
    }
}
