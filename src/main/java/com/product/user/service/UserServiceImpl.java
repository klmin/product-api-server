package com.product.user.service;


import com.product.api.exception.ApiRuntimeException;
import com.product.redis.constants.RedisCacheNames;
import com.product.redis.service.RedisService;
import com.product.user.dto.UserCreateDTO;
import com.product.user.dto.UserUpdateDTO;
import com.product.user.entity.User;
import com.product.user.enums.EnumUserStatus;
import com.product.user.repository.UserRepository;
import com.product.userrole.dto.UserRoleCreateDTO;
import com.product.userrole.repository.UserRoleQueryDslRepository;
import com.product.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final UserRoleService userRoleService;
    private final UserRoleQueryDslRepository userRoleQueryDslRepository;

    @Override
    public void create(UserCreateDTO dto) {
        User user = repository.insert(
                User.builder()
                        .loginId(dto.getLoginId())
                        .userName(dto.getUserName())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .status(dto.getStatus())
                        .userType(dto.getUserType())
                        .mobile(dto.getMobile())
                        .email(dto.getEmail())
                        .description(dto.getDescription())
                .build()
        );

        for(String roleId : dto.getRoleIds()) {
            userRoleService.create(UserRoleCreateDTO.builder().userId(user.getUserId()).roleId(roleId).build());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public User get(Long userId){
        return repository.get(userId);
    }

    @Override
    public void update(UserUpdateDTO dto) {
        User user = repository.get(dto.getUserId());
        user.update(dto);
    }

    @Override
    public void delete(Long userId) {
        long deleteCnt = userRoleQueryDslRepository.deleteUserRoleByUserId(userId);
        if(deleteCnt > 0) {
            repository.deleteOrThrow(userId);
            redisService.delete(RedisCacheNames.generateUserStatusCacheKey(userId));
        }
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {

        User user = repository.get(userId);
        user.changePassword(passwordEncoder, oldPassword, newPassword);

    }

    @Override
    public void changeStatus(Long userId, EnumUserStatus status) {
        User user = repository.get(userId);
        user.changeStatus(status);
        redisService.delete(RedisCacheNames.generateUserStatusCacheKey(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByUserId(Long id, Class<T> type) {
        return repository.findByUserId(id, type).orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T loadUserByLoginId(String loginId, EnumUserStatus status, Class<T> type) {
        return repository.findByLoginIdAndStatus(loginId, status, type).orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 올바르지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByLoginId(String loginId, Class<T> type) {
        return repository.findByLoginId(loginId, type).orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 올바르지 않습니다."));
    }
}
