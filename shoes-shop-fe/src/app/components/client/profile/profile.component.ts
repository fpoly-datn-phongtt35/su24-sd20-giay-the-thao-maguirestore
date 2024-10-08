import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  user: any;
  check: boolean = true;
  visible: boolean = false;

  constructor(
    private userService: UserService,
    private http: HttpClient
  ){}

  ngOnInit(): void {
    if(sessionStorage.getItem('access_token') != null){
      this.getAccount();
      this.check = true;
    }else {
      this.check = false;
    }
    
  }

  showDialog() {
    this.visible = true;
  }

  getAccount() {
    this.http.get("http://localhost:8088/api/v1/account").subscribe((response) => {
      console.log("Response:", response);
      this.user = response;
    });
  }
}
