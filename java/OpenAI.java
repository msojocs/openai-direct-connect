import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class OpenAI {
    
    public static void main(String[] args) {
        HttpsURLConnection r;
        try {
            String url = "https://52.152.96.252/v1/models";
            URL u = new URL(url);
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            r = (HttpsURLConnection) u.openConnection();
            r.setRequestProperty("Host", "api.openai.com");
            r.setConnectTimeout(10000);
            r.setRequestProperty("User-Agent", "Mozilla/5.0");

            // 设置通用的请求属性 更多的头字段信息可以查阅HTTP协议
            r.setRequestProperty("accept", "*/*");
            r.setRequestMethod("GET");
            r.setHostnameVerifier((hostname, sslSession) -> true);
            TrustManager[] trustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, trustManagers, null);

            r.setSSLSocketFactory(ctx.getSocketFactory());
            r.setDoInput(true);
            r.setDoOutput(true);
            r.setInstanceFollowRedirects(false);
            try {
                InputStream inputStream = r.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    byteArrayOutputStream.write(buf, 0, len);
                }
                System.out.println(byteArrayOutputStream);
            }catch (IOException e) {
                InputStream errorStream = r.getErrorStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = errorStream.read(buf)) > 0) {
                    byteArrayOutputStream.write(buf, 0, len);
                }
                System.out.println(byteArrayOutputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
