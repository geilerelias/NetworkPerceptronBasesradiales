/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author geile
 */
public class PerceptronSimple {

    private int m;
    private int n;
    private int patrones;
    private double[][] x;
    private double[][] yd;
    private double[] u;
    private double[][] w;
    private int numeroIteraciones;
    private double errorMaximoPermitido;
    private double tasaAprendizaje;
    private boolean estado;
    private String funcionActivacion;

    public PerceptronSimple() {
    }

    public PerceptronSimple(int m, int n, int patrones, double[][] x, double[][] yd, double[] u, double[][] w, int numeroIteraciones, double errorMaximoPermitido, double tasaAprendizaje, boolean estado, String funcionActivacion) {
        this.m = m;
        this.n = n;
        this.patrones = patrones;
        this.x = x;
        this.yd = yd;
        this.u = u;
        this.w = w;
        this.numeroIteraciones = numeroIteraciones;
        this.errorMaximoPermitido = errorMaximoPermitido;
        this.tasaAprendizaje = tasaAprendizaje;
        this.estado = estado;
        this.funcionActivacion = funcionActivacion;
    }

    public void ObtenerEntradasSalidasPatrones() {
        this.setM(this.getX()[0].length);
        this.setN(this.getYd()[0].length);
        this.setPatrones(this.getX().length);
    }

    public double calcularFuncionActivacion(double soma) {
        double result = 0;
        switch (this.getFuncionActivacion()) {
            case "rampa":
                if (soma < 0) {
                    result = 0;
                } else if (soma >= 0 && soma <= 1) {
                    result = soma;
                } else if (soma > 1) {
                    result = 1;
                }
                break;
            case "escalon":
                if (soma >= 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                break;
            default:
                 if (soma >= 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                 break;
        }
        return result;
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
     * @return the patrones
     */
    public int getPatrones() {
        return patrones;
    }

    /**
     * @param patrones the patrones to set
     */
    public void setPatrones(int patrones) {
        this.patrones = patrones;
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
     * @return the u
     */
    public double[] getU() {
        return u;
    }

    /**
     * @param u the u to set
     */
    public void setU(double[] u) {
        this.u = u;
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

    /**
     * @return the numeroIteraciones
     */
    public int getNumeroIteraciones() {
        return numeroIteraciones;
    }

    /**
     * @param numeroIteraciones the numeroIteraciones to set
     */
    public void setNumeroIteraciones(int numeroIteraciones) {
        this.numeroIteraciones = numeroIteraciones;
    }

    /**
     * @return the errorMaximoPermitido
     */
    public double getErrorMaximoPermitido() {
        return errorMaximoPermitido;
    }

    /**
     * @param errorMaximoPermitido the errorMaximoPermitido to set
     */
    public void setErrorMaximoPermitido(double errorMaximoPermitido) {
        this.errorMaximoPermitido = errorMaximoPermitido;
    }

    /**
     * @return the tasaAprendizaje
     */
    public double getTasaAprendizaje() {
        return tasaAprendizaje;
    }

    /**
     * @param tasaAprendizaje the tasaAprendisaje to set
     */
    public void setTasaAprendizaje(double tasaAprendizaje) {
        this.tasaAprendizaje = tasaAprendizaje;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
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

}
