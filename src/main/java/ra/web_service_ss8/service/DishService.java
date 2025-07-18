package ra.web_service_ss8.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.web_service_ss8.model.entity.Dish;
import ra.web_service_ss8.repository.DishRepository;

import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new RuntimeException("khong ton tai mon an"));
    }

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish updateDish(Long id, Dish updated) {
        Dish dish  = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mon an khong ton tai"));
        dish.setId(id);
        return dishRepository.save(dish);
    }

    public void deleteDish(Long id) {
        Dish dish  = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mon an khong ton tai"));
        dish.setId(id);
        dishRepository.delete(dish);
    }
}