package pedido;

import ingredientes.base.Base;
import ingredientes.base.TipoBase;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import ingredientes.topping.TipoTopping;
import ingredientes.topping.Topping;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static builders.ShakeBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PedidoTest{

    private Pedido pedido;
    private Cardapio cardapio;

    @BeforeAll
    void setup(){
        cardapio = new Cardapio();
        cardapio.adicionarIngrediente(new Base(TipoBase.IOGURTE), 10.0);
        cardapio.adicionarIngrediente(new Base(TipoBase.SORVETE), 5.0);
        cardapio.adicionarIngrediente(new Base(TipoBase.LEITE), 2.0);
        cardapio.adicionarIngrediente(new Fruta(TipoFruta.BANANA), 1.0);
        cardapio.adicionarIngrediente(new Fruta(TipoFruta.MORANGO), 10.0);
        cardapio.adicionarIngrediente(new Fruta(TipoFruta.ABACATE), 6.0);
        cardapio.adicionarIngrediente(new Topping(TipoTopping.AVEIA), 2.0);
        cardapio.adicionarIngrediente(new Topping(TipoTopping.MEL), 1.0);
        cardapio.adicionarIngrediente(new Topping(TipoTopping.CHOCOLATE), 100.0);
    }

    @BeforeEach
    void resetPedido(){
        pedido = new Pedido(1, new ArrayList<>(), new Cliente(
                1,
                "Cliente Test",
                "cliente.test@email.com"
        ));
    }

    @Test
    void deve_adicionarApenasUm_itemPedido_corretamente(){

        var shake = umShake().build();
        var itemPedido = new ItemPedido(shake, 1);

        pedido.adicionarItemPedido(itemPedido);

        assertAll(
                () -> assertEquals(1, pedido.getItens().size()),
                () -> assertEquals(itemPedido, pedido.getItens().get(0)),
                () -> assertEquals(1, pedido.getItens().get(0).getQuantidade())
        );
    }

    @Test
    void deve_atualizarQuantidadeDo_itemPedido_quandoDuplicado(){

        var shake = umShake().build();

        var itemPedido = new ItemPedido(shake, 1);
        var itemPedidoDuplicado = new ItemPedido(shake, 2);

        pedido.adicionarItemPedido(itemPedido);
        pedido.adicionarItemPedido(itemPedidoDuplicado);

        assertAll(
                () -> assertEquals(1, pedido.getItens().size()),
                () -> assertEquals(itemPedido, pedido.getItens().get(0)),
                () -> assertEquals(itemPedidoDuplicado, pedido.getItens().get(0)),
                () -> assertEquals(3, pedido.getItens().get(0).getQuantidade())
        );
    }

    @Test
    void deve_adicionarOutro_itemPedido_quandoDiferente(){
        var shake = umShake().build();
        var shakeDiferente = umShake().semAdicionais().build();

        var itemPedido = new ItemPedido(shake, 1);
        var itemPedidoComOutroShake = new ItemPedido(shakeDiferente, 2);

        pedido.adicionarItemPedido(itemPedido);
        pedido.adicionarItemPedido(itemPedidoComOutroShake);

        assertAll(
                () -> assertEquals(2, pedido.getItens().size()),
                () -> assertEquals(itemPedido, pedido.getItens().get(0)),
                () -> assertEquals(itemPedidoComOutroShake, pedido.getItens().get(1))
        );
    }

    @Test
    void deve_removerUm_itemPedido_corretamente(){

        var shake = umShake().build();

        var itemPedido = new ItemPedido(shake, 1);

        pedido.adicionarItemPedido(itemPedido);
        pedido.removeItemPedido(itemPedido);

        assertEquals(0, pedido.getItens().size());
    }

    @Test
    void deve_removerApenasUmaUnidadeDe_itemPedido_quandoQuantidadeParaRemover_maiorQue1(){

        var shake = umShake().build();
        var shakeParaRemover = umShake().build();

        var itemPedido = new ItemPedido(shake, 3);
        var itemPedidoRemovido = new ItemPedido(shakeParaRemover, 10);

        pedido.adicionarItemPedido(itemPedido);
        pedido.removeItemPedido(itemPedidoRemovido);

        assertAll(
                () -> assertEquals(1, pedido.getItens().size()),
                () -> assertEquals(2, pedido.getItens().get(0).getQuantidade())
        );
    }

    @Test
    void deve_removerO_itemPedido_quandoQuantidadeRestanteFor_igualA0(){

        var shake = umShake().build();
        var shakeParaRemover = umShake().build();

        var itemPedido = new ItemPedido(shake, 1);
        var itemPedidoRemovido = new ItemPedido(shakeParaRemover, 1);

        pedido.adicionarItemPedido(itemPedido);
        pedido.removeItemPedido(itemPedidoRemovido);

        assertEquals(0, pedido.getItens().size());
    }

    @Test
    void deve_lancarUmaExcecao_quandoTentarRemoverItemInexistente(){

        var shake = umShake().build();

        var itemPedidoInexistente = new ItemPedido(shake, 10);

        assertThrows(IllegalArgumentException.class, () -> pedido.removeItemPedido(itemPedidoInexistente),
                "Item nao existe no pedido.");
    }

    @Test
    void deve_calcularPedido_comUmShakeContendo_todosOsAdicionais_corretamente(){

        var shake = umShake().comTodosOsAdicionais().build();

        var itemPedido1 = new ItemPedido(shake, 1);

        pedido.adicionarItemPedido(itemPedido1);
        double total = pedido.calcularTotal(cardapio);

        assertEquals(125, total);
    }

    @Test
    void deve_calcularPedido_comOsTresTamanhosDeShake_semAdicionais_corretamente(){
        var shakeG = umShake().semAdicionais().comTamanhoG().build();
        var shakeM = umShake().semAdicionais().comTamanhoM().build();
        var shakeP = umShake().semAdicionais().comTamanhoP().build();

        var itemPedidoComShakeG = new ItemPedido(shakeG, 1);
        var itemPedidoComShakeM = new ItemPedido(shakeM, 1);
        var itemPedidoComShakeP = new ItemPedido(shakeP, 1);

        pedido.adicionarItemPedido(itemPedidoComShakeG);
        pedido.adicionarItemPedido(itemPedidoComShakeM);
        pedido.adicionarItemPedido(itemPedidoComShakeP);

        double total = pedido.calcularTotal(cardapio);

        assertEquals(19 , total );
    }

}