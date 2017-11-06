package com.nexti.android.dragonglass.bo;

import android.content.Context;
import android.util.Log;

import com.nexti.android.dragonglass.db.DataSource;
import com.nexti.android.dragonglass.db.dao.DgMobileDaoImpl;
import com.nexti.android.dragonglass.model.dto.DgMobileDto;
import com.nexti.android.dragonglass.model.entity.DgMobileEntity;

import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by ISCesar on 29/10/2017.
 */
public class DgMobileBO {

    public static final String TAG = DgMobileBO.class.getSimpleName();

    private Context context = null;
    private DataSource ds = null;
    private DgMobileEntity dgMobileEntity =null;

    public DgMobileBO(Context context, DataSource ds){
        this.context = context;
        this.ds= ds;
    }

    public DgMobileBO(Context context, DataSource ds, int id){
        try{
            this.context = context;
            this.ds= ds;
            dgMobileEntity = new DgMobileDaoImpl(this.context,this.ds).findByPrimaryKey(id);
        }catch(Exception ex){
            Log.e(TAG, "Unexpected error" , ex);
        }
    }

    public long save(DgMobileEntity dgMobileEntity){
        long pkEntity = -1;
        if (dgMobileEntity!=null){
            DgMobileDaoImpl dgMobileEntityDao = new DgMobileDaoImpl(context,ds);
            if (dgMobileEntity.getId()<=0){
                //Insert
                dgMobileEntity.setCreateTimestamp(new Date());
                pkEntity = dgMobileEntityDao.insert(dgMobileEntity);
            }else{
                //Update
                dgMobileEntity.setUpdateTimestamp(new Date());
                dgMobileEntity.setVersion(dgMobileEntity.getVersion() + 1);
                int numberRowsAffected = dgMobileEntityDao.update(dgMobileEntity);
                if (numberRowsAffected>0)
                    pkEntity = dgMobileEntity.getId();
            }
        }
        return pkEntity;
    }

    public DgMobileEntity convertDtoToEntity(DgMobileDto dgMobileDto){
        DgMobileEntity dgMobileEntity = new DgMobileEntity();
        BeanUtils.copyProperties(dgMobileDto, dgMobileEntity);
        this.dgMobileEntity = dgMobileEntity;
        return dgMobileEntity;
    }

    public DgMobileEntity getDgMobileEntity() {
        return dgMobileEntity;
    }

    public void setDgMobileEntity(DgMobileEntity dgMobileEntity) {
        this.dgMobileEntity = dgMobileEntity;
    }
}
