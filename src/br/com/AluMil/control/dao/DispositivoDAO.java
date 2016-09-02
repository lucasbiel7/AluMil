/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Maquina;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class DispositivoDAO extends GenericaDAO<Dispositivo> {

    public List<Dispositivo> pegarPorNome(String nome) {
        entitys = criteria.add(Restrictions.eq("nome", nome)).add(Restrictions.eq("ativo", true)).list();
        closeSession();
        return entitys;
    }

    public List<Dispositivo> pegarPorNomeMaquina(String nome, Maquina maquina) {
        entitys = criteria.add(Restrictions.eq("nome", nome)).add(Restrictions.eq("maquina", maquina)).add(Restrictions.eq("ativo", true)).list();
        closeSession();
        return entitys;
    }

    public List<Dispositivo> pegarPorMaquinaAtivo(Maquina maquina, boolean ativo) {
        entitys = criteria.add(Restrictions.eq("maquina", maquina)).add(Restrictions.eq("ativo", ativo)).list();
        closeSession();
        return entitys;
    }

    public List<Dispositivo> pegarPorMaquinas(List<Maquina> maquinas) {
        if (maquinas.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("maquina", maquinas)).add(Restrictions.eq("ativo", true)).list();
        closeSession();
        return entitys;
    }

}
