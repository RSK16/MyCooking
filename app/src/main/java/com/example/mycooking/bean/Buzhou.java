package com.example.mycooking.bean;

import java.util.List;

/**
 * Created by apple on 16/9/6.
 */
public class Buzhou {


    /**
     * d : 将粉丝提前浸泡三四个小时至心透
     * dt : 0
     * st : 1
     * step : 1
     * showNum : 1
     * i : 0
     */

    private List<ZuofaBean> zuofa;

    public List<ZuofaBean> getZuofa() {
        return zuofa;
    }

    public void setZuofa(List<ZuofaBean> zuofa) {
        this.zuofa = zuofa;
    }

    public static class ZuofaBean {
        private String d;
        private String dt;
        private String st;
        private String step;
        private String showNum;
        private String i;

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public String getSt() {
            return st;
        }

        public void setSt(String st) {
            this.st = st;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getShowNum() {
            return showNum;
        }

        public void setShowNum(String showNum) {
            this.showNum = showNum;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }
    }
}
