package com.nevesdev.controle_financeiro.service;

import com.nevesdev.controle_financeiro.model.debit.DebitOut;
import com.nevesdev.controle_financeiro.model.user.User;
import com.nevesdev.controle_financeiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    private void saveImage(UUID id, String pathImage) {
        User u = userRepository.findById(id).orElseThrow();
        u.setImage(pathImage);
        userRepository.save(u);
    }

    public String setImage(MultipartFile file, UUID userId) {
        if (file.isEmpty()) {
            return "Nenhum arquivo foi enviado.";
        }
        try {
            //String uploadDir = "/home/joao/Documentos/projetos/controlefinanceiro/front/imgs/";
            String uploadDir = "/var/www/html/controle-de-gastos/imgs/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File dest = new File(uploadDir + userId + ".png");
            file.transferTo(dest);
            saveImage(userId, uploadDir + userId + ".png");

            return "Imagem enviada com sucesso: " + dest.getAbsolutePath();
        } catch (IOException e) {
            return "Erro ao salvar a imagem: " + e.getMessage();
        }
    }

    public void removePicture(UUID userId) {
        Path path = Paths.get("/var/www/html/controle-de-gastos/imgs/" + userId + ".png");
        //path = Paths.get("/home/joao/Documentos/projetos/controlefinanceiro/front/imgs/" + userId + ".png");
        try {
            if(Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
