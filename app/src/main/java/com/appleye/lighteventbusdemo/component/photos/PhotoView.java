package com.appleye.lighteventbusdemo.component.photos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleye.eventbus.Event;
import com.appleye.eventbus.EventBusFac;
import com.appleye.eventbus.Hook;
import com.appleye.eventbus.IEventBus;
import com.appleye.eventbus.Subscriber;
import com.appleye.lighteventbusdemo.GroupConstants;
import com.appleye.lighteventbusdemo.mvp.BaseView;
import com.appleye.lighteventbusdemo.mvp.IPresenter;
import com.appleye.lighteventbusdemo.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Liaopu
 * date 2019-12-19
 */
public class PhotoView extends BaseView {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<String> mImgUrlList;

    @Hook
    private IEventBus mEventBus;

    public PhotoView(IPresenter presenter, Context context) {
        super(presenter);
        mContext = context;

        mImgUrlList = (List<String>) presenter.doRequest();

        EventBusFac.getInstance(GroupConstants.SERIES_PHOTO_UNION).register(this).hook(this);
    }

    @Override
    public View getView(){
        if(mRecyclerView == null) {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_common_item_recycler_view, null);
            mRecyclerView = contentView.findViewById(R.id.common_recycler_view);

            initRecyclerView();
        }
        return mRecyclerView;
    }

    private void initRecyclerView(){
        if(mRecyclerView != null) {
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(RecyclerView.HORIZONTAL);
            mRecyclerView.setLayoutManager(lm);
            mAdapter = new RecyclerViewAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Subscriber(eventType = GroupConstants.EVENT_SERIES_SELECTED)
    public void handleEvent(Event event) {
        String type = event.type;
        switch (type){
            case GroupConstants.EVENT_SERIES_SELECTED:{
                handleSeriesSelectEvent(event);
                break;
            }
        }
    }

    private void handleSeriesSelectEvent(Event event) {
        Map<String, Object> data = (Map<String, Object>) event.data;
        if(data != null) {
            Integer position = (Integer)data.get("position");
            if(position != null) {
                mRecyclerView.scrollToPosition(position);
            }
        }
    }

    @Override
    public void show() {
        View view = getView();
        if(view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void hide() {
        View view = getView();
        if(view != null) {
            view.setVisibility(View.GONE);
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<PhotoViewHolder>{

        @NonNull
        @Override
        public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_component_photo_item, null);
            return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
            String url = mImgUrlList.get(position);
            Glide.with(mContext).load(url).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mImgUrlList == null ? 0 : mImgUrlList.size();
        }
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_img);
        }
    }
}
