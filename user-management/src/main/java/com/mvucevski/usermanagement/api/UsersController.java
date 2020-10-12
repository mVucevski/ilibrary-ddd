package com.mvucevski.usermanagement.api;

import com.mvucevski.usermanagement.api.payload.*;
import com.mvucevski.usermanagement.domain.User;
import com.mvucevski.usermanagement.domain.UserId;
import com.mvucevski.usermanagement.security.JwtTokenProvider;
import com.mvucevski.usermanagement.service.MapValidationErrorService;
import com.mvucevski.usermanagement.service.UsersService;
import com.mvucevski.usermanagement.validator.UserValidator;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.security.Principal;

import static com.mvucevski.usermanagement.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UsersController {

    private final MapValidationErrorService mapValidationErrorService;
    private final UsersService usersService;
    private final UserValidator userValidator;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public UsersController(MapValidationErrorService mapValidationErrorService,
                           UsersService usersService,
                           UserValidator userValidator,
                           JwtTokenProvider tokenProvider,
                           AuthenticationManager authenticationManager) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.usersService = usersService;
        this.userValidator = userValidator;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        //UserDetails user = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());


        return ResponseEntity.ok(new JWTLoginSuccessReponse(true, jwt, authentication.getAuthorities().iterator().next().toString()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result){

        userValidator.validate(registerRequest,result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(result);
        if(errorMap != null) return errorMap;

        User newUser = usersService.createUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getFullName());

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(Principal principal){
        //TODO handle NullPointerException if user is not logged it
        User user = usersService.getUser(principal.getName());
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/grantMembership/{username}")
    public ResponseEntity<?> grantMemembership(@PathVariable String username){
        System.out.println("INNN");
        boolean grantMem = usersService.grantMembership(username);
        return new ResponseEntity<String>("The user '" + username + "' successfully was granted membership.",HttpStatus.OK);
    }

    @GetMapping("/authUser")
    public ResponseEntity<?> authUser(Principal principal){
        if(principal==null){
            return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
        User user = usersService.getUser(principal.getName());

        return new ResponseEntity<>(new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.isMembershipExpired(),
                user.getRoles().stream().findFirst().get().getName()), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        User user = usersService.loadUserById(new UserId(userId));
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.isMembershipExpired(),
                user.getRoles().stream().findFirst().get().getName());


        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        User user = usersService.loadUserByUsername(username);
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.isMembershipExpired(),
                user.getRoles().stream().findFirst().get().getName());

        return new ResponseEntity<>(
                userDTO,
                HttpStatus.OK);
    }
}
