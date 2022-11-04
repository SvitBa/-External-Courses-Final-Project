import com.database.entity.UserEntity;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletContext context;
    @Mock
    RequestDispatcher dispatcher;

    @Mock
    UserEntity user1;

    @Mock
    UserEntity user2;

    @Mock
    UserEntity user3;

    @Mock
    List<UserEntity> userList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userList = new ArrayList<>();
        user1 = new UserEntity(1, "test1", "smtest@test.com", "12345","12345", 1, true);
        user2 = new UserEntity(2, "test2", "secondest@test.com", "23451","212345", 2, true);
        user3 = new UserEntity(3, "third", "third@test.com", "34512","312345", 3, true);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }

    @Test
    public void testCreateUserSuccessTesting() {


    }

    @Test
    public void testDeleteUserSuccessTesting() {


    }

    @Test
    public void testUpdateUserStateTesting() {


    }

    @Test
    public void testUpdateUserRoleTesting() {


    }

    @Test
    public void testUpdateUserProfileTesting() {


    }

    @Test
    public void testGetUserByIdTesting() {


    }

    @Test
    public void testGetAllUserTesting() {


    }


}
