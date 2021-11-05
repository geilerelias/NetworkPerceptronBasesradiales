/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author gers
 */
public class BaseRadial {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */

    private int m;
    private int n;
    private int patrones;
    private double d[][];
    private double x[][];
    private double yd[][];
    private double yr[][];
    private double r[][];
    private int numeroCentrosRadiales;
    private double errorOptimo;
    private String funcionActivacion;
    private double w[][];

    public BaseRadial() {
    }

    public BaseRadial(int m, int n, int patrones, double[][] d, double[][] x, double[][] yd, double[][] yr, double[][] r, int numeroCentrosRadiales, int errorOptimo, String funcionActivacion, double[][] w) {
        this.m = m;
        this.n = n;
        this.patrones = patrones;
        this.d = d;
        this.x = x;
        this.yd = yd;
        this.yr = yr;
        this.r = r;
        this.numeroCentrosRadiales = numeroCentrosRadiales;
        this.errorOptimo = errorOptimo;
        this.funcionActivacion = funcionActivacion;
        this.w = w;
    }
    
    
     public double activacion(String funcionActivacion, double sqrt) {
        double resultado = 0;
        switch (funcionActivacion) {
            case "Funcion de base radial":
                resultado = Math.pow(sqrt, 2) * Math.log(sqrt);
                break;
            default:
                throw new AssertionError();
        }
        return resultado;
    }
    
    public void ObtenerEntradasSalidasPatrones() {
        this.setM(this.getX()[0].length);
        this.setN(this.getYd()[0].length);
        this.setPatrones(this.getX().length);
    }

    /**
     * @return the d
     */
    public double[][] getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(double[][] d) {
        this.d = d;
    }

    /**
     * @return the x
     */
    public double[][] getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double[][] x) {
        this.x = x;
    }

    /**
     * @return the yd
     */
    public double[][] getYd() {
        return yd;
    }

    /**
     * @param yd the yd to set
     */
    public void setYd(double[][] yd) {
        this.yd = yd;
    }

    /**
     * @return the yr
     */
    public double[][] getYr() {
        return yr;
    }

    /**
     * @param yd the yr to set
     */
    public void setYr(double[][] yr) {
        this.yr = yr;
    }

    /**
     * @return the r
     */
    public double[][] getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(double[][] r) {
        this.r = r;
    }

    /**
     * @return the numeroCentrosRadiales
     */
    public int getNumeroCentrosRadiales() {
        return numeroCentrosRadiales;
    }

    /**
     * @param numeroCentrosRadiales the numeroCentrosRadiales to set
     */
    public void setNumeroCentrosRadiales(int numeroCentrosRadiales) {
        this.numeroCentrosRadiales = numeroCentrosRadiales;
    }

    /**
     * @return the errorOptimo
     */
    public double getErrorOptimo() {
        return errorOptimo;
    }

    /**
     * @param errorOptimo the errorOptimo to set
     */
    public void setErrorOptimo(double errorOptimo) {
        this.errorOptimo = errorOptimo;
    }

    /**
     * @return the m
     */
    public int getM() {
        return m;
    }

    /**
     * @param m the m to set
     */
    public void setM(int m) {
        this.m = m;
    }

    /**
     * @return the n
     */
    public int getN() {
        return n;
    }

    /**
     * @param n the n to set
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * @return the n
     */
    public int getPatrones() {
        return patrones;
    }

    /**
     * @param n the n to set
     */
    public void setPatrones(int p) {
        this.patrones = p;
    }


    /**
     * @return the funcionActivacion
     */
    public String getFuncionActivacion() {
        return funcionActivacion;
    }

    /**
     * @param funcionActivacion the funcionActivacion to set
     */
    public void setFuncionActivacion(String funcionActivacion) {
        this.funcionActivacion = funcionActivacion;
    }

    /**
     * @return the w
     */
    public double[][] getW() {
        return w;
    }

    /**
     * @param w the w to set
     */
    public void setW(double[][] w) {
        this.w = w;
    }

    

}
