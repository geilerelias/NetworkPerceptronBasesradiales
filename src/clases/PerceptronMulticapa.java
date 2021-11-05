/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author eldoc
 */
public class PerceptronMulticapa {

    private int m, n, patrones;
    private double[][] x;
    private double[][] yd;
    private int epoch;
    private double lr, goal;
    private String nombre;
    private int[] numeroCapasOcultas;
    private String[] funcionesActivacion;
    private String algoritmo;
    private double lrD = 1;

    private ArrayList<double[][]> Pesos;
    private ArrayList<double[]> Umbrales;
    private ArrayList<double[][]> PesosAnteriores;
    private ArrayList<double[]> UmbralesAnteriores;

    public PerceptronMulticapa() {
    }

    private PerceptronMulticapa(int m, int n, int patrones, double[][] x, double[][] yd, int epoch, double lr, double goal, String nombre, int[] numeroCapasOcultas, String[] funcionesActivacion, String algoritmo) {
        this.m = m;
        this.n = n;
        this.patrones = patrones;
        this.x = x;
        this.yd = yd;
        this.epoch = epoch;
        this.lr = lr;
        this.goal = goal;
        this.nombre = nombre;
        this.numeroCapasOcultas = numeroCapasOcultas;
        this.funcionesActivacion = funcionesActivacion;
        this.algoritmo = algoritmo;
    }

