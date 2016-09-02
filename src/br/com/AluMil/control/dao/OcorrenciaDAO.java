/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control.dao;

import br.com.AluMil.model.GenericaDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Manutencao;
import br.com.AluMil.model.entity.Maquina;
import br.com.AluMil.model.entity.Ocorrencia;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class OcorrenciaDAO extends GenericaDAO<Ocorrencia> {

    public Ocorrencia pegarPorManutencao(Manutencao manutencao) {
        entity = (Ocorrencia) criteria.add(Restrictions.eq("manutencao", manutencao)).uniqueResult();
        closeSession();
        return entity;
    }

    public List<Ocorrencia> pegarPorDispositivo(Dispositivo dispositivo) {
        List<Manutencao> manutencoes = new ManutencaoDAO().pegarPorDispositivo(dispositivo);
        if (manutencoes.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("manutencao", manutencoes)).list();
        closeSession();
        return entitys;
    }

    public List<Ocorrencia> pegarPorMaquinas(List<Maquina> maquinas, Date inicio, Date fim) {
        List<Manutencao> manutencoes = new ManutencaoDAO().pegarPorMaquinas(maquinas, inicio, fim);
        if (manutencoes.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("manutencao", manutencoes)).list();
        closeSession();
        return entitys;

    }

    public List<Ocorrencia> pegarPorDispositivo(Dispositivo dispositivo, List<Ciclo> ciclos) {
        List<Manutencao> manutencoes = new ManutencaoDAO().pegarPorDispositivoCiclos(dispositivo,ciclos);
        if (manutencoes.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("manutencao", manutencoes)).list();
        closeSession();
        return entitys;
    }

}
