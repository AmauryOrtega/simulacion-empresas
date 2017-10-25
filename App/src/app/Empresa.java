package app;

import java.util.ArrayList;
import java.util.Random;

public class Empresa {

    public int n_carros;
    public int n_carros_inicial;
    public ArrayList<Integer> ventas;
    public float costo_total;
    public float ingreso_total;
    public float utilidad_real;
    private final float INGRESO_DIARIO_POR_CARRO = 350;
    private final float COSTO_DIARIO_POR_CARRO_OCIOSO = 50;
    private final float COSTO_POR_NO_TENER_UN_CARRO = 200;

    public Empresa(int n_carros_inicial) {
        this.n_carros_inicial = n_carros_inicial;
        this.n_carros = n_carros_inicial;
        ventas = new ArrayList<>();
        this.costo_total = 0;
        this.ingreso_total = 0;
        this.utilidad_real = 0;
    }

    public float getUtilidad() {
        return this.ingreso_total - this.costo_total;
    }

    public void sigDia(boolean DEBUG) {
        float ingresos_dia = 0;
        float costos_dia = 0;
        /* Reducir en 1 los dias de las ventas y añadir lo que ganaron ese dia.
         * Los que llegan a 0 se suman al this.n_carros y se remueven de this.ventas
         */
        if (DEBUG) {
            System.out.println("# Carros disponibles ANTES: " + this.n_carros);
        }
        if (!this.ventas.isEmpty()) {
            if (DEBUG) {
                System.out.println("Ventas antes de cobrar el dia");
                System.out.println("[" + this.ventas.size() + "] => " + this.ventas);
            }
            // Reduce en 1 los dias
            this.ventas.replaceAll((Integer num) -> {
                return (num - 1);
            });
            // Cobra ese dia
            ingresos_dia += this.INGRESO_DIARIO_POR_CARRO * this.ventas.size();
            // Borra de ventas los retornados
            int antes_de_borrar = this.ventas.size();
            this.ventas.removeIf(x -> {
                return x.equals(0);
            });
            this.n_carros += antes_de_borrar - this.ventas.size();
            if (DEBUG) {
                System.out.println("Retornaron " + (antes_de_borrar - this.ventas.size()));
            }
        }
        if (DEBUG) {
            System.out.println("Ventas despues de cobrar el dia");
            System.out.println("[" + this.ventas.size() + "] => " + this.ventas);
        }
        /* Simular las ventas de dicho dia y solo agregar las que se puedan. 
        * Las que se puedan deben disminuir this.n_carros. Y las que no, deben
        * ser contadas para incrementar costos por no tener carros disponibles.
         */
        Random r = new Random();
        int n_ventas_nuevas = n_carros_por_dia(r.nextFloat());
        int n_penalizaciones = 0;
        for (int i = 1; i <= n_ventas_nuevas; i++) {
            if (this.n_carros > 0) {
                this.ventas.add(n_dias_por_carro(r.nextFloat()));
                this.n_carros--;
            } else {
                n_penalizaciones++;
            }
        }
        if (n_penalizaciones > 0) {
            costos_dia += this.COSTO_POR_NO_TENER_UN_CARRO * n_penalizaciones;
        }
        // Cobrar los carros ociosos
        costos_dia += this.COSTO_DIARIO_POR_CARRO_OCIOSO * this.n_carros;
        // Mini resumen
        if (DEBUG) {
            System.out.println("# de ventas simuladas: " + n_ventas_nuevas);
            System.out.println("Penalizaciones: " + n_penalizaciones);
            System.out.println("Ingresos diarios: " + ingresos_dia);
            System.out.println("Costos diarios: " + costos_dia);
            System.out.println("# Carros disponibles DESPUES: " + this.n_carros);
        }
        this.ingreso_total += ingresos_dia;
        this.costo_total += costos_dia;
    }

    public int n_carros_por_dia(float x) {
        if (x < 0.1) {
            return 0;
        } else if (x < 0.2) {
            return 1;
        } else if (x < 0.45) {
            return 2;
        } else if (x < 0.75) {
            return 3;
        } else {
            return 4;
        }
    }

    public int n_dias_por_carro(float x) {
        if (x < 0.4) {
            return 1;
        } else if (x < 0.75) {
            return 2;
        } else if (x < 0.9) {
            return 3;
        } else {
            return 4;
        }
    }

    public void getResumen() {
        System.out.println("Empresa con " + this.n_carros_inicial + " carros");
        System.out.println("Ingresos: " + this.ingreso_total);
        System.out.println("Costos: " + this.costo_total);
        System.out.println("Utilidad: " + this.getUtilidad());
        System.out.println("Costo de autos: " + this.n_carros_inicial * 75000);
        this.utilidad_real = (this.getUtilidad() - this.n_carros_inicial * 75000);
        System.out.println("Utilidad real: " + this.utilidad_real);
    }

}
