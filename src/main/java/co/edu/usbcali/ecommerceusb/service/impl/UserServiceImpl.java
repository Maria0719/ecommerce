package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return List.of();
        return UserMapper.modelToUserResponseList(users);
    }

    @Override
    public UserResponse getUserById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingregar el id para buscar");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Usuario no encontrado con el id: %d", id)));
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) throws Exception {
        if (email == null || email.isBlank()) throw new Exception("Debe ingresar el email");
        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception(String.format("Usuario no encontrado con el email: %s", email)));
        return UserMapper.modelToUserResponse(userByEmail);
    }

    @Override
    public UserResponse createUser(CreateUserRequest req) throws Exception {
        if (Objects.isNull(req)) throw new Exception("El objeto createUserRequest no puede ser nulo");
        if (Objects.isNull(req.getFullName()) || req.getFullName().isBlank()) throw new Exception("El campo fullName no puede ser nulo ni vacío");
        if (Objects.isNull(req.getPhone()) || req.getPhone().isBlank()) throw new Exception("El campo phone no puede ser nulo ni vacío");
        if (Objects.isNull(req.getEmail()) || req.getEmail().isBlank()) throw new Exception("El campo email no puede ser nulo ni vacío");
        if (req.getDocumentTypeId() == null || req.getDocumentTypeId() <= 0) throw new Exception("El campo documentTypeId debe contener un valor mayor a 0");
        if (req.getDocumentNumber() == null || req.getDocumentNumber().isBlank()) throw new Exception("El campo documentNumber no puede estar nulo ni vacío");
        if (Objects.isNull(req.getBirthDate()) || req.getBirthDate().isBlank()) throw new Exception("El campo birthDate no puede estar nulo ni vacío");
        if (Objects.isNull(req.getCountry()) || req.getCountry().isBlank()) throw new Exception("El campo country no puede estar nulo ni vacío");
        if (Objects.isNull(req.getAddress()) || req.getAddress().isBlank()) throw new Exception("El campo address no puede estar nulo ni vacío");
        DocumentType documentType = documentTypeRepository.findById(req.getDocumentTypeId()).orElseThrow(() -> new Exception("El tipo de documento no existe"));
        if (userRepository.existsByEmail(req.getEmail())) throw new Exception("Ya existe un usuario con el email ingresado");
        if (userRepository.existsByDocumentNumberAndDocumentTypeId(req.getDocumentNumber(), req.getDocumentTypeId()))
            throw new Exception("Ya existe un usuario con el documento y tipo de documento ingresado");
        User user = UserMapper.createUserRequestToUser(req, documentType);
        user = userRepository.save(user);
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest req) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para actualizar");
        if (Objects.isNull(req)) throw new Exception("El objeto updateUserRequest no puede ser nulo");
        if (Objects.isNull(req.getFullName()) || req.getFullName().isBlank()) throw new Exception("El campo fullName no puede ser nulo ni vacío");
        if (Objects.isNull(req.getPhone()) || req.getPhone().isBlank()) throw new Exception("El campo phone no puede ser nulo ni vacío");
        if (Objects.isNull(req.getEmail()) || req.getEmail().isBlank()) throw new Exception("El campo email no puede ser nulo ni vacío");
        if (req.getDocumentTypeId() == null || req.getDocumentTypeId() <= 0) throw new Exception("El campo documentTypeId debe contener un valor mayor a 0");
        if (req.getDocumentNumber() == null || req.getDocumentNumber().isBlank()) throw new Exception("El campo documentNumber no puede estar nulo ni vacío");
        if (Objects.isNull(req.getBirthDate()) || req.getBirthDate().isBlank()) throw new Exception("El campo birthDate no puede estar nulo ni vacío");
        if (Objects.isNull(req.getCountry()) || req.getCountry().isBlank()) throw new Exception("El campo country no puede estar nulo ni vacío");
        if (Objects.isNull(req.getAddress()) || req.getAddress().isBlank()) throw new Exception("El campo address no puede estar nulo ni vacío");

        User user = userRepository.findById(id).orElseThrow(() -> new Exception(String.format("Usuario no encontrado con el id: %d", id)));
        DocumentType documentType = documentTypeRepository.findById(req.getDocumentTypeId()).orElseThrow(() -> new Exception("El tipo de documento no existe"));

        // Validar email único si cambió
        if (!user.getEmail().equals(req.getEmail()) && userRepository.existsByEmail(req.getEmail()))
            throw new Exception("Ya existe un usuario con el email ingresado");

        // Validar documento único si cambió
        if ((!user.getDocumentNumber().equals(req.getDocumentNumber()) || !user.getDocumentType().getId().equals(req.getDocumentTypeId()))
                && userRepository.existsByDocumentNumberAndDocumentTypeId(req.getDocumentNumber(), req.getDocumentTypeId()))
            throw new Exception("Ya existe un usuario con el documento y tipo de documento ingresado");

        user.setFullName(req.getFullName());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setDocumentType(documentType);
        user.setDocumentNumber(req.getDocumentNumber());
        user.setBirthDate(LocalDate.parse(req.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        user.setCountry(req.getCountry());
        user.setAddress(req.getAddress());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        user = userRepository.save(user);
        return UserMapper.modelToUserResponse(user);
    }
}
