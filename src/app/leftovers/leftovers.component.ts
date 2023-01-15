import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-leftovers',
  templateUrl: './leftovers.component.html',
  styleUrls: ['./leftovers.component.css']
})
export class LeftoversComponent implements OnInit {
  oversList: any;
  constructor(private httpClient: HttpClient) {
    this.oversList=[];
   }

  ngOnInit(): void {
    this.getOversList()
  }

  getOversList()
  {
    this.httpClient.get('https://treasure-hacktahons-backend-api.vercel.app/sellers/leftoffer/affaa78b-a171-4d3d-9c1a-e6512332dab6').subscribe((result:any)=>{
      this.oversList=result;
    })
  }
}
