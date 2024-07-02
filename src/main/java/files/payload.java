package files;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;



public class payload {
	
    private static final String X_API_KEY = "a3riN1g2iE5VjnirjVT1X4Nhtcr5H3r12eb2M9aQ";
    private static final String CONTENT_TYPE = "application/json";
     
    private static RequestSpecification givenWithCommonHeaders() {
        return given()
                .header("x-api-key", X_API_KEY)
                .header("Content-Type", CONTENT_TYPE);
    }
		
	 public static void VirtualAccountRepayment() {
	        
		    RestAssured.baseURI = "https://test-api.jai-kisan.com";

		    givenWithCommonHeaders().body("{\r\n"
	                            + "    \"amount\": \"1000\",\r\n"
	                            + "    \"creditLineId\": \"JKFPO202405579132\" \r\n"
	                            + "}")
	               .when().post("/automation-webhooks/razorpay/virtualAccount").then().log().all().assertThat()
	               .statusCode(200);   
		    System.out.println("Exection of VIRTUAL BANK PAYMENT is completed");
	        }
	  
	 public static void ExternalPayments(){
		    RestAssured.baseURI = "https://test-api.jai-kisan.com";
       //System.out.println("Executing EXTERNAL PAYMENT LINK PAYMENT");
      
       
       givenWithCommonHeaders().body("{\r\n"
                       + "    \"amount\": \"1000\",\r\n"
                       + "    \"creditLineId\": \"JKFPO202405579132\" \r\n"
                       + "}")
               .when().post("automation-webhooks/razorpay/externalPaymentLink")
               .then().log().all().assertThat().statusCode(200);
       System.out.println("Exection of EXTERNAL PAYMENT LINK PAYMENT is completed");
		 
	 }	 
	 
	 public static void CredgenicsBulkPayment() {
		 
       // Get the current date
       LocalDate currentDate = LocalDate.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       String formattedDate = currentDate.format(formatter);
       System.out.println(formattedDate);
		 
		    RestAssured.baseURI = "https://test-api.jai-kisan.com";
		    givenWithCommonHeaders().body("{\r\n"
	                        + "    \"loan_id\": \"JKFPO202405579132\",\r\n"
	                        + "    \"amount_recovered\": \"1\",\r\n" + "    \"recovery_status\": \"Fully Recovered\",\r\n"
	                        + "    \"source\": \"excel\",\r\n"
	                        + "    \"recovery_date\": \"" + formattedDate + "\"\n" // should get the current date
	                        + "}")
	                .when().post("automation-webhooks/credgenics/payment").then().log().all().statusCode(200);

	        System.out.println("Execution of  Bulk payment comepleted");
		 
	 }
	 
	// FOS ONLINE DEPOSIT
	 public static void FosOnlineDeposit() {
		 
		    RestAssured.baseURI = "https://test-api.jai-kisan.com";
		    
       
       //given().header("x-api-key", X_API_KEY).header("Content-Type", CONTENT_TYPE)
		    
		    givenWithCommonHeaders().body("{\r\n"
               + "    \"loan_id\": \"JKFPO202405579132\",\r\n"
               + "    \"amount_recovered\": \"1.5\"\r\n"
               + "}").when().post("automation-webhooks/credgenics/fos-online-deposit-verification").then().log().all()
               .statusCode(200);
		    
		    System.out.println("Exection of FOS ONLINE PAYMENT is completed");
	 }					
	 
	 //FOS CASH DEPOSIT
	 public static void FosCashDeposit() {
		 
		    RestAssured.baseURI = "https://test-api.jai-kisan.com";
		    
		givenWithCommonHeaders().body("{\r\n"
       + "    \"amount_recovered\": \"1\",\r\n"
       + "    \"loan_id\": \"JKFPO202405579132\",\r\n"
      // + "    \"visit_id\": \"MZ3KkxzBUDAA\",\r\n"
       + "    \"payment_method\": \"Cash\"\r\n"
       + "}").when().post("automation-webhooks/credgenics/fos-deposit-verification").then().log().all()
       .statusCode(200);
		
		System.out.println("Exection of FOS CASH PAYMENT is completed");
	 }
	 
