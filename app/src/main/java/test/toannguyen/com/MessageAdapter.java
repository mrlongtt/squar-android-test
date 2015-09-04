package test.toannguyen.com;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import test.toannguyen.com.androidtest.R;

/**
 * Created by SILONG on 9/4/15.
 */
public class MessageAdapter extends BaseAdapter {

  private static final int TYPE_MINE = 0;
  private static final int TYPE_FRIEND = 1;

  @Override
  public int getCount() {
    return ChatList.instance().getCount();
  }

  @Override
  public ChatDao getItem(int position) {
    return ChatList.instance().get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public int getItemViewType(int position) {
    ChatDao item = getItem(position);
    if(ChatList.instance().isMine(item)) {
      return TYPE_MINE;
    }
    return TYPE_FRIEND;
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }

  public int getLayouRes(int position) {
    if(getItemViewType(position) == TYPE_MINE)
      return R.layout.list_item_message;
    return R.layout.list_item_message_friend;

  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;
      if(convertView == null) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(getLayouRes(position), parent, false);
        holder = new ViewHolder();
        holder.txtMessage = (TextView)convertView.findViewById(R.id.txtMessage);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder)convertView.getTag();
      }

    // update
    ChatDao item = getItem(position);
    holder.txtMessage.setText(String.format("%s: %s", item.username, item.message));

    return convertView;
  }

  static class ViewHolder {
    TextView txtMessage;
  }

}
