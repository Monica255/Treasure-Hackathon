import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  productList: any;
  constructor(private httpClient: HttpClient) {
    this.productList=[];
   }

  ngOnInit(): void {
    this.getProductList()
  }

  getProductList()
  {
    this.httpClient.get('https://treasure-hacktahons-backend-api.vercel.app/sellers/product/affaa78b-a171-4d3d-9c1a-e6512332dab6').subscribe((result:any)=>{
      this.productList=result;
    })
  }
}
