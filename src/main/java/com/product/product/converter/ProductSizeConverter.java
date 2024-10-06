package com.product.product.converter;

import com.product.product.enums.EnumProductSize;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductSizeConverter implements AttributeConverter<EnumProductSize, String> {
    @Override
    public String convertToDatabaseColumn(EnumProductSize enumProductSize) {

        if (enumProductSize == null) {
            return null;
        }
        return enumProductSize.name().toLowerCase();
    }

    @Override
    public EnumProductSize convertToEntityAttribute(String s) {

        if (s == null || s.isEmpty()) {
            return null;
        }
        return EnumProductSize.valueOf(s.toUpperCase());
    }
}
