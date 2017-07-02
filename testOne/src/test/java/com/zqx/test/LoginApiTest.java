package com.zqx.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * Created by zqx on 2017/6/9.
 */

public class LoginApiTest {

    @Before
    public  void  setUP(){

        RestAssured.baseURI = "https://api.occall.com/api/pub/login";

    }

    @Test
    public void loginSuccess(){

        Response response = given().contentType("application/json;charset=UTF-8").headers("headers1","value1","header2","value2").cookies("cookies1","value1","cookies2","value2").when()
                .log().all().post("https://api.occall.com/api/pub/login?");

    }

    @Test
    public void getTest(){

        Response response = given().
                contentType("application/json;charset=UTF-8").
                headers("headers1", "value1", "headers2", "values2").
                cookies("cookies1", "values1", "cookies2", "values2").
                when().log().all().get("http://beta.occall.com/api/pub/apk/version".trim());

        response.getBody().prettyPrint();
    }

    //简书例子
  /** @Test
    public void testLogin(){
        HashMap<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("identifier","admin");
        paramMap.put("password","123456");
        paramMap.put("next","");

        Response response =
                given().
                        params(paramMap).
                        when().
                        post("http://test-web-site/auth/");
        System.out.println(response.getHeaders().toString());
    }

  */

    public void errorPassword(){

    }
}
