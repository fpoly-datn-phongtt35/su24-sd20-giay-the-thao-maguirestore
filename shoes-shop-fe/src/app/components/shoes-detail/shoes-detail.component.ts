import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Component, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Route, Router } from "@angular/router";
import { log } from "console";
import { ConfirmationService, MessageService } from "primeng/api";
import { FileRemoveEvent } from "primeng/fileupload";
import { Table } from "primeng/table";
import { AppConstants } from "src/app/app-constants";

interface expandedRows {
  [key: string]: boolean;
}
interface Product {
  id: number;
  code: string;
  price: number;
  importPrice: number;
  tax: number;
  quantity: number;
  description: string;
  status: number;
  shoes: {
    id: number;
    name: string;
  };
  brand: {
    id: number;
    name: string;
  };
  size: {
    id: number;
    name: string;
  };
  color: {
    id: number;
    name: string;
  };
}

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}
@Component({
  selector: "app-shoes-detail",
  templateUrl: "./shoes-detail.component.html",
  styleUrls: ["./shoes- detail.component.css"],
})
export class ShoesDetailComponent {
  expandedRows: expandedRows = {};

  productDialog: boolean = false;

  products: any[];

  product!: any;

  selectedProducts!: any[] | null;

  submitted: boolean = false;

  statuses!: any[];

