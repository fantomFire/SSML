package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class DailyBean {


    /**
     * status : 200
     * msg : success
     * data : {"theme_title":"#今日格言~妙语品鉴","ranking_list":[{"content_id":"52","content_title":"南方日报：主流媒体应加大舆论监督力度","member_id":"2","member_type":"1","member_name":"user1","member_image":"http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg"},{"content_id":"27","content_title":"高清壁纸002","member_id":"1","member_type":"0","member_name":"中华盛世1","member_image":"http://video.zhonghuass.cn/public/admin/images/tx.jpg"},{"content_id":"56","content_title":"俄开始对从美国进口部分商品加征25%至40%关税","member_id":"2","member_type":"1","member_name":"user1","member_image":"http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg"}]}
     */

    private String status;
    private String msg;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * theme_title : #今日格言~妙语品鉴
         * ranking_list : [{"content_id":"52","content_title":"南方日报：主流媒体应加大舆论监督力度","member_id":"2","member_type":"1","member_name":"user1","member_image":"http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg"},{"content_id":"27","content_title":"高清壁纸002","member_id":"1","member_type":"0","member_name":"中华盛世1","member_image":"http://video.zhonghuass.cn/public/admin/images/tx.jpg"},{"content_id":"56","content_title":"俄开始对从美国进口部分商品加征25%至40%关税","member_id":"2","member_type":"1","member_name":"user1","member_image":"http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg"}]
         */

        private String theme_title;
        private List<RankingListBean> ranking_list;

        public String getTheme_title() {
            return theme_title;
        }

        public void setTheme_title(String theme_title) {
            this.theme_title = theme_title;
        }

        public List<RankingListBean> getRanking_list() {
            return ranking_list;
        }

        public void setRanking_list(List<RankingListBean> ranking_list) {
            this.ranking_list = ranking_list;
        }

        public static class RankingListBean {
            /**
             * content_id : 52
             * content_title : 南方日报：主流媒体应加大舆论监督力度
             * member_id : 2
             * member_type : 1
             * member_name : user1
             * member_image : http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg
             */

            private String content_id;
            private String content_title;
            private String member_id;
            private String member_type;
            private String member_name;
            private String member_image;

            public String getContent_id() {
                return content_id;
            }

            public void setContent_id(String content_id) {
                this.content_id = content_id;
            }

            public String getContent_title() {
                return content_title;
            }

            public void setContent_title(String content_title) {
                this.content_title = content_title;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMember_type() {
                return member_type;
            }

            public void setMember_type(String member_type) {
                this.member_type = member_type;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMember_image() {
                return member_image;
            }

            public void setMember_image(String member_image) {
                this.member_image = member_image;
            }
        }
    }
}
