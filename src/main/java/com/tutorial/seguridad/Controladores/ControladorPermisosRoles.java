package com.tutorial.seguridad.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.seguridad.Modelos.Permiso;
import com.tutorial.seguridad.Modelos.PermisosRoles;
import com.tutorial.seguridad.Modelos.Rol;
import com.tutorial.seguridad.Repositorios.RepositorioPermiso;
import com.tutorial.seguridad.Repositorios.RepositorioPermisosRoles;
import com.tutorial.seguridad.Repositorios.RepositorioRol;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisosRoles {

    @Autowired
    private RepositorioPermisosRoles miRepositorioPermisosRoles;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<PermisosRoles> index() {
        return this.miRepositorioPermisosRoles.findAll();
    }

    @GetMapping("{id}")
    public PermisosRoles show(@PathVariable String id) {
        return this.miRepositorioPermisosRoles.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles create(@PathVariable String id_rol, @PathVariable String id_permiso) {
        PermisosRoles permisosRoles = new PermisosRoles();
        Permiso permiso = this.miRepositorioPermiso.findById(id_permiso).orElse(null);
        Rol rol = this.miRepositorioRol.findById(id_rol).orElse(null);
        if (permiso != null && rol != null) {
            permisosRoles.setPermiso(permiso);
            permisosRoles.setRol(rol);
            return this.miRepositorioPermisosRoles.save(permisosRoles);
        } else {
            return null;
        }
    }

    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles update(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso) {
        PermisosRoles permisosRolesActual = this.miRepositorioPermisosRoles.findById(id).orElse(null);
        Permiso permisoActual = this.miRepositorioPermiso.findById(id_permiso).orElse(null);
        Rol rolActual = this.miRepositorioRol.findById(id_rol).orElse(null);
        if (permisosRolesActual != null && permisoActual != null && rolActual != null) {
            permisosRolesActual.setPermiso(permisoActual);
            permisosRolesActual.setRol(rolActual);
            return this.miRepositorioPermisosRoles.save(permisosRolesActual);
        } else {
            return null;
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        PermisosRoles permisosRoles = this.miRepositorioPermisosRoles.findById(id).orElse(null);
        if (permisosRoles != null) {
            this.miRepositorioPermisosRoles.delete(permisosRoles);
        }
    }

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisosRoles getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso) {
        Permiso elPermiso = this.miRepositorioPermiso.getPermiso(infoPermiso.getUrl(), infoPermiso.getMetodo());
        Rol elRol = this.miRepositorioRol.findById(id_rol).get();
        if (elPermiso != null && elRol != null) {
            return this.miRepositorioPermisosRoles.getPermisoRol(elRol.get_id(), elPermiso.get_id());
        } else {
            return null;
        }
    }
}
