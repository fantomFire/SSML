package zhonghuass.ssml.mvp.model.appbean;

public class EPLoginBean {


    /**
     * status : 200
     * msg : 登录成功！
     * data : {"eid":"2","name":"北京盛世时代网络科技发展股份有限公司","mailbox":"123456@yy.cn","introduction":"华为是全球领先的ICT（信息与通信）基础设施和智能终端提供商，致力于把数字世界带入每个人、每个家庭、每个组织，构建万物互联的智能世界。我们在通信网络、IT、智能终端和云服务等领域为客户提供有竞争力、安全可信赖的产品、解决方案与服务，与生态伙伴开放合作，持续为客户创造价值，释放个人潜能，丰富家庭生活，激发组织创新。华为坚持围绕客户需求持续创新，加大基础研究投入，厚积薄发，推动世界进步。华为成立于1987年，是一家由员工持有全部股份的民营企业，目前有18万员工，业务遍及170多个国家和地区。","identity":"10","member_type":"0"}
     */

    public String status;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * eid : 2
         * name : 北京盛世时代网络科技发展股份有限公司
         * mailbox : 123456@yy.cn
         * introduction : 华为是全球领先的ICT（信息与通信）基础设施和智能终端提供商，致力于把数字世界带入每个人、每个家庭、每个组织，构建万物互联的智能世界。我们在通信网络、IT、智能终端和云服务等领域为客户提供有竞争力、安全可信赖的产品、解决方案与服务，与生态伙伴开放合作，持续为客户创造价值，释放个人潜能，丰富家庭生活，激发组织创新。华为坚持围绕客户需求持续创新，加大基础研究投入，厚积薄发，推动世界进步。华为成立于1987年，是一家由员工持有全部股份的民营企业，目前有18万员工，业务遍及170多个国家和地区。
         * identity : 10
         * member_type : 0
         */

        public String eid;
        public String name;
        public String mailbox;
        public String introduction;
        public String identity;
        public String member_type;
    }
}
