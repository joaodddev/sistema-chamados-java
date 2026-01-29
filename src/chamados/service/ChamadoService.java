package chamados.service;

import chamados.model.Chamado;
import chamados.model.StatusChamado;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChamadoService {

    private List<Chamado> chamados = new ArrayList<>();

    public void adicionarChamado(Chamado chamado) {
        chamados.add(chamado);
    }

    public List<Chamado> filtrarPorStatus(StatusChamado status) {
        return chamados.stream()
                .filter(c -> c.getStatus() == status)
                .collect(Collectors.toList());
    }
}
