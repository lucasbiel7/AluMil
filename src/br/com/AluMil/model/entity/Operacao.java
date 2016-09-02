/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author OCTI01
 */
@Entity
public class Operacao implements Serializable {

    public enum Tipo {
        SIMNAO("Sim/Não", "Sim", "Não"), OKNAOOK("Ok/Não Ok", "Ok", "Não Ok"), MEDICAOVALORES("Medições de valores");

        private String nome;
        private String[] values;

        public String[] getValues() {
            return values;
        }

        private Tipo(String nome, String... values) {
            this.nome = nome;
            this.values = values;
        }

        @Override
        public String toString() {
            return nome;
        }

    }
    @Id
    @GeneratedValue
    private int id;
    private String descricao;
    private Tipo tipo;
    @ManyToOne
    private Maquina maquina;
    
    @OneToMany(mappedBy = "id.operacao", cascade = CascadeType.REMOVE)
    private List<OperacaoOrdemExecucao> opercaoOrdemExecucaos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
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
        final Operacao other = (Operacao) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}
