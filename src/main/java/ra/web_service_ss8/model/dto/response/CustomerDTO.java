package ra.web_service_ss8.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private Integer numberOfPayments;
    private boolean status;
    private LocalDate createdAt;
}