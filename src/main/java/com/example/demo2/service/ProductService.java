package com.example.demo2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo2.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private List<Product> listProduct = new ArrayList<>();

    public ProductService() {
        // Khởi tạo danh sách sản phẩm ở đây (nếu cần)
        this.listProduct.add(new Product(1, "Áo MU", "MU.jpg", 70000));
        this.listProduct.add(new Product(2, "Áo MC", "MC.jpg", 80000));
    }

    public void add(Product newProduct) {
        // Thêm sản phẩm mới vào danh sách
        listProduct.add(newProduct);
    }

    public List<Product> getAll() {
        // Trả về toàn bộ danh sách sản phẩm
        return listProduct;
    }

    public Product get(int id) {
        // Tìm kiếm sản phẩm theo id
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void edit(Product editProduct) {
        // Chỉnh sửa thông tin sản phẩm
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getId() == editProduct.getId()) {
                listProduct.set(i, editProduct);
                break;
            }
        }
    }
    public void delete(int id) {
        listProduct.removeIf(p -> p.getId() == id);
    }
    public List<Product> searchByName(String name) {
        return listProduct.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
