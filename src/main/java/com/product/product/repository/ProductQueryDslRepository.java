package com.product.product.repository;

import com.product.product.query.ProductListQuery;
import com.product.product.response.ProductListResponse;
import com.product.util.KoreanUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.product.product.entity.QProduct.product;
import static com.querydsl.core.types.dsl.Expressions.numberTemplate;

@Repository
@RequiredArgsConstructor
public class ProductQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public long deleteByProductAndUserId(Long productId, Long userId) {
        return jpaQueryFactory.delete(product)
                .where(product.productId.eq(productId).and(product.user.userId.eq(userId)))
                .execute();
    }

    public List<ProductListResponse> list(ProductListQuery query){

        BooleanExpression booleanExpression = product.user.userId.eq(query.getUserId())
                .and(lastCursorIdCondition(query.getLastCursorId()));

        String searchKeyword = query.getSearchKeyword();
        String initials = KoreanUtil.extractInitials(searchKeyword);
        NumberTemplate<Double> productNameCondition = productNameCondition(searchKeyword);
        NumberTemplate<Double> productNameInitialsCondition = productNameInitialsCondition(initials);

        BooleanExpression searchCondition = createOrCondition(productNameCondition, productNameInitialsCondition);

        if (searchCondition != null) {
            booleanExpression = booleanExpression.and(searchCondition);
        }

        return jpaQueryFactory.select(Projections.constructor(ProductListResponse.class,
                        product.productId,
                        product.category,
                        product.price,
                        product.cost,
                        product.productName,
                        product.description,
                        product.barcode,
                        product.size,
                        product.regDttm))
                .from(product)
                .where(booleanExpression)
                .orderBy(product.productId.desc())
                .limit(query.getLimit())
                .fetch();

    }

    private BooleanExpression lastCursorIdCondition(Long lastCursorId) {
        return lastCursorId != null ? product.productId.lt(lastCursorId) : null;
    }
    private NumberTemplate<Double> productNameCondition(String searchKeyword){
        return StringUtils.hasText(searchKeyword) ? numberTemplate(Double.class, "function('match_against', {0}, {1})",
                product.productName, searchKeyword+"*") : null;
    }
    private NumberTemplate<Double> productNameInitialsCondition(String initials){
        return StringUtils.hasText(initials) ? numberTemplate(Double.class, "function('match_against', {0}, {1})",
                product.productNameInitials, initials+"*") : null;
    }

    private BooleanExpression createOrCondition(NumberTemplate<Double> productNameCondition,
                                                NumberTemplate<Double> productNameInitialsCondition) {

        BooleanExpression searchCondition = null;

        if (productNameCondition != null) {
            searchCondition = productNameCondition.gt(0);
        }

        if (productNameInitialsCondition != null) {
            if (searchCondition != null) {
                searchCondition = searchCondition.or(productNameInitialsCondition.gt(0));
            } else {
                searchCondition = productNameInitialsCondition.gt(0);
            }
        }

        return searchCondition;
    }


}
