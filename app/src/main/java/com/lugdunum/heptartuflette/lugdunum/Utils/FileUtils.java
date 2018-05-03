package com.lugdunum.heptartuflette.lugdunum.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileUtils {

    public static void copyFile(File src, File dst) throws IOException {
        if (src.isDirectory()) {
            if (!dst.exists()) {
                dst.mkdirs();
            }

            String[] children = src.list();
            for (int i = 0; i < src.listFiles().length; i++) {

                copyFile(new File(src, children[i]),
                        new File(dst, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();

    }
}