    public void inicializacionPesosUmbrales() {
        setPesos(new ArrayList<>());
        setUmbrales(new ArrayList());
        setPesosAnteriores(new ArrayList());
        setUmbralesAnteriores(new ArrayList());

        double w[][] = new double[getM()][getNumeroCapasOcultas()[0]];
        double wa[][] = new double[getM()][getNumeroCapasOcultas()[0]];
        double u[] = new double[getNumeroCapasOcultas()[0]];
        double ua[] = new double[getNumeroCapasOcultas()[0]];

        for (int co = 0; co < getNumeroCapasOcultas().length + 1; co++) {

            if (co == 0) {
                System.out.println("Capa Entradas Vs oculta 1 ");
                for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                    u[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                    u[i] = 0;
                    for (int j = 0; j < getM(); j++) {
                        w[j][i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                        wa[j][i] = 0;
                    }
                }
                getUmbrales().add(u);
                getUmbralesAnteriores().add(ua);

                getPesos().add(w);
                getPesosAnteriores().add(wa);
            } else if (co == getNumeroCapasOcultas().length) {
                System.out.printf("Capa Oculta %d Vs capa de salida \n", (co - 1) + 1);
                //double[] uAux = new double[co];
                //double[][] wAux = new double[co][co];
                w = new double[getNumeroCapasOcultas()[co - 1]][getN()];
                u = new double[getN()];

                wa = new double[getNumeroCapasOcultas()[co - 1]][getN()];
                ua = new double[getN()];

                for (int i = 0; i < getN(); i++) {
                    u[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                    ua[i] = 0;
                    for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                        w[j][i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                        wa[j][i] = 0;
                    }
                }

                getUmbrales().add(u);
                getPesos().add(w);
                getUmbralesAnteriores().add(ua);
                getPesosAnteriores().add(wa);
            } else {
                System.out.printf("Capa Oculta %d Vs oculta %d \n", (co - 1) + 1, co + 1);
                //double[] uAux = new double[co];
                //double[][] wAux = new double[co][co];
                w = new double[getNumeroCapasOcultas()[co - 1]][getNumeroCapasOcultas()[co]];
                u = new double[getNumeroCapasOcultas()[co]];
                wa = new double[getNumeroCapasOcultas()[co - 1]][getNumeroCapasOcultas()[co]];
                ua = new double[getNumeroCapasOcultas()[co]];
                for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                    u[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                    ua[i] = 0;
                    for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                        w[j][i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                        wa[j][i] = 0;
                    }
                }
                getUmbrales().add(u);
                getPesos().add(w);
                getUmbralesAnteriores().add(ua);
                getPesosAnteriores().add(wa);
            }
        }

    }

    public void mostrarPesosUmbrales() {
        for (int gp = 0; gp < getPesos().size(); gp++) {
            System.out.println("Pesos #" + (gp + 1));
            for (int i = 0; i < getPesos().get(gp).length; i++) {
                for (int j = 0; j < getPesos().get(gp)[i].length; j++) {
                    System.out.printf("w[%d][%d]= %f \n", j, i, getPesos().get(gp)[i][j]);
                }
            }
        }

        for (int gu = 0; gu < getUmbrales().size(); gu++) {
            System.out.println("Umbrales #" + (gu + 1));
            for (int i = 0; i < getUmbrales().get(gu).length; i++) {
                System.out.printf("u[%d]= %f \n", i, getUmbrales().get(gu)[i]);
            }
        }
    }

    public void entrenamiento() {
        double erms;
        ArrayList<double[]> yAux = new ArrayList<>();
        double[] El = new double[getN()];
        double[][] yr = new double[getPatrones()][getN()];
        double[] yo = null;
        System.out.println("");
        System.out.println("");
        for (int e = 1; e < getEpoch(); e++) {//ciclo de iteraciones
            System.out.println("INTERACION # " + (e + 1));

            double[] Ep = new double[getPatrones()];
            double sumEp = 0;
            for (int p = 0; p < getPatrones(); p++) {//ciclo de patrones
                System.out.println("Patron #" + (p + 1));

                double sumEl = 0;
                for (int co = 0; co < getNumeroCapasOcultas().length + 1; co++) {
                    if (co == 0) {
                        yo = new double[getNumeroCapasOcultas()[co]];
                        System.out.println("Capa Entradas Vs oculta 1 ");
                        double somao = 0;
                        for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                            for (int j = 0; j < getM(); j++) {
                                somao = somao + getPesos().get(co)[j][i] * getX()[p][j];
                            }
                            somao = somao - getUmbrales().get(co)[i];

                            yo[i] = activacion(getFuncionesActivacion()[co], somao);
                        }

                        yAux.add(yo);
                    } else if (co == getNumeroCapasOcultas().length) {
                        System.out.printf("Capa Oculta %d Vs capa de salida \n", (co - 1) + 1);
                        for (int i = 0; i < getN(); i++) {
                            double soma = 0;
                            for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                                soma = soma + getPesos().get(co)[j][i] * yo[j];
                            }
                            soma = soma - getUmbrales().get(co)[i];

                            yr[p][i] = activacion(getFuncionesActivacion()[co], soma);

                            //yr[i]=gausiana(soma);
                            El[i] = (getYd()[p][i] - yr[p][i]);
                            //System.out.println("ERROR LINEAL "+El[i
                            System.out.println("salida deseada " + getYd()[p][i] + " salida de la red " + yr[p][i]);

                            sumEl = sumEl + abs(El[i]);
                        }
                    } else {
                        System.out.printf("Capa Oculta %d Vs oculta %d \n", (co - 1) + 1, co + 1);

                        double[] yñ = new double[getNumeroCapasOcultas()[co]];

                        for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                            double somañ = 0;
                            for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                                somañ = somañ + getPesos().get(co)[j][i] * yo[j];
                            }
                            somañ = somañ - getUmbrales().get(co)[i];

                            yñ[i] = activacion(getFuncionesActivacion()[co], somañ);
                        }
                        yo = yñ;
                        yAux.add(yo);
                    }
                }

                Ep[p] = sumEl / getN();
                System.out.println("suma de errores lineales" + sumEl);

                sumEp = sumEp + Ep[p];

                //modificamos pesos y umbrales
                if ("reglaDelta".equals(getAlgoritmo())) {
                    reglaDelta(Ep, p, El, yAux);
                } else if ("reglaDeltaModificada".equals(getAlgoritmo())) {
                    reglaDeltaModificada(e, Ep, p, El, yAux);
                }//fin regla reglaDelta modificada
            }

