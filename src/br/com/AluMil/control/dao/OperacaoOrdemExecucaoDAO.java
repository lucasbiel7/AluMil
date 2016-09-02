/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.OperacaoOrdemExecucao;
import br.com.AluMil.model.entity.OrdemExecucao;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class OperacaoOrdemExecucaoDAO extends GenericaDAO<OperacaoOrdemExecucao> {

    public List<OperacaoOrdemExecucao> pegarPorOrdemExecucao(OrdemExecucao ordemExecucao) {
        entitys = criteria.add(Restrictions.eq("id.ordemExecucao", ordemExecucao)).list();
        closeSession();
        return entitys;
    }

}
