package com.hotelbao.services;

import com.hotelbao.dtos.*;
import com.hotelbao.entities.Role;
import com.hotelbao.entities.User;
import com.hotelbao.projections.RoomDetailsProjection;
import com.hotelbao.projections.UserDetailsProjection;
import com.hotelbao.repository.RoleRepository;
import com.hotelbao.repository.StayRepository;
import com.hotelbao.repository.UserRepository;
import com.hotelbao.services.exceptions.DatabaseException;
import com.hotelbao.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StayRepository stayRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(u -> new UserDTO(u));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> opt = userRepository.findById(id);
        User user = opt.orElseThrow(() -> new ResourceNotFound("Usuário não encontrado"));

        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(
                passwordEncoder.encode(dto.getPassword()));
        User newUser = userRepository.save(entity);

        return new UserDTO(newUser);
    }
    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setUsername(dto.getUsername());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());

        entity.getRoles().clear();
        for (RoleDTO role : dto.getRoles()) {
            Role r = roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(r);
        }
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);

            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Usuário com id: " + id + " não encontrado");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFound("Usuário não encontrado");
        }
        try {
            userRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    //@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result
                = userRepository.searchUserAndRoleByUsername(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Usuário: "+ username +" não encontrado");
        }

        User user = new User();
        user.setUsername(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection p : result) {
            user.addRole(new Role(p.getRoleId(),p.getAuthority()));
        }


        return user;
    }

    public UserDTO signup(UserInsertDTO dto) {

        User entity = new User();
        copyDtoToEntity(dto,entity);

        Role role=
                roleRepository.findByAuthority("ROLE_OPERATOR");
        entity.getRoles().clear();
        entity.getRoles().add(role); //inserimos o perfil de Operador
        entity.setPassword(
                passwordEncoder.encode(dto.getPassword()));
        User novo = userRepository.save(entity);

        return new UserDTO(novo);

    }

    public RoomDetailsProjection expensiveStay(Long id) {
        return stayRepository.getMaxPrice(id);
    }

    public RoomDetailsProjection cheapStay(Long id) {
        return stayRepository.getMinPrice(id);
    }

    public RoomDetailsProjection totalStay(Long id) {
        return stayRepository.getSumPrice(id);
    }

    public String getNfe(Long id) {
        List<RoomDetailsProjection> lista = userRepository.searchUserAndRoomByUserId(id);
        String nfe = findById(id).getName() + "\n";
        if (lista != null && !lista.isEmpty()) {
            Double total = stayRepository.getSumPrice(id).getPrice();
            for (RoomDetailsProjection p : lista) {
                nfe += "Quarto: " + p.getDescription();
                nfe += "\tPreço: " + p.getPrice() + "\n";
            }

            nfe += "Total: " + total;
        }
        else {
            nfe += "Não possui nenhuma estadia cadastrada!";
        }
        return nfe;
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();

    }

}
