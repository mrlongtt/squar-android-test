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
  public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;
      if(convertView == null) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
        holder = new ViewHolder();
        holder.txtMessage = (TextView)convertView.findViewById(R.id.txtMessage);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder)convertView.getTag();
      }

    // update
    ChatDao item = getItem(position);
    holder.txtMessage.setText(item.message);

    return convertView;
  }

  static class ViewHolder {
    TextView txtMessage;
  }
}
