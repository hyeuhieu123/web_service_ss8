package ra.web_service_ss8.controller;

import com.cloudinary.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.web_service_ss8.model.dto.response.ApiResponse;
import ra.web_service_ss8.model.entity.PaymentSlip;
import ra.web_service_ss8.service.PaymentSlipService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-slips")
public class PaymentSlipController {
    @Autowired
    private PaymentSlipService paymentSlipService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentSlip>>> findAll(){
        return new ResponseEntity<>(new ApiResponse<>(paymentSlipService.getAllPaymentSlips(), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentSlip>> createPaymentSlip(@RequestBody PaymentSlip paymentSlip) {
        PaymentSlip createdPaymentSlip = paymentSlipService.createPaymentSlip(paymentSlip);
        return new ResponseEntity<>(new ApiResponse<>(createdPaymentSlip, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentSlip>> updatePaymentSlip(@PathVariable Long id, @RequestBody PaymentSlip paymentSlip) {
        PaymentSlip updatedPaymentSlip = paymentSlipService.updatePaymentSlip(id, paymentSlip);
        if (updatedPaymentSlip != null) {
            return new ResponseEntity<>(new ApiResponse<>(updatedPaymentSlip, HttpStatus.OK), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(null, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePaymentSlip(@PathVariable Long id) {
        try {
            paymentSlipService.deletePaymentSlip(id);
            return new ResponseEntity<>(new ApiResponse<>(null, HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(null, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}