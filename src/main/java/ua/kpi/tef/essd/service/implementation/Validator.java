package ua.kpi.tef.essd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.tef.essd.repository.*;

@Service
public class Validator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private ClothesSetRepository clothesSetRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private OrderRepository orderRepository;

    public boolean validateUser(Integer id) {
        return userRepository.existsById(id);
    }

    public boolean validateClothing(Integer id) {
        return clothingRepository.existsById(id);
    }

    public boolean validateClothesSet(Integer id) {
        return clothesSetRepository.existsById(id);
    }

    public boolean validatePart(Integer id) { return partRepository.existsById(id); }

    public boolean validateOrder(Integer id) { return orderRepository.existsById(id); }
}
