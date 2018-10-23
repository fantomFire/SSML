package zhonghuass.ssml.mvp.model.appbean;

public class LoginBean {
    /**
     * status : 200
     * msg : 登录成功！
     * data : {"uid":"27","username":"17794334178","avatar":"","sex":"1","mobile":"17794334178","member_type":"1"}
     */

    public String status;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * uid : 27
         * username : 17794334178
         * avatar :
         * sex : 1
         * mobile : 17794334178
         * member_type : 1
         */

        public String uid;
        public String username;
        public String avatar;
        public String sex;
        public String mobile;
        public String member_type;

    }
}
