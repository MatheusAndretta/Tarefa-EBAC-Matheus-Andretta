package main.java.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "TB_PRODUTO_QUANTIDADE")
public class ProdutoQuantidade{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Prod_qtd_seq")
    @SequenceGenerator(name = "Prod_qtd_seq",sequenceName = "seq_prod_qtd")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Produto produto;

    @Column(name = "QUANTIDADE",nullable = false)
    private Integer quantidade;

    @Column(name = "VALOR_TOTAL",nullable = false)
    private BigDecimal valorTOTAL;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_venda_fk",foreignKey = @ForeignKey(name = "fk_prod_qtd_venda"),referencedColumnName = "id",nullable = false)
    private Venda venda;

    public ProdutoQuantidade() {
        this.quantidade = 0;
        this.valorTOTAL = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTOTAL() {
        return valorTOTAL;
    }

    public void setValorTOTAL(BigDecimal valorTOTAL) {
        this.valorTOTAL = valorTOTAL;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public void adicionar(Integer quantidade){
        this.quantidade += quantidade;
        BigDecimal novoValor = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal novoTotal = this.valorTOTAL.add(novoValor);
        this.valorTOTAL = novoTotal;
    }

    public void remover(Integer quantidade){
        this.quantidade -= quantidade;
        BigDecimal novoValor = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
        this.valorTOTAL = this.valorTOTAL.subtract(novoValor);
    }
    
}
