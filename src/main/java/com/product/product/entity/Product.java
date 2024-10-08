package com.product.product.entity;

import com.product.util.KoreanUtil;
import com.product.jpa.audit.AuditableEntity;
import com.product.product.dto.ProductUpdateDto;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import com.product.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name="tb_product")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Comment("유저아이디")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("카테고리")
    @Enumerated(EnumType.STRING)
    private EnumProductCategory category;

    @Comment("가격")
    private Integer price;

    @Comment("원가")
    private Integer cost;

    @Comment("상품이름")
    private String productName;

    @Comment("상품이름초성")
    private String productNameInitials;

    @Comment("설명")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Comment("바코드")
    @Column(length=50)
    private String barcode;

    @Comment("유통기한")
    private LocalDate expirationDate;

    @Comment("사이즈")
    private EnumProductSize size;

    @Builder
    public Product(User user, EnumProductCategory category, Integer price, Integer cost, String productName, String productNameInitials, String description, String barcode, LocalDate expirationDate, EnumProductSize size) {
        this.user = user;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.productName = productName;
        this.productNameInitials = productNameInitials;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.size = size;

    }

    public void update(ProductUpdateDto dto){
        if(dto.getCategory() != null){
            this.category = dto.getCategory();
        }
        if(dto.getPrice() != null){
            this.price = dto.getPrice();
        }
        if(dto.getCost() != null){
            this.cost = dto.getCost();
        }
        if(dto.getProductName() != null){
            this.productName = dto.getProductName();
            this.productNameInitials = KoreanUtil.extractInitials(dto.getProductName());
        }
        if(dto.getDescription() != null){
            this.description = dto.getDescription();
        }
        if(dto.getBarcode() != null){
            this.barcode = dto.getBarcode();
        }
        if(dto.getExpirationDate() != null){
            this.expirationDate = dto.getExpirationDate();
        }
        if(dto.getSize() != null){
            this.size = dto.getSize();
        }

    }


}
