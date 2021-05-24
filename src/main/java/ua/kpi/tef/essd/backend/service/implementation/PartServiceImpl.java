package ua.kpi.tef.essd.backend.service.implementation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.Clothing;
import ua.kpi.tef.essd.backend.entity.ClothingPart;
import ua.kpi.tef.essd.backend.entity.Part;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.exception.WrongValueException;
import ua.kpi.tef.essd.backend.repository.PartRepository;
import ua.kpi.tef.essd.backend.service.ClothingService;
import ua.kpi.tef.essd.backend.service.PartService;
import ua.kpi.tef.essd.backend.util.EntityNames;

import java.util.List;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private ClothingService clothingService;

    @Override
    public void addPartToClothing(Integer clothingId, Integer partId, Integer amount) {
        if (amount <= 0)
            throw new WrongValueException("Amount", amount);
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.PART, partId));
        clothingService.getClothing(clothingId).addPart(part, amount);
        partRepository.save(part);
    }

    /**
     * Change amount of parts in clothing to <code>newAmount</code>
     */
    @Override
    public void changePartInClothingAmount(Integer clothingId, Integer partId, Integer newAmount) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.ORDER, partId));
        Clothing clothing = clothingService.getClothing(clothingId);
        clothing.removePart(part);
        if (newAmount > 0)
            clothing.addPart(part, newAmount);
        partRepository.save(part);
    }

    @Override
    public List<Part> getAllParts() {
        List<Part> parts = partRepository.findAll();
        parts.forEach(Hibernate::initialize);
        return parts;
    }

    @Override
    public List<ClothingPart> getClothingParts(Integer clothingId) {
        List<ClothingPart> parts = clothingService.getClothing(clothingId).getParts();
        parts.forEach(Hibernate::initialize);
        return parts;
    }

    //  ---- Simple CRUD methods ----

    @Override
    public Part getPart(Integer id) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.PART, id));
        Hibernate.initialize(part);
        return part;
    }

}
