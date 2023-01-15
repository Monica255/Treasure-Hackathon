# Basic Workflow
1. The seller publishes the product to be seen by contributors (donors) via the POST METHOD:"/sellers/product/:sellerId"
2. The product can be accessed by contributors (donors) via METHOD: GET "/contributor/products". this is because the contributor (donor) will make redemption on the product published by the seller
3. The contributor (donor) makes redemption via METHOD: POST "/contributor/purchase/:contributorId"
4. After being redeemed, the redeemed product will be included in the METHOD endpoint: GET "/users/leftoffers" where this endpoint can be seen by contributors (foundations or users)
5. Contributors (foundations or users) can claim leftoffers available through the METHOD endpoint: POST "/users/claim/:contributorId"

# Workflow Details
### vending routers
- The POST method on the "/sellers/product/:sellerId" endpoint is used to post food products, which will later be redeemed by donors. of course, you must have the right id_seller to post data, if id_seller is not validated, then post data cannot be operated. Parameters required in POST data (name:string, qty:number, desc:string, prize:number)
- The GET method on the "/sellers" endpoint is used to see a list of all sellers in the system.
- The GET method on the "/sellers/product/:sellerId" endpoint is used to view a list of products in a seller
- The GET method on the endpoint "/sellers/history/:sellerId" is used to view the redemption and claimer history in a seller
- The GET method on the endpoint "/sellers/leftoffer/:sellerId" is used to view products that have been redeemed by contributors (donors) but have not been claimed by contributors (users/foundations)
- The DELETE method on the endpoint "/seller/product/:sellerId/:productId" is used to delete a product from the seller regardless of the risk.

### contributor router
- The GET method on "/contributor/products" is used to see all the food sold by the seller
- GET method on "/contributor/history/:contributorId" is used to view the transaction history of contributors
- The POST method in "/contributor/signin" is used to sign-in. if valid then the server will respond in JSON form with contents (contributor_id:string, username:string, email:string, role:string). parameters needed in POST data: (email:string, password:string)
- The Post method on "/contributor/purchase/:contributorId" is used to redeem food products sold by the seller. Parameters needed in POST data: (qty:number, prize:number, id_seller:string, id_product:string). Things to note if the contributorId is invalid (where the id of the user who wants to make a redemption does not include the donor role) then this operation cannot be performed

### user router
- The GET method in "/users/leftoffers" is used to view all food lists from sellers that have been redeemed by donors.
- The POST method on "/users/claim/:contributorId" is used to claim leftover food from sellers who have been redeemed by donors. Parameters needed in the POST data: (id_seller:string, id_product:string) this is to determine what remainder will be claimed by the user.