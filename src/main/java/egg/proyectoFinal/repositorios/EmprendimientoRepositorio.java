package egg.proyectoFinal.repositorios;

import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Long> {

    @Query(value = "SELECT * FROM producto WHERE id_emprendimiento_producto=?1", nativeQuery = true)
    List<Producto> traerProductosPorEmprendimiento(Long id);


    @Query( value = "SELECT * FROM emprendimiento WHERE id_emprendimiento_usuario=?1", nativeQuery = true)
    List<Emprendimiento> traerEmprendimientosPorSesion(Long id);

   /* @Modifying
    @Query("UPDATE emprendimiento e SET e.eliminado = false WHERE e.id = ?1")
    void enableById(Long id);*/
}
