import { Component } from '@angular/core';
import { MessageService, SelectItem } from 'primeng/api';
import { CategoryData } from 'src/app/model/CategoryData';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-category-removed',
  templateUrl: './category-removed.component.html',
  styleUrls: ['./category-removed.component.css']
})
export class CategoryRemovedComponent {
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


  Category: CategoryData[];
  statuses: SelectItem[];
  clonedCategorys: { [s: string]: CategoryData } = {};
  displayConfirmDialog: boolean = false;
  selectedCategoryId: number = 0;
  displayAddDialog: boolean = false;
  visible: boolean = false;


  constructor(private CategoryService: CategoryService, private messageService: MessageService) { }

  ngOnInit() {
    this.CategoryService.getCategoriesRemoved().subscribe((data: any) => {
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
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Product is updated.' });
       this.loadCategoryData();
      },
      (error) => {
        // Xử lý khi lưu không thành công
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Invalid Price.' });
      }
    );
  }

  onEditCategory(confirm: boolean) {
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

 
  hideDialog() {
    this.displayAddDialog = false;
  }
  loadCategoryData() {
    this.CategoryService.getCategoriesRemoved().subscribe((data: any) => {
      this.Category = data;
    });
  }
}
