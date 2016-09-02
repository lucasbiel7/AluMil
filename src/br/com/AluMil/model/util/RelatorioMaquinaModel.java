/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.util;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author OCTI01
 */
public class RelatorioMaquinaModel {

    private Date data;
    private List<Integer> soma;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Integer> getSoma() {
        return soma;
    }

    public void setSoma(List<Integer> soma) {
        this.soma = soma;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.data);
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
        final RelatorioMaquinaModel other = (RelatorioMaquinaModel) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Formatter.toDate(data);
    }

}
