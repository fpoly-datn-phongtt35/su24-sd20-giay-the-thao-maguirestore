import { Component, OnInit, ViewChild } from '@angular/core';
import { log } from 'console';
import { MessageService, SelectItem } from 'primeng/api';
import { CategorySave } from 'src/app/model/AddCategory';
import { CategoryData } from 'src/app/model/CategoryData';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {
  codeError: boolean = false;
  nameError: boolean = false;
  descriptionError: boolean = false;
  searchText: string = '';
  filteredCategorys: CategoryData[] = [];

   onSearchInputChange() {
    
    this.filterCategorys();
  }
  
  filterCategorys() {

    this.filteredCategorys = this.Category.filter((b) => {
      return (
        b.code.toLowerCase().includes(this.searchText.toLowerCase()) ||
        b.name.toLowerCase().includes(this.searchText.toLowerCase()) 
      );
    });
  }

  validateField(field: string) {
    switch (field) {
      case 'code':
        this.codeError = this.newCategory.code === '' || this.newCategory.code.length > 50;
        break;
      case 'name':
        this.nameError = this.newCategory.name === '' || this.newCategory.name.length > 50;
        break;
    }
  }
 
  Category: CategoryData[];
  statuses: SelectItem[];
  clonedCategorys: { [s: string]: CategoryData } = {};
  displayConfirmDialog: boolean = false;
  selectedCategoryId: number = 0;
  displayAddDialog: boolean = false;
  visible: boolean = false;
  newCategory: CategorySave = { code: '', name: '' };

  constructor(private CategoryService: CategoryService, private messageService: MessageService) { }

  ngOnInit() {
    this.CategoryService.getCategories().subscribe((data: any) => {
      // console.log('data Category'+data);
      this.Category = data;
    });

    this.statuses = [
      { label: 'In Stock', value: 'INSTOCK' },
      { label: 'Low Stock', value: 'LOWSTOCK' },
      { label: 'Out of Stock', value: 'OUTOFSTOCK' },
    ];
  }

  onRowEditInit(Category: CategoryData) {
    this.clonedCategorys[Category.id as number] = { ...Category };
  }

  onRowEditSave(Category: CategoryData) {
    this.CategoryService.editCategory(Category).subscribe(
      (response: CategoryData) => {
        // Xử lý khi lưu thành công
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Thành công.' });
      },
      (error) => {
        // Xử lý khi lưu không thành công
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Thất bại.' });
      }
    );
  }

  onRowDeleteInit(CategoryId: number) {
    console.log('Deleting Category with ID:', CategoryId);
    this.selectedCategoryId = CategoryId; // Lưu ID của sản phẩm cần xóa
    this.displayConfirmDialog = true; // Hiển thị hộp thoại xác nhận
  }

  onDeleteCategory(confirm: boolean) {
    this.displayConfirmDialog = false; // Tắt hộp thoại xác nhận

    if (confirm) {
      this.CategoryService.deleteCategory(this.selectedCategoryId).subscribe(
        (response) => {
          this.Category = this.Category.filter((b) => b.id !== this.selectedCategoryId); // Xóa sản phẩm khỏi danh sách hiển thị
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Product is deleted.' });
        },
        (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete product.' });
        }
      );
    }
  }

  onRowEditCancel(Category: CategoryData) {
    const CategoryToUpdate = this.Category.find((item) => item.id === Category.id);
    if (CategoryToUpdate) {
      CategoryToUpdate.code = this.clonedCategorys[Category.id as number].code;
      CategoryToUpdate.name = this.clonedCategorys[Category.id as number].name;
      CategoryToUpdate.lastModifiedBy = this.clonedCategorys[Category.id as number].lastModifiedBy;
      CategoryToUpdate.lastModifiedDate = this.clonedCategorys[Category.id as number].lastModifiedDate;

      // Sau khi hủy bỏ, bạn có thể xử lý messageService tại đây
    }
  }

  showDialog() {
    this.displayAddDialog = true; // Hiển thị modal form thêm mới
    // Kiểm tra xem danh sách Category có phần tử không
    if (this.Category.length > 0) {
      // Tìm độ dài của danh sách và tạo mã code mới
      const newCode = 'SZ' + (this.Category.length + 1).toString().padStart(3, '0');
      // Đặt giá trị mã code mới vào newCategory.code
      this.newCategory.code = newCode;
    } else {
      // Nếu danh sách Category rỗng, sử dụng giá trị mặc định
      this.newCategory.code = 'SZ001';
    }
  }
  

  onSaveNewCategory() {
    // Kiểm tra lỗi trước khi lưu
    this.validateField('code');
    this.validateField('name');
    this.validateField('description');

    if (this.codeError || this.nameError || this.descriptionError) {
      // Hiển thị thông báo lỗi nếu có lỗi
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Please correct the errors before saving.' });
    } else {
      // Kiểm tra trùng tên
      const checkEqual = this.Category.find(o => o.name === this.newCategory.name);
      if (checkEqual) {
        // Nếu có tên trùng, hiển thị thông báo lỗi
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Category name already exists.' });
        return;
      }

      // Nếu không có lỗi, thực hiện lưu dữ liệu
      this.CategoryService.saveCategory(this.newCategory).subscribe(
        (response: CategoryData) => {
          // Xử lý khi lưu thành công
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Category is added.' });

          // Sau khi lưu thành công, bạn có thể thêm Category mới vào danh sách hiển thị hoặc làm điều gì đó tương tự.
          // Ví dụ: this.Category.push(response);
          this.loadCategoryData();
          this.hideDialog(); // Đóng modal form sau khi lưu thành công
        },
        (error) => {
          // Xử lý khi lưu không thành công
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to add Category.' });
        }, 
      );
    }
  }

  hideDialog() {
    this.displayAddDialog = false;
  }
  loadCategoryData() {
    this.CategoryService.getCategories().subscribe((data: any) => {
      this.Category = data;
    });
   
  }

 

}
