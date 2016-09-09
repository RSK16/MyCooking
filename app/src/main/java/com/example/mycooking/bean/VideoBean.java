package com.example.mycooking.bean;

import java.util.List;

/**
 * Created by apple on 16/9/8.
 */
public class VideoBean {

    /**
     * code : 1
     * msg : 成功
     * faxian_region : 美国
     * faxian_list : [{"type":"12","title":"热门的视频菜谱",
     * "tag":"视频菜谱",
     * "video_list":{"img":"http://admin.meishi.cc/article/upload/video_img/20160517124438146346027897823.jpg"
     * ,"name":"味蕾时光","id":"10","describtion":"麻辣烫","play_times":"35258次播放",
     * "img_video":"http://admin.meishi.cc/article/upload/video_img/20160720/20160720100735_713.jpg",
     * "time":"2016-07-20更新","show_type":"1",
     * "vurl":"http://api.meishi.cc/v5/ykPlayer.php?vid=XMTY0ODkyNTg0NA==&sid=2885&udid=8ece86053ddb8a0f47c43966c0f56e856ddac4ba&user_id=6036116"}}]
     */

    private String code;
    private String msg;
    private String faxian_region;
    /**
     * type : 12
     * title : 热门的视频菜谱
     * tag : 视频菜谱
     * video_list : {"img":"http://admin.meishi.cc/article/upload/video_img/20160517124438146346027897823.jpg","name":"味蕾时光","id":"10","describtion":"麻辣烫","play_times":"35258次播放","img_video":"http://admin.meishi.cc/article/upload/video_img/20160720/20160720100735_713.jpg","time":"2016-07-20更新","show_type":"1","vurl":"http://api.meishi.cc/v5/ykPlayer.php?vid=XMTY0ODkyNTg0NA==&sid=2885&udid=8ece86053ddb8a0f47c43966c0f56e856ddac4ba&user_id=6036116"}
     */

    private List<FaxianListBean> faxian_list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFaxian_region() {
        return faxian_region;
    }

    public void setFaxian_region(String faxian_region) {
        this.faxian_region = faxian_region;
    }

    public List<FaxianListBean> getFaxian_list() {
        return faxian_list;
    }

    public void setFaxian_list(List<FaxianListBean> faxian_list) {
        this.faxian_list = faxian_list;
    }

    public static class FaxianListBean {
        private String type;
        private String title;
        private String tag;
        /**
         * img : http://admin.meishi.cc/article/upload/video_img/20160517124438146346027897823.jpg
         * name : 味蕾时光
         * id : 10
         * describtion : 麻辣烫
         * play_times : 35258次播放
         * img_video : http://admin.meishi.cc/article/upload/video_img/20160720/20160720100735_713.jpg
         * time : 2016-07-20更新
         * show_type : 1
         * vurl : http://api.meishi.cc/v5/ykPlayer.php?vid=XMTY0ODkyNTg0NA==&sid=2885&udid=8ece86053ddb8a0f47c43966c0f56e856ddac4ba&user_id=6036116
         */

        private VideoListBean video_list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public VideoListBean getVideo_list() {
            return video_list;
        }

        public void setVideo_list(VideoListBean video_list) {
            this.video_list = video_list;
        }

        public static class VideoListBean {
            private String img;
            private String name;
            private String id;
            private String describtion;
            private String play_times;
            private String img_video;
            private String time;
            private String show_type;
            private String vurl;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDescribtion() {
                return describtion;
            }

            public void setDescribtion(String describtion) {
                this.describtion = describtion;
            }

            public String getPlay_times() {
                return play_times;
            }

            public void setPlay_times(String play_times) {
                this.play_times = play_times;
            }

            public String getImg_video() {
                return img_video;
            }

            public void setImg_video(String img_video) {
                this.img_video = img_video;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getShow_type() {
                return show_type;
            }

            public void setShow_type(String show_type) {
                this.show_type = show_type;
            }

            public String getVurl() {
                return vurl;
            }

            public void setVurl(String vurl) {
                this.vurl = vurl;
            }
        }
    }
}
