package com.example.demo2.controller;

import com.example.demo2.model.Product;
import com.example.demo2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listProduct", productService.getAll());
        return "products/index";
    }
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/create")
    public String create(@Valid Product newProduct, @RequestParam MultipartFile imageProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            return "products/create";
        }
        // Save image to static/images folder
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        productService.add(newProduct);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = productService.get(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Product editProduct, @RequestParam MultipartFile imageProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            return "products/edit";
        }

        // Save new image to static/images folder if provided
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                editProduct.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // If no new image is provided, retain the existing image
            Product existingProduct = productService.get(editProduct.getId());
            if (existingProduct != null) {
                editProduct.setImage(existingProduct.getImage());
            }
        }

        productService.edit(editProduct);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String searchProductByName(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchByName(query);
        model.addAttribute("listProduct", products);
        return "products/index";
    }
}
