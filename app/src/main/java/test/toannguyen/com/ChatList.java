package test.toannguyen.com;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

/**
 * Created by SILONG on 9/4/15.
 */
public class ChatList extends Observable{
  private final Firebase mFirebase;
  private final String username;
  private static final String FIREBASE_PATH = "chat";

  public final HashMap<String, ChatDao> objects = new HashMap<>();
  ArrayList<String> objectIds = new ArrayList<>();

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

    // firebase listener
    mFirebase.child(FIREBASE_PATH).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        ChatDao data = dataSnapshot.getValue(ChatDao.class);
        objects.put(key, data);
        if(!objectIds.contains(key)) {
          objectIds.add(key);
        }
        // notify data set changed
        setChanged();
        notifyObservers();

      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        ChatDao data = dataSnapshot.getValue(ChatDao.class);
        objects.put(key, data);
        // notify data set changed
        setChanged();
        notifyObservers();
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        objects.remove(key);
        objectIds.remove(key);
        //
        setChanged();
        notifyObservers();
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {

      }
    });
  }


  public int getCount() {
    return objectIds.size();
  }

  public ChatDao get(int position) {
    return objects.get(objectIds.get(position));
  }

  public void add(String message) {
    ChatDao obj = new ChatDao(username);
    obj.message = message;
    mFirebase.child(FIREBASE_PATH).push().setValue(obj);

  }


  private static ChatList _instance;


  public static ChatList instance() {
    if(_instance == null) {
      _instance = new ChatList();
    }

    return _instance;
  }


}
