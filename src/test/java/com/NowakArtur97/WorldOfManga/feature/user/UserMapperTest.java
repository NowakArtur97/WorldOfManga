package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("User_Tests")
@Tag("Unit_Tests")
@Tag("UserMapperImpl_Tests")
class UserMapperTest {

    private UserMapper userMapper;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        userMapper = new UserMapper(modelMapper);
    }

    @Test
    void when_map_user_dto_should_return_user_entity() {

        UserDTO userDTOExpected = UserDTO.builder().username("username").firstName("first name").lastName("last name")
                .userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
                .email("user@email.com").areTermsAccepted(true).build();

        User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
                .password("password1").email("user@email.com").isEnabled(true).build();

        when(userMapper.mapUserDTOToUser(userDTOExpected)).thenReturn(userExpected);

        User userActual = userMapper.mapUserDTOToUser(userDTOExpected);

        assertAll(
                () -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
                        () -> "should return user with username: " + userExpected.getUsername() + ", but was: "
                                + userActual.getUsername()),
                () -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
                        () -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
                                + userActual.getFirstName()),
                () -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
                        () -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
                                + userActual.getLastName()),
                () -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
                        () -> "should return user with password: " + userExpected.getPassword() + ", but was: "
                                + userActual.getPassword()),
                () -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
                        () -> "should return user with email: " + userExpected.getEmail() + ", but was: "
                                + userActual.getEmail()),
                () -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
                        () -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
                                + userActual.isEnabled()),
                () -> verify(modelMapper, times(1)).map(userDTOExpected, User.class));
    }
}
