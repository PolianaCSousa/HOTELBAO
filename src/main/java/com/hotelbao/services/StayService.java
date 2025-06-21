package com.hotelbao.services;

import com.hotelbao.dtos.RoleDTO;
import com.hotelbao.dtos.StayDTO;
import com.hotelbao.dtos.UserDTO;
import com.hotelbao.entities.Role;
import com.hotelbao.entities.Stay;
import com.hotelbao.entities.User;
import com.hotelbao.repository.StayRepository;
import com.hotelbao.services.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StayService {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Page<StayDTO> findAll(Pageable pageable) {
        Page<Stay> list = stayRepository.findAll(pageable);
        return list.map(stay -> new StayDTO(stay));

    }

    // Busca estadia pelo id - único
    @Transactional(readOnly = true)
    public StayDTO findById(Long id) {
        Optional<Stay> opt = stayRepository.findById(id);
        Stay stay = opt.orElseThrow(
                () -> new ResourceNotFound("Estadia não encontrada")
        );
        return new StayDTO(stay);
    }

    // Busca todas as estadias de um determinado usuário - pelo id do usuario
    @Transactional(readOnly = true)
    public List<StayDTO> findByUser(Long userId) {
        List<Stay> list = stayRepository.findByUserId(userId);
        List<StayDTO> listDTO = new ArrayList<>();
        for (Stay stay : list) {
            listDTO.add(new StayDTO(stay));
        }
        return listDTO;
    }

    // Busca todas as estadias de um determinado quarto - pelo id do quarto
    @Transactional(readOnly = true)
    public List<StayDTO> findByRoom(Long roomId) {
        List<Stay> list = stayRepository.findByRoomId(roomId);
        List<StayDTO> listDTO = new ArrayList<>();
        for (Stay stay : list) {
            listDTO.add(new StayDTO(stay));
        }
        return listDTO;
    }

    @Transactional
    public StayDTO insert(StayDTO stayDTO) {
        Stay stay = new Stay();

        stay.setStartDate(stayDTO.getStartDate());
        stay.setEndDate(stayDTO.getEndDate());
        stay = stayRepository.save(stay);
        return new StayDTO(stay);
    }


    // Altera estadia
    @Transactional
    public StayDTO update(StayDTO dto, Long id) {
        Stay entity = stayRepository.getReferenceById(id);

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity = stayRepository.save(entity);
        return new StayDTO(entity);
    }


    @Transactional
    public void delete(Long id) {

        stayRepository.deleteById(id);
    }

}
