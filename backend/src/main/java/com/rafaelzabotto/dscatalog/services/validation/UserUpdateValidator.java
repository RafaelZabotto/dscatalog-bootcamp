package com.rafaelzabotto.dscatalog.services.validation;

import com.rafaelzabotto.dscatalog.dto.UserInsertDTO;
import com.rafaelzabotto.dscatalog.dto.UserUpdateDTO;
import com.rafaelzabotto.dscatalog.entities.User;
import com.rafaelzabotto.dscatalog.repositories.UserRepository;
import com.rafaelzabotto.dscatalog.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Validação criada por nós do bootcamp, não é original do SpringBoot

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {


        @SuppressWarnings("unchecked")  //Para tirar o sublinado amarelo da IDE é kkkkkkk
        var uriVars = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        //Testando se email ja esta no banco
        User user = userRepository.findByEmail(dto.getEmail());
        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldMessage()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
