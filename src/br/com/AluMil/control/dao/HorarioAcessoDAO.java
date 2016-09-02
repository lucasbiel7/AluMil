/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.HorarioAcesso;
import br.com.AluMil.model.entity.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class HorarioAcessoDAO extends GenericaDAO<HorarioAcesso> {

    public HorarioAcesso pegarPorUsuarioDiaHora(Usuario usuario, int j, int i) {
        entity = (HorarioAcesso) criteria.add(Restrictions.eq("usuario", usuario)).add(Restrictions.eq("dia", j)).add(Restrictions.eq("hora", i)).uniqueResult();
        closeSession();
        return entity;
    }

    public List<HorarioAcesso> pegarPorUsuario(Usuario usuario) {
        entitys = criteria.add(Restrictions.eq("usuario", usuario)).list();
        closeSession();
        return entitys;
    }

}
