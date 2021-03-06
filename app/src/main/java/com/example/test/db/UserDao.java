package com.example.test.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.test.model.bean.shop.me.address.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Mid = new Property(0, Long.class, "mid", true, "_id");
        public final static Property MName = new Property(1, String.class, "mName", false, "M_NAME");
        public final static Property MPhone = new Property(2, String.class, "mPhone", false, "M_PHONE");
        public final static Property MCity = new Property(3, String.class, "mCity", false, "M_CITY");
        public final static Property MDetail = new Property(4, String.class, "mDetail", false, "M_DETAIL");
        public final static Property ABoolean = new Property(5, boolean.class, "aBoolean", false, "A_BOOLEAN");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: mid
                "\"M_NAME\" TEXT," + // 1: mName
                "\"M_PHONE\" TEXT," + // 2: mPhone
                "\"M_CITY\" TEXT," + // 3: mCity
                "\"M_DETAIL\" TEXT," + // 4: mDetail
                "\"A_BOOLEAN\" INTEGER NOT NULL );"); // 5: aBoolean
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long mid = entity.getMid();
        if (mid != null) {
            stmt.bindLong(1, mid);
        }
 
        String mName = entity.getMName();
        if (mName != null) {
            stmt.bindString(2, mName);
        }
 
        String mPhone = entity.getMPhone();
        if (mPhone != null) {
            stmt.bindString(3, mPhone);
        }
 
        String mCity = entity.getMCity();
        if (mCity != null) {
            stmt.bindString(4, mCity);
        }
 
        String mDetail = entity.getMDetail();
        if (mDetail != null) {
            stmt.bindString(5, mDetail);
        }
        stmt.bindLong(6, entity.getABoolean() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long mid = entity.getMid();
        if (mid != null) {
            stmt.bindLong(1, mid);
        }
 
        String mName = entity.getMName();
        if (mName != null) {
            stmt.bindString(2, mName);
        }
 
        String mPhone = entity.getMPhone();
        if (mPhone != null) {
            stmt.bindString(3, mPhone);
        }
 
        String mCity = entity.getMCity();
        if (mCity != null) {
            stmt.bindString(4, mCity);
        }
 
        String mDetail = entity.getMDetail();
        if (mDetail != null) {
            stmt.bindString(5, mDetail);
        }
        stmt.bindLong(6, entity.getABoolean() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // mid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mPhone
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mCity
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mDetail
            cursor.getShort(offset + 5) != 0 // aBoolean
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setMid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMPhone(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMCity(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMDetail(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setABoolean(cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setMid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getMid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getMid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
