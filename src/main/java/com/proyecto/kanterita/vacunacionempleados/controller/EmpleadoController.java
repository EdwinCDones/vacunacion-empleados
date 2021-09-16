package com.proyecto.kanterita.vacunacionempleados.controller;

import com.proyecto.kanterita.vacunacionempleados.entity.Empleado;
import com.proyecto.kanterita.vacunacionempleados.entity.EmpleadoRequest;
import com.proyecto.kanterita.vacunacionempleados.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @PostMapping("/empleado")
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleadoRequest) {

        try {

            Empleado empleadoResponse = new Empleado();
            empleadoResponse.setNombre(empleadoRequest.getNombre());
            empleadoResponse.setApellido(empleadoRequest.getApellido());
            empleadoResponse.setCedula(empleadoRequest.getCedula());
            empleadoResponse.setCorreo(empleadoRequest.getCorreo());
            empleadoResponse.setRol(empleadoRequest.getRol());
            createUserAndPass(empleadoRequest, empleadoResponse);

            System.out.println("Usuario Hash " + empleadoResponse);

            return new ResponseEntity<>(empleadoRepository.save(empleadoResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createUserAndPass(Empleado empRequest, Empleado empleadoResponse) {

        char usuario = empRequest.getNombre().charAt(0); //obtener la primera letra del nombre
        LocalDate fechaActual = LocalDate.now();
        String usuarioHash = new StringBuilder().append(usuario).append(empRequest.getApellido()).toString();
        String passwordHash = new StringBuilder().append(usuario).append(empRequest.getApellido())
                .append(fechaActual.getYear()).toString();
        empleadoResponse.setUsuario(usuarioHash.toLowerCase());
        empleadoResponse.setPassword(passwordHash.toLowerCase());

    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> obtenerEmpleados() {
        try {
            List<Empleado> empleados = empleadoRepository.findAll();
            if (empleados.isEmpty() || empleados.size() == 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(empleados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable Integer id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);

        if (empleado.isPresent()) {
            return new ResponseEntity<>(empleado.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@RequestBody Empleado emp) {
        try {
            return new ResponseEntity<>(empleadoRepository.save(emp), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/eliminarEmpleado/{id}")
    public ResponseEntity<HttpStatus> eliminarEmpleado(@PathVariable Integer id) {
        try {
            Optional<Empleado> customer = empleadoRepository.findById(id);
            if (customer.isPresent()) {
                empleadoRepository.delete(customer.get());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/empleado/login")
    public ResponseEntity<Empleado> obtenerEmpleado(@RequestBody EmpleadoRequest empleadoRequest) {
        try {
            Empleado emp = empleadoRepository.findEmpleado(empleadoRequest.usuario, empleadoRequest.password);
            if (emp == null) {

                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {

                return new ResponseEntity<>(emp, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
