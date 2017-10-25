package app;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int N_EMPRESAS_A_PROBAR = 10;
        boolean DEBUG = true;

        // Probando para muchas empresas
        ArrayList<Empresa> empresas = new ArrayList<>();
        for (int n = 1; n <= N_EMPRESAS_A_PROBAR; n++) {
            Empresa empresa = new Empresa(n);
            System.out.println("EMPRESA CON [" + n + "] CARROS");
            // Simulando 1 año
            for (int i = 1; i <= 365; i++) {
                System.out.println("----- [Dia " + i + "] -----");
                empresa.sigDia(DEBUG);
                System.out.println();
            }
            empresa.getResumen();
            empresas.add(empresa);
        }

        // Decidiendo lo mejor
        float mejor = 0;
        int n_carros = 0;
        for (Empresa empresa : empresas) {
            if (empresa.utilidad_real >= mejor) {
                mejor = empresa.utilidad_real;
                n_carros = empresa.n_carros_inicial;
            }
        }
        System.out.println("\nMEJOR CON " + n_carros + " que dio " + mejor + " en utilidad bruta");
    }
}
