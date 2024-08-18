import { Component, OnInit } from "@angular/core";
import { LayoutService } from "src/app/layout/service/app.layout.service";
import { LoginService } from "src/app/service/login.service";
import { Router } from "@angular/router";
import { ShoesdetailService } from "src/app/service/shoesdetail.service";
import { ShoesDetailCustom } from "src/app/model/ShoesDetailCustom";
import { CartDetailService } from "src/app/service/cart-detail.service";
import { CartDetailCustomerService } from "src/app/service/cartdetailcustom.service";
import { AppConstants } from "src/app/app-constants";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-client.home",
  templateUrl: "./client.home.component.html",
  styleUrls: ["./client.home.component.css"],
})
export class ClientHomeComponent implements OnInit {
  shoesDetailCustom: ShoesDetailCustom[];
  shoesDetailCustomNewDisCount: ShoesDetailCustom[];
  shoesDetailBestSeller: ShoesDetailCustom[];
  images: any[] | undefined;
  constructor(
    public layoutService: LayoutService,
    private loginService: LoginService,
    private router: Router,
    private shoesdetailService: ShoesdetailService,
    private cartDetailService: CartDetailService,
    private cartDetailCustomerService: CartDetailCustomerService,
    private http: HttpClient
  ) {}
  ngOnInit(): void {
    this.images = [
      "../../../../assets/images/banner2.jpg",
      "../../../../assets/images/banner3.jpg",
      "../../../../assets/images/banner4.jpg",
    ];
    if (sessionStorage.getItem("access_token") != null) {
      this.cartDetailService.getCount().subscribe((Response) => {
        this.cartDetailCustomerService.setData(Response);
      });
    }
    if (
      sessionStorage.getItem("access_token") == null &&
      sessionStorage.getItem("oathu2") != null
    ) {
      this.loginService.ouath2().subscribe({
        next: (body: any) => {
          if (body && body?.id_token) {
            sessionStorage.setItem("access_token", body?.id_token);
            this.cartDetailService.getCount().subscribe((Response) => {
              this.cartDetailCustomerService.setData(Response);
            });
          }
        },
      });
    }
    this.shoesdetailService.getNewShoesDetail().subscribe((response) => {
      this.shoesDetailCustom = response;
      console.log(this.shoesDetailCustom);
    });
    // this.http
    //   .get<any>(AppConstants.BASE_URL_API + "/api/v1/shoes-details").subscribe((response) => {
    //   this.shoesDetailCustom = response;
    //   console.log(this.shoesDetailCustom);
    // });
    this.shoesdetailService
      .getNewDiscountShoesDetail()
      .subscribe((response) => {
        this.shoesDetailCustomNewDisCount = response;
      });
    this.shoesdetailService.getBestSellerShoesDetail().subscribe((response) => {
      this.shoesDetailBestSeller = response;
    });
  }

  shoesDetail(shoesDetail: ShoesDetailCustom) {
    const queryParams = {
      shid: shoesDetail.idShoe,
      brid: shoesDetail.idBrand,
      siid: shoesDetail.idSize,
      clid: shoesDetail.idColor,
    };

    this.router.navigate(["/client/shoes-detail"], {
      queryParams: queryParams,
    });
  }
}
