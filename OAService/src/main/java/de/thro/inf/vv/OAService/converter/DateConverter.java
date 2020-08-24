package de.thro.inf.vv.OAService.converter;
/**
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
@Component
@Configurable
@Converter(autoApply=true)
public class DateConverter implements AttributeConverter<LocalDate, String> {


    public String convertToDatabaseColumn(LocalDate datum) {
        return datum.toString();
    }


    public LocalDate convertToEntityAttribute(String datum) {
        return LocalDate.parse(datum);
    }

}
*/