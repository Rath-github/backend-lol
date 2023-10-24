package com.ufpr.lol.service;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import com.ufpr.lol.modal.ClienteModal;
import com.ufpr.lol.service.ClienteService;
import com.ufpr.lol.utils.GerarSenhas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {

    @Autowired
    private ClienteService ClienteService;

    public void enviarSenhaGeradaPorEmail(String email) {
        // Verifique se o cliente com o email especificado está registrado no sistema
        ClienteModal cliente = ClienteService.buscarClientePorEmail(email);

        if (cliente != null) {
            // Gere uma senha aleatória para o cliente
            String senhaGerada = GerarSenhas.generateRandomPassword();

            // Atualize a senha do cliente no banco de dados
            cliente.setSenha(senhaGerada);
            ClienteService.atualizarCliente(cliente);

            // Construa a solicitação para enviar o email usando a API do Resend
            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .from("onboarding@resend.dev")
                    .to(email)
                    .subject("Sua Nova Senha")
                    .html("Sua senha gerada é: " + senhaGerada)
                    .build();

            // Configure o token de autenticação para a API do Resend
            Resend resend = new Resend("re_QjC4D48N_A1fy8GpvGM9N7NHmYiockocU");

            try {
                // Envie o email
                SendEmailResponse response = resend.emails().send(sendEmailRequest);
                System.out.println("Email com a nova senha enviado com sucesso. ID: " + response.getId());
            } catch (ResendException e) {
                // Trate erros no envio do email, se necessário
                System.out.println("Erro ao enviar o email: " + e.getMessage());
            }
        } else {
            // Cliente não encontrado, trate o caso apropriadamente
            System.out.println("Cliente não encontrado.");
        }
    }
}
