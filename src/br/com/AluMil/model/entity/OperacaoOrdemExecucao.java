/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author OCTI01
 */
@Entity
public class OperacaoOrdemExecucao implements Serializable {

    @Embeddable
    public static class OperacaoOrdemExecucaoId implements Serializable {

        @ManyToOne
        private Operacao operacao;
        @ManyToOne
        private OrdemExecucao ordemExecucao;
        
        public OperacaoOrdemExecucaoId() {
        }

        public OperacaoOrdemExecucaoId(Operacao operacao, OrdemExecucao ordemExecucao) {
            this.operacao = operacao;
            this.ordemExecucao = ordemExecucao;
        }

        public Operacao getOperacao() {
            return operacao;
        }

        public void setOperacao(Operacao operacao) {
            this.operacao = operacao;
        }

        public OrdemExecucao getOrdemExecucao() {
            return ordemExecucao;
        }

        public void setOrdemExecucao(OrdemExecucao ordemExecucao) {
            this.ordemExecucao = ordemExecucao;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + Objects.hashCode(this.operacao);
            hash = 59 * hash + Objects.hashCode(this.ordemExecucao);
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
            final OperacaoOrdemExecucaoId other = (OperacaoOrdemExecucaoId) obj;
            if (!Objects.equals(this.operacao, other.operacao)) {
                return false;
            }
            if (!Objects.equals(this.ordemExecucao, other.ordemExecucao)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return getOperacao().toString();
        }
    }

    @EmbeddedId
    private OperacaoOrdemExecucaoId id;
    @OneToMany(mappedBy = "id.operacaoOrdemExecucao",cascade = CascadeType.REMOVE)
    private List<OperacaoOrdemExecucaoDia> operacaoOrdemExecucaoDias;
    
    public OperacaoOrdemExecucaoId getId() {
        return id;
    }

    public void setId(OperacaoOrdemExecucaoId id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final OperacaoOrdemExecucao other = (OperacaoOrdemExecucao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getId().toString();
    }

}
