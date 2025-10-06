
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josue
 */
public class Vendas {
    
    private int codigo;
    private String nomeCliente;
    private String cpf;
    private double total;
    private List<Produtos> itens;

    public Vendas() {
        itens = new ArrayList<>();
    }
    
    
    
    public Vendas(int codigo, String nomeCliente, String cpf, double total) {
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.total = total;
        
    }

    
    
    
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Produtos> getItens() {
        return itens;
    }

    public void setItens(List<Produtos> itens) {
        this.itens = itens;
    }
    
    
    
    
    
}
