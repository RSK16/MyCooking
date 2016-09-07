package com.example.mycooking.bean;

import java.util.List;

/**
 * Created by apple on 16/9/5.
 */
public class Cailiao {

    /**
     * title : 猪肉
     * id : 25021
     * icon : http://images.meishij.net/p/20111026/211444ad3bcec7436e047c4f50ff67a0_150x150.jpg
     * num : 500克
     * category : 畜肉
     * is_c : 1
     */

    private List<ListBean> list;
    /**
     * title : 冰糖
     * unit : 15克
     * category : 糖蜜饯
     * is_c : 1
     */

    private List<ListTBean> list_t;
    private List<?> list_f;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<ListTBean> getList_t() {
        return list_t;
    }

    public void setList_t(List<ListTBean> list_t) {
        this.list_t = list_t;
    }

    public List<?> getList_f() {
        return list_f;
    }

    public void setList_f(List<?> list_f) {
        this.list_f = list_f;
    }

    public static class ListBean {
        private String title;
        private String id;
        private String icon;
        private String num;
        private String category;
        private String is_c;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getIs_c() {
            return is_c;
        }

        public void setIs_c(String is_c) {
            this.is_c = is_c;
        }


        @Override
        public String toString() {
            return "ListBean{" +
                    "title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    ", icon='" + icon + '\'' +
                    ", num='" + num + '\'' +
                    ", category='" + category + '\'' +
                    ", is_c='" + is_c + '\'' +
                    '}';
        }
    }

    public static class ListTBean {
        private String title;
        private String unit;
        private String category;
        private String is_c;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getIs_c() {
            return is_c;
        }

        public void setIs_c(String is_c) {
            this.is_c = is_c;
        }

        @Override
        public String toString() {
            return "ListTBean{" +
                    "title='" + title + '\'' +
                    ", unit='" + unit + '\'' +
                    ", category='" + category + '\'' +
                    ", is_c='" + is_c + '\'' +
                    '}';
        }
    }
}
