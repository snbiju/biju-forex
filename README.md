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
http://localhost:8088/forex/create(POST REQUEST)

Request body

  {
     "userId": "1",
     "orderType": "ASK",
     "currency": "GBP/USD",
     "price": 1.2100,
     "amount": 100
   }
   
 **Cancel Order**
 (DELETE REQUEST)
 http://localhost:8088/forex/cancel
 {
      "userId": "1",
      "orderType": "ASK",
      "currency": "GBP/USD",
      "price": 1.2100,
      "amount": 100
    }
   
**Find Order**
(GET REQUEST)
 http://localhost:8088/forex/matched
       
**Unmatched Order**
(GET REQUEST)
 http://localhost:8088/forex/unmatched
 
    
**Get All Order**
(GET REQUEST)
 http://localhost:8088/forex/all