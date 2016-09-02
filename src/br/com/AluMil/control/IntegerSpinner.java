/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control;

import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author OCTI01
 */
public class IntegerSpinner extends SpinnerValueFactory<Integer> {

    private int menor;
    private int maior;
    private int step;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public IntegerSpinner(int menor, int maior) {
        this.menor = menor;
        this.maior = maior;
        setValue(menor);
        setStep(1);
    }

    public IntegerSpinner() {
    }

    @Override
    public void decrement(int steps) {
        steps = getStep();
        if (getValue() - steps >= menor) {
            setValue(getValue() - steps);
        }else{
            setValue(menor);
        }
    }

    @Override
    public void increment(int steps) {
        steps = getStep();
        if (getValue() + steps <= maior) {
            setValue(getValue() + steps);
        }else{
            setValue(maior);
        }
    }

}
