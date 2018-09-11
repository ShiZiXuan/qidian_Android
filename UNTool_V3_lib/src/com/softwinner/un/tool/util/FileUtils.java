package com.softwinner.un.tool.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {

  public static final int MEDIA_TYPE_IMAGE_JPG = 1;
  public static final int MEDIA_TYPE_IMAGE_PNG = 2;
  public static final int MEDIA_TYPE_VIDEO = 3;
  public static final int MEDIA_TYPE_MUSIC = 4;
  public static final int MEDIA_TYPE_COMPRESSED_VIDEO = 5;
  public static final int MEDIA_TYPE_COMPRESSED_AUDIO = 6;
  public static final int MEDIA_TYPE_DECODED_WAV = 7;
  public static final String DOWNLOAD_DIR = "download";
  private static final String COMPRESS_DIR = "compressed";
  public static String QIDIAN_DIR = "qidian";
  public static String PICTURE_DIR = "picture";
  public static String DATA_DIR = "data";
  public static String FILE_DIR = "file";
  public static String LOADING_DIR = "loading";
  public static String STATISTICS_DIR = "statistics";
  public static String SITE_INSTALL = "site_install";
  public static String SITE_ACTIVE = "site_active";

  public static boolean existSDCard() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return true;
    } else {
      return false;
    }
  }

  public static File getFolderDir(String dirName) throws IOException {
    //        if (!existSDCard()) {
    //            throw new IOException("sdcard do not exists");
    //        }

    File sdcardDir = Environment.getExternalStorageDirectory();

    File kzDir = new File(sdcardDir.getPath() + File.separator + QIDIAN_DIR + File.separator +
            DATA_DIR);
    if (!kzDir.exists()) {
      boolean rst = kzDir.mkdirs();
    } else {
    }

    File wrapperDir = new File(kzDir.getPath() + File.separator + FILE_DIR);
    if (!wrapperDir.exists()) {
      boolean rst = wrapperDir.mkdirs();
    } else {
    }

    File folderName = new File(wrapperDir.getPath() + File.separator + dirName);
    if (!folderName.exists()) {
      boolean rst = folderName.mkdirs();
    } else {
    }
    return folderName;
  }

  public static File getInstallFile() throws IOException {
    File installed = new File(getFolderDir(STATISTICS_DIR).getPath() + File.separator +
            SITE_INSTALL);
    if (!installed.exists()) {
      installed.createNewFile();
    }
    return installed;
  }


  public static boolean deleteInstallFile() {
    try {
      File installed = new File(getFolderDir(STATISTICS_DIR).getPath() + File.separator +
              SITE_INSTALL);
      if (installed.exists()) {
        return installed.delete();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }


  public static FileInputStream openInputStream(File file) throws IOException {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new IOException("File '" + file + "' exists but is a directory");
      }
      if (!file.canRead()) {
        throw new IOException("File '" + file + "' cannot be read");
      }
    } else {
      throw new FileNotFoundException("File '" + file + "' does not exist");
    }
    return new FileInputStream(file);
  }

  public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new IOException("File '" + file + "' exists but is a directory");
      }
      if (file.canWrite() == false) {
        throw new IOException("File '" + file + "' cannot be write");
      }
    } else {
      throw new FileNotFoundException("File '" + file + "' does not exist");
    }
    return new FileOutputStream(file, append);
  }

  public static List<String> readLines(File file, String encoding) throws IOException {
    InputStream in = null;
    try {
      in = openInputStream(file);
      return readLines(in, encoding);
    } finally {
      closeQuietly(in);
    }
  }

  public static void closeQuietly(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeLine(File file, @NonNull String encoding, String content)
          throws IOException {
    OutputStream out = null;
    try {
      out = openOutputStream(file, false);
      writeLine(out, encoding, content);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out == null) {
        return;
      }
      closeQuietly(out);
    }
  }

  public static void appendLine(File file, @NonNull String encoding, String content)
          throws IOException {
    OutputStream out = null;
    try {
      out = openOutputStream(file, true);
      writeLine(out, encoding, content);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeQuietly(out);
    }
  }

  public static void writeLine(OutputStream outputStream, @NonNull String encoding, String content)
          throws IOException {
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, encoding);
    BufferedWriter writer = new BufferedWriter(outputStreamWriter);
    writer.append(content);
    writer.append("\n");
    writer.flush();
  }

  public static List<String> readLines(InputStream input, @NonNull String encoding)
          throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(input, encoding);
    BufferedReader reader = new BufferedReader(inputStreamReader);
    List<String> list = new ArrayList<String>();
    String line = reader.readLine();
    while (line != null) {
      list.add(line);
      line = reader.readLine();
    }
    return list;
  }

  /**
   * Creates a media file in the {@code Environment.DIRECTORY_PICTURES} directory. The directory
   * is persistent and available to other applications like gallery.
   *
   * @param type Media type. Can be video or image.
   * @return A file object pointing to the newly created file.
   */
  public static File getOutputMediaFile(Context context, int type) {
    // To be safe, you should check that the SDCard is mounted
    // using Environment.getExternalStorageState() before doing this.
    if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
      return getOutputFileInternal(context, type);
    }
    File mediaStorageDir;
    switch (type) {
      default:
      case MEDIA_TYPE_IMAGE_JPG:
      case MEDIA_TYPE_IMAGE_PNG:
        mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        DATA_DIR);
        break;
      case MEDIA_TYPE_MUSIC:
        mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                        DATA_DIR);
        break;
      case MEDIA_TYPE_VIDEO:
        mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                        DATA_DIR);
        break;
      case MEDIA_TYPE_COMPRESSED_AUDIO:
      case MEDIA_TYPE_DECODED_WAV:
        mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                        .getAbsolutePath() + File.separator + DATA_DIR + File.separator + COMPRESS_DIR);
        break;
      case MEDIA_TYPE_COMPRESSED_VIDEO:
        mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                        .getAbsolutePath() + File.separator + DATA_DIR + File.separator +
                        COMPRESS_DIR);
        break;
    }
    // This location works best if you want the created images to be shared
    // between applications and persist after your app has been uninstalled.

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists()) {
      if (!mediaStorageDir.mkdirs()) {
        return getOutputFileInternal(context, type);
      }
    }
    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    File mediaFile;
    switch (type) {
      default:
      case MEDIA_TYPE_IMAGE_JPG:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        break;
      case MEDIA_TYPE_IMAGE_PNG:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".png");
        break;
      case MEDIA_TYPE_VIDEO:
      case MEDIA_TYPE_COMPRESSED_VIDEO:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "VID_" + timeStamp + ".mp4");
        break;
      case MEDIA_TYPE_MUSIC:
      case MEDIA_TYPE_COMPRESSED_AUDIO:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "AUD_" + timeStamp + ".mp3");
        break;
      case MEDIA_TYPE_DECODED_WAV:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "WAV_" + timeStamp + ".wav");
        break;
    }
    return mediaFile;
  }

  public static File getOutputFileInternal(Context context, int type) {
    String path;
    File internalDir = context.getFilesDir();
    File mediaDir;
    switch (type) {
      default:
      case MEDIA_TYPE_IMAGE_JPG:
      case MEDIA_TYPE_IMAGE_PNG:
        mediaDir = new File(internalDir.getPath()
                + File.separator
                + Environment.DIRECTORY_PICTURES
                + File.separator
                + DATA_DIR);
        break;
      case MEDIA_TYPE_MUSIC:
        mediaDir = new File(internalDir.getPath()
                + File.separator
                + Environment.DIRECTORY_MUSIC
                + File.separator
                + DATA_DIR);
        break;
      case MEDIA_TYPE_COMPRESSED_AUDIO:
      case MEDIA_TYPE_DECODED_WAV:
        mediaDir = new File(internalDir.getPath()
                + File.separator
                + Environment.DIRECTORY_MUSIC
                + File.separator
                + DATA_DIR
                +
                File.separator
                + COMPRESS_DIR
                + File.separator
                + DATA_DIR);
        break;
      case MEDIA_TYPE_COMPRESSED_VIDEO:
        mediaDir = new File(internalDir.getPath()
                + File.separator
                + Environment.DIRECTORY_MOVIES
                + File.separator
                + DATA_DIR
                +
                File.separator
                + COMPRESS_DIR
                + File.separator
                + DATA_DIR);
        break;
    }
    if (!mediaDir.exists()) {
      if (!mediaDir.mkdirs()) {
        path = internalDir.getAbsolutePath();
      } else {
        path = mediaDir.getAbsolutePath();
      }
    } else {
      path = mediaDir.getAbsolutePath();
    }
    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    File mediaFile;
    switch (type) {
      default:
      case MEDIA_TYPE_IMAGE_JPG:
        mediaFile = new File(path + File.separator +
                "IMG_" + timeStamp + ".jpg");
        break;
      case MEDIA_TYPE_IMAGE_PNG:
        mediaFile = new File(path + File.separator +
                "IMG_" + timeStamp + ".png");
        break;
      case MEDIA_TYPE_VIDEO:
      case MEDIA_TYPE_COMPRESSED_VIDEO:
        mediaFile = new File(path + File.separator +
                "VID_" + timeStamp + ".mp4");
        break;
      case MEDIA_TYPE_MUSIC:
      case MEDIA_TYPE_COMPRESSED_AUDIO:
        mediaFile = new File(path + File.separator +
                "AUD_" + timeStamp + ".mp3");
        break;
      case MEDIA_TYPE_DECODED_WAV:
        mediaFile = new File(path + File.separator +
                "WAV_" + timeStamp + ".wav");
        break;
    }
    return mediaFile;
  }

  public static String getFileNameFromUrl(String url) {
    if (!TextUtils.isEmpty(url)) {
      int fragment = url.lastIndexOf('#');
      if (fragment > 0) {
        url = url.substring(0, fragment);
      }

      int query = url.lastIndexOf('?');
      if (query > 0) {
        url = url.substring(0, query);
      }

      int filenamePos = url.lastIndexOf('/');
      String filename = 0 <= filenamePos ? url.substring(filenamePos + 1) : url;

      return filename;
    }

    return "";
  }
}
