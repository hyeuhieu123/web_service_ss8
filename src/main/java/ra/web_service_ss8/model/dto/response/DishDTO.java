package ra.web_service_ss8.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String status;
    private String image;
    private MultipartFile imageFile;

}