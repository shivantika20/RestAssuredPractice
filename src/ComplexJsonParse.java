import files.payLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(payLoad.mockApiResponse());

        //courses count

        int count = js.getInt("courses.size()");
        System.out.println(count);

        //Print purchase amount

        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //Print Title of 1st course

        String title = js.getString("courses[0].title");
        System.out.println(title);

        //Print All course Title and prices

        for (int i = 0; i < count; i++) {

            String courseTitles = js.get("courses[" + i + "].title");
            System.out.println(js.get("courses[" + i + "].price").toString());
            System.out.println(courseTitles);
        }

        //Copies

        System.out.println("Print copies sold by RPA");

        for (int i = 0; i < count; i++) {
            String courseTitles = js.get("courses[" + i + "].title");
            if (courseTitles.equalsIgnoreCase("RPA")) ;
            {
                int copies = js.get("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }
    }
}