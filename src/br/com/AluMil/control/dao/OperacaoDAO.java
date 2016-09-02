/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.Maquina;
import br.com.AluMil.model.entity.Operacao;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class OperacaoDAO extends GenericaDAO<Operacao> {

    public List<Operacao> pegarPorMaquina(Maquina maquina) {
        entitys = criteria.add(Restrictions.eq("maquina", maquina)).list();
        closeSession();
        return entitys;
    }

}
