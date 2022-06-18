package egg.proyectoFinal.seguridad;

import egg.proyectoFinal.servicios.UsuarioServicio;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter {


    private final UsuarioServicio usuarioServicio;


    private final BCryptPasswordEncoder codificador;

    public Seguridad(UsuarioServicio usuarioServicio, BCryptPasswordEncoder codificador) {
        this.usuarioServicio = usuarioServicio;
        this.codificador = codificador;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(codificador);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/home","/autenticacion/login", "/autenticacion/registro", "/usuario/*", "/producto/*","/emprendimiento/*", "/css/*", "/js/*", "/img/*").permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                    .formLogin()
                        .loginPage("/home")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("contrasenia")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/autenticacion/login?error=true")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/autenticacion/login?logout=true")
                        .permitAll()
                .and()
                    .csrf()
                    .disable();
    }
}
