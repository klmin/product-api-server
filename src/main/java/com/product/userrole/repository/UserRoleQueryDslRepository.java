package com.product.userrole.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.product.userrole.entity.QUserRole.userRole;


@Repository
@RequiredArgsConstructor
public class UserRoleQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public long deleteUserRoleByUserId(Long userId){
        return jpaQueryFactory.delete(userRole)
                .where(userRole.userId.eq(userId))
                .execute();
    }


}
