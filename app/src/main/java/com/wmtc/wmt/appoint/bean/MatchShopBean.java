package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/17.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MatchShopBean extends BaseBean {

    /**
     * data : {"total":7,"size":3,"current":1,"records":[{"shopId":"322384919625990144","shpoName":"温蔓CBD111","shopAddress":"龙城路31号卓越世纪中心4号楼11层1101& #40;CBD万达三号门& #41;","shopPic":"shopbanner/f2250790afe85fe9e9a2437dd52758e4.png"},{"shopId":"308624582166708224","shpoName":"刘杰game好的4r","shopAddress":"敦化路街道连云港路17号青房·财富地带","shopPic":"shopbanner/fa174f57b4a44a6cd7fb7b9fd13080fa.png"},{"shopId":"319164368807788544","shpoName":"旗舰店","shopAddress":"敦化路街道徐州路187号卓越·大融城"}],"pages":3}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * total : 7
         * size : 3
         * current : 1
         * records : [{"shopId":"322384919625990144","shpoName":"温蔓CBD111","shopAddress":"龙城路31号卓越世纪中心4号楼11层1101& #40;CBD万达三号门& #41;","shopPic":"shopbanner/f2250790afe85fe9e9a2437dd52758e4.png"},{"shopId":"308624582166708224","shpoName":"刘杰game好的4r","shopAddress":"敦化路街道连云港路17号青房·财富地带","shopPic":"shopbanner/fa174f57b4a44a6cd7fb7b9fd13080fa.png"},{"shopId":"319164368807788544","shpoName":"旗舰店","shopAddress":"敦化路街道徐州路187号卓越·大融城"}]
         * pages : 3
         */

        public int total;
        public int size;
        public int current;
        public int pages;
        public List<RecordsBean> records;

        public static class RecordsBean {
            /**
             * shopId : 322384919625990144
             * shpoName : 温蔓CBD111
             * shopAddress : 龙城路31号卓越世纪中心4号楼11层1101& #40;CBD万达三号门& #41;
             * shopPic : shopbanner/f2250790afe85fe9e9a2437dd52758e4.png
             */

            public String shopId;
            public String shopName;
            public String shopAddress;
            public String shopPic;
        }
    }
}
