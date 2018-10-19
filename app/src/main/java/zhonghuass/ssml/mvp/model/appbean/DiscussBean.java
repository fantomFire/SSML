package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class DiscussBean {


    private String comment_id;
    private String comment_detail;
    private String add_time;
    private String amount_of_recoverable;
    private boolean praise_tag;
    private String member_name;
    private String member_image;
    private List<ForwardBeanX> forward;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_detail() {
        return comment_detail;
    }

    public void setComment_detail(String comment_detail) {
        this.comment_detail = comment_detail;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAmount_of_recoverable() {
        return amount_of_recoverable;
    }

    public void setAmount_of_recoverable(String amount_of_recoverable) {
        this.amount_of_recoverable = amount_of_recoverable;
    }

    public boolean isPraise_tag() {
        return praise_tag;
    }

    public void setPraise_tag(boolean praise_tag) {
        this.praise_tag = praise_tag;
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

    public List<ForwardBeanX> getForward() {
        return forward;
    }

    public void setForward(List<ForwardBeanX> forward) {
        this.forward = forward;
    }

    public static class ForwardBeanX {
        /**
         * comment_id : 23
         * comment_detail : aaa
         * add_time : 2018-08-02 11:04
         * amount_of_recoverable : 4
         * praise_tag : true
         * forward : [{"comment_id":"24","comment_detail":"bbb","add_time":"2018-08-02 11:04","amount_of_recoverable":"0","praise_tag":false,"forward":[],"member_name":"user","member_image":"http://video.zhonghuass.cn"},{"comment_id":"25","comment_detail":"bbb","add_time":"2018-08-02 11:05","amount_of_recoverable":"0","praise_tag":false,"forward":[],"member_name":"user","member_image":"http://video.zhonghuass.cn"},{"comment_id":"26","comment_detail":"bbb","add_time":"2018-08-02 11:05","amount_of_recoverable":"0","praise_tag":false,"forward":[],"member_name":"user","member_image":"http://video.zhonghuass.cn"},{"comment_id":"27","comment_detail":"bbb","add_time":"2018-08-02 11:06","amount_of_recoverable":"1","praise_tag":false,"forward":[{"comment_id":"56","comment_detail":"asgsgad","add_time":"2018-09-12 14:46","amount_of_recoverable":"0","praise_tag":false,"forward":[],"member_name":"user3","member_image":"http://video.zhonghuass.cn/public/uploadfile/tmp/tx3.png"}],"member_name":"user","member_image":"http://video.zhonghuass.cn"}]
         * member_name : user1
         * member_image : http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg
         */

        private String comment_id;
        private String comment_detail;
        private String add_time;
        private String amount_of_recoverable;
        private boolean praise_tag;
        private String member_name;
        private String member_image;
        private List<ForwardBean> forward;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_detail() {
            return comment_detail;
        }

        public void setComment_detail(String comment_detail) {
            this.comment_detail = comment_detail;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getAmount_of_recoverable() {
            return amount_of_recoverable;
        }

        public void setAmount_of_recoverable(String amount_of_recoverable) {
            this.amount_of_recoverable = amount_of_recoverable;
        }

        public boolean isPraise_tag() {
            return praise_tag;
        }

        public void setPraise_tag(boolean praise_tag) {
            this.praise_tag = praise_tag;
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

        public List<ForwardBean> getForward() {
            return forward;
        }

        public void setForward(List<ForwardBean> forward) {
            this.forward = forward;
        }

        public static class ForwardBean {
            /**
             * comment_id : 24
             * comment_detail : bbb
             * add_time : 2018-08-02 11:04
             * amount_of_recoverable : 0
             * praise_tag : false
             * forward : []
             * member_name : user
             * member_image : http://video.zhonghuass.cn
             */

            private String comment_id;
            private String comment_detail;
            private String add_time;
            private String amount_of_recoverable;
            private boolean praise_tag;
            private String member_name;
            private String member_image;
            private List<?> forward;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public String getComment_detail() {
                return comment_detail;
            }

            public void setComment_detail(String comment_detail) {
                this.comment_detail = comment_detail;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getAmount_of_recoverable() {
                return amount_of_recoverable;
            }

            public void setAmount_of_recoverable(String amount_of_recoverable) {
                this.amount_of_recoverable = amount_of_recoverable;
            }

            public boolean isPraise_tag() {
                return praise_tag;
            }

            public void setPraise_tag(boolean praise_tag) {
                this.praise_tag = praise_tag;
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

            public List<?> getForward() {
                return forward;
            }

            public void setForward(List<?> forward) {
                this.forward = forward;
            }
        }
    }
}


