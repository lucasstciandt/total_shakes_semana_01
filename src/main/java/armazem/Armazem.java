package armazem;

import ingredientes.Ingrediente;

import java.util.Map;
import java.util.TreeMap;

public class Armazem {

    Map<Ingrediente, Integer> estoque = new TreeMap<>();

    public void cadastrarIngrediente(Ingrediente ingrediente) {
        if (estoque.containsKey(ingrediente)) throw new IllegalArgumentException("Ingrediente já cadastrado");
        estoque.put(ingrediente, 0);
    }

    public Map<Ingrediente, Integer> getEstoque() {
        return estoque;
    }
}
