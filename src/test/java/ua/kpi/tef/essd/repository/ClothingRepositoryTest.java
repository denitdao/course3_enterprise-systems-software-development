package ua.kpi.tef.essd.repository;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.Size;
import ua.kpi.tef.essd.entity.Type;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@AutoConfigureTestEntityManager
@Transactional
class ClothingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClothingRepository clothingRepository;

    @Test
    void When_save_Expect_PersistedToDB() {
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);

        clothingRepository.save(clothing);
        Clothing found = entityManager.find(Clothing.class, clothing.getId());

        assertEquals(clothing, found);
    }

    @Test
    void When_findByWrongId_Expect_EmptyOptional() {
        assertFalse(clothingRepository.findById(-1).isPresent());
    }

    @Test
    void When_findAll_Expect_ContainsAll() {
        Clothing clothing1 = new Clothing("Tester Shirt 1", Type.CHILDREN, Size.S);
        Clothing clothing2 = new Clothing("Tester Shirt 2", Type.CHILDREN, Size.S);

        entityManager.persist(clothing1);
        entityManager.persist(clothing2);

        List<Clothing> found = clothingRepository.findAll();

        assertThat(found, not(IsEmptyCollection.empty()));
        assertThat(found, hasItems(clothing1, clothing2));
    }

    @Test
    void When_update_Expect_PersistedChanges() {
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);
        entityManager.persist(clothing);
        clothing.setName("Tester Shirt Altered");
        clothing.setType(Type.MAN);

        Clothing found = clothingRepository.save(clothing);

        assertEquals(clothing, found);
    }

    @Test
    void When_delete_Expect_RemovedFromDB() {
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);
        entityManager.persist(clothing);

        clothingRepository.deleteById(clothing.getId());
        Clothing found = entityManager.find(Clothing.class, clothing.getId());

        assertNull(found);
    }

}