package za.co.westcoastexplorers.exploreapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by rikus on 2017/05/10.
 */

public class AssetUtils {

    public static byte[] downloadImage(String urlString){
        byte[] image = null;
        try {
            SSLContext ex = SSLContext.getInstance("TLS");
            ex.init((KeyManager[]) null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, (SecureRandom) null);
            HttpsURLConnection.setDefaultSSLSocketFactory(ex.getSocketFactory());
            URL url = new URL(urlString);
            Object connection = null;
            if (url.toString().startsWith("https")) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = url.openConnection();
            }

            ((URLConnection) connection).setUseCaches(true);
            ((URLConnection) connection).setReadTimeout(30000);

            InputStream is = ((URLConnection) connection).getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];

            int bytesReceived;
            while ((bytesReceived = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesReceived);
            }

            image = baos.toByteArray();
            baos.flush();
        } catch (Exception e){
            e.printStackTrace();
        }

        return image;
    }
}
