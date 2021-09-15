package com.proyecto.kanterita.vacunacionempleados.repository;

import com.proyecto.kanterita.vacunacionempleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
