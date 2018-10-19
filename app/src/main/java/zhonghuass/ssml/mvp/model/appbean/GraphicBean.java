package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class GraphicBean {


    /**
     * status : 200
     * msg : success
     * data : {"content_id":"71","content_type":"0","content_title":"城围联八强战前瞻：南京将战大阪 谁能笑到最后","member_id":"1","member_type":"0","amount_of_reading":"11","amount_of_comment":"1","amount_of_collection":"0","amount_of_forward":"0","amount_of_praise":"0","add_time":"2018-08-14","content_detail":"联八强战前瞻：南京将战大阪 谁能笑到最后\r\n\r\n城围联16强城围联16强\r\n　　8月11日，2018赛季城市围棋联赛的1/8决赛在南宁市会展中心落下帷幕，常规赛32支队伍，历经三个月的鏖战厮杀，在烽火硝烟中走出的，只剩下了最后的八强。城市围棋联赛，也慢慢走到了最激动人心的时分。\r\n\r\n　　9月1日，城围联八强战将鸣锣开战，代表着城围联2018赛季最高水准的八支队伍，将为半决赛的名额展开激烈的争夺。此刻，且借着1/8决赛尚未散尽的硝烟，且借着在常规赛闭幕战与季后赛1/8决赛看过的紧张与悲喜，且借着围棋大会的热烈与欢乐，让我们对城围联的最终八强战来做一番巡礼预测，为九月秋高里的无限激情，提前添上一把柴火。\r\n\r\n　　且尽杯中酒，点将夜谈兵。\r\n\r\n　　南京苏中建设VS大阪创新生物\r\n\r\n　　作为一支完全由日本棋手组成的队伍，大阪创新生物，在城围联的诸多俱乐部中，是一个\u201c异数\u201d。相较于其他几支海外俱乐部或多或少还有中国棋手参与，大阪则是彻头彻尾的\u201c海外军团\u201d，在常规赛闭幕战时，这支队伍的研究席极好分辨\u2014\u2014大概是因为语言不通，大阪队的全体队员很少四处走动，也很少见到有人去找他们采访，签名。满满一桌人，围坐在他们的主教练，日本名将","content_position":"陕西 西安","praise_tag":false,"collection_tag":false,"theme_title":"","member_name":"中华盛世1","member_image":"http://video.zhonghuass.cn/public/admin/images/tx.jpg","content_images":["http://video.zhonghuass.cn/public/uploadfile/tmp/image15.jpg"]}
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
         * content_id : 71
         * content_type : 0
         * content_title : 城围联八强战前瞻：南京将战大阪 谁能笑到最后
         * member_id : 1
         * member_type : 0
         * amount_of_reading : 11
         * amount_of_comment : 1
         * amount_of_collection : 0
         * amount_of_forward : 0
         * amount_of_praise : 0
         * add_time : 2018-08-14
         * content_detail : 联八强战前瞻：南京将战大阪 谁能笑到最后

         城围联16强城围联16强
         　　8月11日，2018赛季城市围棋联赛的1/8决赛在南宁市会展中心落下帷幕，常规赛32支队伍，历经三个月的鏖战厮杀，在烽火硝烟中走出的，只剩下了最后的八强。城市围棋联赛，也慢慢走到了最激动人心的时分。

         　　9月1日，城围联八强战将鸣锣开战，代表着城围联2018赛季最高水准的八支队伍，将为半决赛的名额展开激烈的争夺。此刻，且借着1/8决赛尚未散尽的硝烟，且借着在常规赛闭幕战与季后赛1/8决赛看过的紧张与悲喜，且借着围棋大会的热烈与欢乐，让我们对城围联的最终八强战来做一番巡礼预测，为九月秋高里的无限激情，提前添上一把柴火。

         　　且尽杯中酒，点将夜谈兵。

         　　南京苏中建设VS大阪创新生物

         　　作为一支完全由日本棋手组成的队伍，大阪创新生物，在城围联的诸多俱乐部中，是一个“异数”。相较于其他几支海外俱乐部或多或少还有中国棋手参与，大阪则是彻头彻尾的“海外军团”，在常规赛闭幕战时，这支队伍的研究席极好分辨——大概是因为语言不通，大阪队的全体队员很少四处走动，也很少见到有人去找他们采访，签名。满满一桌人，围坐在他们的主教练，日本名将
         * content_position : 陕西 西安
         * praise_tag : false
         * collection_tag : false
         * theme_title :
         * member_name : 中华盛世1
         * member_image : http://video.zhonghuass.cn/public/admin/images/tx.jpg
         * content_images : ["http://video.zhonghuass.cn/public/uploadfile/tmp/image15.jpg"]
         */

        private String content_id;
        private String content_type;
        private String content_title;
        private String member_id;
        private String member_type;
        private String amount_of_reading;
        private String amount_of_comment;
        private String amount_of_collection;
        private String amount_of_forward;
        private String amount_of_praise;
        private String add_time;
        private String content_detail;
        private String content_position;
        private boolean praise_tag;
        private boolean collection_tag;
        private String theme_title;
        private String member_name;
        private String member_image;

        public String getContent_cover() {
            return content_cover;
        }

        public void setContent_cover(String content_cover) {
            this.content_cover = content_cover;
        }

        private String content_cover;
        private List<String> content_images;

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
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

        public String getAmount_of_reading() {
            return amount_of_reading;
        }

        public void setAmount_of_reading(String amount_of_reading) {
            this.amount_of_reading = amount_of_reading;
        }

        public String getAmount_of_comment() {
            return amount_of_comment;
        }

        public void setAmount_of_comment(String amount_of_comment) {
            this.amount_of_comment = amount_of_comment;
        }

        public String getAmount_of_collection() {
            return amount_of_collection;
        }

        public void setAmount_of_collection(String amount_of_collection) {
            this.amount_of_collection = amount_of_collection;
        }

        public String getAmount_of_forward() {
            return amount_of_forward;
        }

        public void setAmount_of_forward(String amount_of_forward) {
            this.amount_of_forward = amount_of_forward;
        }

        public String getAmount_of_praise() {
            return amount_of_praise;
        }

        public void setAmount_of_praise(String amount_of_praise) {
            this.amount_of_praise = amount_of_praise;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getContent_detail() {
            return content_detail;
        }

        public void setContent_detail(String content_detail) {
            this.content_detail = content_detail;
        }

        public String getContent_position() {
            return content_position;
        }

        public void setContent_position(String content_position) {
            this.content_position = content_position;
        }

        public boolean isPraise_tag() {
            return praise_tag;
        }

        public void setPraise_tag(boolean praise_tag) {
            this.praise_tag = praise_tag;
        }

        public boolean isCollection_tag() {
            return collection_tag;
        }

        public void setCollection_tag(boolean collection_tag) {
            this.collection_tag = collection_tag;
        }

        public String getTheme_title() {
            return theme_title;
        }

        public void setTheme_title(String theme_title) {
            this.theme_title = theme_title;
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

        public List<String> getContent_images() {
            return content_images;
        }

        public void setContent_images(List<String> content_images) {
            this.content_images = content_images;
        }
    }
}