            erms = sumEp / getPatrones();
            System.out.println("suma de errores lineales" + sumEp);

            System.out.println("ERMS :" + erms);
            if (erms <= getGoal()) {
                System.out.println("Entranamiento finalizado con exito");
            }
        }

    }

    public void reglaDeltaModificada(int e, double[] Ep, int p, double[] El, ArrayList<double[]> yAux) {
        //regla reglaDelta modificada

        double Rd = 1 / e;
        double pesoAux;
        double umbAux;

        for (int co = 0; co < getNumeroCapasOcultas().length + 1; co++) {
            if (co == 0) {
                System.out.println("Actualizacion de pesos y umbrales Capa Entradas Vs oculta 1 ");
                for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                    for (int j = 0; j < getM(); j++) {
                        pesoAux = getPesos().get(co)[j][i];
                        getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * Ep[p] * getX()[p][j] + Rd * (getPesos().get(co)[j][i] - getPesosAnteriores().get(co)[j][i]);
                        //System.out.print("w"+j+i+":"+wx[j][i]+" ");
                        getPesosAnteriores().get(co)[j][i] = pesoAux;
                    }
                    umbAux = getUmbrales().get(co)[i];
                    getUmbrales().get(co)[i] = getUmbrales().get(co)[i] + getLr() * Ep[p] + Rd * (getUmbrales().get(co)[i] - getUmbralesAnteriores().get(co)[i]);
                    getUmbralesAnteriores().get(co)[i] = umbAux;
                }

            } else if (co == getNumeroCapasOcultas().length) {
                System.out.printf("modificación pesos y umbrales Capa Oculta %d Vs capa de salida \n", (co - 1) + 1);

                //modificación pesos y umbrasel salida
                for (int i = 0; i < getN(); i++) {
                    for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                        pesoAux = getPesos().get(co)[j][i];
                        //ws[j][i]=ws[j][i]+rata*El[i]*yñ[i]+rd*(wsx[j][i]-ws[j][i]);
                        getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * El[i] * yAux.get(co - 1)[i] + Rd * (getPesos().get(co)[j][i] - getPesosAnteriores().get(co)[j][i]);
                        getPesosAnteriores().get(co)[j][i] = pesoAux;
                    }
                    umbAux = getUmbrales().get(co)[i];
                    getUmbrales().get(co)[i] = getUmbrales().get(co)[i] + getLr() * El[i] + Rd * (getUmbrales().get(co)[i] - getUmbralesAnteriores().get(co)[i]);
                    getUmbralesAnteriores().get(co)[i] = umbAux;
                }

            } else {
                System.out.printf("modificamos pesos entre Capa Oculta %d Vs oculta %d \n", (co - 1) + 1, co + 1);
                //modificamos pesos entre capas
                for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                    for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                        pesoAux = this.getPesos().get(co)[j][i];
                        getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * Ep[p] * yAux.get(co - 1)[j] + Rd * (getPesos().get(co)[j][i] - getPesosAnteriores().get(co)[j][i]);
                        getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * Ep[p] * yAux.get(co - 1)[j];
                        getPesosAnteriores().get(co)[j][i] = pesoAux;
                    }
                    umbAux = getUmbrales().get(co)[i];
                    getUmbrales().get(co)[i] = getUmbrales().get(co)[i] + getLr() * Ep[p] + Rd * (getUmbrales().get(co)[i] - getUmbralesAnteriores().get(co)[i]);
                    getUmbralesAnteriores().get(co)[i] = umbAux;
                }

            }
        }
    }

    public void reglaDelta(double[] Ep, int p, double[] El, ArrayList<double[]> yAux) {
        for (int co = 0; co < getNumeroCapasOcultas().length + 1; co++) {
            if (co == 0) {
                System.out.println("Actualizacion de pesos y umbrales Capa Entradas Vs oculta 1 ");
                for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                    for (int j = 0; j < getM(); j++) {
                        getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * Ep[p] * getX()[p][j];
                    }
                    getUmbrales().get(co)[i] = getUmbrales().get(co)[i] + getLr() * Ep[p];
                }

            } else if (co == getNumeroCapasOcultas().length) {
                System.out.printf("modificación pesos y umbrales Capa Oculta %d Vs capa de salida \n", (co - 1) + 1);

                //modificación pesos y umbrasel salida
                for (int i = 0; i < getN(); i++) {
                    for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                        getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * El[i] * yAux.get(co - 1)[j];
                    }
                    getUmbrales().get(co)[i] = getUmbrales().get(co)[i] + getLr() * El[i];
                }

            } else {
                System.out.printf("modificamos pesos entre Capa Oculta %d Vs oculta %d \n", (co - 1) + 1, co + 1);

                //modificamos pesos entre capas
                for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                    for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                        this.getPesos().get(co)[j][i] = getPesos().get(co)[j][i] + getLr() * Ep[p] * yAux.get(co - 1)[j];
                    }

                    getUmbrales().get(co)[i] = getUmbrales().get(co)[i] + getLr() * Ep[p];
                }

            }
        }
    }

    public double activacion(String funcion, double num) {
        double activacion = 0.0;
        switch (funcion) {
            case "Lineal":
                /**
                 * Funcion de activacion(lineal): Identidad
                 */
                activacion = num;
                break;
            case "Sigmoidal":
                /**
                 * Funcion de activacion(sigmoidea): Logaritmo Sigmoidal
                 */
                activacion = 1.0 / (1.0 + (Math.pow(Math.E, -num / 0.01)));
                break;
            case "TanSigmoidal":
                /**
                 * Funcion de activacion(sigmoidea): Tangente Sigmoidal
                 */
                activacion = (2.0 / (1.0 + (Math.pow(Math.E, -num)))) - 1.0;
                break;
            case "TanHiperbolica":
                /**
                 * Funcion de activacion(sigmoidea): Tangente Hiperbolica
                 */
                activacion = ((Math.pow(Math.E, num)) - ((Math.pow(Math.E, -num)))) / ((Math.pow(Math.E, num)) + ((Math.pow(Math.E, -num))));
                break;
            case "Gaussiana":
                /**
                 * Funcion de activacion (gaussiana): Campana de Gauss
                 */
                activacion = num * Math.pow(Math.E, -num);
                break;
            case "Seno":
                /**
                 * Funcion de activacion (gaussiana): Campana de Gauss
                 */
                activacion = sin(num);
                break;
            case "Coseno":
                /**
                 * Funcion de activacion (gaussiana): Campana de Gauss
                 */
                activacion = cos(num);
                break;
            default:
                break;
        }
        return activacion;
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
     * @return the epoch
     */
    public int getEpoch() {
        return epoch;
    }

    /**
     * @param epoch the epoch to set
     */
    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    /**
     * @return the lr
     */
    public double getLr() {
        return lr;
    }

    /**
     * @param lr the lr to set
     */
    public void setLr(double lr) {
        this.lr = lr;
    }

    /**
     * @return the goal
     */
    public double getGoal() {
        return goal;
    }

    /**
     * @param goal the goal to set
     */
    public void setGoal(double goal) {
        this.goal = goal;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numeroCapasOcultas
     */
    public int[] getNumeroCapasOcultas() {
        return numeroCapasOcultas;
    }

    /**
     * @param numeroCapasOcultas the numeroCapasOcultas to set
     */
    public void setNumeroCapasOcultas(int[] numeroCapasOcultas) {
        this.numeroCapasOcultas = numeroCapasOcultas;
    }

    /**
     * @return the funcionesActivacion
     */
    public String[] getFuncionesActivacion() {
        return funcionesActivacion;
    }

    /**
     * @param funcionesActivacion the funcionesActivacion to set
     */
    public void setFuncionesActivacion(String[] funcionesActivacion) {
        this.funcionesActivacion = funcionesActivacion;
    }

    /**
     * @return the algoritmo
     */
    public String getAlgoritmo() {
        return algoritmo;
    }

    /**
     * @param algoritmo the algoritmo to set
     */
    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    /**
     * @return the lrD
     */
    public double getLrD() {
        return lrD;
    }

    /**
     * @param lrD the lrD to set
     */
    public void setLrD(double lrD) {
        this.lrD = lrD;
    }

    /**
     * @return the Peso
     */
    public ArrayList<double[][]> getPesos() {
        return Pesos;
    }

    /**
     * @param Pesos the Peso to set
     */
    public void setPesos(ArrayList<double[][]> Pesos) {
        this.Pesos = Pesos;
    }

    /**
     * @return the Umbrales
     */
    public ArrayList<double[]> getUmbrales() {
        return Umbrales;
    }

    /**
     * @param Umbrales the Umbrales to set
     */
    public void setUmbrales(ArrayList<double[]> Umbrales) {
        this.Umbrales = Umbrales;
    }

    /**
     * @return the PesosAnteriores
     */
    public ArrayList<double[][]> getPesosAnteriores() {
        return PesosAnteriores;
    }

    /**
     * @param PesosAnteriores the PesosAnteriores to set
     */
    public void setPesosAnteriores(ArrayList<double[][]> PesosAnteriores) {
        this.PesosAnteriores = PesosAnteriores;
    }

    /**
     * @return the UmbralesAnteriores
     */
    public ArrayList<double[]> getUmbralesAnteriores() {
        return UmbralesAnteriores;
    }

    /**
     * @param UmbralesAnteriores the UmbralesAnteriores to set
     */
    public void setUmbralesAnteriores(ArrayList<double[]> UmbralesAnteriores) {
        this.UmbralesAnteriores = UmbralesAnteriores;
    }

    public void ObtenerEntradasSalidasPatrones() {

        this.setM(this.getX()[0].length);
        this.setN(this.getYd()[0].length);
        this.setPatrones(this.getX().length);
    }

    public double[][] simular(double[][] patrones) {

        double[][] yr = new double[getPatrones()][getN()];
        double[] yo = null;

        for (int p = 0; p < getPatrones(); p++) {//ciclo de patrones
            System.out.println("Patron #" + (p + 1));
            for (int co = 0; co < getNumeroCapasOcultas().length + 1; co++) {
                if (co == 0) {
                    yo = new double[getNumeroCapasOcultas()[co]];
                    System.out.println("Capa Entradas Vs oculta 1 ");
                    double somao = 0;
                    for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                        for (int j = 0; j < getM(); j++) {
                            somao = somao + getPesos().get(co)[j][i] * getX()[p][j];
                        }
                        somao = somao - getUmbrales().get(co)[i];

                        yo[i] = activacion(getFuncionesActivacion()[co], somao);
                    }
                } else if (co == getNumeroCapasOcultas().length) {
                    System.out.printf("Capa Oculta %d Vs capa de salida \n", (co - 1) + 1);
                    for (int i = 0; i < getN(); i++) {
                        double soma = 0;
                        for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                            soma = soma + getPesos().get(co)[j][i] * yo[j];
                        }
                        soma = soma - getUmbrales().get(co)[i];

                        yr[p][i] = activacion(getFuncionesActivacion()[co], soma);

                    }
                } else {
                    System.out.printf("Capa Oculta %d Vs oculta %d \n", (co - 1) + 1, co + 1);

                    double[] yñ = new double[getNumeroCapasOcultas()[co]];

                    for (int i = 0; i < getNumeroCapasOcultas()[co]; i++) {
                        double somañ = 0;
                        for (int j = 0; j < getNumeroCapasOcultas()[co - 1]; j++) {
                            somañ = somañ + getPesos().get(co)[j][i] * yo[j];
                        }
                        somañ = somañ - getUmbrales().get(co)[i];

                        yñ[i] = activacion(getFuncionesActivacion()[co], somañ);
                    }
                    yo = yñ;
                }
            }
        }
        
        return yr;
    }
}
