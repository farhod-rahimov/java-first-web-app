package edu.school21.cinema.services;

import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.AuthenticationRepository;
import edu.school21.cinema.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public class UsersService {

    public void signIn(HttpServletRequest req, HttpServletResponse resp, UsersRepository repository, AuthenticationRepository authRepository,
                       PasswordEncoder encoder) throws ServletException, IOException {
        Optional<User> user = repository.findByEmail(req.getParameter("email"));
        RequestDispatcher requestDispatcher;

        if (user.isPresent() && encoder.matches(req.getParameter("password"), user.get().getPassword())) {
            saveNewAuthentication(user.get().getId(), req, authRepository);
            req.getSession().setAttribute("user", user.get());
            req.getSession().setAttribute("authentications", authRepository.findAllByUserId(user.get().getId()));
            requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/secure/SignInSucceeded.jsp");
        }
        else {
            requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/secure/SignIn.jsp");
        }
        requestDispatcher.forward(req, resp);
    }

    void saveNewAuthentication(Long userId, HttpServletRequest request, AuthenticationRepository authRepository) {
        Authentication authentication = createNewAuthentication(userId, request.getRemoteAddr());
        authRepository.save(authentication);
    }

    Authentication createNewAuthentication(Long userId, String authIp) {
        Date authDate = new Date(System.currentTimeMillis());
        Time authTime = new Time(System.currentTimeMillis());

        return new Authentication(authDate, authTime, authIp, userId);
    }

    public void signUp(HttpServletRequest req, UsersRepository repository, PasswordEncoder encoder) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");

        if (areAllUserInputDataValid(firstName, lastName, email, phoneNumber, password) && isUniqueEmail(email, repository)) {
            User newUser = createUser(firstName, lastName, email, phoneNumber, encoder.encode(password));
            repository.save(newUser);
        }
        else {
            throw new UsersServiceException(UsersServiceExceptionEnum.WRONG_INPUT_DATA);
        }
    }

    private boolean areAllUserInputDataValid(String firstName, String lastName, String email, String phoneNumber, String password) {
        return isValidFirstAndLastNames(firstName, lastName) && isValidEmail(email) && isValidPhoneNumber(phoneNumber) && isValidPassword(password);

    }

    private boolean isValidFirstAndLastNames(String firstName, String lastName) {
        return !firstName.isEmpty() && firstName.length() <= 70 && !lastName.isEmpty() && lastName.length() <= 70;
    }

    private boolean isValidEmail(String email) {
        boolean atFound = false;

        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atFound = true;
            }
            else if (email.charAt(i) == '.' && atFound) {
                return email.length() <= 70;
            }
        }
        return false;
    }

    private boolean isUniqueEmail(String email, UsersRepository repository) {
        return !repository.findByEmail(email).isPresent();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return !phoneNumber.isEmpty() && phoneNumber.length() <= 20;
    }

    private boolean isValidPassword(String password) {
        boolean thereIsUpperCaseChar = false;
        boolean thereIsLowerCaseChar = false;
        boolean thereIsNumber = false;

        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                thereIsUpperCaseChar = true;
            }
            else if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
                thereIsLowerCaseChar = true;
            }
            else if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                thereIsNumber = true;
            }

            if (thereIsUpperCaseChar && thereIsLowerCaseChar && thereIsNumber) {
                break;
            }
        }
        return thereIsUpperCaseChar && thereIsLowerCaseChar && thereIsNumber &&
                password.length() >= 8 && password.length() <= 80;
    }

    private User createUser(String firstName, String lastName, String email, String phoneNumber, String password) {
        return new User(firstName, lastName, email, phoneNumber, password);
    }
}
