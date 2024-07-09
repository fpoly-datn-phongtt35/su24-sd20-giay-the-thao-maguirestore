import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CategoryData } from "../model/CategoryData";

@Injectable({
  providedIn: "root",
})
export class CategoryService {
  private apiUrl = "http://localhost:8080/api/v1/categories";

  constructor(private http: HttpClient) {}
  getAllCategory() {
    return this.http.get(this.apiUrl + "/all");
  }
  getCategories() {
    return this.http.get(this.apiUrl);
  }
  getCategoriesRemoved() {
    return this.http.get(this.apiUrl + "/removed");
  }
  editCategory(category: CategoryData): Observable<CategoryData> {
    const updateUrl = `${this.apiUrl}/${category.id}`;
    return this.http.put<CategoryData>(updateUrl, category);
  }

  saveCategory(category: any): Observable<CategoryData> {
    return this.http.post<CategoryData>(this.apiUrl, category);
  }

  deleteCategory(id: number): Observable<any> {
    // Để xóa sản phẩm, gửi yêu cầu HTTP DELETE với id của sản phẩm cần xóa
    const deleteUrl = `${this.apiUrl}/${id}`;
    return this.http.delete(deleteUrl);
  }
}
