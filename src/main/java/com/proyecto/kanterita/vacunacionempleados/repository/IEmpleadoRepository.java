package com.proyecto.kanterita.vacunacionempleados.repository;

import com.proyecto.kanterita.vacunacionempleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {

    @Query("SELECT emp FROM Empleado emp WHERE emp.usuario= ?1 and emp.password= ?2")
    Empleado findEmpleado(String usuario, String password) throws Exception;
}
