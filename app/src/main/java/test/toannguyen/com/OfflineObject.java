package test.toannguyen.com;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SILONG on 9/4/15.
 */
@DatabaseTable(tableName = "OfflineObjects")
public class OfflineObject extends ChatDao {
  public OfflineObject() {

  }
  public OfflineObject(String key, ChatDao object) {
    this.key = key;
    this.username = object.username;
    this.message = object.message;
    this.sentDate = object.sentDate;
  }

  @DatabaseField(id = true)
  public String key;

}
