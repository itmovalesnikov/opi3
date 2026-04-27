package com.github.itmovalesnikov.web3.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.math3.fraction.BigFraction;

@Converter
public class BigFractionConverter implements AttributeConverter<BigFraction, String> {

    @Override
    public String convertToDatabaseColumn(BigFraction fraction) {
        return BigFractionUtils.toDecimalString(fraction);
    }

    @Override
    public BigFraction convertToEntityAttribute(String dbData) {
        return BigFractionUtils.fromDecimalString(dbData);
    }
}
