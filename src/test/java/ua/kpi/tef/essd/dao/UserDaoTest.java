package ua.kpi.tef.essd.dao;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@AutoConfigureTestEntityManager
@Transactional
class UserDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDao userDao;

    @Test
    public void When_save_Expect_PersistedToDB() {
        User user = new User("Tester", 10, "nothing interesting");

        userDao.save(user);
        User found = entityManager.find(User.class, user.getId());

        assertEquals(user, found);
    }

    @Test
    public void When_findByWrongId_Expect_NullReturned() {
        User found = userDao.findById(-1);

        assertNull(found);
    }


    @Test
    public void When_findAll_Expect_ContainsAll() {
        User user1 = new User("Tester 1", 10, "nothing interesting");
        User user2 = new User("Tester 2", 20, "nothing interesting twice");
        entityManager.persist(user1);
        entityManager.persist(user2);

        List<User> userList = userDao.findAll();

        assertThat(userList, not(IsEmptyCollection.empty()));
        assertThat(userList, hasItems(user1, user2));
    }

    @Test
    public void When_update_Expect_PersistedChanges() {
        User user = new User("Tester", 10, "nothing interesting");
        entityManager.persist(user);
        user.setName("Another name");
        user.setAge(100);

        User found = userDao.update(user);

        assertEquals(user, found);
    }

    @Test
    public void When_delete_Expect_RemovedFromDB() {
        User user = new User("Tester", 10, "nothing interesting");
        entityManager.persist(user);

        userDao.deleteById(user.getId());
        User found = entityManager.find(User.class, user.getId());

        assertNull(found);
    }

}