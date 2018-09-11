package com.uautogo.qidian.model;

/**
 * Created by Serenade on 2017/9/1.
 */

public class AppUpdateRespons extends BaseQidianRespons {
    public AppUpdate data;

    public class AppUpdate{
        private int updateFlag;
        private int versionCode;
        private String versionName;
        private String updateUrl;
        private String upgradeInfo;

        public int getUpdateFlag() {
            return updateFlag;
        }

        public void setUpdateFlag(int updateFlag) {
            this.updateFlag = updateFlag;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public String getUpgradeInfo() {
            return upgradeInfo;
        }

        public void setUpgradeInfo(String upgradeInfo) {
            this.upgradeInfo = upgradeInfo;
        }
    }
}
