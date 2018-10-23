package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class MessageListBean {


    /**
     * message_type : register_feedback
     * status : 200
     * message : 登记成功!
     * member_name : 用户名称
     * member_image : 用户头像
     * data : [{"receiver":"接收者标识","sender":"发送者标识","add_time":"发送时间","message_id":"消息id","message":"消息内容","sender_name":"发送者名称","sender_image":"发送者头像","type":"0字符消息，1图片消息"},"..."]
     */

    private String message_type;
    private String status;
    private String message;
    private String member_name;
    private String member_image;
    private List<DataBean> data;

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * receiver : 接收者标识
         * sender : 发送者标识
         * add_time : 发送时间
         * message_id : 消息id
         * message : 消息内容
         * sender_name : 发送者名称
         * sender_image : 发送者头像
         * type : 0字符消息，1图片消息
         */

        private String receiver;
        private String sender;
        private String add_time;
        private String message_id;
        private String message;
        private String sender_name;
        private String sender_image;
        private String type;

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getSender_image() {
            return sender_image;
        }

        public void setSender_image(String sender_image) {
            this.sender_image = sender_image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
