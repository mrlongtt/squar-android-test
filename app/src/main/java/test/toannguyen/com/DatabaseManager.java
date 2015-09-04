package test.toannguyen.com;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.ListView;

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

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTableIfNotExists(getConnectionSource(), OfflineObject.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

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
      return getDao(OfflineObject.class).queryBuilder().orderBy("sentDate", true).query();
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  public void createOrUpdate(OfflineObject obj) {
    try {
      ((Dao<OfflineObject, String>)getDao(OfflineObject.class)).createOrUpdate(obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void delete(String id) {
    try {
      ((Dao<OfflineObject, String>)getDao(OfflineObject.class)).deleteById(id);

    }catch (Exception e) {

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
