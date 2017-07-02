package com.zqx.test;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by zqx on 2017/6/8.
 */
public class MainTest {
    public static void main(String[] args) {

    }

    @Before
    public  void  setUP(){

        RestAssured.baseURI = "https://api.douban.com/v2/book";

    }

    @Test
    public void test() {

        Response response = get("/1220562");
        //response.getBody();
        if (response.getStatusCode() == 200){

            System.out.println("----------Success--------------");

            //格式化打印JSON数据
            response.getBody().prettyPrint();
        }

         get("/1220562").then().body("title", equalTo("满月之夜白鲸现"));


// 获取所有 headers 信息
        Headers allHeaders = response.getHeaders();

// 获取单个 header 信息
        String headerName = response.getHeader("headerName");

// 获取所有 cookie 键值对
        Map<String, String> allCookies = response.getCookies();

// 获取单个 cookie 信息
        String cookieValue = response.getCookie("cookieName");

// 获取状态行信息
        String statusLine = response.getStatusLine();

// 获取状态码信息
        int statusCode = response.getStatusCode();




    }
}
