package com.example.qq.bean;

import java.util.List;

public class DongTaiBean {
    /**
     * err : 200
     * errmsg :
     * data : [{"id":25,"uid":"d5eb8c5a-a078-4f6f-97aa-c2fa7e04ccbc","address":null,"mobile":null,"time":2147483647,"content":"5月11号星期一","resources":"https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=ui&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=3634522755,1673602565&os=110839237,409661125&simid=4122119777,702300698&pn=4&rn=1&di=40810&ln=1767&fr=&fmq=1589160685249_R&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fabc01ea19842ac864fdc5d4104c273ba15f5ff9a6848f-O4vwEt_fw658&rpstart=0&rpnum=0&adpicid=0&force=undefined$https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2257753365,2705906619&fm=26&gp=0.jpg","username":"yun6","avater":"http://yun918.cn/study/public/uploadfiles/yun6/Screenshot_2020-03-04-12-58-43.png","discussNum":0,"discuss":[]},{"id":24,"uid":"7cd86e79-de63-47ee-aa06-c1ec54206f84","address":null,"mobile":null,"time":2147483647,"content":"android","resources":"http://yun918.cn/study/public/uploadfiles/yanjuncai chat/magazine-unlock-05-2.3.1047-_EEC20D3BB2ADE4B8898208FCAE1CBC94.jpg","username":"yanjuncai chat","avater":"http://yun918.cn/study/public/uploadfiles/yanjuncai chat/IMG_CROP_20200512_22151576.jpeg","discussNum":0,"discuss":[]},{"id":23,"uid":"7cd86e79-de63-47ee-aa06-c1ec54206f84","address":null,"mobile":null,"time":2147483647,"content":"android","resources":"http://yun918.cn/study/public/uploadfiles/yanjuncai chat/magazine-unlock-05-2.3.1047-_EEC20D3BB2ADE4B8898208FCAE1CBC94.jpg","username":"yanjuncai chat","avater":"http://yun918.cn/study/public/uploadfiles/yanjuncai chat/IMG_CROP_20200512_22151576.jpeg","discussNum":0,"discuss":[]},{"id":22,"uid":null,"address":null,"mobile":null,"time":2147483647,"content":"ggg111","resources":"","username":null,"avater":null,"discussNum":0,"discuss":[]},{"id":21,"uid":null,"address":null,"mobile":null,"time":2147483647,"content":"额","resources":"","username":null,"avater":null,"discussNum":0,"discuss":[]}]
     */

    private int err;
    private String errmsg;
    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 25
         * uid : d5eb8c5a-a078-4f6f-97aa-c2fa7e04ccbc
         * address : null
         * mobile : null
         * time : 2147483647
         * content : 5月11号星期一
         * resources : https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=ui&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=3634522755,1673602565&os=110839237,409661125&simid=4122119777,702300698&pn=4&rn=1&di=40810&ln=1767&fr=&fmq=1589160685249_R&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fabc01ea19842ac864fdc5d4104c273ba15f5ff9a6848f-O4vwEt_fw658&rpstart=0&rpnum=0&adpicid=0&force=undefined$https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2257753365,2705906619&fm=26&gp=0.jpg
         * username : yun6
         * avater : http://yun918.cn/study/public/uploadfiles/yun6/Screenshot_2020-03-04-12-58-43.png
         * discussNum : 0
         * discuss : []
         */

        private int id;
        private String uid;
        private Object address;
        private Object mobile;
        private int time;
        private String content;
        private String resources;
        private String username;
        private String avater;
        private int discussNum;
        private List<DiscussBean> discuss;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getResources() {
            return resources;
        }

        public void setResources(String resources) {
            this.resources = resources;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public int getDiscussNum() {
            return discussNum;
        }

        public void setDiscussNum(int discussNum) {
            this.discussNum = discussNum;
        }

        public List<DiscussBean> getDiscuss() {
            return discuss;
        }

        public void setDiscuss(List<DiscussBean> discuss) {
            this.discuss = discuss;
        }

        public static class DiscussBean {
            /**
             * discussuid : 27b6391f-bd12-4a15-91ef-f3a0bc6b5277
             * discussusername : lizhijun321321
             * targetuid :
             * targetusername :
             * conent : 服务遗留
             */

            private String discussuid;
            private String discussusername;
            private String targetuid;
            private String targetusername;
            private String content;

            public String getDiscussuid() {
                return discussuid;
            }

            public void setDiscussuid(String discussuid) {
                this.discussuid = discussuid;
            }

            public String getDiscussusername() {
                return discussusername;
            }

            public void setDiscussusername(String discussusername) {
                this.discussusername = discussusername;
            }

            public String getTargetuid() {
                return targetuid;
            }

            public void setTargetuid(String targetuid) {
                this.targetuid = targetuid;
            }

            public String getTargetusername() {
                return targetusername;
            }

            public void setTargetusername(String targetusername) {
                this.targetusername = targetusername;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String conent) {
                this.content = content;
            }
        }
    }
}
