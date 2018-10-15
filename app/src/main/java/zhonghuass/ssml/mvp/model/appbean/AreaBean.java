package zhonghuass.ssml.mvp.model.appbean;

import java.util.List;

public class AreaBean {

        private String area_id;
        private String area_name;
        private String area_parent_id;
        private List<LevelBeanX> level;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getArea_parent_id() {
            return area_parent_id;
        }

        public void setArea_parent_id(String area_parent_id) {
            this.area_parent_id = area_parent_id;
        }

        public List<LevelBeanX> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBeanX> level) {
            this.level = level;
        }

        public static class LevelBeanX {
            /**
             * area_id : 36
             * area_name : 北京市
             * area_parent_id : 1
             * level : [{"area_id":"37","area_name":"东城区","area_parent_id":"36"},{"area_id":"38","area_name":"西城区","area_parent_id":"36"},{"area_id":"41","area_name":"朝阳区","area_parent_id":"36"},{"area_id":"42","area_name":"丰台区","area_parent_id":"36"},{"area_id":"43","area_name":"石景山区","area_parent_id":"36"},{"area_id":"44","area_name":"海淀区","area_parent_id":"36"},{"area_id":"45","area_name":"门头沟区","area_parent_id":"36"},{"area_id":"46","area_name":"房山区","area_parent_id":"36"},{"area_id":"47","area_name":"通州区","area_parent_id":"36"},{"area_id":"48","area_name":"顺义区","area_parent_id":"36"},{"area_id":"49","area_name":"昌平区","area_parent_id":"36"},{"area_id":"50","area_name":"大兴区","area_parent_id":"36"},{"area_id":"51","area_name":"怀柔区","area_parent_id":"36"},{"area_id":"52","area_name":"平谷区","area_parent_id":"36"},{"area_id":"53","area_name":"密云县","area_parent_id":"36"},{"area_id":"54","area_name":"延庆县","area_parent_id":"36"},{"area_id":"566","area_name":"其他","area_parent_id":"36"}]
             */

            private String area_id;
            private String area_name;
            private String area_parent_id;
            private List<LevelBean> level;

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getArea_parent_id() {
                return area_parent_id;
            }

            public void setArea_parent_id(String area_parent_id) {
                this.area_parent_id = area_parent_id;
            }

            public List<LevelBean> getLevel() {
                return level;
            }

            public void setLevel(List<LevelBean> level) {
                this.level = level;
            }

            public static class LevelBean {
                /**
                 * area_id : 37
                 * area_name : 东城区
                 * area_parent_id : 36
                 */

                private String area_id;
                private String area_name;
                private String area_parent_id;

                public String getArea_id() {
                    return area_id;
                }

                public void setArea_id(String area_id) {
                    this.area_id = area_id;
                }

                public String getArea_name() {
                    return area_name;
                }

                public void setArea_name(String area_name) {
                    this.area_name = area_name;
                }

                public String getArea_parent_id() {
                    return area_parent_id;
                }

                public void setArea_parent_id(String area_parent_id) {
                    this.area_parent_id = area_parent_id;
                }

                @Override
                public String toString() {
                    return "LevelBean{" +
                            "area_id='" + area_id + '\'' +
                            ", area_name='" + area_name + '\'' +
                            ", area_parent_id='" + area_parent_id + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "LevelBeanX{" +
                        "area_id='" + area_id + '\'' +
                        ", area_name='" + area_name + '\'' +
                        ", area_parent_id='" + area_parent_id + '\'' +
                        ", level=" + level +
                        '}';
            }
        }

    @Override
    public String toString() {
        return "AreaBean{" +
                "area_id='" + area_id + '\'' +
                ", area_name='" + area_name + '\'' +
                ", area_parent_id='" + area_parent_id + '\'' +
                ", level=" + level +
                '}';
    }
}
