package edu.school21.cinema.services;

import com.sun.javafx.binding.StringFormatter;
import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.AuthenticationsRepository;
import edu.school21.cinema.repositories.ImagesRepository;
import edu.school21.cinema.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public class UsersService {

    public void signIn(HttpServletRequest req, HttpServletResponse resp, UsersRepository usersRepository,
                       AuthenticationsRepository authRepository, PasswordEncoder encoder) throws ServletException, IOException {
        Optional<User> user = usersRepository.findByEmail(req.getParameter("email"));

        if (user.isPresent() && encoder.matches(req.getParameter("password"), user.get().getPassword())) {
            saveNewAuthentication(user.get().getId(), req, authRepository);
            req.getSession().setAttribute("user", user.get());
            resp.sendRedirect("/profile");
        }
        else {
            req.getRequestDispatcher("/WEB-INF/jsp/secure/SignIn.jsp").forward(req, resp);
        }
    }

    private void saveNewAuthentication(Long userId, HttpServletRequest request, AuthenticationsRepository authRepository) {
        Authentication authentication = createNewAuthentication(userId, request.getRemoteAddr());
        authRepository.save(authentication);
    }

    private Authentication createNewAuthentication(Long userId, String authIp) {
        Date authDate = new Date(System.currentTimeMillis());
        Time authTime = new Time(System.currentTimeMillis());

        return new Authentication(authDate, authTime, authIp, userId);
    }

    public void signUp(HttpServletRequest req, UsersRepository usersRepository, PasswordEncoder encoder) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");

        if (areAllUserInputDataValid(firstName, lastName, email, phoneNumber, password) && isUniqueEmail(email, usersRepository)) {
            User newUser = createUser(firstName, lastName, email, phoneNumber, encoder.encode(password));
            usersRepository.save(newUser);
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

    private boolean isUniqueEmail(String email, UsersRepository usersRepository) {
        return !usersRepository.findByEmail(email).isPresent();
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
    
    public void saveImage(User user, Part part, String storagePath, ImagesRepository imagesRepository) throws IOException {
        if (part.getSubmittedFileName().isEmpty()) {
            return;
        }
        String originalName = part.getSubmittedFileName();
        String uniqueName = getUniqueName(user.getId(), originalName);
        String imagePath = getImagePath(storagePath, uniqueName);
        Image image = new Image(originalName, uniqueName, imagePath, user.getId());

        saveImageToHardDrive(part.getInputStream(), imagePath);
        imagesRepository.save(image);
    }

    private String getUniqueName(Long userId, String originalName) {
        int indexOfDot = originalName.indexOf(".");
        return userId + "-" + System.currentTimeMillis() + (indexOfDot != -1 ? originalName.substring(indexOfDot) : "");
    }

    private String getImagePath(String storagePath, String uniqueName) {
        return storagePath + uniqueName;
    }

    private void saveImageToHardDrive(InputStream inputStream, String imagePath) throws IOException {
        FileOutputStream fileOutputStream = null;
        byte buf[] = new byte[1024];
        int read;

        try {
            fileOutputStream = new FileOutputStream(imagePath);

            while (true) {
                read = inputStream.read(buf, 0, 1024);

                if (read < 0) {
                    break;
                }
                fileOutputStream.write(buf, 0, read);
            }
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            inputStream.close();
        }
    }
}
