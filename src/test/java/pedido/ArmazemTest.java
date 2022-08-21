package pedido;

import armazem.Armazem;
import ingredientes.Ingrediente;
import ingredientes.base.Base;
import ingredientes.base.TipoBase;
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

        var exception = assertThrows(IllegalArgumentException.class, () -> armazem.cadastrarIngrediente(morango));
        assertEquals("Ingrediente já cadastrado", exception.getMessage());
    }


    @Test
    public void deve_descadastrarUm_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);

        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);
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

        var exception =assertThrows(IllegalArgumentException.class,
                () -> armazem.descadastrarIngrediente(morango));
        assertEquals("Ingrediente não encontrado", exception.getMessage());
    }

    @Test
    public void deve_aumentarAQuantidadeDo_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 2);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 1);

       assertAll(
               () -> assertEquals(3, armazem.getEstoque().get(morango))
       );

    }

    @Test
    public void deve_lancarExcecaoAoAumentarQuantidadeDo_ingrediente_noEstoque_quandoValorIgualOuAbaixoDe0(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);
        armazem.cadastrarIngrediente(leite);

        var exception = assertThrows( IllegalArgumentException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 0));
        assertEquals("Quantidade invalida", exception.getMessage());
    }

    @Test
    public void deve_lancarExcecaoAoAumentarQuantidadeDo_ingrediente_noEstoque_quandoNaoExistente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        Armazem armazem = new Armazem();

        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 2)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(leite, 3))
        );
    }

    @Test
    public void deve_reduzirAQuantidadeDo_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);
        armazem.cadastrarIngrediente(leite);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 3);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(leite, 1);


        armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 2);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(leite, 8);

        assertAll(
                () -> assertTrue(armazem.getEstoque().containsKey(morango)),
                () -> assertEquals(1, armazem.getEstoque().get(morango)),
                () -> assertFalse(armazem.getEstoque().containsKey(leite)),
                () -> assertNull(armazem.getEstoque().get(leite))
        );
    }

    @Test
    public void deve_lancarExcecaoAoReduzirQuantidadeDo_ingrediente_noEstoque_quandoValorIgualOuAbaixoDe0(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);
        armazem.cadastrarIngrediente(leite);

        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 0)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(leite, -4))
        );
    }

    @Test
    public void deve_lancarExcecaoAoReduzirQuantidadeDo_ingrediente_noEstoque_quandoNaoExistente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        Armazem armazem = new Armazem();

        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 2)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(leite, 3))
        );
    }

    @Test
    public void deve_retornarAQuantidadeDeUm_ingrediente_doEstoque_corretamente(){
        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        Armazem armazem = new Armazem();
        armazem.cadastrarIngrediente(morango);
        armazem.cadastrarIngrediente(leite);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 3);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(leite, 1);

        int quantidadeMorango = armazem.consultarQuantidadeDoIngredienteEmEstoque(morango);
        int quantidadeLeite = armazem.consultarQuantidadeDoIngredienteEmEstoque(leite);

        assertAll(
                () -> assertEquals(3, quantidadeMorango),
                () -> assertEquals(1, quantidadeLeite)
        );

    }

    @Test
    public void deve_lancarExcecaoAoBuscarPrecoDeUm_ingrediente_doEstoque_quandoInexistente(){
        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Armazem armazem = new Armazem();

        assertAll(
                () -> assertFalse(armazem.getEstoque().containsKey(morango)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () ->armazem.consultarQuantidadeDoIngredienteEmEstoque(morango))
        );
    }
}
