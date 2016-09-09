package com.example.mycooking.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by apple on 16/9/3.
 */
public class Recipe extends BmobObject {
    public void setThecomment(String thecomment) {
        this.thecomment = thecomment;
    }

    private Integer id;
    private String title;//----------------------------菜名
    private String onclick;
    private String fav_num;
    private String photo_num;
    private String href;//分享链接
    private String classname;//-----------------------菜类
    private String bclassname;//菜级别： 中华菜系。。
    private String fclassname;
    private String kouwei;//--------------------------口味
    private String gongyi;//----------------------------工艺
    private String make_time;//-----------------------制作时间
    private String make_time_num;
    private String make_diff;//制作水平：初级 中级。。
    private String make_pretime;//准备时间
    private String make_amount;//制作人数
    private String step;//步骤
    private String titlepic;//--------------------每道菜的图片
    private String smalltext;
    private String is_recipe;
    private String author;//用户  Json数据
    private String newsphoto;
    private String newsphoto_h;
    private String liaos;//制作材料
    private String zuofa;//做法  Json数据
    private String tips;//介绍  Json 数据
    private String yyxx;//菜所含元素和功效  Json数据
    private String scene;//场景
    private String tools;//工具
    private String rate;//打分   五个星星为满分
    private String lastdotime;
    private String kitchen_ware;
    private String is_zan; //是否点赞
    private String video;//制作视频
    private String share_num;//分享人数
    private String comment_num;//评论人数
    private String share_image_url;//分享图片的URL
    private String share_image_url_p;
    private String thecomment;

    public String getThecomment() {
        return thecomment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getFav_num() {
        return fav_num;
    }

    public void setFav_num(String fav_num) {
        this.fav_num = fav_num;
    }

    public String getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(String photo_num) {
        this.photo_num = photo_num;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getBclassname() {
        return bclassname;
    }

    public void setBclassname(String bclassname) {
        this.bclassname = bclassname;
    }

    public String getFclassname() {
        return fclassname;
    }

    public void setFclassname(String fclassname) {
        this.fclassname = fclassname;
    }

    public String getKouwei() {
        return kouwei;
    }

    public void setKouwei(String kouwei) {
        this.kouwei = kouwei;
    }

    public String getGongyi() {
        return gongyi;
    }

    public void setGongyi(String gongyi) {
        this.gongyi = gongyi;
    }

    public String getMake_time() {
        return make_time;
    }

    public void setMake_time(String make_time) {
        this.make_time = make_time;
    }

    public String getMake_time_num() {
        return make_time_num;
    }

    public void setMake_time_num(String make_time_num) {
        this.make_time_num = make_time_num;
    }

    public String getMake_diff() {
        return make_diff;
    }

    public void setMake_diff(String make_diff) {
        this.make_diff = make_diff;
    }

    public String getMake_pretime() {
        return make_pretime;
    }

    public void setMake_pretime(String make_pretime) {
        this.make_pretime = make_pretime;
    }

    public String getMake_amount() {
        return make_amount;
    }

    public void setMake_amount(String make_amount) {
        this.make_amount = make_amount;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public String getSmalltext() {
        return smalltext;
    }

    public void setSmalltext(String smalltext) {
        this.smalltext = smalltext;
    }

    public String getIs_recipe() {
        return is_recipe;
    }

    public void setIs_recipe(String is_recipe) {
        this.is_recipe = is_recipe;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewsphoto() {
        return newsphoto;
    }

    public void setNewsphoto(String newsphoto) {
        this.newsphoto = newsphoto;
    }

    public String getNewsphoto_h() {
        return newsphoto_h;
    }

    public void setNewsphoto_h(String newsphoto_h) {
        this.newsphoto_h = newsphoto_h;
    }

    public String getLiaos() {
        return liaos;
    }

    public void setLiaos(String liaos) {
        this.liaos = liaos;
    }

    public String getZuofa() {
        return zuofa;
    }

    public void setZuofa(String zuofa) {
        this.zuofa = zuofa;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getYyxx() {
        return yyxx;
    }

    public void setYyxx(String yyxx) {
        this.yyxx = yyxx;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLastdotime() {
        return lastdotime;
    }

    public void setLastdotime(String lastdotime) {
        this.lastdotime = lastdotime;
    }

    public String getKitchen_ware() {
        return kitchen_ware;
    }

    public void setKitchen_ware(String kitchen_ware) {
        this.kitchen_ware = kitchen_ware;
    }

    public String getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(String is_zan) {
        this.is_zan = is_zan;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getShare_num() {
        return share_num;
    }

    public void setShare_num(String share_num) {
        this.share_num = share_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getShare_image_url() {
        return share_image_url;
    }

    public void setShare_image_url(String share_image_url) {
        this.share_image_url = share_image_url;
    }

    public String getShare_image_url_p() {
        return share_image_url_p;
    }

    public void setShare_image_url_p(String share_image_url_p) {
        this.share_image_url_p = share_image_url_p;
    }
}
