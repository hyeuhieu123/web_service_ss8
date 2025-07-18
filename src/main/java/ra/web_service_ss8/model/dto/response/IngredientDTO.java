package ra.web_service_ss8.model.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IngredientDTO {
    private Long id;
    private String name;
    private Integer stock;
    private LocalDate expiry;
    private String image;
    private MultipartFile imageFile;
}