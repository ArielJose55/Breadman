/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.util;

import breadman.logica.modelo.entidades.Categoria;
import breadman.logica.modelo.persistencia.daos.ControlCategoria;
import breadman.logica.modelo.persistencia.daos.ControlPanadero;
import breadman.logica.modelo.persistencia.daos.ControlProveedor;
import breadman.logica.modelo.persistencia.daos.ControlVendedor;
import java.util.Collection;

/**public ComboBoxModelo(TipoDato tipoDato) {
        super();
        this.tipoDato = tipoDato;
        list = new java.util.ArrayList<>();
        initComponents();
    }
    * 
 *
 * @author Ariel Arnedo
 * @param <Object>
 */
public class ComboBoxModelo <Object> extends javax.swing.DefaultComboBoxModel<Object>{
    
    private TipoDato tipoDato;
    private final java.util.List<Object> list;
    

    public ComboBoxModelo(TipoDato tipoDato){
        super();
        this.tipoDato = tipoDato;
        list = new java.util.ArrayList<>();
        initComponents();
    }

    public ComboBoxModelo(java.util.List<Object> list) {
        this.list = list;
        showItems(true);
    }

    
    
    private void initComponents(){
        switch(tipoDato){
            
            case CATEGORIAS:{
                list.addAll((Collection<? extends Object>) new ControlCategoria().listarTodas());
                fireIntervalAdded(this, 0, list.size());
                break;
            }
            
            case CLIENTES:{
                
//                list.addAll(ControlTipoDeEmpleado.listarTodosLosTipoDeEmpleados());
//                fireIntervalAdded(this, 0, list.size());

                break;
            }
            
            case PRODUCTOS:{
//                list.addAll(new Sucursal(Usuario.getUsuarioDeSession().getCodigoSurcusal()).getListaDeDependencias());
//                fireIntervalAdded(this, 0, list.size());
                break;
            }
            
            case VENDEDORES:{
                list.addAll((Collection<? extends Object>) new ControlVendedor().listarTodas());
                fireIntervalAdded(this, 0, list.size());
                break;
            }
                     
            case USUARIOS:{
//                list.addAll(ControlComida.listaDeComida());
//                fireIntervalAdded(this, 0, list.size());
                break;
            }
            
            case PROVEEDORES:{
                list.addAll((Collection<? extends Object>) new ControlProveedor().listarTodas());
                fireIntervalAdded(this, 0, list.size());
                break;
            }
            case PANADEROS:{
                list.addAll((Collection<? extends Object>) new ControlPanadero().listarTodas());
                fireIntervalAdded(this, 0, list.size());
                break;
            }
        }
    }

    public void showItems(boolean ocultar){
        if(!ocultar){
            fireIntervalRemoved(this, 0, list.size());
        }else{
            fireIntervalAdded(this, 0, list.size());
        }
    }
    
    public TipoDato getTipoDato() {
        return tipoDato;
    }
    
    @Override
    public Object getElementAt(int index) {
        return ( list == null || list.isEmpty()) ? null : list.get(index);
    }

    @Override
    public int getSize() {
        return ( list == null || list.isEmpty()) ? 0 : list.size();
    }
    
    
}
