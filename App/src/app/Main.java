package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        int N_EMPRESAS_A_PROBAR = 10;
        int N_PRUEBAS = 5;
        boolean DEBUG = true;
        Map<Integer, Float> mejores = new HashMap<Integer, Float>();
        for (int j = 0; j < 5; j++) {
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
            if (!mejores.containsKey(n_carros)) {
                mejores.put(n_carros, Float.parseFloat("1"));
            } else {
                mejores.put(n_carros, mejores.get(n_carros) + 1);
            }
        }
        for (Integer mejor : mejores.keySet()) {
            mejores.put(mejor, mejores.get(mejor) / N_PRUEBAS);
        }
        System.out.println("Probabilidad despues de correr todo " + mejores);
    }
}
