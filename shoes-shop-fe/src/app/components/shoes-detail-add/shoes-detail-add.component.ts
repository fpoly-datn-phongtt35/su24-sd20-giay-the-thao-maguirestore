import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { Router } from "@angular/router";
import { log, warn } from "console";
import { ConfirmationService, MessageService } from "primeng/api";
import { FileRemoveEvent, FileUpload } from "primeng/fileupload";
import { forkJoin } from "rxjs";
import { AppConstants } from "src/app/app-constants";
import { catchError } from "rxjs/operators";
import { of } from "rxjs";
interface AutoCompleteCompleteEvent {
  originalEvent: Event;
  query: string;
}

interface expandedRows {
  [key: string]: boolean;
}
export interface ShoesDetail {
  id?: number;
  code: string;
  price: number;
  importPrice: number;
  tax: number;
  quantity: number;
  status: number;
  images: File[];
  description: string;
  shoes: {
    id: number;
    name: string;
  };
  brand: {
    id: number;
    name: string;
  };
  color: {
    id: number;
    name: string;
  };
  size: {
    id: number;
    name: string;
  };
}

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}

@Component({
  selector: "app-shoes-detail-add",
  templateUrl: "./shoes-detail-add.component.html",
  styleUrls: ["./shoes-detail-add.component.css"],
})
export class ShoesDetailAddComponent implements OnInit {
  countries: any[] | undefined;
  shoeVariants: ShoesDetail[];
  shoesVariantsList: any[];
  selectedCountry: any;

  expandedRows: expandedRows = {};

  productDialog: boolean = false;

  shoes: any[]; //products mean shoes

  brands: any[];

  sizes: any[];

  colors: any[];

  selectedColors: any[];
  selectedSizes: any[];

  filteredShoes: any[];
  filteredBrands: any[];
  filteredSizes: any[];
  filteredColors: any[];

  product: any;

  selectedProducts!: any[] | null;

  submitted: boolean = false;

  statuses!: any[];

  formGroup: FormGroup;

  checked: boolean = false;

  shoes_detail: ShoesDetail;
  displayTable: boolean = false;

