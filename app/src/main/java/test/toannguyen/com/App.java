package test.toannguyen.com;

import android.app.Application;
import android.content.Context;

/**
 * Created by SILONG on 9/4/15.
 */
public class App extends Application {

  private static Context mContext;

  public static Context getContext() {
    return mContext;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();
  }
}
