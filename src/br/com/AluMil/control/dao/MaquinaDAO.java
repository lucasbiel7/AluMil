/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.Maquina;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class MaquinaDAO extends GenericaDAO<Maquina> {

    public List<Maquina> pegarPorNome(String nome) {
        entitys = criteria.add(Restrictions.eq("nome", nome)).add(Restrictions.eq("ativo", true)).list();
        closeSession();
        return entitys;
    }

    public List<Maquina> pegarPorAtivo(boolean ativo) {
        entitys = criteria.add(Restrictions.eq("ativo", ativo)).list();
        closeSession();
        return entitys;
    }
}
