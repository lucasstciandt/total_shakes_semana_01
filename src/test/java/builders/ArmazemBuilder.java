package builders;

import armazem.Armazem;
import ingredientes.base.Base;
import ingredientes.base.TipoBase;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import ingredientes.topping.TipoTopping;
import ingredientes.topping.Topping;

public class ArmazemBuilder {

    private Armazem armazem;

    private ArmazemBuilder() {
    }

    public static ArmazemBuilder umArmazem(){
        var armazemBuilder = new ArmazemBuilder();
        armazemBuilder.armazem = new Armazem();

        return armazemBuilder;
    }

    public ArmazemBuilder comEstoqueCompleto(){

        armazem.getEstoque().put(new Fruta(TipoFruta.MORANGO), 1);
        armazem.getEstoque().put(new Fruta(TipoFruta.BANANA), 1);
        armazem.getEstoque().put(new Fruta(TipoFruta.ABACATE), 1);
        armazem.getEstoque().put(new Topping(TipoTopping.MEL), 1);
        armazem.getEstoque().put(new Topping(TipoTopping.CHOCOLATE), 1);
        armazem.getEstoque().put(new Topping(TipoTopping.AVEIA), 1);
        armazem.getEstoque().put(new Base(TipoBase.LEITE), 1);
        armazem.getEstoque().put(new Base(TipoBase.IOGURTE), 1);
        armazem.getEstoque().put(new Base(TipoBase.SORVETE), 1);

        return this;
    }

    public Armazem build(){
        return armazem;
    }
}
