package desafio.one.digitalInnovation.desafio.dio.padroes.projeto.service.impl;

import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.model.Cliente;
import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.model.ClienteRepository;
import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.model.Endereco;
import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.model.EnderecoRepository;
import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.service.ClienteService;
import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/*
Implementação da Strategy {@link ClienteService}, a qual pode ser injetada pelo Spring via {@link Autowired}.
Com isso, como essa classe é um {@link Service}, ela será tratada com um Singleton.
*/
@Service
public class ClienteServiceImpl implements ClienteService {

    //TODO Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;
    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.
    @Override
    public Iterable<Cliente> buscarTodos() {
        //FIXME Buscar todos os Clientes.
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        //FIXME Buscar  Clientes por id.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }


    @Override
    public void atualizar(Long id, Cliente cliente) {
        //FIXME Buscliente por id, caso exita.
        Optional<Cliente> clienteBD = clienteRepository.findById(id);
        if(clienteBD.isPresent()){
            salvarClienteComCep(cliente);
        }

    }

    @Override
    public void deletar(Long id) {
        //FIXME Deletar cliente por id;
        clienteRepository.deleteById(id);
    }
    private void salvarClienteComCep(Cliente cliente) {
        //FIXME Verificar se o endereço do cliente já existe pelo cep.
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
        //FIXME Caso não exista, integrar com o viacep e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        //FIXME Inserir cliente, vinculando o endereço novo ou existente.
        clienteRepository.save(cliente);
    }
}
