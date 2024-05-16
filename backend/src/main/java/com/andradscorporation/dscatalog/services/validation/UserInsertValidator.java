package com.andradscorporation.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.andradscorporation.dscatalog.repositories.UserRepository;
import com.andradscorporation.dscatalog.resource.exceptions.FieldMessage;
import com.andradscorporation.dscatalog.entities.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.andradscorporation.dscatalog.dto.UserInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        if(user != null){
            list.add(new FieldMessage("Email", "Esse email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}