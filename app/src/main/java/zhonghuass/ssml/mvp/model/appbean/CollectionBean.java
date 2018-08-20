package zhonghuass.ssml.mvp.model.appbean;

public class CollectionBean {
    private String content_id;
    private String content_type;
    private String content_cover;
    private String content_title; //"内容标题"
    private String amount_of_praise;//"内容点赞数"
    private String member_id;
    private String member_type;
    private String theme_title = ""; //"每日一语标题"
    private String add_time;
    private String amount_of_reading; //"内容阅读量"
    private String amount_of_comment;//"内容评论量"
    private String amount_of_forward;//"内容转发量"
    private boolean praise_tag; //"用户是否点赞"
    private String member_name;
    private boolean identity; //"是否为会员"
    private String cover_width;
    private String cover_height;
    private String member_image;

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_cover(String content_cover) {
        this.content_cover = content_cover;
    }

    public String getContent_cover() {
        return content_cover;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setAmount_of_praise(String amount_of_praise) {
        this.amount_of_praise = amount_of_praise;
    }

    public String getAmount_of_praise() {
        return amount_of_praise;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setTheme_title(String theme_title) {
        this.theme_title = theme_title;
    }

    public String getTheme_title() {
        return theme_title;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAmount_of_reading(String amount_of_reading) {
        this.amount_of_reading = amount_of_reading;
    }

    public String getAmount_of_reading() {
        return amount_of_reading;
    }

    public void setAmount_of_comment(String amount_of_comment) {
        this.amount_of_comment = amount_of_comment;
    }

    public String getAmount_of_comment() {
        return amount_of_comment;
    }

    public void setAmount_of_forward(String amount_of_forward) {
        this.amount_of_forward = amount_of_forward;
    }

    public String getAmount_of_forward() {
        return amount_of_forward;
    }

    public void setPraise_tag(boolean praise_tag) {
        this.praise_tag = praise_tag;
    }

    public boolean getPraise_tag() {
        return praise_tag;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public boolean getIdentity() {
        return identity;
    }

    public void setCover_width(String cover_width) {
        this.cover_width = cover_width;
    }

    public String getCover_width() {
        return cover_width;
    }

    public void setCover_height(String cover_height) {
        this.cover_height = cover_height;
    }

    public String getCover_height() {
        return cover_height;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    public String getMember_image() {
        return member_image;
    }
}
