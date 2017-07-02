package com.JJApiTools;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;

/**
 * Created by zqx on 2017/6/12.
 */
public class JJApiTools {

    /**类型一：
     * 请求参数为json数据结构的post请求
     */

    public static Response post(String apiPath, String jsonData){

        Response response = given().
                contentType("application/json;charset=UTF-8").
                body(jsonData).
                when().log().all().post(apiPath.trim());

        return response;
    }


    /**类型二：
     * 请求无参数结构的post请求
     */


    public static Response post(String apiPath){


        Response response = given().contentType("application/json; charset=UTF-8").
                headers("headers1", "value1", "header2", "value2").
                cookies("cookies1","value1", "cookies2", "value2").
                expect().statusCode(200).
                when().post(apiPath.trim());

        return response;

    }

    /**类型三：
     * 请求有多个参数结构的post请求
     */
    //方式一
    public static Response post(String apiPath, String parm1, String value1, String parm2, String value2){

        Response response =  given().
                param(parm1, value1).
                param(parm2, value2).
                expect().statusCode(200).
                when().post(apiPath);

        return response;
    }

    //方式二
    public static Response mapPost(String apiPath, Map map){

        Response response =  given().params(map).
                expect().statusCode(200).
                when().post(apiPath);

        return response;
    }

    /**类型三：
     * 含有cookie的post请求
     */
    public static  Response cookiesPost(String apiPath, String cookies){
        System.out.println("Cookie = "+cookies);

        Response response = given().contentType("application/json; charset=UTF-8").
                cookies("web-session", cookies).
                expect().statusCode(200).
                when().
                //log().all().
                post(apiPath.trim());

        return response;
    }


    /**类型三：
     * get请求
     */
    public static Response get(String apiPath) {

        Response response = given().contentType("application/json; charset=UTF-8").
                //headers("headers1", "value1", "headers2", "values2").
                //cookies("cookies1", "values1", "cookies2", "values2").
                expect().statusCode(200).
                when().get(apiPath.trim());
        System.out.println("_________________________________________");

        return response;


    }


    public static Response get(String apiPath, String cookie, Headers headers) {

        System.out.println("Cookie = "+cookie);

        Response response = given().contentType("application/json; charset=UTF-8").
                headers("Headers", headers).
                cookies("web-session", cookie).
                when().log().all().get(apiPath.trim());

        //given().contentType("application/json").when().get("/xxx").then().body();

        return response;


    }
    public static Response get(String apiPath, String cookie) {

        System.out.println("Cookie = "+cookie);

        Response response = given().contentType("application/json; charset=UTF-8").
                cookies("web-session", cookie).
                expect().statusCode(200).
                when().get(apiPath.trim());

        //given().contentType("application/json").when().get("/xxx").then().body();

        return response;


    }
    public static Response get(String apiPath, Map map, String cookie){

        Response response = given().contentType("application/json; charset=UTF-8").
                params(map).expect().statusCode(200).
                when().get(apiPath.trim());

        return response;
    }
}
