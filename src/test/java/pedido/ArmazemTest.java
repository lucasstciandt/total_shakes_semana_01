package pedido;

import armazem.Armazem;
import ingredientes.Ingrediente;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ArmazemTest {

    @Test
    public void deve_cadastrar_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);

        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);

        assertTrue(armazem.getEstoque().containsKey(morango));
        assertEquals(0, armazem.getEstoque().get(morango));
    }

    @Test
    public void deve_lancarExcecaoAoAdicionar_ingrediente_noEstoque_quandoJaCadastrado(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);

        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);

        assertThrows(IllegalArgumentException.class, () -> armazem.cadastrarIngrediente(morango),
                "Ingrediente já cadastrado");
    }
}
