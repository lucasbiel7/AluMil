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
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ManutencaoDAO extends GenericaDAO<Manutencao> {

    public List<Manutencao> pegarPorCiclo(Ciclo ciclo) {
        entitys = criteria.add(Restrictions.eq("ciclo", ciclo)).list();
        closeSession();
        return entitys;
    }

    public List<Manutencao> pegarPorDispositivoData(Dispositivo dispositivo, Date date) {
        List<Ciclo> ciclos = new CicloDAO().pegarPorDispositivo(dispositivo);
        if (ciclos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("ciclo", ciclos)).add(Restrictions.le("dataPrevista", date)).list();
        closeSession();
        return entitys;
    }

    public List<Manutencao> pegarPorDispositivo(Dispositivo dispositivo) {
        List<Ciclo> ciclos = new CicloDAO().pegarPorDispositivo(dispositivo);
        if (ciclos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("ciclo", ciclos)).list();
        closeSession();
        return entitys;
    }

    public List<Manutencao> pegarPorMaquinas(List<Maquina> maquinas, Date inicio, Date fim) {
        List<Ciclo> ciclos = new CicloDAO().pegarPorMaquinas(maquinas);
        if (ciclos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("ciclo", ciclos)).add(Restrictions.between("dataPrevista", inicio, fim)).list();
        closeSession();
        return entitys;
    }

    public List<Manutencao> pegarPorDispositivoCiclos(Dispositivo dispositivo, List<Ciclo> ciclosTeste) {
        List<Ciclo> ciclos = new CicloDAO().pegarPorDispositivo(dispositivo);
        ciclos.removeIf((Ciclo t) -> !ciclosTeste.contains(t));
        if (ciclos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("ciclo", ciclos)).list();
        closeSession();
        return entitys;
    }

}
