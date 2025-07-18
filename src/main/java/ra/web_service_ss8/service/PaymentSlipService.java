package ra.web_service_ss8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.web_service_ss8.model.entity.PaymentSlip;
import ra.web_service_ss8.repository.PaymentSlipRepository;

import java.util.List;
@Service
public class PaymentSlipService  {
    @Autowired
    private PaymentSlipRepository paymentSlipRepository;
    public List<PaymentSlip> getAllPaymentSlips() {
        return paymentSlipRepository.findAll();
    }

    public PaymentSlip getPaymentSlipById(Long id) {
        return paymentSlipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment slip with id " + id + " does not exist."));
    }
    public PaymentSlip createPaymentSlip(PaymentSlip paymentSlip) {
        if (paymentSlip == null) {
            throw new IllegalArgumentException("Payment slip cannot be null");
        }
        return paymentSlipRepository.save(paymentSlip);
    }

    public PaymentSlip updatePaymentSlip(Long id, PaymentSlip paymentSlip) {
        if (paymentSlipRepository.existsById(id)) {
            paymentSlip.setId(id);
            return paymentSlipRepository.save(paymentSlip);
        }
        throw new IllegalArgumentException("Payment slip with id " + id + " does not exist.");
    }

    public void deletePaymentSlip(Long id) {
        if (paymentSlipRepository.existsById(id)) {
            paymentSlipRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Payment slip with id " + id + " does not exist.");
        }
    }
}