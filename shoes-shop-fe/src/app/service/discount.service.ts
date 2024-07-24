import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class DiscountService {
  private baseUrl: string = "http://localhost:8088/api/v1/discount";
  constructor(private http: HttpClient) {}
  public getDiscounts() {
    return this.http.get<any>(this.baseUrl , {
      withCredentials: true,
    });
  }
  public saveDiscount(data: any) {
    return this.http.post<any>(this.baseUrl , data, {
      withCredentials: true,
    });
  }
  public getDiscount(id: number) {
    return this.http.get<any>(this.baseUrl + id, {
      withCredentials: true,
    });
  }
  public deleteDiscount(id: number) {
    return this.http.delete<any>(this.baseUrl + id, {
      withCredentials: true,
    });
  }
  public search(data: string) {
    return this.http.post<any>(this.baseUrl + "/search", data, {
      withCredentials: true,
    });
  }
}
