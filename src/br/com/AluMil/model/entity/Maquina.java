/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author OCTI01
 */
@Entity
public class Maquina implements Serializable {

    @OneToMany(mappedBy = "maquina")
    private List<Operacao> operacaos;

    @OneToMany(mappedBy = "maquina")
    private List<CheckList> checkLists;

    @OneToMany(mappedBy = "maquina")
    private List<Dispositivo> dispositivos;

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    @Lob
    private byte[] foto;
    private boolean ativo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id;
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
        final Maquina other = (Maquina) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    

}