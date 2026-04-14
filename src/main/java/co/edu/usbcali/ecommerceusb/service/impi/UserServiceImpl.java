package co.edu.usbcali.ecommerceusb.service.impi;


import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //Inyeccion de dependencias de UserRepository
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> getUsers() {
        //Definir una liosta de Users
        List<User> users = userRepository.findAll();

        //Validar si la lista esta vacia
        if (users.isEmpty()) {
            return List.of();
        }
        /* si la lista contiene infomacion, entonces
        *retornamos una listra mapeada de UserResponse*/
         List<UserResponse> userResponses = UserMapper.modelToUserResponseList(users);
         return  userResponses;

    }

    @Override
    public UserResponse getUserById(Integer id) throws Exception {

        if (id == null || id <= 0){
            throw new Exception("Debe ingregar el id para buscar");
        }
        //Busca usuario en base de datos id
        // si no lo encuentras, lanza excepcion
        User user = userRepository.findById(id).
                orElseThrow(() ->
                        new Exception(
                                String.format("Usuario no encontrado con el id: %d", id)));

        //mapear a reponse
        UserResponse userResponse = UserMapper.modelToUserResponse(user);
        //Retornar usuario encontrado
        return userResponse;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return null;
    }
}
