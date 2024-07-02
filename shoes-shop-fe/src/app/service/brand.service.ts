import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BrandData } from "../model/Brand";
import { Observable } from "rxjs";
import { BrandSave } from "../model/AddBrand";

@Injectable({
  providedIn: "root",
})
export class BrandService {
  private apiUrl = "http://localhost:8080/api/v1/brand";

  constructor(private http: HttpClient) {}

  getBrand() {
    return this.http.get(this.apiUrl + "/brands");
  }
  getBrandRemoved() {
    return this.http.get(this.apiUrl + "/removed");
  }
  editBrand(brand: BrandData): Observable<BrandData> {
    const updateUrl = `${this.apiUrl + "/brands"}/${brand.id}`;
    return this.http.put<BrandData>(updateUrl, brand);
  }

  saveBrand(brand: BrandSave): Observable<BrandData> {
    return this.http.post<BrandData>(this.apiUrl + "/brands", brand);
  }

  deleteBrand(id: number): Observable<any> {
    // Để xóa sản phẩm, gửi yêu cầu HTTP DELETE với id của sản phẩm cần xóa
    const deleteUrl = `${this.apiUrl + "/brands"}/${id}`;
    return this.http.delete(deleteUrl);
  }
}
