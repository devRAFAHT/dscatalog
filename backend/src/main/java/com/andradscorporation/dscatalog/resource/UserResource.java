package com.andradscorporation.dscatalog.resource;

import com.andradscorporation.dscatalog.dto.UserDTO;
import com.andradscorporation.dscatalog.dto.UserInsertDTO;
import com.andradscorporation.dscatalog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
        Page<UserDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO userDTO = service.findById(id);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO userDTO){
        UserDTO newDTO = service.insert(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO){
        userDTO = service.update(id, userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable  Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
