package com.tencent.shadow.sample.host;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssertUtils {

    public static String loadAssetsToCache(Context context, String assertName) {
        String filePath = context.getFilesDir().getAbsolutePath();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(assertName);
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String targetFilePath = filePath + "/" + assertName;
            //保存到本地的文件夹下的文件
            FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            return targetFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
