package com.luanr.agregadorinvestimentos.service;

import com.luanr.agregadorinvestimentos.dto.CreateUserDto;
import com.luanr.agregadorinvestimentos.dto.UpdateUserDto;
import com.luanr.agregadorinvestimentos.entity.User;
import com.luanr.agregadorinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uiidArgumentCaptor;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should create user with success")
        void ShouldCreateUserWithSucess() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("username",
                    "email",
                    "password"
            );

            // Act
            var userId = userService.createUser(input);
            // Assert
            assertNotNull(userId);
            assertEquals(input.username(), userArgumentCaptor.getValue().getUsername());
            assertEquals(input.email(), userArgumentCaptor.getValue().getEmail());
            assertEquals(input.password(), userArgumentCaptor.getValue().getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void ShouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doReturn(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto("username",
                    "email",
                    "password"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUser{


        @Test
        @DisplayName("Should get user by id with success when user exists")
        void ShouldGetUserByIdWithSuccessWhenUserExist() {

            // Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uiidArgumentCaptor.capture());

            // Act
            var output = userService.getUser(user.getUser_id().toString());
            // Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUser_id(), uiidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when user not exists")
        void ShouldGetUserByIdWithSuccessWhenUserNotExist() {

            // Arrange
            var user_Id = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(uiidArgumentCaptor.capture());

            // Act
            var output = userService.getUser(user_Id.toString());
            // Assert
            assertTrue(output.isEmpty());
            assertEquals(user_Id, uiidArgumentCaptor.getValue());
        }
    }
    
    @Nested
    class getAllUsers{

            @Test
            @DisplayName("Should get all users with success")
            void ShouldGetAllUsersWithSuccess() {
                // Arrange
                var user = new User(
                        UUID.randomUUID(),
                        "username",
                        "email",
                        "password",
                        Instant.now(),
                        null
                );
                var userList = List.of(user);
                doReturn(userList).when(userRepository).findAll();

                // Act
                var output = userService.getAllUsers();
                // Assert
                assertNotNull(output);
                assertEquals(userList.size(), output.size());
            }

    }

    @Nested
    class deleteUser{

        @Test
        @DisplayName("Should delete user with success when user exists")
        void ShouldDeleteUserWithSuccessWhenUserExist() {

            // Arrange
            doReturn(true).when(userRepository).existsById(uiidArgumentCaptor.capture());

            doNothing().when(userRepository).deleteById(uiidArgumentCaptor.capture());

            var user_Id = UUID.randomUUID();
            // Act
            userService.deleteUser(user_Id.toString());
            // Assert
            var idList = uiidArgumentCaptor.getAllValues();
            assertEquals(user_Id, idList.getFirst());
            assertEquals(user_Id, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.getFirst());
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }
        @Test
        @DisplayName("Should not delete user when user not exists")
        void ShouldNotDeleteUserWhenUserNotExist() {

            // Arrange
            doReturn(false).when(userRepository).existsById(uiidArgumentCaptor.capture());
            var user_Id = UUID.randomUUID();

            // Act
            userService.deleteUser(user_Id.toString());

            // Assert
            assertEquals(user_Id, uiidArgumentCaptor.getValue());
            verify(userRepository, times(1)).existsById(uiidArgumentCaptor.getValue());
            verify(userRepository, never()).deleteById(any());
        }
    }

    @Nested
    class updateUser{

            @Test
            @DisplayName("Should update user with success when user exists and username OR password are not null")
            void ShouldUpdateUserWithSuccessWhenUserExist() {
                var updateUserDto = new UpdateUserDto("newUsername", "newPassword");

                // Arrange
                var user = new User(
                        UUID.randomUUID(),
                        "username",
                        "email",
                        "password",
                        Instant.now(),
                        null
                );
                doReturn(Optional.of(user))
                        .when(userRepository)
                        .findById(uiidArgumentCaptor.capture());


                doReturn(user)
                        .when(userRepository)
                        .save(userArgumentCaptor.capture());

                // Act

                userService.updateUser(user.getUser_id().toString(), updateUserDto);
                // Assert

                assertEquals(user.getUser_id(), uiidArgumentCaptor.getValue());
                var user_capture = userArgumentCaptor.getValue();

                assertEquals(updateUserDto.username(), user_capture.getUsername());
                assertEquals(updateUserDto.password(), user_capture.getPassword());

                verify(userRepository, times(1)).findById(uiidArgumentCaptor.getValue());
                verify(userRepository, times(1)).save(user);
            }

            @Test
            @DisplayName("Should not update user when user not exists")
            void ShouldNotUpdateUserWhenUserNotExist() {

                // Arrange
                var updateUserDto = new UpdateUserDto("newUsername", "newPassword");

                var user_Id = UUID.randomUUID();
                doReturn(Optional.empty())
                        .when(userRepository)
                        .findById(uiidArgumentCaptor.capture());

                // Act
                userService.updateUser(user_Id.toString(), updateUserDto);

                // Assert
                assertEquals(user_Id, uiidArgumentCaptor.getValue());
                verify(userRepository, times(1)).findById(uiidArgumentCaptor.getValue());
                verify(userRepository, never()).save(any());
            }
    }
}