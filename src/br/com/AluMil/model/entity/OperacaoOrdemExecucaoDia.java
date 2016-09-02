/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI01
 */
@Entity
public class OperacaoOrdemExecucaoDia implements Serializable {

    @Embeddable
    public static class OperacaoOrdemExecucaoDiaId implements Serializable {

        @ManyToOne
        private OperacaoOrdemExecucao operacaoOrdemExecucao;
        private int dia;

        public OperacaoOrdemExecucaoDiaId(OperacaoOrdemExecucao operacaoOrdemExecucao, int dia) {
            this.operacaoOrdemExecucao = operacaoOrdemExecucao;
            this.dia = dia;
        }

        public OperacaoOrdemExecucaoDiaId() {
            
        }

        public OperacaoOrdemExecucao getOperacaoOrdemExecucao() {
            return operacaoOrdemExecucao;
        }

        public void setOperacaoOrdemExecucao(OperacaoOrdemExecucao operacaoOrdemExecucao) {
            this.operacaoOrdemExecucao = operacaoOrdemExecucao;
        }

        public int getDia() {
            return dia;
        }

        public void setDia(int dia) {
            this.dia = dia;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.operacaoOrdemExecucao);
            hash = 97 * hash + this.dia;
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
            final OperacaoOrdemExecucaoDiaId other = (OperacaoOrdemExecucaoDiaId) obj;
            if (this.dia != other.dia) {
                return false;
            }
            if (!Objects.equals(this.operacaoOrdemExecucao, other.operacaoOrdemExecucao)) {
                return false;
            }
            return true;
        }

    }

    @EmbeddedId
    private OperacaoOrdemExecucaoDiaId id;
    private String valor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public OperacaoOrdemExecucaoDiaId getId() {
        return id;
    }

    public void setId(OperacaoOrdemExecucaoDiaId id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final OperacaoOrdemExecucaoDia other = (OperacaoOrdemExecucaoDia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
