package com.nexti.android.dragonglass.model.entity;

import java.util.Date;

/**
 * Created by ISCesar on 01/10/2017.
 */
public interface IEntity {

    int getId();
    void setId(int id);
    Date getCreateTimestamp();
    void setCreateTimestamp(Date createTimestamp);
    Date getUpdateTimestamp();
    void setUpdateTimestamp(Date updateTimestamp);
    int getVersion();
    void setVersion(int version);

}
