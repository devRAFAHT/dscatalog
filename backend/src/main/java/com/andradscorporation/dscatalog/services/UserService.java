package com.andradscorporation.dscatalog.services;

import com.andradscorporation.dscatalog.dto.CategoryDTO;
import com.andradscorporation.dscatalog.dto.RoleDTO;
import com.andradscorporation.dscatalog.dto.UserDTO;
import com.andradscorporation.dscatalog.dto.UserInsertDTO;
import com.andradscorporation.dscatalog.entities.Category;
import com.andradscorporation.dscatalog.entities.Role;
import com.andradscorporation.dscatalog.entities.User;
import com.andradscorporation.dscatalog.repositories.CategoryRepository;
import com.andradscorporation.dscatalog.repositories.RoleRepository;
import com.andradscorporation.dscatalog.repositories.UserRepository;
import com.andradscorporation.dscatalog.services.exceptions.DatabaseException;
import com.andradscorporation.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable){
        Page<User> list = repository.findAll(pageable);
        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        Optional<User> obj = repository.findById(id);
        User UserEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(UserEntity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userInsertDTO) {
        User userEntity = new User();
        copyDtoToEntity(userInsertDTO, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
        userEntity = repository.save(userEntity);
        return new UserDTO(userEntity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            User userEntity = repository.getOne(id);
            copyDtoToEntity(userDTO, userEntity);
            userEntity = repository.save(userEntity);
            return new UserDTO(userEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }

    private void copyDtoToEntity(UserDTO userDTO, User userEntity) {
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());

        userEntity.getRoles().clear();
        for(RoleDTO roleDTO : userDTO.getRoles()){
            Role role = roleRepository.getReferenceById(roleDTO.getId());
            userEntity.getRoles().add(role);
        }
    }

}
