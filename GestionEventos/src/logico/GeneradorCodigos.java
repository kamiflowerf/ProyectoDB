package logico;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorCodigos {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Set<String> codigosGenerados = new HashSet<>();
    private static final Random random = new Random();

    public static String generarCodigoUnico(int longitud) {
        String codigo;

        do {
            codigo = generarCodigoAleatorio(longitud);
        } while (codigosGenerados.contains(codigo));

        codigosGenerados.add(codigo);
        return codigo;
    }

    private static String generarCodigoAleatorio(int longitud) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(index));
        }

        return sb.toString();
    }
}
