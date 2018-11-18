**Basic requirement**

Java 8 or higher

Spring boot 2.1.0.RELEASE
Apache Maven 4.0.0
Postman or any other REST API Testing tool
(Can use swagger documentation as well)

**To run**

clone https://github.com/snbiju/biju-forex.git / download

go to biju-forex-master

mvn spring-boot:run


**Swagger Documentation**
url: http://localhost:8088/swagger-ui.html

**Create new Order**
(POST REQUEST)
http://localhost:8088/forex/order(POST REQUEST)

Request body

 {
        "orderId": "2",
        "currency": "GBP",
        "amount":"6500.00"
   }
   
 **Cancel Order**
 (DELETE REQUEST)
 http://localhost:8088/forex/cancel
 {
        "orderId": "1",
        "currency": "GBP",
        "amount":"6500.00"
   }
   
  **Not matched Order**
(GET REQUEST)
 http://localhost:8088/forex/mismatch/3
 
  **Find Order** (By Order Id)
    (GET REQUEST)
    
    http://localhost:8088/forex/order/1
    
**Get All Order**
(GET REQUEST)
http://localhost:8088/forex/order