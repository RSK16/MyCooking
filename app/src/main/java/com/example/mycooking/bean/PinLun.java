package com.example.mycooking.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class PinLun {

    public PinLun(List<ABean> a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "PinLun{" +
                "a=" + a +
                '}';
    }

    /**
     * avatar : http://site.meishij.net/user/129/120/st30129_76767.jpg
     * says : 普通用户
     * time : 2013-10-24
     * dianzan_num : 30129
     * user_name : 圆猪猪
     */

    private List<ABean> a;

    public List<ABean> getA() {
        return a;
    }

    public void setA(List<ABean> a) {
        this.a = a;
    }

    public static class ABean {

        public ABean(String avatar, String says, long time, int dianzan_num, String user_name) {
            this.avatar = avatar;
            this.says = says;
            this.time = time;
            this.dianzan_num = dianzan_num;
            this.user_name = user_name;
        }

        private String avatar;
        private String says;
        private long time;
        private int dianzan_num;
        private String user_name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSays() {
            return says;
        }

        public void setSays(String says) {
            this.says = says;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getDianzan_num() {
            return dianzan_num;
        }

        public void setDianzan_num(int dianzan_num) {
            this.dianzan_num = dianzan_num;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        @Override
        public String toString() {
            return "ABean{" +
                    "avatar='" + avatar + '\'' +
                    ", says='" + says + '\'' +
                    ", time='" + time + '\'' +
                    ", dianzan_num=" + dianzan_num +
                    ", user_name='" + user_name + '\'' +
                    '}';
        }
    }
}
