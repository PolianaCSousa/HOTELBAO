package com.hotelbao.util;

import com.hotelbao.dtos.RoomDTO;
import com.hotelbao.entities.Room;

public class Factory {

    public static Room createRoom() {
        Room room = new Room();

        room.setDescription("Quarto teste");
        room.setPrice(255);
        room.setImage_url("http://img.com/quartoteste.jpg");

        return room;
    }

    public static RoomDTO createRoomDTO() {
        Room room = createRoom();
        return new RoomDTO(room);
    }
}