  productForm: FormGroup;
  @ViewChild(Table) dt: Table;
  uploadedFiles: any[] = [];
  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private http: HttpClient,
    private route: Router
  ) {
    this.productForm = this.formBuilder.group({
      id: [null, Validators.required],
      code: [null, Validators.required],
      price: [null, Validators.required],
      importPrice: [null, Validators.required],
      tax: [null, Validators.required],
      quantity: [null, Validators.required],
      description: [null, Validators.required],
      status: [],
    });
  }

  ngOnInit() {
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/shoes-details")
      .subscribe((response) => {
        this.products = response;
        console.log(response);
      });
      
    this.statuses = [
      { label: "Khả dụng", value: 1 },
      { label: "Không khả dụng", value: 0 },
    ];
  }

  openNew() {
    this.route.navigate(["admin/shoes-detail-add"]);
  }

  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: "Bạn có chắc xóa các sản phẩm được chọn?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.selectedProducts?.every((e) => {
          this.http.delete(
            AppConstants.BASE_URL_API + "/api/v1/shoes-details/" + e.id
          );
        });
        this.products = this.products.filter(
          (val) => !this.selectedProducts?.includes(val)
        );
        this.selectedProducts = null;
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Xóa các sản phẩm thành công",
          life: 3000,
        });
      },
    });
  }

  clearSelectedFiles() {
    this.uploadedFiles = [];
  }

  ouput() {
    console.log(this.productForm.value);
    console.log(this.uploadedFiles);
  }

  editProduct(productData: Product) {
    this.product = { ...productData };
    this.productForm.setValue({
      id: productData.id,
      code: productData.code,
      price: productData.price,
      importPrice: productData.importPrice,
      tax: productData.tax,
      quantity: productData.quantity,
      description: productData.description,
      status: productData.status,
    });
    this.productDialog = true;
    console.log(this.product);
    console.log(productData);
  }

  deleteProduct(product: Product) {
    this.confirmationService.confirm({
      message: "Bạn có chắc chắn xóa ?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.http
          .delete(
            AppConstants.BASE_URL_API + "/api/v1/shoes-details/" + product.id
          )
          .subscribe(
            (response) => {
              this.products.forEach((producty) => {
                if (producty.id === product.id) {
                  producty.status = 0;
                  this.dt.filter(1, "status", "contains");
                }
              });
              this.product = {};
              this.messageService.add({
                severity: "success",
                summary: "Successful",
                detail: "Xóa sản phẩm thành công",
                life: 3000,
              });
            },
            (err) => {
              this.messageService.add({
                severity: "error",
                summary: "ERROR",
                detail: "Xóa thất bại",
                life: 3000,
              });
            }
          );
      },
    });
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
    this.uploadedFiles = [];
  }

  saveProduct() {
    if (this.productForm.valid) {
      this.confirmationService.confirm({
        message: "Bạn có muốn update sản phẩm này ?",
        header: "Confirm",
        icon: "pi pi-exclamation-triangle",
        accept: () => {
          const httpOptions = {
            headers: new HttpHeaders({ enctype: "multipart/form-data" }),
          };
          const objectTest = new FormData();
          this.submitted = true;
          this.product.id = this.productForm.get("id")?.value;
          this.product.code = this.productForm.get("code")?.value;
          this.product.price = this.productForm.get("price")?.value;
          this.product.importPrice =
            this.productForm.get("importPrice")?.value;
          this.product.tax = this.productForm.get("tax")?.value;
          this.product.quantity = this.productForm.get("quantity")?.value;
          this.product.description = this.productForm.get("description")?.value;
          this.product.status = this.productForm.get("status")?.value;
          this.product.brand = { id: this.product.shoes.brand.id };
          this.product.shoes = { id: this.product.shoes.id };
          this.product.color = { id: this.product.color.id };
          this.product.size = { id: this.product.size.id };
          console.log(this.product);
          let jsonBlob = new Blob([JSON.stringify(this.product)], {
            type: "application/json",
          });
          objectTest.append(
            "shoesDetailsDTO",
            jsonBlob,
            "shoesDetailsDTO.json"
          );
          this.uploadedFiles.forEach((image) => {
            objectTest.append("images", image);
          });
          this.http
            .put<any>(
              "http://localhost:8088/api/v1/shoes-details/shoes-details-image",
              objectTest,
              httpOptions
            )
            .subscribe(
              (response) => {
                this.messageService.add({
                  severity: "success",
                  summary: "Successful",
                  detail: "Đã cập nhật",
                  life: 3000,
                });
                this.products.forEach((producty) => {
                  if (producty.id === response.id) {
                    const sts = producty.status;
                    producty.tax = response.tax;
                    producty.description = response.description;
                    producty.quantity = response.quantity;
                    producty.importPrice = response.importPrice;
                    producty.price = response.price;
                    producty.status = response.status;
                    if (producty.status != sts)
                      this.dt.filter(1, "status", "contains");
                    if (this.uploadedFiles.length > 0)
                      producty.path =
                        this.uploadedFiles[0].objectURL.changingThisBreaksApplicationSecurity;
                  }
                });
              },
              (error) => {
                this.messageService.add({
                  severity: "error",
                  summary: "ERROR",
                  detail: "Lỗi cập nhật",
                  life: 3000,
                });
              }
            );
          console.log(this.product);
          this.productDialog = false;
          this.product = {};
        },
      });
    } else {
      console.log("/");
    }
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === id) {
        index = i;
        break;
      }
    }
    return index;
  }

  createCode(): string {
    var code = "SH";
    let number = this.products.length.toString();

    for (var i = 2; i < 8 - number.length; i++) {
      code += "0";
    }
    code += number;
    return code;
  }

  getSeverity(status: number) {
    switch (true) {
      case status < 0:
        return "danger";
      case status < 10:
        return "warning";
      case status > 10:
        return "success";
    }
    return "warning";
  }
  getStatus(status: number) {
    switch (true) {
      case status <= 0:
        return "Hết hàng";
      case status < 10:
        return "Còn ít";
      case status > 10:
        return "Còn hàng";
    }
    return "LOWSTOCK";
  }

  getSeverityProduct(status: number) {
    switch (status) {
      case 0:
        return "danger";
      case 1:
        return "success";
    }
    return "danger";
  }

  /**
   * Kiểm tra xem FormControl đã bị lỗi và đã được tương tác (click) hay chưa.
   * @param controlName Tên của FormControl cần kiểm tra
   * @returns True nếu FormControl có lỗi và đã được tương tác, ngược lại trả về false hoặc undefined
   */
  isFormControlInvalidAndTouched(controlName: string): boolean | undefined {
    // Lấy FormControl từ FormGroup bằng cách sử dụng tên của FormControl
    const control = this.productForm.get(controlName);
    // Kiểm tra xem FormControl có tồn tại, có lỗi và đã được tương tác hay không
    return control?.invalid && (control?.touched || control?.dirty);
  }

  /**
   * Đánh dấu tất cả các FormControl trong FormGroup và các nhóm FormGroup con như đã tương tác (touched).
   * @param formGroup Đối tượng FormGroup cần đánh dấu các FormControl đã tương tác
   */
  markAllFormControlsAsTouched(formGroup: any) {
    // Duyệt qua tất cả các tên của các FormControl trong FormGroup
    Object.keys(formGroup.controls).forEach((controlName) => {
      // Lấy ra FormControl tương ứng từ FormGroup
      const control = formGroup.get(controlName);
      control.markAsTouched({ onlySelf: true });
    });
  }

  selection(files: File[]): void {
    console.log(files.length);
    if (files.length > 5) {
      files.splice(5);
    }
    this.uploadedFiles = files;
  }
}
