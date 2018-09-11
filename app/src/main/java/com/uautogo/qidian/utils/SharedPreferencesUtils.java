package com.uautogo.qidian.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
  public static String PREFERENCE_NAME = "qidian";

  private SharedPreferencesUtils() {
    throw new AssertionError();
  }

  /**
   * put string preferences
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @return True if the new values were successfully written to persistent storage.
   */
  public static boolean putString(Context context, String key, String value) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(key, value);
    return editor.commit();
  }

  /**
   * get string preferences
   *
   * @param key The name of the preference to retrieve
   * @return The preference value if it exists, or null. Throws ClassCastException if there is a
   * preference with this
   * name that is not a string
   * @see #getString(Context, String, String)
   */
  public static String getString(Context context, String key) {
    return getString(context, key, null);
  }
  public static void removeString(Context context, String key) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.remove(key);
    editor.commit();
  }




  /**
   * get string preferences
   *
   * @param key The name of the preference to retrieve
   * @param defaultValue Value to return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a
   * preference with
   * this name that is not a string
   */
  public static String getString(Context context, String key, String defaultValue) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    return settings.getString(key, defaultValue);
  }

  /**
   * put int preferences
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @return True if the new values were successfully written to persistent storage.
   */
  public static void putInt(Context context, String key, int value) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt(key, value);
    editor.commit();
  }

  /**
   * get int preferences
   *
   * @param key The name of the preference to retrieve
   * @return The preference value if it exists, or -1. Throws ClassCastException if there is a
   * preference with this
   * name that is not a int
   * @see #getInt(Context, String, int)
   */
  public static int getInt(Context context, String key) {
    return getInt(context, key, -1);
  }

  /**
   * get int preferences
   *
   * @param key The name of the preference to retrieve
   * @param defaultValue Value to return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a
   * preference with
   * this name that is not a int
   */
  public static int getInt(Context context, String key, int defaultValue) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    return settings.getInt(key, defaultValue);
  }

  /**
   * put long preferences
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @return True if the new values were successfully written to persistent storage.
   */
  public static void putLong(Context context, String key, long value) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putLong(key, value);
    editor.commit();
  }

  /**
   * get long preferences
   *
   * @param key The name of the preference to retrieve
   * @return The preference value if it exists, or -1. Throws ClassCastException if there is a
   * preference with this
   * name that is not a long
   * @see #getLong(Context, String, long)
   */
  public static long getLong(Context context, String key) {
    return getLong(context, key, -1);
  }

  /**
   * get long preferences
   *
   * @param key The name of the preference to retrieve
   * @param defaultValue Value to return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a
   * preference with
   * this name that is not a long
   */
  public static long getLong(Context context, String key, long defaultValue) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    return settings.getLong(key, defaultValue);
  }

  /**
   * put float preferences
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @return True if the new values were successfully written to persistent storage.
   */
  public static void putFloat(Context context, String key, float value) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putFloat(key, value);
    editor.commit();
  }

  /**
   * get float preferences
   *
   * @param key The name of the preference to retrieve
   * @return The preference value if it exists, or -1. Throws ClassCastException if there is a
   * preference with this
   * name that is not a float
   * @see #getFloat(Context, String, float)
   */
  public static float getFloat(Context context, String key) {
    return getFloat(context, key, -1);
  }

  /**
   * get float preferences
   *
   * @param key The name of the preference to retrieve
   * @param defaultValue Value to return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a
   * preference with
   * this name that is not a float
   */
  public static float getFloat(Context context, String key, float defaultValue) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    return settings.getFloat(key, defaultValue);
  }

  /**
   * put boolean preferences
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @return True if the new values were successfully written to persistent storage.
   */
  public static void putBoolean(Context context, String key, boolean value) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(key, value);
    editor.commit();
  }

  /**
   * get boolean preferences, default is false
   *
   * @param key The name of the preference to retrieve
   * @return The preference value if it exists, or false. Throws ClassCastException if there is a
   * preference with this
   * name that is not a boolean
   * @see #getBoolean(Context, String, boolean)
   */
  public static boolean getBoolean(Context context, String key) {
    return getBoolean(context, key, false);
  }

  /**
   * get boolean preferences
   *
   * @param key The name of the preference to retrieve
   * @param defaultValue Value to return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a
   * preference with
   * this name that is not a boolean
   */
  public static boolean getBoolean(Context context, String key, boolean defaultValue) {
    SharedPreferences settings =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    return settings.getBoolean(key, defaultValue);
  }



  public static class Key {
    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_USER_PHONE = "key_user_phone";
    public static final String KEY_HEAD_URI = "key_head_uri";
    public static final String KEY_NICKY = "key_nicky";

    public static final String KEY_CAR_NUMBER = "key_car_number";
    public static final String KEY_CAR_ENGINE = "key_car_engine";
    public static final String KEY_CAR_FRAME = "key_car_frame";
    public static final String KEY_CAR_CITY_CODE = "key_car_city_code";

    public static final String KEY_DEVICE_VERSION = "key_device_version";


    public static final String KEY_CAR_LONGITUDE = "key_car_longitude";
    public static final String KEY_CAR_LATITUDE = "key_car_latitude";

  }

}

