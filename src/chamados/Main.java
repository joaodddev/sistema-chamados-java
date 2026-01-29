package chamados;

import chamados.model.*;
import chamados.service.ChamadoService;

public class Main {

    public static void main(String[] args) {

        ChamadoService service = new ChamadoService();

        Chamado c1 = new Chamado(1, "Erro no sistema");
        Chamado c2 = new Chamado(2, "Falha no login");

        c1.atualizarStatus(StatusChamado.EM_ATENDIMENTO);

        service.adicionarChamado(c1);
        service.adicionarChamado(c2);

        System.out.println("Chamados abertos:");
        service.filtrarPorStatus(StatusChamado.ABERTO)
               .forEach(c -> System.out.println(c));
    }
}
