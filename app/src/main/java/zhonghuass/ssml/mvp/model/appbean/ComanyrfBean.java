package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class ComanyrfBean {


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    /**
         * count : 2
         * list : [{"p_id":"3","title":"量子大米22","description":"又在粮食中难以植入的锌才是珍品中的极品。因为人体6大酶系统少不了它做\u201c辅酶\u201d，且在人体代谢中有着更加广泛的应用。有趣的是：摆在面前的量子大米其淡雅清纯的米香味或/及量子的作用，可使人进入一种莫名其妙的良好状态","linkman":"刘美女.总经理","add_time":"2018-01-09","eid":"1","logo":"http://video.zhonghuass.cn/public/admin/images/tx.jpg","shortname":"中华盛世1"},{"p_id":"1","title":"l量子大米","description":"量子大米采用获国家金奖的\u201c国米一号\u201d种植技术。用量子对水处理，然后用水对土地处理，分解除去以往的公害物质，使土地呈现肥沃又干净的状态。量子通过各种种植环节进入到稻米中，所施加的硒、锌等微量元素与量子一道以大米为载体，作用于人体。\r\n子化的大米，对五脏六腑等躯体器官的调养和增强免疫力、修复力、自愈力等生命力具有良好的作用。它还会使体能迅速提高20%左右。其中硒、锌等微量元素对抗衰老、防治血管病、防癌抗癌和促进儿童智力发育、养护皮肤、抑制老年痴呆等多种生理现象都有相当理想的作用。现在人们把富硒米当做珍品，岂不知量子大米不仅富硒，还是富锌米，而广泛缺乏又在粮食中难以植入的锌才是珍品中的极品。因为人体6大酶系统少不了它做\u201c辅酶\u201d，且在人体代谢中有着更加广泛的应用。有趣的是：摆在面前的量子大米其淡雅清纯的米香味或/及量子的作用，可使人进入一种莫名其妙的良好状态","linkman":"刘美女.总经理","add_time":"2018-01-09","eid":"1","logo":"http://video.zhonghuass.cn/public/admin/images/tx.jpg","shortname":"中华盛世1"}]
         */

        public int count;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * p_id : 3
             * title : 量子大米22
             * description : 又在粮食中难以植入的锌才是珍品中的极品。因为人体6大酶系统少不了它做“辅酶”，且在人体代谢中有着更加广泛的应用。有趣的是：摆在面前的量子大米其淡雅清纯的米香味或/及量子的作用，可使人进入一种莫名其妙的良好状态
             * linkman : 刘美女.总经理
             * add_time : 2018-01-09
             * eid : 1
             * logo : http://video.zhonghuass.cn/public/admin/images/tx.jpg
             * shortname : 中华盛世1
             */

            public String p_id;
            public String title;
            public String description;
            public String linkman;
            public String add_time;
            public String eid;
            public String logo;
            public String shortname;
            public String getP_id() {
                return p_id;
            }

            public void setP_id(String p_id) {
                this.p_id = p_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getEid() {
                return eid;
            }

            public void setEid(String eid) {
                this.eid = eid;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getShortname() {
                return shortname;
            }

            public void setShortname(String shortname) {
                this.shortname = shortname;
            }


        }
}
