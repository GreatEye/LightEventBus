package com.appleye.lighteventbusdemo.mvp;

import android.view.View;

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
public class BasePresenter implements IPresenter{
    private IModel mModel;
    private IView mView;
    private Object mData;
    private boolean mChanged;

    public BasePresenter(IModel model) {
        mModel = model;
        mChanged = true;
    }

    public void bindView(IView view) {
        mView = view;
    }

    public IModel getModel() {
        return mModel;
    }

    public IView getView(){
        return mView;
    }

    @Override
    public View getContainerView() {
        if(mView != null) {
            return mView.getView();
        }

        return null;
    }

    @Override
    public void updateChange() {
        mChanged = true;
    }

    @Override
    public Object doRequest() {
        if(mChanged && mModel != null) {
            mChanged = false;
            mData = mModel.request();
        }

        return mData;
    }
}
