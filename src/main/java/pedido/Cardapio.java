package pedido;

import ingredientes.Adicional;
import ingredientes.Ingrediente;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Cardapio {

    private final TreeMap<Ingrediente,Double> precos;

    public Cardapio(){
        this.precos= new TreeMap<>();
    }

    public void adicionarIngrediente(Ingrediente ingrediente,Double preco){

        if(preco <= 0) throw new IllegalArgumentException("Preco invalido.");
        precos.put(ingrediente, preco);
    }

    public boolean atualizarIngrediente(Ingrediente ingrediente,Double preco){

        if(preco <= 0) throw new IllegalArgumentException("Preco invalido.");

        precos.entrySet().stream()
                .filter(cardapio -> cardapio.getKey().obterTipo().equals(ingrediente.obterTipo()))
                .findFirst()
                .ifPresentOrElse(
                        ingredienteExistente -> precos.put(ingrediente, preco),
                        () -> { throw new IllegalArgumentException("Ingrediente nao existe no cardapio."); }
                );

        return true;
    }

    public boolean removerIngrediente(Ingrediente ingrediente){

        precos.entrySet().stream()
                .filter(cardapio -> cardapio.getKey().obterTipo().equals(ingrediente.obterTipo()))
                .findFirst()
                .ifPresentOrElse(
                        ingredienteExistente -> precos.remove(ingredienteExistente.getKey()),
                        () -> { throw new IllegalArgumentException("Ingrediente nao existe no cardapio."); }
                );

        return true;
    }

    public Double buscarPreco(Ingrediente ingrediente){

        precos.entrySet().stream()
                .filter(cardapio -> cardapio.getKey().obterTipo().equals(ingrediente.obterTipo()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Ingrediente nao existe no cardapio."));

        return precos.get(ingrediente);
    }


    public TreeMap<Ingrediente, Double> getPrecoAdicionais(){
        return this.precos.entrySet().stream()
                .filter(ingredientePreco -> ingredientePreco.getKey() instanceof Adicional)
                .collect(
                        TreeMap::new,
                        (map, elemento) -> map.put(elemento.getKey(), elemento.getValue()),
                        TreeMap::putAll
                );
    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

    public TreeMap<Ingrediente, Double> getPrecos(){
        return this.precos;
    }
}
