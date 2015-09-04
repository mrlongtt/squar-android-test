package test.toannguyen.com;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by SILONG on 9/4/15.
 */

public class ChatDao {
  ChatDao() {

  }
  ChatDao(String username) {
    this.username = username;
  }

  @DatabaseField
  public String message;

  @DatabaseField
  public String username;

  @DatabaseField
  public long sentDate;

}
