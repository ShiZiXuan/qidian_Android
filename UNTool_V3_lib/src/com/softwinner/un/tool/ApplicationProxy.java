package com.softwinner.un.tool;

import android.app.Application;

/**
 * Created by Jeremy on 16/12/8.
 */

public class ApplicationProxy {
  private static Application mApp;

  public String mAppName;

  public static boolean isDebug;



  public static ApplicationProxy getInstance() {
    return InnerClass.applicationProxy;
  }

  public Application getApplication() {
    return mApp;
  }

  public void setApplication(Application application) {
    mApp = application;
  }

  public void setDebug(boolean isDebug){
    this.isDebug = isDebug;
  }

  private static class InnerClass {
    private static ApplicationProxy applicationProxy = new ApplicationProxy();
  }
}
