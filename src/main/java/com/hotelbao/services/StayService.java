package com.hotelbao.services;

import com.hotelbao.dtos.RoleDTO;
import com.hotelbao.dtos.RoomDTO;
import com.hotelbao.dtos.StayDTO;
import com.hotelbao.dtos.UserDTO;
import com.hotelbao.entities.Role;
import com.hotelbao.entities.Room;
import com.hotelbao.entities.Stay;
import com.hotelbao.entities.User;
import com.hotelbao.repository.StayRepository;
import com.hotelbao.services.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StayService {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

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

        // So cria os objetos com o id, sem buscar ou copiar os outros dados
        User user = new User();
        user.setId(stayDTO.getUserId());//cria o objeto user apenas com o id
        stay.setUser(user);//passa o user somente com id para o stay

        Room room = new Room();
        room.setId(stayDTO.getRoomId());
        stay.setRoom(room);


        stay = stayRepository.save(stay);
        return new StayDTO(stay);
    }

    @Transactional
    public StayDTO getRoomDate (Long roomId, LocalDateTime endDate, LocalDateTime startDate) {
        Stay stay = stayRepository.getRoomDate(roomId, endDate, startDate);

        if (stay == null) {
            return null;  // ou você pode lançar uma exceção se quiser
        }
        return new StayDTO(stay);
        //return new StayDTO(stayRepository.getRoomDate(roomId, endDate, startDate));
    }


    // Altera estadia
    @Transactional
    public StayDTO update(StayDTO dto, Long id) {
        Stay entity = stayRepository.getReferenceById(id);

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        User user = new User();
        user.setId(dto.getUserId());
        entity.setUser(user);

        Room room = new Room();
        room.setId(dto.getRoomId());
        entity.setRoom(room);

        entity = stayRepository.save(entity);
        return new StayDTO(entity);
    }


    @Transactional
    public void delete(Long id) {

        stayRepository.deleteById(id);
    }

}
