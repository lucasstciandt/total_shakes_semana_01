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

        assertAll(
                () -> assertTrue(armazem.getEstoque().containsKey(morango)),
                () -> assertEquals(0, armazem.getEstoque().get(morango))
        );
    }

    @Test
    public void deve_lancarExcecaoAoAdicionar_ingrediente_noEstoque_quandoJaCadastrado(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);

        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);

        assertThrows(IllegalArgumentException.class, () -> armazem.cadastrarIngrediente(morango),
                "Ingrediente já cadastrado");
    }


    @Test
    public void deve_descadastrarUm_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);

        Armazem armazem = new Armazem();
        armazem.descadastrarIngrediente(morango);

        assertAll(
                () -> assertFalse(armazem.getEstoque().containsKey(morango)),
                () -> assertNull(armazem.getEstoque().get(morango))
        );

    }

    @Test
    public void deve_lancarExcecaoAoDescadastrarUm_ingrediente_doEstoque_quandoNaoExistente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Armazem armazem = new Armazem();

        assertThrows(IllegalArgumentException.class, () -> armazem.descadastrarIngrediente(morango),
                "Ingrediente não encontrado");

    }
}
