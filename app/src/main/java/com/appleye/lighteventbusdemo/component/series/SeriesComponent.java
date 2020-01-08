package com.appleye.lighteventbusdemo.component.series;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.appleye.lighteventbusdemo.component.IComponent;

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
 * date 2019-12-18
 */
public class SeriesComponent implements IComponent {
    private SeriesPresenter mPresenter;
    public SeriesComponent(Context context) {
        mPresenter = new SeriesPresenter(context);
    }

    @Override
    public View getContainer() {
        if(mPresenter != null) {
            return mPresenter.getContainerView();
        }
        return null;
    }

    public int getType() {
        return TYPE_SERIES;
    }

    @Override
    public void notifyData(){
        if(mPresenter != null) {
            mPresenter.doRequest();
        }
    }

    @Override
    public void attachView(ViewGroup rootView){
        View containerView = getContainer();
        if(containerView == null) {
            rootView.removeAllViews();
            return;
        }
        if(rootView.indexOfChild(containerView) < 0) {
            rootView.removeAllViews();
            rootView.addView(containerView);
        }
    }

    @Override
    public void destroy() {
    }
}
