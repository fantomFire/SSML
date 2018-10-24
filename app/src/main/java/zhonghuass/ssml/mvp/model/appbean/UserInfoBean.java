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
                '}';
    }
}
