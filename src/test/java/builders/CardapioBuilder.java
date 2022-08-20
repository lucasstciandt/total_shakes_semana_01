package builders;

import ingredientes.base.Base;
import ingredientes.base.TipoBase;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import ingredientes.topping.TipoTopping;
import ingredientes.topping.Topping;
import pedido.Cardapio;
import produto.Shake;
import produto.TipoTamanho;

import java.util.Arrays;

public class CardapioBuilder {

    private Cardapio cardapio;

    private CardapioBuilder(){

    }

    public static CardapioBuilder umCardapio(){
        var cardapioBuilder = new CardapioBuilder();
        cardapioBuilder.cardapio = new Cardapio();

        return  cardapioBuilder;
    }


    public Cardapio build(){
        return cardapio;
    }

    public CardapioBuilder comTodasAsBases() {
        cardapio.adicionarIngrediente(new Base(TipoBase.LEITE), 2.0);
        cardapio.adicionarIngrediente(new Base(TipoBase.SORVETE), 3.0);
        cardapio.adicionarIngrediente(new Base(TipoBase.IOGURTE), 1.0 );

        return this;
    }

    public CardapioBuilder comTodosAdicionais() {

        cardapio.adicionarIngrediente(new Fruta(TipoFruta.MORANGO), 3.0);
        cardapio.adicionarIngrediente(new Fruta(TipoFruta.ABACATE), 2.0);
        cardapio.adicionarIngrediente(new Fruta(TipoFruta.BANANA), 1.0 );
        cardapio.adicionarIngrediente(new Topping(TipoTopping.AVEIA), 1.0 );
        cardapio.adicionarIngrediente(new Topping(TipoTopping.MEL), 2.0 );
        cardapio.adicionarIngrediente(new Topping(TipoTopping.CHOCOLATE), 3.0 );

        return this;
    }
}
