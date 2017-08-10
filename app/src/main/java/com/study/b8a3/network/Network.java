package com.study.b8a3.network;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Network {
    private static final String LOGTAG = Network.class.getSimpleName();

    /**
     * User agent for mobile.
     */
    private static final String DEFAULT_USERAGENT = "Mozilla/5.0 (Linux; U;" +
            " Android 2.2; zh-cn;) AppleWebKit/533.1 (KHTML, " +
            "like Gecko) Version/4.0 Mobile Safari/533.1";

    private static final String ENCODING_GZIP = "gzip";

    public static String downloadXml(Context context, String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return downloadXml(context, new URL(url), true, null, null, null);
    }

    public static String downloadXml(Context context, URL url, boolean noEncryptUrl, String userAgent,
                                     String encoding, String cookie) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        InputStream responseStream = null;
        BufferedReader reader = null;
        StringBuilder sbReponse;
        try {
            responseStream = downloadXmlAsStream(context, url, noEncryptUrl, userAgent, cookie, null, null);
            sbReponse = new StringBuilder(1024);
            reader = new BufferedReader(new InputStreamReader(responseStream, encoding));
            String line;
            while (null != (line = reader.readLine())) {
                sbReponse.append(line);
                sbReponse.append("\r\n");
            }
        } finally {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sbReponse.toString();
    }

    public static InputStream downloadXmlAsStream(Context context, String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return downloadXmlAsStream(context, new URL(url), true, null, null, null, null);
    }

    public static InputStream downloadXmlAsStream(Context context, String url, boolean noEncryptUrl,
                                                  String userAgent, String cookie) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return downloadXmlAsStream(context, new URL(url), noEncryptUrl, userAgent, cookie, null, null);
    }

    /**
     * 包装 HTTP request/response 的辅助函数
     *
     * @param context      应用程序上下文
     * @param url          HTTP地址
     * @param noEncryptUrl 是否加密
     * @param userAgent
     * @param cookie
     * @param requestHdrs  用于传入除userAgent和cookie之外的其他header info
     * @param responseHdrs 返回的HTTP response headers;
     * @return
     * @throws IOException
     */
    private static InputStream downloadXmlAsStream(
            /* in */ Context context,
            /* in */ URL url, boolean noEncryptUrl, String userAgent, String cookie, Map<String, String> requestHdrs,
            /* out */ HttpHeaderInfo responseHdrs) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (null == context) throw new IllegalArgumentException("context");
        if (null == url) throw new IllegalArgumentException("url");

        URL newUrl = url;
        //if (!noEncryptUrl) {
        //    newUrl = new URL(encryptURL(url.toString()));
        //}

        InputStream responseStream = null;
        HttpURLConnection.setFollowRedirects(true);

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

        HttpURLConnection conn = getProxiableURLConnection(context, newUrl);
        conn.setConnectTimeout(getSocketTimeout(context, newUrl.toString()));
        conn.setReadTimeout(getSocketTimeout(context, newUrl.toString()));

        if (!TextUtils.isEmpty(userAgent)) {
            conn.setRequestProperty("User-agent", userAgent);
        }
        if (cookie != null) {
            conn.setRequestProperty("Cookie", cookie);
        }
        if (null != requestHdrs) {
            for (String key : requestHdrs.keySet()) {
                conn.setRequestProperty(key, requestHdrs.get(key));
            }
        }

        if (responseHdrs != null &&
                (url.getProtocol().equals("http") || url.getProtocol().equals("https"))) {
            responseHdrs.mResponseCode = safeGetResponseCode(conn);
            responseHdrs.mContentType = conn.getContentType();
            if (responseHdrs.mAllHeaders == null) {
                responseHdrs.mAllHeaders = new HashMap<String, String>();
            }
            for (int i = 0; ; i++) {
                String name = conn.getHeaderFieldKey(i);
                String value = conn.getHeaderField(i);

                if (name == null && value == null) break;
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) continue;

                responseHdrs.mAllHeaders.put(name.toLowerCase(), value);
            }
        }

        responseStream = conn.getInputStream();
        return responseStream;
    }

    private static class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }
    }

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }


    public static class HttpHeaderInfo {
        public int mResponseCode;
        public String mContentType;
        public String mUserAgent;
        public String mRealUrl;
        public Map<String, String> mAllHeaders;
    }

    /**
     * 向服务端提交HttpPost请求
     * 设置为5秒钟连接超时，发送数据不超时；
     *
     * @param context         : Context
     * @param url:            HTTP post的URL地址
     * @param nameValuePairs: HTTP post参数
     * @throws IOException : 调用过程中可能抛出到exception
     * @return: 如果post response代码不是2xx，表示发生了错误，返回null。否则返回服务器返回的数据（如果服务器没有返回任何数据，返回""）；
     */
    public static String doHttpPost(Context context, String url, List<NameValuePair> nameValuePairs) throws IOException {
        return getHttpPostAsString(context, new URL(url), nameValuePairs);
    }

    /**
     * 向服务端提交HttpGet请求
     * 设置为5秒钟连接超时，发送数据不超时；
     *
     * @param context         : Context
     * @param url:            HTTP get的URL地址
     * @param nameValuePairs: HTTP jquery 参数
     * @throws IOException : 调用过程中可能抛出到exception
     * @noGZip gzip: whether response Accept-Encoding: gzip
     * @return: 如果get response代码不是2xx，表示发生了错误，返回null。否则返回服务器返回的数据（如果服务器没有返回任何数据，返回""）；
     */
    public static String doHttpGet(Context context, String url, List<NameValuePair> nameValuePairs, boolean gzip) throws IOException {
        BufferedReader reader = null;
        InputStream responseStream = null;
        StringBuilder sbReponse = null;

        try {
            String params = null;
            String newUrl = url;
            if (nameValuePairs != null && nameValuePairs.size() > 0) {
                params = URLEncodedUtils.format(nameValuePairs, "UTF-8");
                newUrl = url + "?" + params;
            }
            Map<String, String> headers = new HashMap<String, String>();
            responseStream = getHttpGetAsStream(context, new URL(newUrl), headers, DEFAULT_USERAGENT, null, gzip);
            if (headers.containsKey("Content-Encoding")
                    && headers.get("Content-Encoding").toLowerCase().equals(ENCODING_GZIP)) {
                responseStream = new GZIPInputStream(responseStream);
            }
            sbReponse = new StringBuilder(1024);
            reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
            String line;
            while (null != (line = reader.readLine())) {
                sbReponse.append(line);
                sbReponse.append("\r\n");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (KeyManagementException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (responseStream != null) {
                responseStream.close();
            }
        }
        return sbReponse.toString();
    }

    /**
     * Upload file to server.
     *
     * @param context         : Context
     * @param url:            HTTP post的URL地址
     * @param fieldName:      field name
     * @param fileName:       file name
     * @param nameValuePairs: HTTP post参数
     * @throws IOException : 调用过程中可能抛出到exception
     * @return: 如果post response代码不是2xx，表示发生了错误，返回null。否则返回服务器返回的数据（如果服务器没有返回任何数据，返回""）；
     */
    public static String doHttpPost(Context context, String url, String fieldName, String fileName, List<NameValuePair> nameValuePairs) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        InputStream responseStream = null;
        DataOutputStream outputStream = null;
        FileInputStream inputStream = null;
        HttpURLConnection conn = null;
        StringBuilder sbReponse;
        try {
            if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("url");

            String data = null;
            if (nameValuePairs != null && nameValuePairs.size() > 0) {
                data = URLEncodedUtils.format(nameValuePairs, "UTF-8");
            }

            final String lineEnd = "\r\n";
            final String boundary = "*****";
            final String twoHyphens = "--";

            URL newUrl = new URL(url);
            HttpURLConnection.setFollowRedirects(true);
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

            conn = getProxiableURLConnection(context, newUrl);
            conn.setConnectTimeout(getSocketTimeout(context, null));
            conn.setReadTimeout(getSocketTimeout(context, null));
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setChunkedStreamingMode(1024);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            if (data != null) {
                conn.getOutputStream().write(data.getBytes());
                conn.getOutputStream().flush();
            }


            outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName +
                    "\";filename=\"" + fileName + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            inputStream = new FileInputStream(new File(fileName));
            int bytesRead = 0, bufferSize;
            final int maxBufferSize = 1024;
            bufferSize = Math.min(inputStream.available(), maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            // Read file.
            bytesRead = inputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bufferSize = Math.min(inputStream.available(), maxBufferSize);
                bytesRead = inputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            outputStream.flush();

            responseStream = conn.getInputStream();

            sbReponse = new StringBuilder(1024);
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
            String line;
            while (null != (line = reader.readLine())) {
                sbReponse.append(line);
                sbReponse.append(lineEnd);
            }
        } finally {
            try {
                if (responseStream != null) {
                    responseStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sbReponse.toString();
    }

    private static String getHttpPostAsString(Context context, URL url, List<NameValuePair> nameValuePairs)
            throws IOException {
        BufferedReader reader = null;
        InputStream responseStream = null;
        StringBuilder sbReponse = null;

        try {
            String data = null;
            if (nameValuePairs != null && nameValuePairs.size() > 0) {
                data = URLEncodedUtils.format(nameValuePairs, "UTF-8");
            }
//            Log.e("Network",data);
//            data = "pn=com.hawk.emoji.keyboard&tk=9sOEq8T%2BsEHh%2BY9OUBRmBHcgZYFqMRgneGpOhJSTUR9wzIplLqiH4cey0l9BJojG5i5zfV9j5okg8P2TKcmjLu7kjpyapgOw0RuHgy9jC9nZFotXt2TKcQ%3D%3D&s=100&tp=theme&cs=-1&lg=de&p=1&v=1.0.12&dit=0&mi=867025020102819%7C3c%3Acb%3A7c%3Af4%3Ab1%3A8a%7Cbd2d4995ce07d91b&u=0";
            sbReponse = new StringBuilder(1024);
            responseStream = getHttpPostAsStream(context, url, data, new HashMap<String, String>(), null, null);
            reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
            String line;
            while (null != (line = reader.readLine())) {
                sbReponse.append(line);
                sbReponse.append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (KeyManagementException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (responseStream != null) {
                    responseStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sbReponse.toString();
    }

    private static InputStream getHttpPostAsStream(Context context, URL url, String data,
                                                   Map<String, String> headers, String userAgent, String cookie) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (null == url) throw new IllegalArgumentException("url");

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

        HttpURLConnection.setFollowRedirects(true);
        HttpURLConnection conn = getProxiableURLConnection(context, url);
        conn.setConnectTimeout(getSocketTimeout(context, null));
        conn.setReadTimeout(getSocketTimeout(context, null));
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        if (!TextUtils.isEmpty(userAgent)) {
            conn.setRequestProperty("User-agent", userAgent);
        }

        if (!TextUtils.isEmpty(cookie)) {
            conn.setRequestProperty("Cookie", cookie);
        }

        if (data != null) {
            conn.getOutputStream().write(data.getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
        }

        String responseCode = safeGetResponseCode(conn) + "";
        headers.put("ResponseCode", responseCode);

        for (int i = 0; ; i++) {
            String name = conn.getHeaderFieldKey(i);
            String value = conn.getHeaderField(i);
            if (name == null && value == null) {
                break;
            }
            headers.put(name, value);
        }
        InputStream responseStream = conn.getInputStream();
        return responseStream;
    }

    private static InputStream getHttpGetAsStream(Context context, URL url, Map<String, String> headers,
                                                  String userAgent, String cookie, boolean gzip) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (null == url) throw new IllegalArgumentException("url");

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

        HttpURLConnection.setFollowRedirects(true);
        HttpURLConnection conn = getProxiableURLConnection(context, url);
        conn.setConnectTimeout(getSocketTimeout(context, null));
        conn.setReadTimeout(getSocketTimeout(context, null));
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        if (gzip) {
            conn.setRequestProperty("Accept-Encoding", ENCODING_GZIP);
        }

        if (!TextUtils.isEmpty(userAgent)) {
            conn.setRequestProperty("User-agent", userAgent);
        }

        if (!TextUtils.isEmpty(cookie)) {
            conn.setRequestProperty("Cookie", cookie);
        }

        String responseCode = safeGetResponseCode(conn) + "";
        headers.put("ResponseCode", responseCode);

        for (int i = 0; ; i++) {
            String name = conn.getHeaderFieldKey(i);
            String value = conn.getHeaderField(i);
            if (name == null && value == null) {
                break;
            }
            headers.put(name, value);
        }
        InputStream responseStream = conn.getInputStream();
        return responseStream;
    }

    private static int getSocketTimeout(Context context, String url) {
        if (TextUtils.isEmpty(url) || (url.indexOf("wap") == -1)) {
            return 8000; // ms
        }
        return 15000;  // ms
    }

    private static int safeGetResponseCode(HttpURLConnection conn) throws IOException {
        try {
            return conn.getResponseCode();
        } catch (NumberFormatException e) {
            // defensive check for bad response code
            Log.e(LOGTAG, "Bad response code " + e.toString());
        } catch (NullPointerException e) {
            Log.e(LOGTAG, "NullPointerException " + e.toString());
        }
        return 500; // Internal Server Error
    }

    /**
     * @param strUrl 要加密的URL string
     * @return 获取加密后的URL string
     */
    private static String encryptURL(String strUrl) {
        if (!TextUtils.isEmpty(strUrl)) {
            // TODO:
        }
        return strUrl;
    }

    /**
     * 实现自适应的 HttpURLConnection
     *
     * @param url
     * @return
     * @throws IOException
     * @throws MalformedURLException
     * @throws Exception
     */
    private static HttpURLConnection getProxiableURLConnection(Context context, URL url)
            throws MalformedURLException, IOException, NoSuchAlgorithmException, KeyManagementException {
        boolean useProxy = false;
        String host = null;
        int port = -1;

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

        // 尝试使用用户设置的代理服务器
        if (context != null && !useProxy) {
            host = android.net.Proxy.getHost(context);
            port = android.net.Proxy.getPort(context);
            useProxy = (!TextUtils.isEmpty(android.net.Proxy.getHost(context))) && NetworkUtils.isProxyEnabled(context);
            if (useProxy && NetworkUtils.isProxyForWifiOnly(context)) {
                useProxy = NetworkUtils.isWifiConnect();
            }
        }

        if (useProxy) {
            try {
                java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
                        new InetSocketAddress(host, port));
                return (HttpURLConnection) url.openConnection(p);
            } catch (IllegalArgumentException e) {
                return (HttpURLConnection) url.openConnection();
            } catch (UnsupportedOperationException e) {
                return (HttpURLConnection) url.openConnection();
            }
        } else {
            return (HttpURLConnection) url.openConnection();
        }
    }

    public interface PostDownloadHandler {
        void OnPostDownload(boolean success);
    }

    /**
     * 实际的网络流交由具体的业务处理
     */
    public interface OutsourceHandler {
        boolean outsource(InputStream input);
    }

    /**
     * 开始下载远程文件到指定输出流
     *
     * @param url     远程文件地址
     * @param output  输出流
     * @param handler 下载成功或者失败的处理
     */
    public static void downloadFile(Context context, String url, OutputStream output,
                                    PostDownloadHandler handler) {
        DownloadTask task = new DownloadTask(context, url, output, handler);
        task.execute();
    }

    /**
     * 下载远程文件到指定输出流
     *
     * @param url    远程文件地址
     * @param output stream
     * @return success or failed
     */
    public static boolean downloadFile(Context context, String url, OutputStream output) {
        InputStream input = null;
        try {
            HttpURLConnection conn = getProxiableURLConnection(context, new URL(url));
            conn.setConnectTimeout(getSocketTimeout(context, url));
            conn.setReadTimeout(getSocketTimeout(context, url));
            input = conn.getInputStream();

            byte[] buffer = new byte[1024];
            int count;
            while ((count = input.read(buffer)) > 0) {
                output.write(buffer, 0, count);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 只做网络请求，拿到流数据之后，交由具体的业务处理
     * 针对图像部分
     *
     * @param context
     * @param url
     * @param handler
     */
    public static boolean downloadFile(Context context, String url, OutsourceHandler handler) {
        InputStream input = null;
        try {
            HttpURLConnection conn = getProxiableURLConnection(context, new URL(url));
            conn.setConnectTimeout(getSocketTimeout(context, url));
            conn.setReadTimeout(getSocketTimeout(context, url));
            input = conn.getInputStream();

            return handler.outsource(input);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    private static class DownloadTask extends AsyncTask<Void, Void, Boolean> {
        private Context mContext;
        private String mUrl;
        private OutputStream mOutput;
        private PostDownloadHandler mHandler;

        public DownloadTask(Context context, String url, OutputStream output, PostDownloadHandler handler) {
            mContext = context;
            mUrl = url;
            mOutput = output;
            mHandler = handler;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = Network.downloadFile(mContext, mUrl, mOutput);
            if (mOutput != null) {
                try {
                    mOutput.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (mHandler != null) {
                mHandler.OnPostDownload(result);
            }
        }
    }
}
