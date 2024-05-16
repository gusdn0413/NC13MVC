package controller;

import model.UserDTO;

import java.util.ArrayList;

public class UserController {
    private ArrayList<UserDTO> list;

    private int nextId;

    public UserController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(UserDTO userDTO) {
        userDTO.setId(nextId++);
        list.add(userDTO);
    }

    public boolean validateUsername(String username) {
        for (UserDTO userDTO : list) {
            if (userDTO.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    public UserDTO selectOne(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        if (list.contains(userDTO)) {
            return list.get(list.indexOf(userDTO));
        }

        return null;
    }

    public void update(UserDTO userDTO) {
        int index = list.indexOf(userDTO);
        list.set(index, userDTO);
    }

    public void delete(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        list.remove(userDTO);
    }

    public UserDTO auth(String username, String password) {
        for (UserDTO userDTO : list) {
            if (username.equalsIgnoreCase(userDTO.getUsername())) {
                if (password.equals(userDTO.getPassword())) {
                    return userDTO;
                }
            }
        }
        return null;
    }
}
