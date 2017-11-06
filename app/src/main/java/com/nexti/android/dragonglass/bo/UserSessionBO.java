package com.nexti.android.dragonglass.bo;

import android.content.Context;
import android.util.Log;

import com.nexti.android.dragonglass.db.DataSource;
import com.nexti.android.dragonglass.db.dao.UserSessionDaoImpl;
import com.nexti.android.dragonglass.model.entity.UserSessionEntity;

import java.util.Date;

/**
 * Created by ISCesar on 02/08/2017.
 */
public class UserSessionBO {

    public static final String TAG = "UserSessionBO";

    private Context context = null;
    private DataSource ds = null;
    private UserSessionEntity userSessionEntity =null;

    public UserSessionBO(Context context, DataSource ds){
        this.context = context;
        this.ds= ds;
    }

    public UserSessionBO(Context context, DataSource ds, int idUserSession){
        try{
            this.context = context;
            this.ds= ds;
            userSessionEntity = new UserSessionDaoImpl(this.context,this.ds).findByPrimaryKey(idUserSession);
        }catch(Exception ex){
            Log.e(TAG, "Unexpected error" , ex);
        }
    }

    public long save(UserSessionEntity userSessionEntity){
        long pkEntity = -1;
        if (userSessionEntity!=null){
            UserSessionDaoImpl userSessionEntityDao = new UserSessionDaoImpl(context,ds);
            if (userSessionEntity.getId()<=0){
                //Insert
                userSessionEntity.setCreateTimestamp(new Date());
                pkEntity = userSessionEntityDao.insert(userSessionEntity);
            }else{
                //Update
                userSessionEntity.setUpdateTimestamp(new Date());
                userSessionEntity.setVersion(userSessionEntity.getVersion() + 1);
                int numberRowsAffected = userSessionEntityDao.update(userSessionEntity);
                if (numberRowsAffected>0)
                    pkEntity = userSessionEntity.getId();
            }
        }
        return pkEntity;
    }

    public UserSessionEntity getUserSessionEntity() {
        if (userSessionEntity!=null){
            userSessionEntity = new UserSessionDaoImpl(this.context,this.ds).findByPrimaryKey(1);
        }
        return userSessionEntity;
    }

    public void setUserSessionEntity(UserSessionEntity userSessionEntity) {
        this.userSessionEntity = userSessionEntity;
    }
}
