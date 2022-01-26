package com.rafaelzabotto.dscatalog.services.validation;

import com.rafaelzabotto.dscatalog.dto.UserInsertDTO;
import com.rafaelzabotto.dscatalog.entities.User;
import com.rafaelzabotto.dscatalog.repositories.UserRepository;
import com.rafaelzabotto.dscatalog.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

//Validação criada por nós do bootcamp, não é original do SpringBoot

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        //Testando se email ja esta no banco
        User user = userRepository.findByEmail(dto.getEmail());
        if (user != null) {
            list.add(new FieldMessage("email", "Email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();;
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldMessage()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
