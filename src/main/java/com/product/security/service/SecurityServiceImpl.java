package com.product.security.service;


import com.product.redis.constants.RedisCacheNames;
import com.product.redis.repository.RedisRepository;
import com.product.security.dto.SecurityDTO;
import com.product.user.projection.UserProjection;
import com.product.userrole.projection.UserRoleProjection;
import com.product.user.projection.UserStatusProjection;
import com.product.user.enums.EnumUserStatus;
import com.product.user.service.UserService;
import com.product.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RedisRepository redisRepository;
    private final RoleHierarchyImpl roleHierarchy;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        UserProjection userProjection = userService.loadUserByLoginId(id, EnumUserStatus.ACTIVE, UserProjection.class);
        Collection<UserRoleProjection> userRoleProjection = userRoleService.loadRoleByUserId(userProjection.userId(), UserRoleProjection.class);

        return SecurityDTO.builder()
                        .userId(userProjection.userId())
                        .loginId(userProjection.loginId())
                        .password(userProjection.password())
                        .authorities(userRoleProjection.stream().map(role -> new SimpleGrantedAuthority(role.roleId())).toList())
                        .build();
    }

    public boolean isActive(Long userId) {
        String key = RedisCacheNames.generateUserStatusCacheKey(userId);
        String value = redisRepository.find(key);
        if(value != null){
            return Boolean.parseBoolean(value);
        }
        UserStatusProjection userStatusProjection = userService.findByUserId(userId, UserStatusProjection.class);
        boolean isActive = EnumUserStatus.ACTIVE.equals(userStatusProjection.status());
        redisRepository.save(key, String.valueOf(isActive), 60L, TimeUnit.MINUTES);
        return isActive;
    }

    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities(Collection<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .flatMap(authority -> roleHierarchy.getReachableGrantedAuthorities(Collections.singleton(authority)).stream())
                .map(grantedAuthority -> new SimpleGrantedAuthority(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());
    }
}
