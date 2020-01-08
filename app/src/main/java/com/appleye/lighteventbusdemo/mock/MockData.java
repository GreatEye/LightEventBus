package com.appleye.lighteventbusdemo.mock;

import com.appleye.lighteventbusdemo.component.photos.PhotoComponent;
import com.appleye.lighteventbusdemo.component.series.SeriesComponent;

import java.util.ArrayList;
import java.util.List;

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
 * date 2019-12-22
 */
public class MockData {
    public static List<String> getSeriesList() {
        List<String> seriesList = new ArrayList<String>();
        seriesList.add("First");
        seriesList.add("Second");
        seriesList.add("Third");
        seriesList.add("Fourth");
        seriesList.add("Fifth");
        seriesList.add("Sixth");
        seriesList.add("Seventh");
        seriesList.add("Eighth");
        seriesList.add("Ninth");
        seriesList.add("Tenth");
        seriesList.add("Eleventh");
        return seriesList;
    }

    public static List<String> getPhotoList() {
        List<String> photos = new ArrayList<String>();
        photos.add("http://n.sinaimg.cn/ent/transform/672/w630h842/20181231/5NKU-hqwsysz7826307.jpg");
        photos.add("http://c3.haibao.cn/img/600_0_100_0/1528211850.3997/7670cb35fe72083b5afa269be50c8ba3.jpg");
        photos.add("http://img31.mtime.cn/ph/2014/05/20/101300.56613054_290X440X4.jpg");
        photos.add("http://photocdn.sohu.com/20161019/Img470621553.jpg");
        photos.add("http://gb.cri.cn/mmsource/images/2006/03/15/em060313051.jpg");
        photos.add("http://pic3.nipic.com/20090605/2270026_121039002_2.jpg");
        photos.add("http://photocdn.sohu.com/20140217/Img395103798.jpg");
        photos.add("http://5b0988e595225.cdn.sohucs.com/images/20180910/89b2b1f71ee64753a3cfc5327fcecc2b.jpeg");
        photos.add("http://n.sinaimg.cn/ent/transform/20160712/FN_x-fxtwiht3591270.jpg");
        photos.add("http://pic11.nipic.com/20101111/4835309_105059091641_2.jpg");
        photos.add("https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=985b60729416fdfacc61cebcd5e6e731/faedab64034f78f0c88201aa70310a55b2191cd9.jpg");
        return photos;
    }

    public static Class[] getComponents() {
        return new Class[]{PhotoComponent.class, SeriesComponent.class};
    }
}
