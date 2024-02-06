package com.saneacre.gecons.domain.compras;

import com.saneacre.gecons.domain.compras.itens.CompraItemEntity;
import com.saneacre.gecons.domain.compras.itens.CompraItemId;
import com.saneacre.gecons.domain.compras.itens.CompraItemRepository;
import com.saneacre.gecons.domain.compras.itens.Item;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoId;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoRepository;
import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;
import com.saneacre.gecons.domain.empenhos.EmpenhoRepository;
import com.saneacre.gecons.domain.fornecedores.FornecedorEntity;
import com.saneacre.gecons.domain.fornecedores.FornecedorRepository;
import com.saneacre.gecons.domain.plano_operativo.DemandaEntity;
import com.saneacre.gecons.domain.plano_operativo.DemandaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    CompraItemRepository compraItemRepository;

    @Autowired
    EmpenhoRepository empenhoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    DemandaRepository demandaRepository;

    @Autowired
    ContratoFornecedorPoRepository contratoFornecedorPoRepository;

    public RetornaCompraDTO cadastrarCompra(CriaCompraDTO dados) {
        var empenho = validaEmpenho(dados.idEmpenho());
        var contrato = empenho.getContrato();
        List<ContratoFornecedorPoEntity> itens = new ArrayList<>();
        BigDecimal valorTotalCompra = BigDecimal.ZERO;

        for (Item item : dados.itens()) {
            var fornecedor = validaFornecedor(item.idFornecedor());
            var demanda = validaDemanda(item.idDemanda());
            var itemCompra = validaItem(contrato, fornecedor, demanda, item.quantidade());
            valorTotalCompra = valorTotalCompra.add(itemCompra.getValorUnitario().multiply(item.quantidade()));
            itens.add(itemCompra);
        }

        validaValorTotal(empenho, valorTotalCompra);

        var compra = new CompraEntity(empenho, valorTotalCompra, dados.data());
        compraRepository.save(compra);

        for (int i = 0; i < itens.size(); i++) {
            var id = new CompraItemId(compra.getId(), itens.get(i).getContrato().getId(),
                                        itens.get(i).getFornecedor().getId(), itens.get(i).getDemanda().getId());

            var jaExiste = compraItemRepository.procuraChaveDuplicada(id);
            if (jaExiste.isPresent())
                throw new DataIntegrityViolationException("O item " + itens.get(i).getDemanda().getNome() +
                                                             " esta repetido na compra!");

            var compraItem = new CompraItemEntity(id, compra, itens.get(i).getContrato(), itens.get(i).getFornecedor(),
                                                    itens.get(i).getDemanda(), dados.itens().get(i).quantidade());

            compraItemRepository.save(compraItem);
        }

        return new RetornaCompraDTO(compra, dados.itens());
    }

    public Page<RetornaCompraDTO> buscaTodasCompras(Pageable paginacao) {
        return compraRepository.findAll(paginacao).map(compraEntity -> {
            List<Item> itens = new ArrayList<>();
            for (var itemCompra : compraEntity.getItens()) {
                var item = new Item(itemCompra.getFornecedor().getId(),
                        itemCompra.getDemanda().getId(), itemCompra.getQuantidade());
                itens.add(item);
            }
            return new RetornaCompraDTO(compraEntity, itens);
        });
    }

    public RetornaCompraDTO buscaCompraPorId(Long id) {
        var compra = compraRepository.findById(id);
        if (compra.isEmpty())
            throw new EntityNotFoundException("Compra com o id " + id + " não encontrada!");

        List<Item> itens = new ArrayList<>();
        for (var itemCompra : compra.get().getItens()) {
            var item = new Item(itemCompra.getFornecedor().getId(),
                    itemCompra.getDemanda().getId(), itemCompra.getQuantidade());
            itens.add(item);
        }

        return new RetornaCompraDTO(compra.get(), itens);
    }

    public void deletaCompra(Long id) {
        var compra = compraRepository.findById(id);
        if (compra.isEmpty())
            throw new EntityNotFoundException("Compra com o id " + id + " não encontrada!");

        compraRepository.delete(compra.get());
    }

    private EmpenhoEntity validaEmpenho(Long idEmpenho) {
        var empenho = empenhoRepository.findById(idEmpenho);
        if (empenho.isEmpty())
            throw new EntityNotFoundException("Empenho com o id " + idEmpenho + " não encontrado!");

        return empenho.get();
    }

    private FornecedorEntity validaFornecedor(Long idFornecedor) {
        var fornecedor = fornecedorRepository.findById(idFornecedor);
        if (fornecedor.isEmpty())
            throw new EntityNotFoundException("Fornecedor com o id " + idFornecedor + " não encontrado!");
        return fornecedor.get();
    }

    private DemandaEntity validaDemanda(Long idDemanda) {
        var demanda = demandaRepository.findById(idDemanda);
        if (demanda.isEmpty())
            throw new EntityNotFoundException("Demanda com o id " + idDemanda + " não encontrado!");

        return demanda.get();
    }

    private ContratoFornecedorPoEntity validaItem(ContratoEntity contrato, FornecedorEntity fornecedor,
                                                  DemandaEntity demanda, BigDecimal quantidade) {
        ContratoFornecedorPoId id = new ContratoFornecedorPoId(contrato.getId(), fornecedor.getId(), demanda.getId());
        var itemContrato = contratoFornecedorPoRepository.findById(id);
        if (itemContrato.isEmpty())
            throw new EntityNotFoundException("O insumo/serviço " + demanda.getNome() + " não esta presente no contrato "
                                                + contrato.getNumero() + " para o fornecedor " + fornecedor.getNome());

        var comprasItem = compraItemRepository.findAllByContratoAndFornecedorAndDemanda(contrato, fornecedor, demanda);
        var quantidadeComprada = comprasItem.stream()
                .map(CompraItemEntity::getQuantidade)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var novaQuantidadeComprada = quantidadeComprada.add(quantidade);
        var quantidadeTotal = itemContrato.get().getQuantRegistro();
        if(novaQuantidadeComprada.compareTo(quantidadeTotal) > 0)
            throw new DataIntegrityViolationException("A compra não pode ser cadastrada, a quantidade informada do item "
                                                        + demanda.getNome() + " ultrapassa o saldo do contrato!");

        return itemContrato.get();
    }

    private void validaValorTotal(EmpenhoEntity empenho, BigDecimal valorTotalCompra) {
        BigDecimal valorAtual = getValorAtualCompras(empenho);
        BigDecimal novoValor = valorAtual.add(valorTotalCompra);
        if (novoValor.compareTo(empenho.getValor()) > 0)
            throw new DataIntegrityViolationException("A compra não pode ser cadastrada, " +
                    "pois ultrapassa o saldo do empenho!");

    }

    private BigDecimal getValorAtualCompras(EmpenhoEntity empenho) {
        var comprasComEmpenho = compraRepository.findAllByEmpenho(empenho);
        return comprasComEmpenho.stream().map(CompraEntity::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
