package com.example.demo2.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.*;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;
    @NotNull
    @NotBlank(message = "tên sản phẩm không được để trống")
    private String name;

    @Length(min = 0, max = 50, message = "tên hình ảnh không quá 50 ký tự")
    private String image;

    @NotNull(message = "giá sản phẩm không được để trống")
    @Min(value = 1, message = "giá sản phẩm không được nhỏ hơn 1")
    @Max(value = 999999999, message = "giá sản phẩm không được lớn hơn 999999999")
    private long price;

    // Constructor, getters, setters
}
