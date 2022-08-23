package armazem;

import builders.ArmazemBuilder;
import ingredientes.Ingrediente;
import ingredientes.base.Base;
import ingredientes.base.TipoBase;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import ingredientes.topping.TipoTopping;
import ingredientes.topping.Topping;
import org.junit.jupiter.api.Test;

import static builders.ArmazemBuilder.*;
import static org.junit.jupiter.api.Assertions.*;


public class ArmazemTest {

    @Test
    public void deve_cadastrar_ingrediente_noEstoque_corretamente(){
        Ingrediente ingrediente = new Fruta(TipoFruta.MORANGO);
        var armazemEstoqueVazio = umArmazem().build();

        armazemEstoqueVazio.cadastrarIngredienteNoEstoque(ingrediente);

        assertAll(
                () -> assertTrue(armazemEstoqueVazio.getEstoque().containsKey(ingrediente)),
                () -> assertEquals(0, armazemEstoqueVazio.getEstoque().get(ingrediente))
        );
    }

    @Test
    public void deve_lancarExcecaoAoAdicionar_ingrediente_noEstoque_quandoJaCadastrado(){
        var armazemComEstoque = umArmazem().comEstoqueCompleto().build();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> armazemComEstoque.cadastrarIngredienteNoEstoque(new Base(TipoBase.LEITE))
        );
        assertEquals("Ingrediente já cadastrado", exception.getMessage());
    }


    @Test
    public void deve_descadastrarUm_ingrediente_noEstoque_corretamente(){
        Ingrediente ingrediente = new Topping(TipoTopping.CHOCOLATE);
        var armazem = umArmazem().comEstoqueCompleto().build();

        armazem.descadastrarIngredienteNoEstoque(ingrediente);

        assertAll(
                () -> assertFalse(armazem.getEstoque().containsKey(ingrediente)),
                () -> assertNull(armazem.getEstoque().get(ingrediente))
        );
    }

    @Test
    public void deve_lancarExcecaoAoDescadastrarUm_ingrediente_doEstoque_quandoNaoExistente(){

        var armazemSemEstoque = umArmazem().build();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> armazemSemEstoque.descadastrarIngredienteNoEstoque(new Fruta(TipoFruta.BANANA))
        );
        assertEquals("Ingrediente não encontrado", exception.getMessage());
    }

    @Test
    public void deve_aumentarAQuantidadeDo_ingrediente_noEstoque_corretamente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente iogurte = new Base(TipoBase.IOGURTE);
        var armazem = umArmazem().comEstoqueCompleto().build();

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 2);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(iogurte, 1);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(iogurte, 3);

       assertAll(
               () -> assertEquals(3, armazem.getEstoque().get(morango)),
               () -> assertEquals(5, armazem.getEstoque().get(iogurte))
       );
    }

    @Test
    public void deve_lancarExcecaoAoAumentarQuantidadeDo_ingrediente_noEstoque_quandoValorIgualOuAbaixoDe0(){

        var armazemComEstoque = umArmazem().comEstoqueCompleto().build();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> armazemComEstoque.adicionarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.IOGURTE), 0)
        );
        assertEquals("Quantidade invalida", exception.getMessage());
    }

    @Test
    public void deve_lancarExcecaoAoAumentarQuantidadeDo_ingrediente_noEstoque_quandoNaoExistente(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        var armazem = umArmazem().build();

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
        var armazemComEstoque = umArmazem().comEstoqueCompleto().build();

        armazemComEstoque.adicionarQuantidadeDoIngredienteEmEstoque(morango, 2);
        armazemComEstoque.reduzirQuantidadeDoIngredienteEmEstoque(morango, 2);
        armazemComEstoque.reduzirQuantidadeDoIngredienteEmEstoque(leite, 8);

        assertAll(
                () -> assertTrue(armazemComEstoque.getEstoque().containsKey(morango)),
                () -> assertEquals(1, armazemComEstoque.getEstoque().get(morango)),
                () -> assertFalse(armazemComEstoque.getEstoque().containsKey(leite)),
                () -> assertNull(armazemComEstoque.getEstoque().get(leite))
        );
    }

    @Test
    public void deve_lancarExcecaoAoReduzirQuantidadeDo_ingrediente_noEstoque_quandoValorIgualOuAbaixoDe0(){

        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        Ingrediente leite = new Base(TipoBase.LEITE);
        var armazem = umArmazem().comEstoqueCompleto().build();

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
        var armazemSemEstoque = umArmazem().build();

        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazemSemEstoque.reduzirQuantidadeDoIngredienteEmEstoque(morango, 2)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> armazemSemEstoque.reduzirQuantidadeDoIngredienteEmEstoque(leite, 3))
        );
    }

    @Test
    public void deve_retornarAQuantidadeDeUm_ingrediente_doEstoque_corretamente(){

        var armazem = umArmazem().comEstoqueCompleto().build();
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(new Fruta(TipoFruta.MORANGO), 3);

        int quantidadeMorango = armazem.consultarQuantidadeDoIngredienteEmEstoque(new Fruta(TipoFruta.MORANGO));
        int quantidadeLeite = armazem.consultarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.LEITE));

        assertAll(
                () -> assertEquals(4, quantidadeMorango),
                () -> assertEquals(1, quantidadeLeite)
        );
    }

    @Test
    public void deve_lancarExcecaoAoBuscarAQuantidadeDeUm_ingrediente_doEstoque_quandoInexistente(){
        Ingrediente morango = new Fruta(TipoFruta.MORANGO);
        var armazem = umArmazem().build();

        assertAll(
                () -> assertFalse(armazem.getEstoque().containsKey(morango)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () ->armazem.consultarQuantidadeDoIngredienteEmEstoque(morango)
                )
        );
    }
}
