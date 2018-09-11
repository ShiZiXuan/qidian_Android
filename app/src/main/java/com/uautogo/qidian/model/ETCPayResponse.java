package com.uautogo.qidian.model;

import java.util.List;

/**
 * Created by linjing on 2017/11/13.
 */

public class ETCPayResponse {


    /**
     * code : 0
     * data : {"amount":13700,"amountRefunded":0,"amountSettle":13700,"app":"app_8uLWH8jPu1CCO0OG","body":"启点 行车记录仪（带内存卡）","channel":"wx","clientIp":"127.0.0.1","created":1504386087,"credential":{"object":"credential","wx":{"appId":"wxxbvrlsc4azfhlafj","partnerId":"1217951601","prepayId":"1101000000170903wnfdqtxl4ixl5iz9","nonceStr":"3e7e0ba5511f5a95909599a03e4c2e89","timeStamp":"1504386087","packageValue":"Sign=WXPay","sign":"f91d295490ff5e62521e116778e55c0e703abf46"}},"currency":"cny","extra":{},"id":"ch_ezzLC84iTOKOfTybr9CG4yfL","livemode":false,"metadata":{},"object":"charge","orderNo":"10000112","paid":false,"refunded":false,"refunds":{"data":[],"hasMore":false,"object":"list","uRL":"/v1/charges/ch_ezzLC84iTOKOfTybr9CG4yfL/refunds"},"subject":"ETC","timeExpire":1504393287}
     * msg : success
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * amount : 13700
         * amountRefunded : 0
         * amountSettle : 13700
         * app : app_8uLWH8jPu1CCO0OG
         * body : 启点 行车记录仪（带内存卡）
         * channel : wx
         * clientIp : 127.0.0.1
         * created : 1504386087
         * credential : {"object":"credential","wx":{"appId":"wxxbvrlsc4azfhlafj","partnerId":"1217951601","prepayId":"1101000000170903wnfdqtxl4ixl5iz9","nonceStr":"3e7e0ba5511f5a95909599a03e4c2e89","timeStamp":"1504386087","packageValue":"Sign=WXPay","sign":"f91d295490ff5e62521e116778e55c0e703abf46"}}
         * currency : cny
         * extra : {}
         * id : ch_ezzLC84iTOKOfTybr9CG4yfL
         * livemode : false
         * metadata : {}
         * object : charge
         * orderNo : 10000112
         * paid : false
         * refunded : false
         * refunds : {"data":[],"hasMore":false,"object":"list","uRL":"/v1/charges/ch_ezzLC84iTOKOfTybr9CG4yfL/refunds"}
         * subject : ETC
         * timeExpire : 1504393287
         */

        private int amount;
        private int amountRefunded;
        private int amountSettle;
        private String app;
        private String body;
        private String channel;
        private String clientIp;
        private int created;
        private CredentialBean credential;
        private String currency;
        private ExtraBean extra;
        private String id;
        private boolean livemode;
        private MetadataBean metadata;
        private String object;
        private String orderNo;
        private boolean paid;
        private boolean refunded;
        private RefundsBean refunds;
        private String subject;
        private int timeExpire;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmountRefunded() {
            return amountRefunded;
        }

        public void setAmountRefunded(int amountRefunded) {
            this.amountRefunded = amountRefunded;
        }

        public int getAmountSettle() {
            return amountSettle;
        }

        public void setAmountSettle(int amountSettle) {
            this.amountSettle = amountSettle;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public CredentialBean getCredential() {
            return credential;
        }

        public void setCredential(CredentialBean credential) {
            this.credential = credential;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isLivemode() {
            return livemode;
        }

        public void setLivemode(boolean livemode) {
            this.livemode = livemode;
        }

        public MetadataBean getMetadata() {
            return metadata;
        }

        public void setMetadata(MetadataBean metadata) {
            this.metadata = metadata;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public boolean isPaid() {
            return paid;
        }

        public void setPaid(boolean paid) {
            this.paid = paid;
        }

        public boolean isRefunded() {
            return refunded;
        }

        public void setRefunded(boolean refunded) {
            this.refunded = refunded;
        }

        public RefundsBean getRefunds() {
            return refunds;
        }

        public void setRefunds(RefundsBean refunds) {
            this.refunds = refunds;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getTimeExpire() {
            return timeExpire;
        }

        public void setTimeExpire(int timeExpire) {
            this.timeExpire = timeExpire;
        }

        public static class CredentialBean {
            public static class WxBean {
            }
        }

        public static class ExtraBean {
        }

        public static class MetadataBean {
        }

        public static class RefundsBean {
            /**
             * data : []
             * hasMore : false
             * object : list
             * uRL : /v1/charges/ch_ezzLC84iTOKOfTybr9CG4yfL/refunds
             */

            private boolean hasMore;
            private String object;
            private String uRL;
            private List<?> data;

            public boolean isHasMore() {
                return hasMore;
            }

            public void setHasMore(boolean hasMore) {
                this.hasMore = hasMore;
            }

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public String getURL() {
                return uRL;
            }

            public void setURL(String uRL) {
                this.uRL = uRL;
            }

            public List<?> getData() {
                return data;
            }

            public void setData(List<?> data) {
                this.data = data;
            }
        }
    }
}
