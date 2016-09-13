package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.project.chatwe.android.zero.chatwe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
/*OnlineActivity的ListView的adapter*/

public class OnLineMessageListAdapter extends BaseAdapter {
    private List<ChatMessge> messgeList=new ArrayList<ChatMessge>();
    private Context context;
    public OnLineMessageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return messgeList.size();
    }

    @Override
    public ChatMessge getItem(int position) {
        return messgeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.message_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) view.findViewById(R.id.tvmsglist);
            view.setTag(viewHolder);

        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();

        }
        viewHolder.textView.setText(messgeList.get(position).getMsg());

        return view;
    }

    class ViewHolder{
        TextView textView;
    }


    public void addAll(List<ChatMessge> data){

        messgeList.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        messgeList.clear();
        notifyDataSetChanged();
    }



}
