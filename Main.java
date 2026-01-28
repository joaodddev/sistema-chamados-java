import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// ================= ENUM =================
enum StatusChamado {
    ABERTO,
    EM_ANDAMENTO,
    FECHADO
}

// ================= USUARIO =================
class Usuario {
    private String nome;
    private String setor;

    public Usuario(String nome, String setor) {
        this.nome = nome;
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }
}

// ================= ATENDIMENTO =================
class Atendimento {
    private Usuario responsavel;
    private String descricao;
    private LocalDateTime dataHora;

    public Atendimento(Usuario responsavel, String descricao) {
        this.responsavel = responsavel;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "[" + dataHora.format(formatter) + "] "
                + responsavel.getNome()
                + " - " + descricao;
    }
}

// ================= CHAMADO =================
class Chamado {
    private int id;
    private String descricao;
    private StatusChamado status;
    private List<Atendimento> atendimentos;

    public Chamado(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.status = StatusChamado.ABERTO;
        this.atendimentos = new ArrayList<>();
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (status == StatusChamado.FECHADO) {
            System.out.println("Chamado #" + id + " já está fechado.");
            return;
        }

        atendimentos.add(atendimento);

        if (status == StatusChamado.ABERTO) {
            status = StatusChamado.EM_ANDAMENTO;
        }
    }

    public void fecharChamado() {
        if (status == StatusChamado.FECHADO) {
            System.out.println("Chamado #" + id + " já está fechado.");
            return;
        }

        if (atendimentos.isEmpty()) {
            System.out.println("Não é possível fechar o chamado #" + id + " sem atendimento.");
            return;
        }

        status = StatusChamado.FECHADO;
        System.out.println("Chamado #" + id + " fechado com sucesso.");
    }

    public void exibirResumo() {
        System.out.println("\nChamado #" + id);
        System.out.println("Descrição: " + descricao);
        System.out.println("Status: " + status);
        System.out.println("Atendimentos:");

        if (atendimentos.isEmpty()) {
            System.out.println("- Nenhum atendimento registrado");
        }

        for (Atendimento a : atendimentos) {
            System.out.println("- " + a);
        }
    }
}

// ================= FILTRO =================
class FiltroChamados {

    public static List<Chamado> filtrarPorStatus(
            List<Chamado> chamados,
            StatusChamado status
    ) {
        List<Chamado> resultado = new ArrayList<>();

        for (Chamado c : chamados) {
            if (c.getStatus() == status) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public static void exibir(List<Chamado> chamados, StatusChamado status) {
        System.out.println("\n📌 CHAMADOS COM STATUS: " + status);

        if (chamados.isEmpty()) {
            System.out.println("Nenhum chamado encontrado.");
            return;
        }

        for (Chamado c : chamados) {
            c.exibirResumo();
        }
    }
}

// ================= RELATORIO =================
class RelatorioChamados {

    public static void gerar(List<Chamado> chamados) {
        int abertos = 0;
        int andamento = 0;
        int fechados = 0;

        for (Chamado c : chamados) {
            if (c.getStatus() == StatusChamado.ABERTO) abertos++;
            else if (c.getStatus() == StatusChamado.EM_ANDAMENTO) andamento++;
            else if (c.getStatus() == StatusChamado.FECHADO) fechados++;
        }

        System.out.println("\n📊 RELATÓRIO DE CHAMADOS");
        System.out.println("Total: " + chamados.size());
        System.out.println("Abertos: " + abertos);
        System.out.println("Em andamento: " + andamento);
        System.out.println("Fechados: " + fechados);
    }
}

// ================= MAIN =================
public class Main {

    public static void main(String[] args) {

        Usuario tecnico = new Usuario("João", "TI");

        Chamado c1 = new Chamado(1, "Sistema fora do ar");
        Chamado c2 = new Chamado(2, "Erro na impressora");
        Chamado c3 = new Chamado(3, "Problema de acesso ao sistema");

        c1.adicionarAtendimento(
                new Atendimento(tecnico, "Análise inicial")
        );
        c1.adicionarAtendimento(
                new Atendimento(tecnico, "Serviço reiniciado")
        );
        c1.fecharChamado();

        c3.adicionarAtendimento(
                new Atendimento(tecnico, "Reset de senha realizado")
        );

        List<Chamado> chamados = new ArrayList<>();
        chamados.add(c1);
        chamados.add(c2);
        chamados.add(c3);

        // Exibir todos
        for (Chamado c : chamados) {
            c.exibirResumo();
        }

        // Relatório geral
        RelatorioChamados.gerar(chamados);

        // ===== FILTRO POR STATUS =====
        List<Chamado> abertos =
                FiltroChamados.filtrarPorStatus(chamados, StatusChamado.ABERTO);

        FiltroChamados.exibir(abertos, StatusChamado.ABERTO);
    }
}
