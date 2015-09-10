package test.toannguyen.com;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.LruObjectCache;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SILONG on 9/4/15.
 */
public class DatabaseManager extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "users";
    public static final int DB_VERSION = 2;

    DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private final LruObjectCache mCache = new LruObjectCache(100);
    private Dao<OfflineObject, String> mOfflineDao;

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(getConnectionSource(), OfflineObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dao<OfflineObject, String> getOfflineDao() throws SQLException {
        if (mOfflineDao == null) {
            mOfflineDao = getDao(OfflineObject.class);
            mOfflineDao.setObjectCache(mCache);
        }
        return mOfflineDao;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTableIfNotExists(getConnectionSource(), OfflineObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OfflineObject> getAll() {
        try {
            return getOfflineDao().queryBuilder().orderBy("sentDate", true).query();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void createOrUpdate(OfflineObject obj) {
        try {
            getOfflineDao().createOrUpdate(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        try {
            getOfflineDao().deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static DatabaseManager _instance;

    public static void init(Context context) {
        _instance = new DatabaseManager(context);
    }

    public static DatabaseManager instance() {
        return _instance;
    }


}
