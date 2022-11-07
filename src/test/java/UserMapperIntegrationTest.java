import com.database.entity.UserEntity;
import com.domain.mapper.UserMapper;
import com.domain.model.User;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;

public class UserMapperIntegrationTest {

    private UserMapper mapper
            = Mappers.getMapper(UserMapper.class);

    @Test
    public void givenUserToUserEntity_whenMaps_thenCorrect() {
        User user = new User();
        user.setLogin("SourceLogin");
        user.setEmail("SourceEmail");
        user.setPassport("SourcePassport");
        user.setPassword("password");
        user.setUserRoleId(1);
        user.setUserStateActive(true);
        UserEntity userEntity = mapper.userToUserEntity(user);

        assertEquals(user.getLogin(), userEntity.getLogin());
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getPassport(), userEntity.getPassport());
        assertEquals(user.getUserRoleId(), userEntity.getUserRoleId());
        assertEquals(user.isUserStateActive(), userEntity.isUserStateActive());
    }

    @Test
    public void givenUserEntityToUser_whenMaps_thenCorrect() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("SourceLogin");
        userEntity.setEmail("SourceEmail");
        userEntity.setPassport("SourcePassport");
        userEntity.setPassword("password");
        userEntity.setUserRoleId(1);
        userEntity.setUserStateActive(true);
        User user = mapper.userEntityToUser(userEntity);

        assertEquals(userEntity.getLogin(), user.getLogin());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getPassword(), user.getPassword());
        assertEquals(userEntity.getPassport(), user.getPassport());
        assertEquals(userEntity.getUserRoleId(), user.getUserRoleId());
        assertEquals(userEntity.isUserStateActive(), user.isUserStateActive());
    }

}
