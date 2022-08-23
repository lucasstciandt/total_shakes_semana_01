package builders;

import ingredientes.Adicional;
import ingredientes.base.Base;
import ingredientes.base.TipoBase;
import ingredientes.fruta.Fruta;
import ingredientes.fruta.TipoFruta;
import ingredientes.topping.TipoTopping;
import ingredientes.topping.Topping;
import produto.Shake;
import produto.TipoTamanho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShakeBuilder {

    private Shake shake;

    private ShakeBuilder(){

    }

    public static ShakeBuilder umShake(){
        var shakeBuilder = new ShakeBuilder();
        shakeBuilder.shake = new Shake();
        shakeBuilder.shake.setBase(new Base(TipoBase.SORVETE));
        shakeBuilder.shake.setFruta(new Fruta(TipoFruta.MORANGO));
        shakeBuilder.shake.setTopping(new Topping(TipoTopping.MEL));
        shakeBuilder.shake.setAdicionais(Arrays.asList((new Fruta(TipoFruta.BANANA)), new Topping(TipoTopping.AVEIA)));
        shakeBuilder.shake.setTipoTamanho(TipoTamanho.P);

        return shakeBuilder;
    }

    public ShakeBuilder semAdicionais() {
        shake.setAdicionais(new ArrayList<>());
        return this;
    }

    public ShakeBuilder comTamanhoG() {
        shake.setTipoTamanho(TipoTamanho.G);
        return this;
    }

    public ShakeBuilder comTamanhoM() {
        shake.setTipoTamanho(TipoTamanho.M);
        return this;
    }

    public ShakeBuilder comTamanhoP() {
        shake.setTipoTamanho(TipoTamanho.P);
        return this;
    }

    public ShakeBuilder comTodosOsAdicionais() {
        List<Adicional> todosOsAdicionais = new ArrayList<>(Arrays.asList(
                new Fruta(TipoFruta.MORANGO),
                new Fruta(TipoFruta.ABACATE),
                new Fruta(TipoFruta.BANANA),
                new Topping(TipoTopping.AVEIA),
                new Topping(TipoTopping.MEL),
                new Topping(TipoTopping.CHOCOLATE)
        ));

        shake.setAdicionais(todosOsAdicionais);
        return this;
    }

    public Shake build(){
        return shake;
    }
}
