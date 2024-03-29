package njgis.opengms.portal.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by wang ming on 2019/5/14.
 */
public class MyHttpUtils {

    public static String POSTMultiPartFileToDataServer(String url,String encode, Map<String,String>params,Map<String,MultipartFile> fileMap)throws IOException{
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //设置header
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");

        //构建body
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .setCharset(Charset.forName(encode));

        if(fileMap != null && fileMap.size() > 0){
            for(Map.Entry<String,MultipartFile> entry: fileMap.entrySet()){
                MultipartFile file = entry.getValue();
                builder.addBinaryBody(entry.getKey(),file.getInputStream(),ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            }
        }


        //构建参数部分，解决中文乱码
        ContentType contentType = ContentType.create("text/plain", Charset.forName(encode));
        if(params != null && params.size() > 0){
            for(Map.Entry<String, String> key: params.entrySet()){
                builder.addTextBody(key.getKey(), key.getValue(), contentType);
            }
        }
        HttpEntity entityIn = builder.build();
        //设置参数到请求参数中
        httpPost.setEntity(entityIn);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entityOut = response.getEntity();
        if (entityOut != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entityOut, encode);
        }
        EntityUtils.consume(entityOut);
        //释放链接
        response.close();
        client.close();
        return body;
    }

    public static String POSTFile(String url, String encode, Map<String,String> params, Map<String,String> fileMap) throws IOException {
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //设置header
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");

        //构建body
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .setCharset(Charset.forName(encode));

        if(fileMap != null && fileMap.size() > 0){
            for(Map.Entry<String, String> entry: fileMap.entrySet()){
                File file = new File(entry.getValue());
                builder.addBinaryBody(entry.getKey(), MyFileUtils.getInputStream(file), ContentType.MULTIPART_FORM_DATA, file.getName());
            }
        }

        //构建参数部分，解决中文乱码
        ContentType contentType = ContentType.create("text/plain", Charset.forName(encode));
        if(params != null && params.size() > 0){
            for(Map.Entry<String, String> key: params.entrySet()){
                builder.addTextBody(key.getKey(), key.getValue(), contentType);
            }
        }

        HttpEntity entityIn = builder.build();
        //设置参数到请求参数中
        httpPost.setEntity(entityIn);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entityOut = response.getEntity();
        if (entityOut != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entityOut, encode);
        }
        EntityUtils.consume(entityOut);
        //释放链接
        response.close();
        client.close();
        return body;
    }
}
