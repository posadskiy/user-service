package com.posadskiy.user.core.service;

import com.posadskiy.user.core.db.UsersRepository;
import com.posadskiy.user.core.db.entity.UserEntity;
import com.posadskiy.user.core.exception.AuthException;
import com.posadskiy.user.core.mapper.entity.UserEntityMapper;
import com.posadskiy.user.core.model.User;
import com.posadskiy.user.core.utils.PasswordEncoder;
import jakarta.inject.Singleton;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Singleton
@NoArgsConstructor
public class RegistrationService {
    private UserEntityMapper userEntityMapper;
    private UsersRepository userRepository;

    public RegistrationService(UserEntityMapper userEntityMapper, UsersRepository userRepository) {
        this.userEntityMapper = userEntityMapper;
        this.userRepository = userRepository;
    }

    public User registration(@NonNull User user) {
        final Optional<UserEntity> byEmail = userRepository.findByEmail(user.email());
        if (byEmail.isPresent()) throw new AuthException("User already exists");
        
        var username = (StringUtils.isBlank(user.username())) ? user.email().split("@")[0] : user.username();

        var encodedPassword = PasswordEncoder.encode(user.password());
        var encodedPasswordUser = new User(user.id(), username, user.email(), encodedPassword);

        UserEntity savedUser = userRepository.save(
            userEntityMapper.mapToEntity(encodedPasswordUser)
        );

        return userEntityMapper.mapFromEntity(
            savedUser
        );
    }
}
