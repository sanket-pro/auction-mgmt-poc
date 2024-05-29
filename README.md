# auction-mgmt-poc

The Project contains following Microservice
1)	user-mgmt: Allows to register users of the system as SELLER and BUYER

POST Register buyer API url: http://localhost:8080/user/buyer
Payload: 
{
    "firstName":"BuyerFname2",
    "lastName": "BuyerLname",
    "email": "Buyer2@email.com",
    "address":"Buyer2 address"
}

POST Register seller API url : http://localhost:8080/user/seller
Payload: 
{
    "firstName":"sellerFname2",
    "lastName": "sellerLname",
    "email": "serller2@email.com",
    "address":"seller2 address"
}



2)	product-mgmt: Is used to maintain the products created by SELLER for an Auction. Also it provides way to access individual product details by BUYER to know more about product like product name, min price, productId etc. The product id is used in auction to identify the product and put a bid on to.

POST ADD-product url: http://localhost:8081/product
Payload: 
{
    "productName": "D",
    "price": 400,
    "sellerId": 2
}

GET-Product: http://localhost:8081/product/id?pid=4
Patch –product: http://localhost:8081/product/availability?pid=1&availability=NO


3)	auction-mgmt:  Is used to maintain details of Auction and Bidding. This service allows SELLER to CREATE Auction on his already registered products. It also has API to view open auctions which BUYER can participate into using buyer token, auction id and product id. It also provides SELLER an API to invoke auction closure and view the Results. BUYER can view status of the all the bids using BUYER token



POST Create auction:
http://localhost:8082/auction
{
    "startDateMMDDYYY": "05292024",
    "endDateMMDDYYYY": "05292024",
    "token": 2
}
	
	GET-Auction: http://localhost:8082/auction/open

	PATCH-Auction (to close): http://localhost:8082/auction/close?auctionId=4

	CREATE BID: http://localhost:8082/bid
{
   	 "productId": 4,
  	  "auctionId": 4,
   	 "bidPrice": 400,
   	 "token": 3
}
	
GET-BID-BY-BUYER-TOKEN 
http://localhost:8082/bid/buyer
{
    "token": 2
}

 


