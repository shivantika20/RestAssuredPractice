import files.payLoad;
import files.reUseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class basics {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").
                header("Content-Type", "application/json").
                body(payLoad.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);// for parsing Json
        String placeId = js.getString("place_id");

        System.out.println("Place id is " + placeId);


        //Update place

        String newAddress = "70 Summer walk, USA";
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json").
                body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when()
                .put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200).
                body("msg", equalTo("Address successfully updated"));

        //Get Place

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId).
                        when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).
                        extract().response().asString();
        JsonPath js1 = reUseableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress,newAddress);
    }
}
