package de.thro.inf.vv.OAService.converter;
/**
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Base64;
@Component
@Configurable
@Converter(autoApply=true)
public class UsernameConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";


    private static final byte[] KEY = "%36^Vfxqj3LUo&L%".getBytes();


    @Override

    public String convertToDatabaseColumn(String decryptedData) {

        /* Encrypt data using AES encryption */
/**
        Key key = new SecretKeySpec(KEY, "AES");

        try {

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return Base64.getEncoder().encodeToString(cipher.doFinal(decryptedData.getBytes()));

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }


    @Override

    public String convertToEntityAttribute(String encryptedData) {

        Key key = new SecretKeySpec(KEY, "AES");

        try {

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}*/