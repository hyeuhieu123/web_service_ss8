package ra.web_service_ss8.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.web_service_ss8.model.dto.response.ApiResponse;
import ra.web_service_ss8.model.dto.response.IngredientDTO;
import ra.web_service_ss8.model.entity.Ingredient;
import ra.web_service_ss8.service.IngredientService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private Cloudinary cloudinary;
    @GetMapping
    public ResponseEntity<ApiResponse<List<IngredientDTO>>> findAll(){
        List<IngredientDTO> result = new ArrayList<>();
        for (Ingredient i : ingredientService.findAll()) {
            IngredientDTO dto = toDTO(i);
            result.add(dto);
        }
        return ResponseEntity.ok(new ApiResponse<>(result, true, "Lay danh sach nguyen lieu thanh cong"));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<IngredientDTO>> createIngredient(@ModelAttribute IngredientDTO dto) {
        String imageUrl = null;
        try {
            if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(dto.getImageFile().getBytes(), ObjectUtils.emptyMap());
                imageUrl = uploadResult.get("secure_url").toString();
                dto.setImage(imageUrl);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Loi upload anh"));
        }

        Ingredient saved = ingredientService.create(toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(toDTO(saved), true, "Them nguyen lieu thanh cong"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IngredientDTO>> updateIngredient(@PathVariable Long id, @ModelAttribute IngredientDTO dto) {
        String imageUrl = null;
        try {
            if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(dto.getImageFile().getBytes(), ObjectUtils.emptyMap());
                imageUrl = uploadResult.get("secure_url").toString();
                dto.setImage(imageUrl);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Loi upload anh"));
        }

        Ingredient updated = ingredientService.update(id, toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(toDTO(updated), true, "Cap nhat nguyen lieu thanh cong"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(null,true, "Xoa nguyen lieu thanh cong"));
    }

    private Ingredient toEntity(IngredientDTO dto) {
        Ingredient i = new Ingredient();
        i.setId(dto.getId());
        i.setName(dto.getName());
        i.setStock(dto.getStock());
        i.setExpiry(dto.getExpiry());
        i.setImage(dto.getImage());
        return i;
    }

    private IngredientDTO toDTO(Ingredient d) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setStock(d.getStock());
        dto.setExpiry(d.getExpiry());
        dto.setImage(d.getImage());
        return dto;
    }
}