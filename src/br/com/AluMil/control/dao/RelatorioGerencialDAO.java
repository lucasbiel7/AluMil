/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.RelatorioGerencial;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class RelatorioGerencialDAO extends GenericaDAO<RelatorioGerencial> {

    public RelatorioGerencialDAO() {
        super();
        criteria.addOrder(Order.asc("data"));
    }

    public List<RelatorioGerencial> pegarEntreData(Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.between("data", inicio, fim)).list();
        closeSession();
        return entitys;
    }

}
