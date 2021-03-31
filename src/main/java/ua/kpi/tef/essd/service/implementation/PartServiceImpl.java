package ua.kpi.tef.essd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.ClothingPart;
import ua.kpi.tef.essd.entity.Part;
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.repository.PartRepository;
import ua.kpi.tef.essd.service.ClothingService;
import ua.kpi.tef.essd.service.PartService;
import ua.kpi.tef.essd.util.EntityNames;

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
        if(newAmount > 0)
            clothing.addPart(part, newAmount);
        partRepository.save(part);
    }

    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @Override
    public List<ClothingPart> getClothingParts(Integer clothingId) {
        List<ClothingPart> clothingParts = clothingService.getClothing(clothingId).getParts();
        clothingParts.forEach(cp -> cp.getPart().getName()); // load the parts
        return clothingParts;
    }

    @Override
    public String getPartInfo(Integer partId) {
        return partRepository.findById(partId)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.PART, partId))
                .toString();
    }

    //  ---- Simple CRUD methods ----

    @Override
    public Part getPart(Integer id) {
        return partRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(EntityNames.PART, id));
    }

}
