package test.toannguyen.com;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by SILONG on 9/4/15.
 */
public class ChatList extends Observable{

  public final ArrayList<ChatDao> objects = new ArrayList<>();

  public int getCount() {
    return objects.size();
  }

  public ChatDao get(int position) {
    return objects.get(position);
  }

  public void add(String message) {
    ChatDao obj = new ChatDao();
    obj.message = message;
    objects.add(obj);
    // notify
    setChanged();
    notifyObservers();
  }

  private static ChatList _instance;

  public static ChatList instance() {
    if(_instance == null)
      _instance = new ChatList();
    return _instance;
  }


}
