package com.zqx.test;

import com.JJApiTools.JJApiTools;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.get;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;


/**
 * Created by zqx on 2017/6/12.
 */
public class MainApiTest {

    String cookieValue;
    String id;
    Headers allHeaders;

    @Before
    public  void  setUP(){

        RestAssured.baseURI = "https://beta.occall.com";

    }


    private void loginSuccess(){

        Response response = JJApiTools.post("/api/pub/login", "mobile","+869990422","passwd","111111");

        //获取Code值，并输出
        int code = response.getStatusCode();
        // 获取所有 headers 信息
        allHeaders = response.getHeaders();
        //List<String> headersValue = allHeaders.getValues();
        // 获取单个 cookie 信息
        cookieValue = response.getCookie("web-session");
        // 获取请求结果中的某个字段的值
        JsonPath jsonPath = response.jsonPath();
        id = jsonPath.getString("id");



         //System.out.println("code = "+code);
         //System.out.println(allHeaders);
         //System.out.println("Cookies = "+cookieValue);
         //System.out.print("UserId = "+id);

         // response.getBody().prettyPrint();
        if (response.getStatusCode() == 200){

            System.out.println("++++++++登录成功+++++++++++");
        }

        response.getBody().prettyPrint();

    }


    @Test//登录
    public  void  loginTest(){

        Map map = new HashMap();
        map.put("mobile", "+869990422");
        map.put("passwd", "111111");

        //Response response = JJApiTools.mapPost("/api/pub/login", map);
        //response.getBody().prettyPrint();

        // Given
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();

        // When
        JJApiTools.mapPost("/api/pub/login", map).then().assertThat().body(matchesJsonSchemaInClasspath("login.json").using(jsonSchemaFactory));




    }

    @Test//验证注册用户
    public void userExist(){
        ///api/pub/check/user/exist
        Map map = new HashMap();
        map.put("countryCode", "+86");
        map.put("mobile", "9990422");
        Response response = JJApiTools.mapPost("api/pub/check/user/exist", map);

        //String str = response.getBody().asString();
        JJApiTools.mapPost("api/pub/check/user/exist", map).then().body(equalTo("此号码已在侨联通用户中心注册"));


        map.put("mobile", "9990430");
        map.put("countryCode", "+86");
        response = JJApiTools.mapPost("api/pub/check/user/exist", map);
        JJApiTools.mapPost("api/pub/check/user/exist", map).then().body(equalTo(""));


    }

    @Test//退出登录
    public void logout(){

        //Response response = JJApiTools.post("/api/pub/logout");

        JJApiTools.post("/api/pub/logout").then().body(equalTo(""));

    }

    @Test//OA公众号列表
    public void office(){

        ///api/pub/oausers/office
        Response response = JJApiTools.post("/api/pub/oausers/office");
        //response.getBody().prettyPrint();

    }

    @Test//android版本管理最新数据
    public  void  lastVersion(){

        //Response response = JJApiTools.get("/api/pub/apk/version");
        //response.getBody().prettyPrint();

        // Given
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();

        // When
        get("/api/pub/apk/version").then().assertThat().body(matchesJsonSchemaInClasspath("ApkVersion.json").using(jsonSchemaFactory));

    }


    @Test//图片上传
    public void up(){
        //POST /api/pub/up
    }

    @Test//获取公司信息
    public void companyInfo(){

        loginSuccess();

        Response response = JJApiTools.cookiesPost("/api/company", cookieValue);
        response.getBody().prettyPrint();

    }

    @Test //获取组织架构信息
    public void  orgInfo(){
        loginSuccess();

        Response response = JJApiTools.get("/api/org/:0", cookieValue);
        response.getBody().prettyPrint();

    }
    @Test //模糊搜索组织内用户
    public void search(){
        loginSuccess();

        Map map = new HashMap();
        map.put("name", "王");

        Response response = JJApiTools.get("/api/org/users/search", map, cookieValue);
        response.getBody().prettyPrint();

    }

    @Test
    public void updateUserInfo(){

        loginSuccess();

        ///api/shai/user/me
        //System.out.println("Cookies = "+cookieValue);
        Response response = JJApiTools.post("/api/shai/user/me", "name", "JJ", "title","测试的小人儿");
        response.getBody().prettyPrint();

        System.out.println(response.getStatusCode());


    }

    @Test
    public void newsTest(){

        loginSuccess();

        //发起Get请求，请求频道信息
        Response response = JJApiTools.get("api/pub/oa/news/channels");

        //提取请求结果中频道ID，用来请求该频道下新闻列表信息
        JsonPath jsonPath = response.jsonPath();
        String id = jsonPath.getString("data[0].id");
        //System.out.println("频道ID值为："+id);

        //发起Get请求，请求某频道下新闻列表信息
        String newsListApi = "api/pub/oa/news?channelId="+id+"&since=0&until=0&count=20";
        //System.out.println(newsListApi);
        response = JJApiTools.get(newsListApi);

        //提取请求结果中新闻ID，用来请求该频道下新闻列表中某条新闻信息
        jsonPath = response.jsonPath();
        String id1 = jsonPath.getString("list[0].id");
        //System.out.println("新闻ID值为："+id1);

        //请求该频道下，新闻列表中某条新闻的详情内容
        String newDetailApi = "/pub/oa/news/"+id1;
        response = JJApiTools.get(newDetailApi);

    }



    @Test
    public void unRead(){

        //GET https://beta.occall.com/api/shai/unread
        loginSuccess();

        //请求当前用户的未读消息数量
        Response response = JJApiTools.get("/api/shai/unread", cookieValue);
        response.getBody().prettyPrint();


    }

    @Test
    public void memoInfo(){

        //GET https://beta.occall.com/api/shai/friends/memo
        loginSuccess();
        Response response = JJApiTools.get("/api/shai/friends/memo", cookieValue);
        response.getBody().prettyPrint();
    }


    @Test
    public void friendsInfo(){

        //GET https://beta.occall.com/api/shai/friends
        loginSuccess();
        Response response = JJApiTools.get("api/shai/friends", cookieValue);
        response.getBody().prettyPrint();

    }


    @Test
    public void cpp(){
        //GET https://beta.occall.com/api/exconfig/cpp
        loginSuccess();

        Response response = JJApiTools.get("/api/exconfig/cpp", cookieValue);


    }



    @Test
    public void actList(){

        //https://beta.occall.com/api/meeting/act
        loginSuccess();
        Response response = JJApiTools.get("/api/meeting/act", cookieValue);
        response.getBody().prettyPrint();

    }



    @Test
    public void test() {
        loginSuccess();
        
        lastVersion();
    }

}
