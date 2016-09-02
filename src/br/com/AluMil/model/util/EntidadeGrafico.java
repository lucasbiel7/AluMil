/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.util;

import java.util.Objects;

/**
 *
 * @author OCTI01
 * @param <Entity>
 */
public class EntidadeGrafico<Entity> {

    private Entity entidade;
    private double value;

    public EntidadeGrafico(Entity entidade, double value) {
        this.entidade = entidade;
        this.value = value;
    }

    public EntidadeGrafico() {
    }

    public Entity getEntidade() {
        return entidade;
    }

    public void setEntidade(Entity entidade) {
        this.entidade = entidade;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.entidade);
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
        final EntidadeGrafico<?> other = (EntidadeGrafico<?>) obj;
        if (!Objects.equals(this.entidade, other.entidade)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getEntidade().toString();
    }

}
