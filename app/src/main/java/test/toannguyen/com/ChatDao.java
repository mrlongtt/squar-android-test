package test.toannguyen.com;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by SILONG on 9/4/15.
 */
public class ChatDao {
  ChatDao() {
    ModifiedDate = Calendar.getInstance().getTime();
  }

  public int Id;
  public String message;
  public Date ModifiedDate;

}
