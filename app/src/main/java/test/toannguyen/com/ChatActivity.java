package test.toannguyen.com;

import com.firebase.client.Firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import test.toannguyen.com.androidtest.R;

/**
 * Created by SILONG on 9/4/15.
 */
public class ChatActivity extends AppCompatActivity implements Observer, View.OnClickListener{

  private ListView mListView;
  private EditText edtMessage;
  private MessageAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    Firebase.setAndroidContext(this);
    mListView = (ListView)findViewById(R.id.listView);
    edtMessage = (EditText) findViewById(R.id.edtMessage);
    adapter = new MessageAdapter();
    mListView.setAdapter(adapter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    ChatList.instance().addObserver(this);
    if(adapter != null)
      adapter.notifyDataSetChanged();
  }

  @Override
  protected void onPause() {
    super.onPause();
    ChatList.instance().deleteObserver(this);
  }

  @Override
  public void update(Observable observable, Object data) {
    if(adapter != null)
      adapter.notifyDataSetChanged();
  }

  public void sendMessage() {
    String message = edtMessage.getText().toString();
    if(!TextUtils.isEmpty(message)) {
      ChatList.instance().add(message);
    }
    edtMessage.setText("");
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnSend:
        sendMessage();
        break;
    }
  }
}
