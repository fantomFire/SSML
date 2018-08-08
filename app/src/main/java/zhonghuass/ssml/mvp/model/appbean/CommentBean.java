package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class CommentBean {

        /**
         * comment_id : 38
         * add_time : 2018-08-03
         * comment_detail : zzz
         * member_name : user6
         * member_image : http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg
         */

        private String comment_id;
        private String add_time;
        private String comment_detail;
        private String member_name;
        private String member_image;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getComment_detail() {
            return comment_detail;
        }

        public void setComment_detail(String comment_detail) {
            this.comment_detail = comment_detail;
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
