package test.toannguyen.com;

import android.provider.Settings;
import android.text.TextUtils;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * Created by SILONG on 9/4/15.
 */
public class ChatList extends Observable {
    private static final String FIREBASE_PATH = "chat";
    private static final String FIREBASE_SERVER = "https://mysquar-test.firebaseio.com";
    private static final String username;

    static {
        String android_id = Settings.Secure.getString(App.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(android_id)) {
            username = "USER_0000";
        } else {
            String postfix = android_id.substring(0, Math.min(4, android_id.length()));
            username = "USER_" + postfix;
        }
    }

    private final Firebase mFirebase;
    private final HashMap<String, ChatDao> objects = new HashMap<>();
    private final ArrayList<String> objectIds = new ArrayList<>();

    ChatList() {
        mFirebase = initFirebase(FIREBASE_SERVER);
        loadLocalData();
    }

    public boolean isMine(ChatDao item) {
        return TextUtils.equals(item.username, username);
    }


    private Firebase initFirebase(String url) {
        Firebase firebase = new Firebase(url);
        // firebase listener
        firebase.child(FIREBASE_PATH).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                ChatDao data = dataSnapshot.getValue(ChatDao.class);
                objects.put(key, data);
                if (!objectIds.contains(key)) {
                    objectIds.add(key);
                }
                DatabaseManager.instance().createOrUpdate(new OfflineObject(key, data));
                // notify data set changed
                setChanged();
                notifyObservers();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                ChatDao data = dataSnapshot.getValue(ChatDao.class);
                objects.put(key, data);
                DatabaseManager.instance().createOrUpdate(new OfflineObject(key, data));
                // notify data set changed
                setChanged();
                notifyObservers();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                objects.remove(key);
                objectIds.remove(key);
                DatabaseManager.instance().delete(key);

                // notify data set changed
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
        return firebase;
    }

    private void loadLocalData() {
        objectIds.clear();
        objectIds.clear();
        List<OfflineObject> _local = DatabaseManager.instance().getAll();
        for (OfflineObject o : _local) {
            objects.put(o.key, o);
            objectIds.add(o.key);
            setChanged();
        }
        // notify data set changed
        notifyObservers();
    }

    public int getCount() {
        return objectIds.size();
    }

    public ChatDao get(int position) {
        return objects.get(objectIds.get(position));
    }

    public void add(String message) {
        final ChatDao obj = new ChatDao(username);
        obj.message = message;
        obj.sentDate = System.currentTimeMillis();
        mFirebase.child(FIREBASE_PATH).push().setValue(obj);
    }

    private static ChatList _instance;

    public static ChatList instance() {
        if (_instance == null) {
            _instance = new ChatList();
        }
        return _instance;
    }


}
