/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.Usuario;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class UsuarioDAO extends GenericaDAO<Usuario> {

    public Usuario pegarPorMatricula(String matricula) {
        entity = (Usuario) criteria.add(Restrictions.eq("matricula", matricula)).uniqueResult();
        closeSession();
        return entity;
    }

}
