/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.RelatorioMaquina;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class RelatorioMaquinaDAO extends GenericaDAO<RelatorioMaquina> {

    public RelatorioMaquinaDAO() {
        criteria.addOrder(Order.asc("data"));
    }

    public List<String> pegarMaquinas() {
        List<String> maquinas = criteria.setProjection(Projections.groupProperty("maquina")).list();
        closeSession();
        return maquinas;
    }

    public List<RelatorioMaquina> pegarEntreData(Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.between("data", inicio, fim)).list();
        closeSession();
        return entitys;
    }

    public List<RelatorioMaquina> pegarEntreDataMaquina(Date inicio, Date fim, String maquina) {
        entitys = criteria.add(Restrictions.between("data", inicio, fim)).add(Restrictions.eq("maquina", maquina)).list();
        closeSession();
        return entitys;
    }

}
