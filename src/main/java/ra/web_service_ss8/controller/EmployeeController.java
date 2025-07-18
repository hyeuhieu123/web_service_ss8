package ra.web_service_ss8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.web_service_ss8.model.dto.response.ApiResponse;
import ra.web_service_ss8.model.dto.response.EmployeeDTO;
import ra.web_service_ss8.model.entity.Employee;
import ra.web_service_ss8.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping
    public
    ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
        List<EmployeeDTO> result = new ArrayList<>();
        for (Employee e : employeeService.findAll()) {
            EmployeeDTO dto = toDTO(e);
            result.add(dto);
        }
        return ResponseEntity.ok(new ApiResponse<>(result, true, "Lay danh sach nhan vien"));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(EmployeeDTO dto) {
        Employee saved = employeeService.create(toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(toDTO(saved), true, "Them nhan vien thanh cong"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id, EmployeeDTO dto) {
        Employee updated = employeeService.update(id, toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(toDTO(updated), true, "Cap nhat nhan vien thanh cong"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(null, true, "Xoa nhan vien thanh cong"));
    }
    private Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFullName(dto.getFullName());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        return employee;
    }
    private EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setPosition(employee.getPosition());
        dto.setSalary(employee.getSalary());
        return dto;
    }
}