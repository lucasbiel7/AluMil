/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Maquina;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class CicloDAO extends GenericaDAO<Ciclo> {

    public CicloDAO() {
        super();
        criteria.addOrder(Order.asc("inicio"));
    }

    public List<Ciclo> pegarPorDispositivoInicioFim(Dispositivo dispositivo, Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.eq("dispositivo", dispositivo)).add(
                Restrictions.or(
                        Restrictions.between("inicio", inicio, fim),
                        Restrictions.between("fim", inicio, fim),
                        Restrictions.and(Restrictions.ge("inicio", inicio), Restrictions.le("fim", inicio)),
                        Restrictions.and(Restrictions.ge("inicio", fim), Restrictions.le("fim", fim))
                )).list();
        closeSession();
        return entitys;
    }

    public List<Ciclo> pegarPorDispositivo(Dispositivo dispositivo) {
        entitys = criteria.add(Restrictions.eq("dispositivo", dispositivo)).addOrder(Order.asc("inicio")).list();
        closeSession();
        return entitys;
    }

    public List<Ciclo> pegarPorMaquinas(List<Maquina> maquinas) {
        List<Dispositivo> dispositivos=new DispositivoDAO().pegarPorMaquinas(maquinas);
        if (dispositivos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("dispositivo", dispositivos)).addOrder(Order.asc("inicio")).list();
        closeSession();
        return entitys;
    }

}
