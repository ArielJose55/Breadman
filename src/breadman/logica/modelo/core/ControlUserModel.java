/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.core;

import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Usuario;
import breadman.logica.modelo.entidades.Vendedor;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlUserModel {
    
    private static ControlUserModel controlUserModel = null;
    
    private Usuario usuario;

    private ControlUserModel() {}
    
    private ControlUserModel(Usuario usuario){
        this.usuario = usuario;
    }
    
    public static ControlUserModel getInstance(Usuario usuario){
        if(controlUserModel == null){
            controlUserModel = new ControlUserModel(usuario);
        }
        return controlUserModel;
    }
    
    public static ControlUserModel getInstance(){
        if(controlUserModel == null){
            controlUserModel = new ControlUserModel();
        }
        return controlUserModel;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public Panadero getPanadero(){
        for(Panadero panadero : usuario.getPanaderoList()){
            if(panadero.getUsuario().getCedula().equals(usuario.getCedula())){
                return panadero;
            }
        }
        return null;
    }
    
    public Vendedor getVendedor(){
        for(Vendedor vendedor : usuario.getVendedorList()){
            if(vendedor.getUsuario().getCedula().equals(usuario.getCedula())){
                return vendedor;
            }
        }
        return null;
    }
}
