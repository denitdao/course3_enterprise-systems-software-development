package ua.kpi.tef.essd.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.dao.PartDao;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.ClothingPart;
import ua.kpi.tef.essd.entity.Part;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Log4j2
public class PartServiceImpl implements PartService {

    @Autowired
    private PartDao partDao;

    @Autowired
    private ClothingService clothingService;

    @Override
    @Transactional(readOnly = false)
    public void addPartToClothing(Integer clothingId, Integer partId, Integer amount) {
        Part part = partDao.findById(partId);
        clothingService.getClothing(clothingId).addPart(part, amount);
        partDao.update(part);
    }

    /**
     * Change amount of parts in clothing to <code>newAmount</code>
     */
    @Override
    @Transactional(readOnly = false)
    public void changePartInClothingAmount(Integer clothingId, Integer partId, Integer newAmount) {
        Part part = partDao.findById(partId);
        Clothing clothing = clothingService.getClothing(clothingId);
        clothing.removePart(part);
        if(newAmount > 0)
            clothing.addPart(part, newAmount);
        partDao.update(part);
    }

    @Override
    public List<Part> getAllParts() {
        return partDao.findAll();
    }

    @Override
    public List<ClothingPart> getClothingParts(Integer clothingId) {
        List<ClothingPart> clothingParts = clothingService.getClothing(clothingId).getParts();
        clothingParts.forEach(cp -> cp.getPart().getName()); // load the parts
        return clothingParts;
    }

    @Override
    public String getPartInfo(Integer partId) {
        return partDao.findById(partId).toString();
    }

    //  ---- Simple CRUD methods ----

    @Override
    public Part getPart(Integer id) {
        return partDao.findById(id);
    }

}
