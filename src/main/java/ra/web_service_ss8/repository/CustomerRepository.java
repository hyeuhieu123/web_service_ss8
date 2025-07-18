package ra.web_service_ss8.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.web_service_ss8.model.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}