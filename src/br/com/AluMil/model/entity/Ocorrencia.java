/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
public class Ocorrencia implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Usuario usuario;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRealizada;
    private int tempoGasto;
    private String ocorrencia;
    @ManyToOne
    private Manutencao manutencao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(Date dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public int getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(int tempoGasto) {
        this.tempoGasto = tempoGasto;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Manutencao getManutencao() {
        return manutencao;
    }

    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ocorrencia other = (Ocorrencia) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getOcorrencia();
    }
}
