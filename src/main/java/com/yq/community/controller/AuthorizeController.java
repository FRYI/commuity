package com.yq.community.controller;

import com.yq.community.dto.AccessTokenDTO;
import com.yq.community.dto.GithubUser;
import com.yq.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;


    @GetMapping("callback")
    public String callback(@RequestParam("code")String code,
                           @RequestParam("state") String state) throws IOException {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("7a624ba5ff87f79300ea");
        accessTokenDTO.setClient_secret("84da1453267c6edeb4f168ed8dc8988740d54fec");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);

        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        String access = access_token.split("=")[1].split("&")[0];
        GithubUser user = githubProvider.getUser(access);

        System.out.println(user);

        return "index";
    }
}
