package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class ShareMeBean {

        /**
         * record_type : 2
         * member_id : 0
         * member_type : 0
         * add_time : 2018-08-06
         * member_name : 匿名用户
         * member_image : http://video.zhonghuass.cn/public/admin/images/tx.jpg
         */

        private String record_type;
        private String member_id;
        private String member_type;
        private String add_time;
        private String member_name;
        private String member_image;

        public String getRecord_type() {
            return record_type;
        }

        public void setRecord_type(String record_type) {
            this.record_type = record_type;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
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
