# Rest-api-for-medical-Store-


Introduction

I have exposed few RESTful APIs to interact with the medicines stored in the database and perform basic CRUD operations. Have also
introduced an API which will keep an audit of all the successfully placed orders of medicines in stock and I have also provided an API which
returns the list of ordered medicines.


Overview

Medicine collection contains the following details :-
S.No Key Information
1 name Name of the medicine
2 batchNo Batch Number of the medicine
3 expiryDate Expiry date of the medicine
4 balanceQty The quantity of any medicine in the stock
5 packaging Packaging of the medicine
6 uniqueCode Unique Code specific to any medicine
7 schemes Scheme of the particular batch of the medicine
8 mrp Maximum retail price of the medicine
9 manufacturer Manufacturer of the medicine
10 hsnCode Harmonized system of nomenclature code to classify goods


Order collection contains the following details :-

S.No Key Information

1 id Unique order Id
2 uniqueId Unique id of any medicine
3 quantity Number of ordered quantity
4 name Name of the medicine



API Request/Response

1. uploadCsv API

Problem Statement : This API takes a CSV file as input and update the database with the medicines.
Method : POST
Event : Upload
URL : http://localhost:8080/uploadCSV
Response:


2. medicines API

Problem statement :This API takes each and every entity(except Hsn no. , schemes,mrp and packaging) as a parameter and it will return list of
medicines based on the search parameters.

Input : Any entity which exists.
Output : List of names of medicines.
Method :GET
Event : Search
URL : http://localhost:8080/medicines

Response
(1)When user will enter the name

(2) User can fetch the medicine detail by entering batchNo

(3) User can see the medicine detail by entering UniqueCode

(4) With manufacturer name user can see the medicine details

(5)User can see all the expired medicine

(6)User can see all the non expired medicine

(7)User can see the list of non expired medicine which will expire in future by entering the upcoming date

(8)User can see the list of medicine by applying filters

3.medicine API

Problem Statement :This API return the details of one medicine from the Database based on the uniqueCode, name and batch no.

Input : uniqueCode, name and batch no
Output : Medicine complete details. Details includes uniqueCode,name, manufacturer, packaging, hsn no, mrp, total stock quantity and all
batches specific information like batch no, expiry, quantity, schemes (if any).

Method :GET
Event : Search
URL : http://localhost:8080/medicine

Response
(1) When user will send uniqueCode,name and batch no

(2) if user will forget to pass any of the above mentioned constraints (uniqueCode,name and batch no)


4. placeOrder API

Problem Statement : This API place an order for a list of medicines. It will check if the available quantity in the medicine database is greater than
the ordered quantity then it will place the order . Further it will update the medicine database with updated quantity of the ordered medicine. Input
for the API should be a list of json objects. Format of json object is mentioned below:
{
“uniqueId”:XXX,

“quantity” : 10,
“name”: XXXX
}
Method:PUT
Event:Place Order
URL :http://localhost:8080/placeOrder

Response

(1) The available quantity of medicine before ordering

(2) Now User has ordered the medicine and the ordered quantity is available

(3) The available quantity of medicine after ordering

(4) User order any medicine and the ordered quantity of medicine is more than the available in the stock.

(5) When the quantity of medicine is zero or any medicine is not in the stock

(6)If any of the constraint is missing


5. Order API

Problem Statement : This API will return the details of the ordered medicine.

Method:GET
Event: order
URL :http://localhost:8080/order

Response
(1)User can see the ordered items with name

(2)User can see the ordered items with uniqueId

(3)User can see the ordered medicine within the range of two date

(4)User can also fetch the ordered medicine by entering the start date only and end date will be automatically taken as the date on
which the request has been made.

Tools

Eclipse
MongoDB 5.0.10
Postman
Robo 3T 1.4.4
Git 2.37.0
Review of Literature
The Spring Boot Framework is a well-known and widely used framework for web and corporate application development. Spring boot uses
dependency injection that allows you to create beans via XML, annotations, and Java code. It's a promising platform for creating