	 public static String bkTokenGeneration() {
		 
       RestAssured.baseURI = "https://www.googleapis.com";
       String key = "AIzaSyAT2E63kpZzlTv0AuNpMVyXnH8xY7fWN-U";

       // Sending the first request to get sessionInfo
       io.restassured.response.Response response = given()
               .queryParam("key", key)
               .header("Content-Type", CONTENT_TYPE)
               .body("{\r\n" + "    \"phoneNumber\": \"+917171717171\"\r\n" + "}")
               .when()
               .post("/identitytoolkit/v3/relyingparty/sendVerificationCode?key=AIzaSyAT2E63kpZzlTv0AuNpMVyXnH8xY7fWN-U")
               .then()
               .log().all()
               .extract().response();

     //  System.out.println(response.asString()); // Print the response body

       JsonPath firstResponseJsonPath = response.jsonPath();
       String sessionInfo = firstResponseJsonPath.getString("sessionInfo");
      // System.out.println("Session Info: " + sessionInfo);

       // Sending the second request with sessionInfo to get Authorisation token
       io.restassured.response.Response response_Auth = given()
               .queryParam("key", key)
               .header("Content-Type", CONTENT_TYPE)
               .body("{\r\n"
                       + "    \"phoneNumber\": \"+917171717171\",\r\n"
                       + "    \"code\": \"140398\",\r\n"
                       + "    \"returnSecureToken\":true,\r\n"
                       + "    \"sessionInfo\": \"" + sessionInfo + "\"\r\n"
                       + "}")
               .when()
               .post("/identitytoolkit/v3/relyingparty/verifyPhoneNumber?key=AIzaSyAT2E63kpZzlTv0AuNpMVyXnH8xY7fWN-U")
               .then().log().all().extract().response();

       System.out.println(response_Auth.asString()); // Print the response body

       JsonPath secondResponseJsonPath = response_Auth.jsonPath();
       String idToken = secondResponseJsonPath.getString("idToken");
      // System.out.println("idToken: " + idToken);
       return idToken;
		 
	 }
	 
	 public static void bkQrRepayment() {
		 
		 String idToken = bkTokenGeneration();		 
		 
       // QR PAYMENTS - Qr CODE Create
       RestAssured.baseURI = "https://uat-api.bharatkhata.com";
       String QR = givenWithCommonHeaders().header("Authorization", "Bearer " + idToken)
               .body("{\r\n"
                       + "    \"loanApplicationId\": \"JKFPO202405579132\",\r\n"
                       + "    \"recollected_amount\": \"1\",\r\n"
                       + "    \"payment_method\": \"QR_CODE\"\r\n"
                       + "}")
               .when().post("/user-profile/repayment/create").then().log().all().statusCode(200).extract().response()
               .asString();

     //  System.out.println(QR);
       JsonPath js = new JsonPath(QR);
       String repaymentId = js.getString("repaymentId");
     //  System.out.println("************" + repaymentId + "*********");
       
       

       // QR PAYMENTS - Qr CODE GENERATE
       givenWithCommonHeaders().header("Authorization", "Bearer " + idToken)
               .body("{\r\n"
                       + "    \"record_id\": \"" + repaymentId + "\"\r\n"
                       + "}")
               .when().post("/user-profile/qr-generate").then().log().all().statusCode(200);

       // QR PAYEMENTS - Qr code event
       RestAssured.baseURI = "https://test-api.jai-kisan.com";

       givenWithCommonHeaders().header("Authorization", "Bearer " + idToken)
               .body("{\r\n"
                       + "    \"amount\": \"100\",\r\n"
                       + "    \"repaymentId\": \"" + repaymentId + "\" \r\n"
                       + "}")
               .when().post("/automation-webhooks/razorpay/qrCode").then().log().all().statusCode(200);
       
         System.out.println("Exection of BK QR PAYMENT is completed");
		 
	 }
	 
	 public static void bkPaymentLink() {
		 
	   String idToken = bkTokenGeneration();
       RestAssured.baseURI = "https://uat-api.bharatkhata.com";

       String paymentlink = givenWithCommonHeaders().header("Authorization", "Bearer " + idToken)
               .body("{\r\n"
                       + "    \"loanApplicationId\": \"JKFPO202405579132\",\r\n"
                       + "    \"payment_method\": \"PAYMENT_LINK\"\r\n"
                       + "}")
               .when().post("/user-profile/short-payment-link").then().log().all().statusCode(200).extract().response()
               .asString();

    
       JsonPath js1 = new JsonPath(paymentlink);
       String url = js1.getString("link");
     //  System.out.println("************" + url + "*********");

       JsonPath js2 = new JsonPath(paymentlink);
       String repaymentId_pl = js2.getString("repaymentId");
    //   System.out.println("*******Repayment id ****" + repaymentId_pl);

       // Extracting the desired part from the URL
       String extractedPart = null;
       try {
           URI uri = new URI(url);
           String path = uri.getPath();
           extractedPart = path.substring(path.lastIndexOf('/') + 1);
       } catch (Exception e) {
           e.printStackTrace();
       }

     //  System.out.println("Extracted part: " + extractedPart);

       RestAssured.baseURI = "https://test-api.jai-kisan.com";

       
       givenWithCommonHeaders() .body("{\r\n"
                       + "    \"loanApplicationId\": \"JKFPO202405579132\",\r\n"
                       + "    \"amount\": 20,\r\n"
                       + "    \"shortCode\": \"" + extractedPart + "\"\r\n"
                       + "}")
               .when().post("/lms-repayments/payment-link").then().log().all().statusCode(200);

       // Settlement api
       
       givenWithCommonHeaders() .body("{\r\n"
                       + "    \"amount\": \"2000\",\r\n"
                       + "    \"repaymentId\": \"" + repaymentId_pl + "\"\r\n"
                       + "}")
               .when().post("/automation-webhooks/razorpay/paymentLink").then().log().all().statusCode(200);
       
       System.out.println("Exection of BK Link PAYMENT is completed");
	 
	 }
	
 }



