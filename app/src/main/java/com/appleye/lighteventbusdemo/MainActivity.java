package com.appleye.lighteventbusdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appleye.eventbus.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<Action> mActionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    private void initData() {
        if(mActionList == null) {
            mActionList = new ArrayList<>();
            mActionList.add(new Action("空页面对照测试", ActionUtil.ACTION_JUMP_TO_EMPTY));
            mActionList.add(new Action("订阅/分发模型测试", ActionUtil.ACTION_JUMP_TO_SUBSCRIBER));
            mActionList.add(new Action("请求/响应模型测试", ActionUtil.ACTION_JUMP_TO_REQ_RSP));
            mActionList.add(new Action("组件联动分组测试", ActionUtil.ACTION_JUMP_TO_UNION));
        }
    }

    private void initViews() {
        if(mRecyclerView == null) {
            mRecyclerView = findViewById(R.id.recycler_view);
            LinearLayoutManager lm= new LinearLayoutManager(this);
            lm.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(lm);

            mAdapter= new RecyclerViewAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static class Action{
        String text;
        String acton;
        public Action(String text, String acton) {
            this.text = text;
            this.acton = acton;
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder>{

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_main_item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            Action action = mActionList.get(position);
            holder.mTextView.setText(action.text);
            holder.mTextView.setTag(action.acton);
            holder.mTextView.setOnClickListener(mOnItemClickListener);
        }

        @Override
        public int getItemCount() {
            return mActionList == null ? 0 : mActionList.size();
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_view);
        }
    }

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String action = (String)v.getTag();
            if(!TextUtils.isEmpty(action)) {
                ActionUtil.doAction(MainActivity.this, action);
            }
        }
    };
}
