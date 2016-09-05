package com.example.mycooking.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class Categories {

    /**
     * t : 实用分类
     * im : 2
     * d : [{"t":"快手菜","dt":10,"i":"http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_1.png"},{"t":"下饭菜","dt":10,"i":"http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_2_1.png"},{"t":"肉食","dt":10,"i":"http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_5_1.png"},{"t":"素食","dt":10,"i":"http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_4.png"},{"t":"应季时蔬","dt":10,"i":"http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_3.png"},{"t":"汤","dt":10,"i":"http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_6_1.png"}]
     */

    private List<CategoryBean> category;

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class CategoryBean {
        private String t;
        private int im;
        /**
         * t : 快手菜
         * dt : 10
         * i : http://static.meishij.net/images/get3/fenlei/fl_shiyongfenlei_1.png
         */

        private List<DBean> d;

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public int getIm() {
            return im;
        }

        public void setIm(int im) {
            this.im = im;
        }

        public List<DBean> getD() {
            return d;
        }

        public void setD(List<DBean> d) {
            this.d = d;
        }

        public static class DBean {
            private String t;
            private int dt;
            private String i;

            //小标题
            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }

            public int getDt() {
                return dt;
            }

            public void setDt(int dt) {
                this.dt = dt;
            }

            //图片网址
            public String getI() {
                return i;
            }

            public void setI(String i) {
                this.i = i;
            }
        }
    }
}
