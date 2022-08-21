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

    public void descadastrarIngrediente(Ingrediente ingrediente) {
        if (!estoque.containsKey(ingrediente)) throw new IllegalArgumentException("Ingrediente não encontrado");
        estoque.remove(ingrediente);
    }

    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, int quantidade) {

        this.validarQuantidade(quantidade);

        estoque.entrySet().stream()
                .filter(ingredientes -> ingredientes.getKey().obterTipo() == ingrediente.obterTipo())
                .findFirst()
                .ifPresentOrElse(ingredienteEncontrado -> {
                    int novaQuantidade = ingredienteEncontrado.getValue() + quantidade;
                    estoque.put(ingrediente, novaQuantidade);

                }, () -> {
                    throw new IllegalArgumentException("Ingrediente não encontrado");
                });
    }

    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, int quantidade) {

        this.validarQuantidade(quantidade);
        
        estoque.entrySet().stream()
                .filter(ingredientes -> ingredientes.getKey().obterTipo() == ingrediente.obterTipo())
                .findFirst()
                .ifPresentOrElse(ingredienteEncontrado -> {
                    int novaQuantidade = ingredienteEncontrado.getValue() - quantidade;
                    if(novaQuantidade <= 0){
                        estoque.remove(ingrediente);
                    }else{
                        estoque.put(ingrediente, novaQuantidade);
                    }
                }, () -> {
                    throw new IllegalArgumentException("Ingrediente não encontrado");
                });
    }

    private void validarQuantidade(int quantidade) {
        if(quantidade <= 0) throw new IllegalArgumentException("Quantidade invalida");
    }

    public Map<Ingrediente, Integer> getEstoque() {
        return estoque;
    }
}
