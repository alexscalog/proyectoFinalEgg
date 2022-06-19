package egg.proyectoFinal.repositorios;


import egg.proyectoFinal.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {


    List<Producto> findByCategoria(String categoria);


    List<Producto> findByNombreEmprendimiento(Long nombreEmprendimiento);

}
