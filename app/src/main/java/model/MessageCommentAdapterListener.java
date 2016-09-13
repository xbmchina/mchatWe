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
 * Created by Administrator on 2016/9/9.
 */
public class MessageCommentAdapterListener extends BaseAdapter {
    private List<Comment> comments=new ArrayList<>();
    private Context context;

    public MessageCommentAdapterListener( Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return  comments.get(position);
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
            view= LayoutInflater.from(context).inflate(R.layout.comment_content_listview_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) view.findViewById(R.id.tvcomment);
            view.setTag(viewHolder);

        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();

        }
        viewHolder.textView.setText(comments.get(position).getContent());

        return view;
    }

    class ViewHolder{
        TextView textView;
    }


    public void addAll(List<Comment> data){

        comments.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        comments.clear();
        notifyDataSetChanged();
    }
}
