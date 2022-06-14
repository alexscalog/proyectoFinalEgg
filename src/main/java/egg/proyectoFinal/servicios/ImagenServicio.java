package egg.proyectoFinal.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImagenServicio {


    private static final String DIRECTORY = "src/main/resources/imagenes";

    public String copiar(MultipartFile imagen) {
        try {
            String nombreImagen = imagen.getOriginalFilename();
            Path rutaImagen = Paths.get(DIRECTORY, nombreImagen).toAbsolutePath();

            Files.copy(imagen.getInputStream(), rutaImagen, StandardCopyOption.REPLACE_EXISTING);
            return nombreImagen;


        } catch (IOException e) {
            throw new IllegalArgumentException("Error al guardar la imagen");
        }
    }
}
