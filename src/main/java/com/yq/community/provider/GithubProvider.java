package com.yq.community.provider;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yq.community.dto.AccessTokenDTO;
import com.yq.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    /**
     * 获取access_token
     * @param accessTokenDTO
     * @return
     * @throws IOException
     */
        public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(accessTokenDTO));
                Request request = new Request.Builder()
                        .url("https://github.com/login/oauth/access_token")
                        .post(body)
                        .build();
                try (Response response = client.newCall(request).execute()) {
    //                System.out.println(response.body().string());
                    return response.body().string();
            }
    }


        public GithubUser getUser(String access_token) throws IOException {

            OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.github.com/user?access_token="+access_token)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String string = response.body().string();
                    GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                    return githubUser;
                }catch (IOException e){
                    return null;
                }
        }
}
