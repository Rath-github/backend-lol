package com.ufpr.lol.service;

import com.ufpr.lol.utils.GerarSenhas;
import com.ufpr.lol.utils.SecureUtils;
import com.ufpr.lol.modal.ClienteModal;
import com.ufpr.lol.repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ClienteService {
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final clienteRepository clienteRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public ClienteService(clienteRepository clienteRepository, JavaMailSender javaMailSender) {
        this.clienteRepository = clienteRepository;
        this.javaMailSender = javaMailSender;
    }

    public ClienteModal criarCliente(ClienteModal cliente) {
        try {
            logger.info("Tentando criar um novo cliente: {}", cliente.getNome());

            byte[] salt = SecureUtils.getSalt();

            // Valide a entrada do cliente aqui se necessário.

            // Gere uma senha aleatória para o cliente
            String senhaAleatoria = GerarSenhas.generateRandomPassword();
            cliente.setSenha(senhaAleatoria);

            // Criptografar a senha
            String senhaCriptografada = GerarSenhas.encryptPassword(senhaAleatoria, salt);
            cliente.setSenha(senhaCriptografada);

            // Envie um e-mail ao cliente com a senha gerada
            enviarEmailDeRegistro(cliente.getEmail(), senhaAleatoria);

            ClienteModal novoCliente = clienteRepository.save(cliente);
            logger.info("Novo cliente criado com sucesso: ID {}", novoCliente.getId());
            return novoCliente;
        } catch (Exception ex) {
            logger.error("Erro ao criar cliente", ex);
            throw new RuntimeException("Erro ao criar cliente", ex);
        }
    }

    public ClienteModal buscarClientePorEmail(String email) {
        try {
            logger.info("Tentando buscar cliente por email: {}", email);

            // Valide a entrada do email se necessário.

            ClienteModal cliente = clienteRepository.findByEmail(email);
            if (cliente != null) {
                logger.info("Cliente encontrado: ID {}", cliente.getId());
            } else {
                logger.info("Cliente não encontrado para o email: {}", email);
            }
            return cliente;
        } catch (Exception ex) {
            logger.error("Erro ao buscar cliente por email", ex);
            //throw Constants.a new RuntimeException("Erro ao buscar cliente por email", ex);
        }
        return null;
    }

    public void atualizarCliente(ClienteModal cliente) {
        try {
            logger.info("Tentando atualizar cliente: ID {}", cliente.getId());

            // Implemente a lógica de atualização do cliente aqui.

            logger.info("Cliente atualizado com sucesso: ID {}", cliente.getId());
        } catch (Exception ex) {
            logger.error("Erro ao atualizar cliente", ex);
            throw new RuntimeException("Erro ao atualizar cliente", ex);
        }
    }

    private void enviarEmailDeRegistro(String emailDestinatario, String senhaGerada) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(emailDestinatario);
        mensagem.setSubject("Bem-vindo ao Sistema LOL");
        mensagem.setText("Seu registro foi concluído com sucesso. Sua senha temporária é: " + senhaGerada);

        javaMailSender.send(mensagem);
    }

    // Método para listar todos os clientes
    public List<ClienteModal> listarClientes() {
        try {
            logger.info("Listando todos os clientes");
            return clienteRepository.findAll();
        } catch (Exception ex) {
            logger.error("Erro ao listar clientes", ex);
            throw new RuntimeException("Erro ao listar clientes", ex);
        }
    }

    // Método para buscar detalhes de um cliente pelo ID
    public ClienteModal buscarClientePorId(Long clienteId) {
        try {
            logger.info("Tentando buscar cliente por ID: {}", clienteId);
            return clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));
        } catch (Exception ex) {
            logger.error("Erro ao buscar cliente por ID", ex);
            throw new RuntimeException("Erro ao buscar cliente por ID", ex);
        }
    }

    // Método para editar um cliente
    public void editarCliente(Long clienteId, ClienteModal cliente) {
        try {
            logger.info("Tentando editar cliente: ID {}", clienteId);
            ClienteModal clienteExistente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));
            // Realize as alterações necessárias no clienteExistente com base nos dados do cliente recebido.
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setEmail(cliente.getEmail());
            // ...
            clienteRepository.save(clienteExistente);
            logger.info("Cliente editado com sucesso: ID {}", clienteId);
        } catch (Exception ex) {
            logger.error("Erro ao editar cliente", ex);
            throw new RuntimeException("Erro ao editar cliente", ex);
        }
    }

    // Método para excluir um cliente pelo ID
    public void excluirCliente(Long clienteId) {
        try {
            logger.info("Tentando excluir cliente: ID {}", clienteId);
            ClienteModal cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));
            clienteRepository.delete(cliente);
            logger.info("Cliente excluído com sucesso: ID {}", clienteId);
        } catch (Exception ex) {
            logger.error("Erro ao excluir cliente", ex);
            throw new RuntimeException("Erro ao excluir cliente", ex);
        }
    }

    public boolean autenticarUsuario(String email, String senhaDigitada) {
        ClienteModal cliente = clienteRepository.findByEmail(email);

        return cliente != null && senhaDigitada.equals(cliente.getSenha());
    }





}
