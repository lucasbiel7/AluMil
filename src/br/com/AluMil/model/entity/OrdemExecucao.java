/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
public class OrdemExecucao implements Serializable {

    public enum Periodicidade {
        DIARIO("Diário", 1), SEMANAL("Semanal", 7), MENSAL("Mensal", 30);
        private String nome;
        private int dias;

        private Periodicidade(String nome, int dias) {
            this.nome = nome;
            this.dias = dias;
        }

        public int getDias() {
            return dias;
        }

        @Override
        public String toString() {
            return nome;
        }
    }

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private CheckList checkList;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fim;
    private int quantidade;
    private Periodicidade periodicidade;
    @OneToMany(mappedBy = "id.ordemExecucao",cascade = CascadeType.REMOVE)
    private List<OperacaoOrdemExecucao> operacaoOrdemExecucaos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
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
        final OrdemExecucao other = (OrdemExecucao) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
