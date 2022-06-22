package egg.proyectoFinal.repositorios;


import egg.proyectoFinal.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    @Query(value = "SELECT * FROM producto WHERE categoria LIKE %?1%", nativeQuery = true)
    List<Producto> findAll(String categoria);

    @Query(value = "SELECT * FROM producto WHERE id_emprendimiento_producto=?1", nativeQuery = true)
    List<Producto> findByEmprendimiento(Long id);



}
