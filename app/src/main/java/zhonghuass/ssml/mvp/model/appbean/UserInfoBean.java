package zhonghuass.ssml.mvp.model.appbean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    public String uid;
    public String avatar;
    public String introduction;
    public String nickname;
    public String provincie;
    public String city;
    public String area;

    public String member_id;//" : "用户id",
    public String member_type;//" : "用户类型",
    public String member_name;//" : "用户名称",
    public String member_image;//" : "用户头像",
    public String amount_of_vermicelli;//" : "粉丝数量",
    public String amount_of_concern;//" : "关注数量",
    public String amount_of_praise;//" : "点赞量"
    public String content_video_num;//": "视频数量",
    public String content_image_text_num;//": "图文数量"


    @Override
    public String toString() {
        return "UserInfoBean{" +
                "uid='" + uid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", introduction='" + introduction + '\'' +
                ", nickname='" + nickname + '\'' +
                ", provincie='" + provincie + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", member_id='" + member_id + '\'' +
                ", member_type='" + member_type + '\'' +
                ", member_name='" + member_name + '\'' +
                ", amount_of_vermicelli='" + amount_of_vermicelli + '\'' +
                ", amount_of_concern='" + amount_of_concern + '\'' +
                ", amount_of_praise='" + amount_of_praise + '\'' +
                '}';
    }
}
