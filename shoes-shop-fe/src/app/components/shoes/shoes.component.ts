import { Component, OnInit, ViewChild } from "@angular/core";
import { MessageService, SelectItem } from "primeng/api";
import { ShoesService } from "../../service/shoes.service";
import { Shoes } from "src/app/model/Shoes";
import { ShoesSave } from "src/app/model/AddShoes";
import { DialogModule } from "primeng/dialog";
import { BrandService } from "src/app/service/brand.service";
import { AutoCompleteCompleteEvent } from "primeng/autocomplete";
import { CategoryService } from "src/app/service/category.service";

@Component({
  selector: "app-Shoes",
  templateUrl: "./shoes.component.html",
  styleUrls: ["./shoes.component.css"],
  providers: [MessageService],
})
export class ShoesComponent implements OnInit {
  codeError: boolean = false;
  nameError: boolean = false;
  descriptionError: boolean = false;
  searchText: string = "";
  filteredShoess: Shoes[] = [];
  items: any[] = [];
  onSearchInputChange() {
    this.filterShoess();
  }

  filteredBrand: any[] = [];

  filteredCategory: any[] = [];

  filterBrand(event: any) {
    let query = event.query; // Lấy giá trị từ sự kiện tìm kiếm

    this.filteredBrand = this.items?.filter(
      (item) => item.name.toLowerCase().indexOf(query.toLowerCase()) !== -1
    );
  }

  filterCategory(event: any) {
    let query = event.query; // Lấy giá trị từ sự kiện tìm kiếm

    this.filteredCategory = this.items?.filter(
      (item) => item.name.toLowerCase().indexOf(query.toLowerCase()) !== -1
    );
  }

  filterShoess() {
    this.filteredShoess = this.Shoes.filter((b) => {
      return (
        b.code.toLowerCase().includes(this.searchText.toLowerCase()) ||
        b.name.toLowerCase().includes(this.searchText.toLowerCase())
      );
    });
  }
  validateField(field: string) {
    switch (field) {
      case "code":
        this.codeError =
          this.newShoes.code === "" || this.newShoes.code.length > 50;
        break;
      case "name":
        this.nameError =
          this.newShoes.name === "" || this.newShoes.name.length > 50;
        break;
    }
  }

  page: number = 2;
  currentPage: number = 0;

  allShoes: Shoes[] = [];

  Shoes: Shoes[];
  statuses: SelectItem[];
  clonedShoess: { [s: string]: Shoes } = {};
  displayConfirmDialog: boolean = false;
  selectedShoesId: number = 0;
  displayAddDialog: boolean = false;
  visible: boolean = false;
  newShoes: ShoesSave = { code: "", name: "", category: "", brand: "" };

  constructor(
    private ShoesService: ShoesService,
    private brandService: BrandService,
    private categoryService: CategoryService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.ShoesService.getShoes().subscribe((data: any) => {
      this.allShoes = data;
      this.Shoes = this.allShoes.filter((s, index) => {
        return (
          this.currentPage * this.page <= index &&
          index < (this.currentPage + 1) * this.page
        );
      });
    });

    this.brandService.getAllBrand().subscribe((data: any) => {
      this.items = data;
    });
    this.filteredBrand = this.items;

    this.categoryService.getAllCategory().subscribe((data: any) => {
      this.items = data;
    });
    this.filteredCategory = this.items;

    this.statuses = [
      { label: "In Stock", value: "INSTOCK" },
      { label: "Low Stock", value: "LOWSTOCK" },
      { label: "Out of Stock", value: "OUTOFSTOCK" },
    ];
  }

  onRowEditInit(Shoes: Shoes) {
    this.clonedShoess[Shoes.id as number] = { ...Shoes };
  }

  onRowEditSave(Shoes: Shoes) {
    this.ShoesService.editShoes(Shoes).subscribe(
      (response: Shoes) => {
        // Xử lý khi lưu thành công
        this.messageService.add({
          severity: "success",
          summary: "Success",
          detail: "Product is updated.",
        });
      },
      (error) => {
        // Xử lý khi lưu không thành công
        this.messageService.add({
          severity: "error",
          summary: "Error",
          detail: "Invalid Price.",
        });
      }
    );
  }

