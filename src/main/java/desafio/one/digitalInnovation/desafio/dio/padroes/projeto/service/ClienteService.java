package desafio.one.digitalInnovation.desafio.dio.padroes.projeto.service;

import desafio.one.digitalInnovation.desafio.dio.padroes.projeto.model.Cliente;

/*
* Interface que define o padrão Strategy no domínio de cliente.
* Com isso, se necessário, podemos ter multiplas implementações dessa interface
*/

public interface ClienteService {
    Iterable <Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
