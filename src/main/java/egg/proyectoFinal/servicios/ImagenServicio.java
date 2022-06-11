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

    //Directorio donde se alojan las img que ingresa el usuario
    private static final String DIRECTORY = "src/main/resources/productos_img";

    public String copiar(MultipartFile imagen) {
        try {
            String nombreImagen = imagen.getOriginalFilename();//extrae el nombre de la img, getOriginalFilename es un método de MultiparFile.
            Path rutaImagen = Paths.get(DIRECTORY, nombreImagen).toAbsolutePath();//establecemos el directorio y el nombre de la img y genera una ruta absoluta de esa foto.
            //A través del método copy de la clase Files, copio la img en la carpeta y si existe un archivo con ese nombre lo reemplaza
            Files.copy(imagen.getInputStream(), rutaImagen, StandardCopyOption.REPLACE_EXISTING);
            return nombreImagen;

            //La invocación del método getInputStream genera una excepción
        } catch (IOException e) {
            throw new IllegalArgumentException("Error al guardar la imágen");
        }
    }
}
