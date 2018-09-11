package com.uautogo.qidian.model;

public class AlipayResponse extends BaseResponse {
    /**
     * data : {"sign":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017062107534679&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%2220180312100903wentest%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwangzk.free.ngrok.cc%2Fapi%2Falipay%2Fnotify&sign=NGaYVtw1XBGN%2FVJ19ioQ4n8KioL1TMkN6q7WG2i2N6TAB5PmE4aku0CiBBmANahqvT0XdeJ5eeFZToR%2Fl4kIWckAESGdr0zBsIzLGl3K9CP8HNXMiaKJrTbxY8rB7oN64590DJTqfcC54JcIs0vVz7IERJ8k3kwXCBwJ6FXUeMw%3D&sign_type=RSA&timestamp=2018-03-12+10%3A09%3A03&version=1.0 "}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sign : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017062107534679&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%2220180312100903wentest%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwangzk.free.ngrok.cc%2Fapi%2Falipay%2Fnotify&sign=NGaYVtw1XBGN%2FVJ19ioQ4n8KioL1TMkN6q7WG2i2N6TAB5PmE4aku0CiBBmANahqvT0XdeJ5eeFZToR%2Fl4kIWckAESGdr0zBsIzLGl3K9CP8HNXMiaKJrTbxY8rB7oN64590DJTqfcC54JcIs0vVz7IERJ8k3kwXCBwJ6FXUeMw%3D&sign_type=RSA&timestamp=2018-03-12+10%3A09%3A03&version=1.0
         */

        private String sign;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