  onRowDeleteInit(ShoesId: number) {
    console.log("Deleting Shoes with ID:", ShoesId);
    this.selectedShoesId = ShoesId; // Lưu ID của sản phẩm cần xóa
    this.displayConfirmDialog = true; // Hiển thị hộp thoại xác nhận
  }

  nextPage() {
    if (this.currentPage >= this.allShoes.length / this.page - 1) {
      return;
    }
    this.currentPage += 1;
    this.Shoes = this.allShoes.filter((s, index) => {
      return (
        this.currentPage * this.page <= index &&
        index < (this.currentPage + 1) * this.page
      );
    });
  }

  totalPage() {
    return Math.ceil(this.allShoes.length / this.page);
  }

  previousPage() {
    if (this.currentPage <= 0) {
      return;
    }
    this.currentPage -= 1;
    this.Shoes = this.allShoes.filter((s, index) => {
      return (
        this.currentPage * this.page <= index &&
        index < (this.currentPage + 1) * this.page
      );
    });
  }

  onDeleteShoes(confirm: boolean) {
    this.displayConfirmDialog = false; // Tắt hộp thoại xác nhận

    if (confirm) {
      this.ShoesService.deleteShoes(this.selectedShoesId).subscribe(
        (response) => {
          this.allShoes = this.allShoes.filter(
            (b) => b.id !== this.selectedShoesId
          ); // Xóa sản phẩm khỏi danh sách hiển thị
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Product is deleted.",
          });
          this.Shoes = this.allShoes.filter((s, index) => {
            return (
              this.currentPage * this.page <= index &&
              index < (this.currentPage + 1) * this.page
            );
          });
        },
        (error) => {
          this.messageService.add({
            severity: "error",
            summary: "Error",
            detail: "Failed to delete product.",
          });
        }
      );
    }
  }

  onRowEditCancel(Shoes: Shoes) {
    const ShoesToUpdate = this.Shoes.find((item) => item.id === Shoes.id);
    if (ShoesToUpdate) {
      ShoesToUpdate.code = this.clonedShoess[Shoes.id as number].code;
      ShoesToUpdate.name = this.clonedShoess[Shoes.id as number].name;
      ShoesToUpdate.lastModifiedBy =
        this.clonedShoess[Shoes.id as number].lastModifiedBy;
      ShoesToUpdate.lastModifiedDate =
        this.clonedShoess[Shoes.id as number].lastModifiedDate;

      // Sau khi hủy bỏ, bạn có thể xử lý messageService tại đây
    }
  }

  showDialog() {
    this.displayAddDialog = true; // Hiển thị modal form thêm mới
    // Kiểm tra xem danh sách Shoes có phần tử không
    if (this.Shoes.length > 0) {
      // Tìm độ dài của danh sách và tạo mã code mới
      const newCode =
        "SP" + (this.Shoes.length + 1).toString().padStart(3, "0");
      // Đặt giá trị mã code mới vào newShoes.code
      this.newShoes.code = newCode;
    } else {
      // Nếu danh sách Shoes rỗng, sử dụng giá trị mặc định
      this.newShoes.code = "SP001";
    }
  }

  onSaveNewShoes() {
    // Kiểm tra lỗi trước khi lưu
    this.validateField("name");

    if (this.nameError) {
      // Hiển thị thông báo lỗi nếu có lỗi
      this.messageService.add({
        severity: "error",
        summary: "Error",
        detail: "Please correct the errors before saving.",
      });
    } else {
      // Nếu không có lỗi, thực hiện lưu dữ liệu
      this.ShoesService.saveShoes(this.newShoes).subscribe(
        (response: Shoes) => {
          // Xử lý khi lưu thành công
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Shoes is added.",
          });

          // Sau khi lưu thành công, bạn có thể thêm Shoes mới vào danh sách hiển thị hoặc làm điều gì đó tương tự.
          // Ví dụ: this.Shoes.push(response);
          this.loadShoes();
          this.hideDialog(); // Đóng modal form sau khi lưu thành công
        },
        (error) => {
          // Xử lý khi lưu không thành công
          this.messageService.add({
            severity: "error",
            summary: "Error",
            detail: "Failed to add Shoes.",
          });
        }
      );
    }
  }

  hideDialog() {
    this.displayAddDialog = false;
  }
  loadShoes() {
    this.ShoesService.getShoes().subscribe((data: any) => {
      this.Shoes = data;
    });
  }
}
