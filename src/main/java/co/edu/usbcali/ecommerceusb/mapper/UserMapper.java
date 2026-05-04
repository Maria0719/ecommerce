package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.model.User;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserMapper {

    public static UserResponse modelToUserResponse (User user) {
        DocumentType documentType = user.getDocumentType();

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .documentTypeId(
                        user.getDocumentType() != null ? user.getDocumentType().getId() : null)
                .documentTypeName(
                        user.getDocumentNumber()!= null ? user.getDocumentType().getName() : null)
                .documentNumber(user.getDocumentNumber())
                .build();
    }
    public static List<UserResponse> modelToUserResponseList(List<User> users) {
        return users.stream().map(UserMapper::modelToUserResponse).toList();
    }
    public static User createUserRequestToUser(CreateUserRequest createUserRequest,
                                               DocumentType documentType){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Convertir el objeto createUserRequest a User
        return User.builder()
                .fullName(createUserRequest.getFullName())
                .phone(createUserRequest.getPhone())
                .email(createUserRequest.getEmail())
                .documentType(documentType)
                .documentNumber(createUserRequest.getDocumentNumber())
                .birthDate(
                        LocalDate.parse(
                                createUserRequest.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .country(createUserRequest.getCountry())
                .address(createUserRequest.getAddress())
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();

    }

}

