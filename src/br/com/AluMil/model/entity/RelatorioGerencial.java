/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author OCTI01
 */
@Entity
@Table(name = "relatorio_gerencial")
@NamedQueries({
    @NamedQuery(name = "RelatorioGerencial.findAll", query = "SELECT r FROM RelatorioGerencial r")})
public class RelatorioGerencial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "INFO_01")
    private Integer info01;
    @Column(name = "INFO_02")
    private Integer info02;
    @Column(name = "INFO_03")
    private Integer info03;
    @Column(name = "INFO_04")
    private Integer info04;
    @Column(name = "INFO_05")
    private Integer info05;
    @Column(name = "INFO_06")
    private Integer info06;
    @Column(name = "INFO_07")
    private Integer info07;
    @Column(name = "INFO_08")
    private Integer info08;
    @Column(name = "INFO_09")
    private Integer info09;
    @Column(name = "INFO_10")
    private Integer info10;
    @Column(name = "INFO_11")
    private Integer info11;
    @Column(name = "INFO_12")
    private Integer info12;
    @Column(name = "INFO_13")
    private Integer info13;
    @Column(name = "INFO_14")
    private Integer info14;
    @Column(name = "INFO_15")
    private Integer info15;
    @Column(name = "INFO_16")
    private Integer info16;
    @Column(name = "INFO_17")
    private Integer info17;
    @Column(name = "INFO_18")
    private Integer info18;
    @Column(name = "INFO_19")
    private Integer info19;
    @Column(name = "INFO_20")
    private Integer info20;
    @Column(name = "INFO_21")
    private Integer info21;
    @Column(name = "INFO_22")
    private Integer info22;
    @Column(name = "INFO_23")
    private Integer info23;
    @Column(name = "INFO_24")
    private Integer info24;

    public RelatorioGerencial() {
    }

    public RelatorioGerencial(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getInfo01() {
        return info01;
    }

    public void setInfo01(Integer info01) {
        this.info01 = info01;
    }

    public Integer getInfo02() {
        return info02;
    }

    public void setInfo02(Integer info02) {
        this.info02 = info02;
    }

    public Integer getInfo03() {
        return info03;
    }

    public void setInfo03(Integer info03) {
        this.info03 = info03;
    }

    public Integer getInfo04() {
        return info04;
    }

    public void setInfo04(Integer info04) {
        this.info04 = info04;
    }

    public Integer getInfo05() {
        return info05;
    }

    public void setInfo05(Integer info05) {
        this.info05 = info05;
    }

    public Integer getInfo06() {
        return info06;
    }

    public void setInfo06(Integer info06) {
        this.info06 = info06;
    }

    public Integer getInfo07() {
        return info07;
    }

    public void setInfo07(Integer info07) {
        this.info07 = info07;
    }

    public Integer getInfo08() {
        return info08;
    }

    public void setInfo08(Integer info08) {
        this.info08 = info08;
    }

    public Integer getInfo09() {
        return info09;
    }

    public void setInfo09(Integer info09) {
        this.info09 = info09;
    }

    public Integer getInfo10() {
        return info10;
    }

    public void setInfo10(Integer info10) {
        this.info10 = info10;
    }

    public Integer getInfo11() {
        return info11;
    }

    public void setInfo11(Integer info11) {
        this.info11 = info11;
    }

    public Integer getInfo12() {
        return info12;
    }

    public void setInfo12(Integer info12) {
        this.info12 = info12;
    }

    public Integer getInfo13() {
        return info13;
    }

    public void setInfo13(Integer info13) {
        this.info13 = info13;
    }

    public Integer getInfo14() {
        return info14;
    }

    public void setInfo14(Integer info14) {
        this.info14 = info14;
    }

    public Integer getInfo15() {
        return info15;
    }

    public void setInfo15(Integer info15) {
        this.info15 = info15;
    }

    public Integer getInfo16() {
        return info16;
    }

    public void setInfo16(Integer info16) {
        this.info16 = info16;
    }

    public Integer getInfo17() {
        return info17;
    }

    public void setInfo17(Integer info17) {
        this.info17 = info17;
    }

    public Integer getInfo18() {
        return info18;
    }

    public void setInfo18(Integer info18) {
        this.info18 = info18;
    }

    public Integer getInfo19() {
        return info19;
    }

    public void setInfo19(Integer info19) {
        this.info19 = info19;
    }

    public Integer getInfo20() {
        return info20;
    }

    public void setInfo20(Integer info20) {
        this.info20 = info20;
    }

    public Integer getInfo21() {
        return info21;
    }

    public void setInfo21(Integer info21) {
        this.info21 = info21;
    }

    public Integer getInfo22() {
        return info22;
    }

    public void setInfo22(Integer info22) {
        this.info22 = info22;
    }

    public Integer getInfo23() {
        return info23;
    }

    public void setInfo23(Integer info23) {
        this.info23 = info23;
    }

    public Integer getInfo24() {
        return info24;
    }

    public void setInfo24(Integer info24) {
        this.info24 = info24;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelatorioGerencial)) {
            return false;
        }
        RelatorioGerencial other = (RelatorioGerencial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.AluMil.model.entity.RelatorioGerencial[ id=" + id + " ]";
    }
    
}
