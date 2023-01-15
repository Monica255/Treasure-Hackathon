# Basic Workflow
1. seller melakukan publikasi produk yang akan di perlihatkan oleh contributor(donatur) melalui METHOD POST:"/sellers/product/:sellerId"
2. produk tersebut dapat diakses oleh contributor(donatur) melalui METHOD: GET "/contributor/products". hal ini karena contributor(donatur) akan melakukan penebusan pada produk yang dipublikasikan seller
3. contributor(donatur) melakukan penebusan melalui METHOD: POST "/contributor/purchase/:contributorId"
4. setelah ditebus produk yang ditebus akan masuk dalam endpoint  METHOD: GET "/users/leftoffers" dimana endpoint ini bisa dilihat oleh pihak contributor(yayasan atau user)
5. pihak contributor(yayasan atau user) dapat melakukan claim leftoffers yang tersedia melalui endpoint METHOD: POST "/users/claim/:contributorId"

# WorkFlow Details
### seller router
- method POST pada endpoint "/sellers/product/:sellerId" dilakukan untuk posting produk makanan, yang nantinya akan di tebus oleh donatur. tentunya harus memiliki id_seller yang tepat untuk melakukan posting data, jika id_seller tidak tervalidasi, maka post data tidak dapat dioperasikan. Parameter yang dibutuhkan dalam POST data (name:string, qty:number, desc:string, prize:number)
- method GET pada endpoint "/sellers" digunakan untuk melihat list semua seller dalam sistem.
- method GET pada endpoint "/sellers/product/:sellerId digunakan untuk melihat list produk dalam suatu seller
- method GET pada endpoint "/sellers/history/:sellerId" digunakan untuk melihat riwayat penebusan dan claimer dalam suatu seller
- method GET pada endpoint "/sellers/leftoffer/:sellerId" digunakan untuk melihat product yang telah ditebus oleh contributor(donatur) tapi belum di claim oleh contributor(user/yayasan)
- method DELETE pada endpoint "/seller/product/:sellerId/:productId" digunakan untuk menghapus sebuah produk dari seller apapun resikonya.

### contributor router
- method GET pada "/contributor/products" digunakan untuk melihat semua makanan yang dijual oleh pihak seller
- method GET pada "/contributor/history/:contributorId" dignunakan untuk melihat riwayat transaksi dari kontributor 
- method POST pada "/contributor/signin" digunakan untuk melakukan sign-in. jika valid maka server akan meresponse dalam bentuk JSON dengan isi (contributor_id:string, username:string, email:string, role:string). parameter yang dibutuhkan dalam POST data: (email:string, password:string)
- method Post pada "/contributor/purchase/:contributorId" digunakan untuk melakukan penebusan product makanan yang dijual oleh pihak seller. Parameter yang dibutuhkan dalam POST data: (qty:number, prize:number, id_seller:string, id_product:string). Hal yang perlu diperhatikan jika contributorId tidak valid (dimana id pengguna yang ingin melakukan penebusan bukan termasuk role donatur) maka tidak bisa melakukan operasi ini

### users router
- method GET pada "/users/leftoffers" digunakan untuk melihat semua list makanan dari pihak seller yang telah ditebus oleh donatur.
- method POST pada "/users/claim/:contributorId" digunakan untuk melakukan claim makanan leftovers dari seller yang sudah ditebus oleh donatur. Parameter yang dibutuhkan dalam POST data: (id_seller:string, id_product:string) hal ini karena untuk menentukan leftover apa yang akan di claim oleh pengguna.
