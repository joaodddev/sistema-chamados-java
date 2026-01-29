package chamados.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Chamado {

    private int id;
    private String descricao;
    private StatusChamado status;
    private List<String> historico;

    public Chamado(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.status = StatusChamado.ABERTO;
        this.historico = new ArrayList<>();
        registrarEvento("Chamado criado");
    }

    public void atualizarStatus(StatusChamado novoStatus) {
        if (this.status == StatusChamado.ENCERRADO) {
            throw new IllegalStateException("Chamado já encerrado.");
        }
        this.status = novoStatus;
        registrarEvento("Status alterado para " + novoStatus);
    }

    private void registrarEvento(String evento) {
        historico.add(LocalDateTime.now() + " - " + evento);
    }

    public StatusChamado getStatus() {
        return status;
    }

    public List<String> getHistorico() {
        return historico;
    }
    @Override
public String toString() {
    return "Chamado #" + id +
           " | Descrição: " + descricao +
           " | Status: " + status;
}
}

