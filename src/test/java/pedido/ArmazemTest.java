package pedido;

import ingredientes.Ingrediente;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import org.junit.jupiter.api.Test;

public class ArmazemTest {

    @Test
    public void deve_cadastrar_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);

        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);

        assertTrue(armazem.getEstoque().contains(morango));
    }
}
