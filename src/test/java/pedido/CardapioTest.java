package pedido;


import ingredientes.*;
import ingredientes.base.Base;
import ingredientes.base.TipoBase;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import ingredientes.topping.TipoTopping;
import ingredientes.topping.Topping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.TreeMap;

import static builders.CardapioBuilder.umCardapio;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardapioTest {

    @Test
    void deve_adicionar_ingredientesEPrecos_corretamente(){
        var cardapioVazio = umCardapio().build();

        cardapioVazio.adicionarIngrediente(new Base(TipoBase.SORVETE), 2.0);
        cardapioVazio.adicionarIngrediente(new Fruta(TipoFruta.MORANGO), 4.0);
        cardapioVazio.adicionarIngrediente(new Topping(TipoTopping.CHOCOLATE), 10.0);

        assertAll(
                () -> assertEquals(3, cardapioVazio.getPrecos().size()),
                () -> assertTrue( cardapioVazio.getPrecos().containsKey(new Base(TipoBase.SORVETE))),
                () -> assertEquals(2.0, cardapioVazio.buscarPreco(new Base(TipoBase.SORVETE))),
                () -> assertTrue( cardapioVazio.getPrecos().containsKey(new Fruta(TipoFruta.MORANGO))),
                () -> assertEquals(4.0, cardapioVazio.buscarPreco(new Fruta(TipoFruta.MORANGO))),
                () -> assertTrue( cardapioVazio.getPrecos().containsKey(new Topping(TipoTopping.CHOCOLATE))),
                () -> assertEquals(10.0, cardapioVazio.buscarPreco(new Topping(TipoTopping.CHOCOLATE)))
        );
    }

    @Test
    void deve_retornarApenas_ingredientes_queSaoDoTipoAdicionais(){
        var cardapioCompleto = umCardapio().comTodosAdicionais().comTodasAsBases().build();

        TreeMap<Ingrediente, Double> adicionais = cardapioCompleto.getPrecoAdicionais();

        adicionais.forEach((key, value) -> assertTrue(key instanceof Adicional));

        assertAll(
                () -> assertFalse(adicionais.containsKey(new Base(TipoBase.SORVETE))),
                () -> assertFalse(adicionais.containsKey(new Base(TipoBase.IOGURTE))),
                () -> assertFalse(adicionais.containsKey(new Base(TipoBase.LEITE)))
        );
    }

    @Test
    void deve_remover_ingredientes_corretamente(){
        var cardapioComBases = umCardapio().comTodasAsBases().build();

        cardapioComBases.removerIngrediente(new Base(TipoBase.SORVETE));

        assertAll(
                () -> assertEquals(2, cardapioComBases.getPrecos().size()),
                () -> assertTrue( cardapioComBases.getPrecos().containsKey(new Base(TipoBase.LEITE))),
                () -> assertTrue(cardapioComBases.getPrecos().containsKey(new Base(TipoBase.IOGURTE)))
        );
    }

    @Test
    void deve_lancarExcecaoAoAdicionar_ingredientes_quandoPrecoNegativoOuZero(){
        var cardapioVazio = umCardapio().build();

        var exception = assertThrows( IllegalArgumentException.class,
                        () -> cardapioVazio.adicionarIngrediente(new Base(TipoBase.IOGURTE), -9.0));
        assertEquals("Preco invalido.", exception.getMessage());
    }

    @Test
    void deve_atualizarPrecoDo_ingrediente_corretamente(){
        var cardapioCompleto = umCardapio().comTodasAsBases().comTodosAdicionais().build();

        cardapioCompleto.atualizarIngrediente(new Fruta(TipoFruta.MORANGO), 9.0);
        cardapioCompleto.atualizarIngrediente(new Base(TipoBase.SORVETE), 10.0);
        cardapioCompleto.atualizarIngrediente(new Topping(TipoTopping.CHOCOLATE), 20.0);

        assertAll(
                () -> assertEquals(9.0, cardapioCompleto.buscarPreco(new Fruta(TipoFruta.MORANGO))),
                () -> assertEquals(10.0, cardapioCompleto.buscarPreco(new Base(TipoBase.SORVETE))),
                () -> assertEquals(20.0, cardapioCompleto.buscarPreco(new Topping(TipoTopping.CHOCOLATE)))
        );
    }

    @Test
    void deve_lancarExcecaoAoTentarAtualizar_ingredientes_quandoPrecoNegativoOuZero(){

        var cardapioComBases = umCardapio().comTodasAsBases().build();

        var exception = assertThrows(IllegalArgumentException.class,
                        () -> cardapioComBases.atualizarIngrediente(new Base(TipoBase.IOGURTE), -9.0));
        assertEquals("Preco invalido.", exception.getMessage());
    }

    @Test
    void deve_lancarExcecaoAoTentarAtualizar_ingredientes_inexistentes(){
        var cardapioVazio = umCardapio().build();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> cardapioVazio.atualizarIngrediente(new Topping(TipoTopping.MEL), 19.0));
        assertEquals("Ingrediente nao existe no cardapio.", exception.getMessage());
    }

    @Test
    void deve_lancarExcecaoAoTentarRemover_ingredientes_inexistentes(){
        var cardapioVazio = umCardapio().build();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> cardapioVazio.removerIngrediente(new Topping(TipoTopping.MEL)));
        assertEquals("Ingrediente nao existe no cardapio.", exception.getMessage());

    }

    @Test
    void deve_retornarValorDo_ingrediente_corretamente(){
        var cardapio = umCardapio().comTodasAsBases().build();

        assertAll(
                () -> assertEquals(1.0, cardapio.buscarPreco(new Base(TipoBase.IOGURTE))),
                () -> assertEquals(2.0, cardapio.buscarPreco(new Base(TipoBase.LEITE))),
                () -> assertEquals(3.0, cardapio.buscarPreco(new Base(TipoBase.SORVETE)))
        );
    }

    @Test
    void deve_lancarExcecaoAoTentarBuscarPrecoDe_ingredientes_inexistentes(){
        var cardapioComBases = umCardapio().comTodasAsBases().build();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> cardapioComBases.buscarPreco(new Fruta(TipoFruta.MORANGO)));
        assertEquals("Ingrediente nao existe no cardapio.", exception.getMessage());
    }

}