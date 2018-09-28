package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class IniviteBean {

    /**
     * count : 3
     * list : [{"posi_id":"4","hiring":"php程序猿","company":"中华盛世集团","work_address":"大连","xueli":"不限","salary":"6k-8k","work_experience":"6k-8k","linkman":"刘歌 . 人事总监","add_time":"2018-07-27","eid":"1","logo":"http://video.zhonghuass.cn/public/admin/images/tx.jpg","shortname":"中华盛世1"},{"posi_id":"3","hiring":"php程序猿","company":"中华盛世集团","work_address":"西安","xueli":"本科","salary":"6k-8k","work_experience":"3年以上","linkman":"刘歌 . 人事总监","add_time":"2018-07-27","eid":"1","logo":"http://video.zhonghuass.cn/public/admin/images/tx.jpg","shortname":"中华盛世1"},{"posi_id":"1","hiring":"php程序猿","company":"中华盛世集团","work_address":"西安","xueli":"不限","salary":"6k-8k","work_experience":"6k-8k","linkman":"刘歌 . 人事总监","add_time":"2018-07-27","eid":"1","logo":"http://video.zhonghuass.cn/public/admin/images/tx.jpg","shortname":"中华盛世1"}]
     */
    public int count;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * posi_id : 4
         * hiring : php程序猿
         * company : 中华盛世集团
         * work_address : 大连
         * xueli : 不限
         * salary : 6k-8k
         * work_experience : 6k-8k
         * linkman : 刘歌 . 人事总监
         * add_time : 2018-07-27
         * eid : 1
         * logo : http://video.zhonghuass.cn/public/admin/images/tx.jpg
         * shortname : 中华盛世1
         */

        public String posi_id;
        public String hiring;
        public String company;
        public String work_address;
        public String xueli;
        public String salary;
        public String work_experience;
        public String linkman;
        public String add_time;
        public String eid;
        public String logo;
        public String shortname;
    }

}
