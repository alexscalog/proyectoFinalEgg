package egg.proyectoFinal.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AplicacionConfiguracion {

    @Bean
    public BCryptPasswordEncoder codificador() {

        return new BCryptPasswordEncoder();
    }

}
