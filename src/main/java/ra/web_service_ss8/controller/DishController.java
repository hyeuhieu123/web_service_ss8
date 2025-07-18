package ra.web_service_ss8.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.web_service_ss8.model.dto.response.ApiResponse;
import ra.web_service_ss8.model.dto.response.DishDTO;
import ra.web_service_ss8.model.entity.Dish;
import ra.web_service_ss8.service.DishService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DishDTO>>> getAllDishes() {
        List<DishDTO> result = new ArrayList<>();
        for (Dish d : dishService.getAllDishes()) {
            DishDTO dto = toDTO(d);
            result.add(dto);
        }
        return ResponseEntity.ok(new ApiResponse<>(result,true, "Lay danh sach mon an"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DishDTO>> createDish(@ModelAttribute DishDTO dto) {
        String imageUrl = null;
        try {
            if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(
                        dto.getImageFile().getBytes(), ObjectUtils.emptyMap()
                );
                imageUrl = uploadResult.get("secure_url").toString();
                dto.setImage(imageUrl);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Loi upload anh"));
        }

        Dish saved = dishService.createDish(toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(toDTO(saved), true, "TThem mon an thanh cong"));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DishDTO>> updateDish(@PathVariable Long id, @ModelAttribute DishDTO dto) {
        String imageUrl = null;
        try {
            if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(
                        dto.getImageFile().getBytes(), ObjectUtils.emptyMap()
                );
                imageUrl = uploadResult.get("secure_url").toString();
                dto.setImage(imageUrl);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Loi upload anh"));
        }
        Dish updated = dishService.updateDish(id, toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(toDTO(updated),true, "Cap nhat mon an thanh cong"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.ok(new ApiResponse<>(null,true, "Xoa mon an thanh cong"));
    }

    private Dish toEntity(DishDTO dto) {
        Dish d = new Dish();
        d.setId(dto.getId());
        d.setName(dto.getName());
        d.setDescription(dto.getDescription());
        d.setPrice(dto.getPrice());
        d.setStatus(dto.getStatus());
        d.setImage(dto.getImage());
        return d;
    }

    private DishDTO toDTO(Dish d) {
        DishDTO dto = new DishDTO();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setDescription(d.getDescription());
        dto.setPrice(d.getPrice());
        dto.setStatus(d.getStatus());
        dto.setImage(d.getImage());
        return dto;
    }
}