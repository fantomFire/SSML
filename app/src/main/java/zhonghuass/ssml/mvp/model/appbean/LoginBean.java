package zhonghuass.ssml.mvp.model.appbean;

public class LoginBean {

    /**
     * status : 200
     * msg : 登录成功！
     * data : {"member_id":"27","member_name":"17794334178","member_image":"http://video.zhonghuass.cn/public/uploadfile/default.png","login_name":"17794334178","introduction":"当前用户什么都没有留下","identity":"10","member_type":"1"}
     */

    public String status;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * member_id : 27
         * member_name : 17794334178
         * member_image : http://video.zhonghuass.cn/public/uploadfile/default.png
         * login_name : 17794334178
         * introduction : 当前用户什么都没有留下
         * identity : 10
         * member_type : 1
         */

        public String member_id;
        public String member_name;
        public String member_image;
        public String login_name;
        public String introduction;
        public String identity;
        public String member_type;
    }
}
