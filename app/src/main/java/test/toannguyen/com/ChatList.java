package test.toannguyen.com;

import com.firebase.client.Firebase;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by SILONG on 9/4/15.
 */
public class ChatList extends Observable{
  private final Firebase mFirebase;
  private final String username;
  ChatList() {
    mFirebase = new Firebase("https://mysquar-test.firebaseio.com");
    String android_id = Settings.Secure.getString(App.getContext().getContentResolver(),
        Settings.Secure.ANDROID_ID);
    if(TextUtils.isEmpty(android_id)) {
      username = "USER_0000";
    } else {
      String postfix = android_id.substring(0, Math.min(4, android_id.length()));
      username = "USER_"+ postfix;
    }
  }

  public final ArrayList<ChatDao> objects = new ArrayList<>();

  public int getCount() {
    return objects.size();
  }

  public ChatDao get(int position) {
    return objects.get(position);
  }

  public void add(String message) {
    ChatDao obj = new ChatDao(username);
    obj.message = message;
    objects.add(obj);
    mFirebase.child("chat").push().setValue(obj);
    // notify
    setChanged();
    notifyObservers();
  }

  private static ChatList _instance;


  public static ChatList instance() {
    if(_instance == null) {
      _instance = new ChatList();
    }

    return _instance;
  }


}
