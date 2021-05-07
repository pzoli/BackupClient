/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author pzoli
 */
public class HttpClientRequestUtils {

    public static Reader sendFileWithMultipartData(String link, String fileInfo, File file) throws ClientProtocolException, IOException {
        //https://stackoverflow.com/questions/1378920/how-can-i-make-a-multipart-form-data-post-request-using-java
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(link);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("fileInfo", fileInfo, ContentType.APPLICATION_JSON);

        builder.addBinaryBody("file", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM, file.getName());

        HttpEntity multipart = builder.build();
        System.out.println(multipart);
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");
        Reader result = new StringReader(responseString.toString());
        return result;
    }

    public static Reader sendPostRequestWithHttpClient(String urlString, String body) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(urlString);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("cache-control", "no-cache");
        StringEntity requestEntity = new StringEntity(body, "UTF-8");
        post.setEntity(requestEntity);
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        return new StringReader(responseString);
    }

    public static Reader sendGetRequestWithHttpClient(String urlString) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(urlString);
        get.setHeader("cache-control", "no-cache");
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        return new StringReader(responseString);
    }

    public static void sendGetRequestWithDataInputStream(String urlString, String restoreRoot, String fileName) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("cache-control", "no-cache");

        DataInputStream ds = new DataInputStream(con.getInputStream());
        FileUtils.copyInputStreamToFile(ds, new File(restoreRoot + fileName));
    }

    public static int sendDeleteRequest(String urlString) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("cache-control", "no-cache");

        return con.getResponseCode();
    }

}
