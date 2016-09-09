package com.example.mycooking.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class Collect {


    public Collect() {

    }

    public Collect(ArrayList<ABean> a) {
        this.a = a;
    }

    /**
     * id : 1
     */



    private ArrayList<ABean> a;

    public ArrayList<ABean> getA() {
        return a;
    }

    public void setA(ArrayList<ABean> a) {
        this.a = a;
    }

    public static class ABean {
        private String id;

        public ABean(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
