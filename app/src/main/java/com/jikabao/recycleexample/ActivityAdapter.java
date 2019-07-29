package com.jikabao.recycleexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jikabao.recycleexample.model.Post;
import com.jikabao.recycleexample.model.Tag;

import java.util.List;

import lombok.Data;

@Data
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.CustomViewHolder> {
    private static final Integer VIEW_TYPE_ITEM = 1;
    private static final Integer VIEW_TYPE_LOADING = 2;
    private Context context;
    View.OnClickListener onItemClick;

    private List<Post> dataList;

    public ActivityAdapter(List<Post> dataList) {
        this.dataList = dataList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = null;
        context = parent.getContext();
        if (viewType == VIEW_TYPE_ITEM) {
            root = LayoutInflater.from(context).inflate(R.layout.activity_list_item, parent, false);
            if (onItemClick != null) {
                root.setOnClickListener(onItemClick);
            }
            return new DataViewHolder(root);
        } else {
            root = LayoutInflater.from(context).inflate(R.layout.row_process, parent, false);
            return new ProgressViewHolder(root);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull CustomViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            if (dataList != null) {
                Post post = dataList.get(position);
                holder.title.setText(post.getTitle());
                holder.updated_at.setText(post.getUpdated_at());
                List<Tag> tags = post.getTags();
                holder.tags_container.removeAllViews();
                for (int i = 0; i < tags.size(); i++) {
                    Tag t = tags.get(i);
                    Button child = new Button(context);
                    child.setText(t.getName());
                    holder.tags_container.addView(child);
                }
            }
        }else{
            //Do whatever you want. Or nothing !!
        }
    }

    @Override
    public int getItemCount () {
        if (dataList == null) {
            return 0;
        } else {
            return dataList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) != null)
            return VIEW_TYPE_ITEM;
        else
            return VIEW_TYPE_LOADING;
    }

    class DataViewHolder extends CustomViewHolder {


        public DataViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            updated_at = itemView.findViewById(R.id.updated_at);
            tags_container = itemView.findViewById(R.id.tags);
        }

    }

    class ProgressViewHolder extends CustomViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView updated_at;
        public LinearLayout tags_container;

        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addData(List<Post> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void removeNull() {
        dataList.remove(dataList.size() - 1);
        notifyItemRemoved(dataList.size());
    }

    public void addNullData() {
        dataList.add(null);
        notifyItemInserted(dataList.size() - 1);
    }

}
