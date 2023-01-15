import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit{

  userList: any;
  constructor(private httpClient: HttpClient) {
    this.userList=[];
   }

  ngOnInit(): void {
    this.getUserList()
  }

  getUserList()
  {
    this.httpClient.get('https://treasure-hacktahons-backend-api.vercel.app/sellers/history/affaa78b-a171-4d3d-9c1a-e6512332dab6').subscribe((result:any)=>{
      this.userList=result;
    })
  }

}
