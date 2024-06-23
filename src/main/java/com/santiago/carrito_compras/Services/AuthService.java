package com.santiago.carrito_compras.Services;

import com.santiago.carrito_compras.Dto.SingUpRequestDto;
import com.santiago.carrito_compras.Dto.UserDto;
import com.santiago.carrito_compras.Entities.Rol;
import com.santiago.carrito_compras.Entities.User;
import com.santiago.carrito_compras.Interfaces.AuthInterface;
import com.santiago.carrito_compras.Repositories.RolRepository;
import com.santiago.carrito_compras.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthInterface {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RolRepository rolRepository;
    public AuthService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //SingUp method, receives DTO, encodes the password and saves in database
    public UserDto SingUpClient(SingUpRequestDto singUpRequestDto){

        Rol rol = rolRepository.findById(singUpRequestDto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol not found"));
        String encodedPassword = passwordEncoder.encode(singUpRequestDto.getPassword());

        User user = new User();
        user.setName(singUpRequestDto.getName());
        user.setEmail(singUpRequestDto.getEmail());
        user.setPassword(encodedPassword);
        user.setRol(rol);

        return userRepository.save(user).getDto();
    }

    public  Boolean presentByEmail(String email){
        return userRepository.findFirstByEmail(email) != null;
    }


}