  uploadedFiles: any[] = [];
  uploadMoodelFiles: any[] = [];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private http: HttpClient,
    private route: Router,
    private fb: FormBuilder
  ) {
    this.formGroup = this.fb.group({
      shoes: [null, Validators.required],
      checked: [false, Validators.required],
      // brand: [null, Validators.required],
      description: [null, Validators.required],
      importPrice: [
        null,
        [Validators.required, Validators.min(0), Validators.max(999999999)],
      ],
      quantity: [
        null,
        [Validators.required, Validators.min(0), Validators.max(999999999)],
      ],
      price: [
        null,
        [Validators.required, Validators.min(0), Validators.max(999999999)],
      ],
      tax: [
        null,
        [Validators.required, Validators.min(0), Validators.max(999999999)],
      ],
      color: [null, Validators.required],
      size: [null, Validators.required],
    });
  }

  ngOnInit() {
    this.fetchData();
    this.statuses = [
      { label: "INSTOCK", value: 1 },
      { label: "LOWSTOCK", value: 2 },
      { label: "OUTOFSTOCK", value: 3 },
      { label: "not available", value: 0 },
    ];
  }

  fetchData() {
    // forkJoin({
    //   shoes: this.http
    //     .get<any>(`${AppConstants.BASE_URL_API}/api/shoes`)
    //     .pipe(catchError((error) => of([]))),
    //   sizes: this.http
    //     .get<any>(`${AppConstants.BASE_URL_API}/api/sizes`)
    //     .pipe(catchError((error) => of([]))),
    //   colors: this.http
    //     .get<any>(`${AppConstants.BASE_URL_API}/api/colors`)
    //     .pipe(catchError((error) => of([]))),
    //   shoesVariantsList: this.http
    //     .get<any>(`${AppConstants.BASE_URL_API}/api/shoes-details`)
    //     .pipe(catchError((error) => of([]))),
    // }).subscribe((response) => {
    //   this.shoes = response.shoes;
    //   this.sizes = response.sizes;
    //   this.colors = response.colors;
    //   this.shoesVariantsList = response.shoesVariantsList;
    // });
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/shoes")
      .subscribe((response) => {
        this.shoes = response;
      });
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/brand")
      .subscribe((response) => {
        this.brands = response;
      });
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/sizes")
      .subscribe((response) => {
        this.sizes = response;
      });
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/color/")
      .subscribe((response) => {
        this.colors = response;
      });
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/shoes-details")
      .subscribe((response) => {
        this.shoesVariantsList = response;
      });
  }

  /**
   * Kiểm tra xem FormControl đã bị lỗi và đã được tương tác (click) hay chưa.
   * @param controlName Tên của FormControl cần kiểm tra
   * @returns True nếu FormControl có lỗi và đã được tương tác, ngược lại trả về false hoặc undefined
   */
  isFormControlInvalidAndTouched(controlName: string): boolean | undefined {
    // Lấy FormControl từ FormGroup bằng cách sử dụng tên của FormControl
    const control = this.formGroup.get(controlName);
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

  clearSelectedFiles() {
    this.uploadedFiles = [];
  }

  clearSelectedFilesChild() {
    this.product.images = [];
  }

  removeSelectedImage(event: FileRemoveEvent) {
    const fileToRemove = event.file; // Assuming you want to remove the first file in the event
    const index = this.uploadedFiles.findIndex(
      (uploadedFile) => uploadedFile.name === fileToRemove.name
    );
    if (index !== -1) {
      this.uploadedFiles.splice(index, 1);
    }
  }
  selection(files: File[]): void {
    console.log(files.length);
    if (files.length > 5) {
      files.splice(5);
    }
    this.uploadedFiles = files;
  }

  removeSelectedImageChild(event: FileRemoveEvent, produceImages: File[]) {
    const fileToRemove = event.file; // Assuming you want to remove the first file in the event
    const index = produceImages.findIndex(
      (uploadedFile) => uploadedFile.name === fileToRemove.name
    );
    if (index !== -1) {
      produceImages.splice(index, 1);
    }
    console.log(this.uploadedFiles);
    console.log(this.shoeVariants);
  }

  selectionChild(event: UploadEvent, produceImages: File[]) {
    for (let i = 0; produceImages.length < 5; i++) {
      produceImages.push(event.files[i]);
      if (produceImages.length == 5) break;
    }
  }

  saveVariants() {
    this.confirmationService.confirm({
      message: "Are you sure you want to create ?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        const httpOptions = {
          headers: new HttpHeaders({ enctype: "multipart/form-data" }),
        };
        this.shoeVariants.forEach((variant) => {
          let isCodeFound = this.shoesVariantsList.some(
            (v) => v.code == variant.code
          );
          if (isCodeFound) {
            this.messageService.add({
              severity: "warning",
              summary: "Exist",
              detail:
                "Variants " +
                variant.shoes.name +
                "-" +
                variant.brand.name +
                "[" +
                variant.color.name +
                "-" +
                variant.size.name +
                "]" +
                " Existed",
              life: 3000,
            });
          } else {
            const objectTest = new FormData();
            const { images, ...variantWithoutImages } = variant;
            console.log(variantWithoutImages);
            let jsonBlob = new Blob([JSON.stringify(variantWithoutImages)], {
              type: "application/json",
            });
            console.log("blob");
            objectTest.append("shoesDetailsDTO", jsonBlob);

            variant.images.forEach((image) => {
              objectTest.append("images", image);
            });
            console.log(objectTest);
            this.http
              .post<any>(
                AppConstants.BASE_URL_API +
                  "/api/v1/shoes-details/shoes-details-image",
                objectTest,
                httpOptions
              )
              .subscribe(
                (response) => {
                  this.messageService.add({
                    severity: "success",
                    summary: "Successful",
                    detail: "Variants [" + variant.code + "] Created",
                    life: 3000,
                  });
                },
                (error) => {
                  this.messageService.add({
                    severity: "error",
                    summary: "ERROR",
                    detail: "Variants [" + variant.code + "]  Create Error",
                    life: 3000,
                  });
                }
              );
          }
        });
        this.shoeVariants = [];
        this.http
          .get<any>(AppConstants.BASE_URL_API + "/api/v1/shoes-details")
          .subscribe((response) => {
            this.shoesVariantsList = response;
          });
      },
    });
  }

  async generateShoeVariants(selectedColors: any[], selectedSizes: any[]) {
    this.http
      .get<any>(AppConstants.BASE_URL_API + "/api/v1/shoes-details")
      .subscribe((response) => {
        this.shoesVariantsList = response;
      });
    const variants: ShoesDetail[] = [];
    for (const color of selectedColors) {
      for (const size of selectedSizes) {
        const shoes = this.formGroup?.get("shoes")?.value;
        // const brand = this.formGroup?.get("brand")?.value;
        const brand = shoes.brand;
        console.log(brand);
        const variant: ShoesDetail = {
          shoes: { id: shoes.id, name: shoes.name },
          status: this.formGroup?.get("checked")?.value == false ? 0 : 1,
          quantity: this.formGroup?.get("quantity")?.value,
          brand: { id: brand.id, name: brand.name },
          description: this.formGroup?.get("description")?.value,
          importPrice: this.formGroup?.get("importPrice")?.value,
          price: this.formGroup?.get("price")?.value,
          tax: this.formGroup?.get("tax")?.value,
          code: shoes.code + brand.code + color.code + size.code,
          color: { id: color.id, name: color.name },
          size: { id: size.id, name: size.name },
          images: [...this.uploadedFiles],
        };
        const isCodeFound = await this.fetchProducts(
          shoes.id,
          brand.id,
          size.id,
          color.id
        );
        console.log(isCodeFound);
        if (isCodeFound && isCodeFound.id != null) {
          this.messageService.add({
            severity: "warn",
            summary: "Exist",
            detail:
              "Variants " +
              variant.shoes.name +
              "-" +
              variant.brand.name +
              "[" +
              variant.color.name +
              "-" +
              variant.size.name +
              "]" +
              " Existed",
            life: 3000,
          });
        } else {
          variants.push(variant);
          this.messageService.add({
            severity: "success",
            summary: "Generate",
            detail:
              "Variants " +
              variant.shoes.name +
              "-" +
              variant.brand.name +
              "[" +
              variant.color.name +
              "-" +
              variant.size.name +
              "]" +
              " Generated",
            life: 3000,
          });
        }
      }
    }
    console.log(variants);
    return variants;
  }

  async fetchProducts(shid: any, brid: any, siid: any, clid: any) {
    const productId = { shid: shid, brid: brid, siid: siid, clid: clid };
    const apiUrl = `http://localhost:8088/api/v1/shoes-details`;
    // Make the HTTP request
    try {
      const response = await this.http.post<any>(apiUrl, productId).toPromise();
      return response;
    } catch (error) {
      console.error("Error fetching product details:", error);
      return null;
    }
  }

  editProduct(product: ShoesDetail) {
    this.product = {};
    this.product = product;
    this.productDialog = true;
  }

  backToList() {
    this.route.navigate(["admin/shoes-detail"]);
  }

  async showTable() {
    // if (this.formGroup.valid) {
    const selectedColors = this.formGroup?.get("color")?.value;
    const selectedSizes = this.formGroup?.get("size")?.value;

    console.log(selectedColors);
    console.log(selectedSizes);

    // Sử dụng await để đợi kết quả từ generateShoeVariants
    this.shoeVariants = await this.generateShoeVariants(
      selectedColors,
      selectedSizes
    );
    console.log(this.shoeVariants);
    this.displayTable = true;
    // } else {
    //   this.markAllFormControlsAsTouched(this.formGroup);
    //   this.messageService.add({
    //     severity: "error",
    //     summary: "ERROR",
    //     detail: "Variants Create Validate Error",
    //     life: 3000,
    //   });
    // }
  }

  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: "Are you sure you want to delete the selected products?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.shoeVariants = this.shoeVariants.filter(
          (val) => !this.selectedProducts?.includes(val)
        );
        this.selectedProducts = null;
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Products Deleted",
          life: 3000,
        });
      },
    });
  }

  deleteProduct(product: ShoesDetail) {
    this.confirmationService.confirm({
      message:
        "Are you sure you want to delete " +
        product.shoes.name +
        "[" +
        product.color.name +
        " - " +
        product.size.name +
        "]" +
        "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.shoeVariants = this.shoeVariants.filter((val) => val !== product);
        this.product = {};
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Product Deleted",
          life: 3000,
        });
      },
    });
  }

  getSeverity(status: any) {
    switch (status) {
      case 1:
        return "success";
      case 2:
        return "warning";
      case 3:
        return "danger";
      case 0:
        return "danger";
    }
    return "danger";
  }

  getStatus(status: number) {
    switch (status) {
      case 0:
        return "Không khả dụng";
      case 1:
        return "Khả dụng";
    }
    return "Không khả dụng";
  }

  filterList(
    event: AutoCompleteCompleteEvent,
    list: any[],
    filteredList: string
  ) {
    let filtered: any[] = [];
    let query = event.query;
    for (let i = 0; i < list.length; i++) {
      let products = list[i];
      if (products.name.toLowerCase().includes(query.toLowerCase())) {
        filtered.push(products);
      }
    }
    switch (filteredList) {
      case "shoes": {
        this.filteredShoes = filtered;
        // this.filteredBrands = filtered.map(shoe => shoe.brand);
        // console.log(this.filteredShoes);
        // console.log(this.filteredBrands);
        break;
      }
      case "brands": {
        this.filteredBrands = filtered;
        break;
      }
      case "sizes": {
        this.filteredSizes = filtered;
        break;
      }
      case "colors": {
        this.filteredColors = filtered;
        break;
      }
      default: {
        break;
      }
    }
  }
}
