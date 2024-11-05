package br.com.sidroniolima.admin.infrastructure.video.persistence;

import br.com.sidroniolima.admin.domain.video.Rating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating rating) {
        if (null == rating) return null;
        return rating.getName();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return Rating.of(dbData).orElse(null);
    }
}
