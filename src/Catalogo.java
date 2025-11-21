import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Catalogo {

    private ArrayList<Livro> catalogoLivros = CSVUtil.lerCSV("livros.csv", Livro.class);
    private ArrayList<Funcionario> funcionariosCadastrados = CSVUtil.lerCSV("funcionarios.csv", Funcionario.class);
    private ArrayList<Cliente> clientesCadastrados = CSVUtil.lerCSV("clientes.csv", Cliente.class);
    private ArrayList<Request> requests = CSVUtil.lerCSV("requests.csv", Request.class);

    public ArrayList<Livro> getCatalogoLivros() { return catalogoLivros; }
    public ArrayList<Funcionario> getFuncionariosCadastrados() { return funcionariosCadastrados; }
    public ArrayList<Cliente> getClientesCadastrados() { return clientesCadastrados; }
    public ArrayList<Request> getRequests() { return requests; }

    public void atualizarCatalogo() {
        CSVUtil.gravarCSV(catalogoLivros, "livros.csv");
    }

    public void atualizarClientes() {
        CSVUtil.gravarCSV(clientesCadastrados, "clientes.csv");
    }

    public void atualizarFuncionarios() {
        CSVUtil.gravarCSV(funcionariosCadastrados, "funcionarios.csv");
    }

    public void atualizarRequests() {
        CSVUtil.gravarCSV(requests, "requests.csv");
    }

    public void addLivro(Livro livro) {
        if (livro == null) return;
        if (catalogoLivros == null) catalogoLivros = new ArrayList<>();
        catalogoLivros.add(livro);
        atualizarCatalogo();
    }

    public void addFuncionario(Funcionario f) {
        if (f == null) return;
        if (funcionariosCadastrados == null) funcionariosCadastrados = new ArrayList<>();
        funcionariosCadastrados.add(f);
        atualizarFuncionarios();
    }

    public void addCliente(Cliente c) {
        if (c == null) return;
        if (clientesCadastrados == null) clientesCadastrados = new ArrayList<>();
        c.setStatusDevendo(false);
        if (c.getLivroAlugado() == null || c.getLivroAlugado().trim().isEmpty()) c.setLivroAlugado("Nenhum");
        clientesCadastrados.add(c);
        atualizarClientes();
    }

    public Livro buscarLivroTitulo(String titulo) {
        if (titulo == null || catalogoLivros == null) return null;
        for (Livro l : catalogoLivros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) return l;
        }
        return null;
    }

    public List<Livro> buscarLivroAutor(String autor) {
        List<Livro> out = new ArrayList<>();
        if (autor == null || catalogoLivros == null) return out;
        for (Livro l : catalogoLivros) {
            if (l.getAutor().equalsIgnoreCase(autor)) out.add(l);
        }
        return out;
    }

    public Cliente buscarClienteNome(String nome) {
        if (nome == null || clientesCadastrados == null) return null;
        for (Cliente c : clientesCadastrados) {
            if (c.getNome().equalsIgnoreCase(nome)) return c;
        }
        return null;
    }

    public List<Funcionario> buscarFuncionarioNome(String nome) {
        List<Funcionario> out = new ArrayList<>();
        if (nome == null || funcionariosCadastrados == null) return out;
        for (Funcionario f : funcionariosCadastrados) {
            if (f.getNome().equalsIgnoreCase(nome)) out.add(f);
        }
        return out;
    }

    public List<Funcionario> buscarFuncionarioCargo(String cargo) {
        List<Funcionario> out = new ArrayList<>();
        if (cargo == null || funcionariosCadastrados == null) return out;
        for (Funcionario f : funcionariosCadastrados) {
            if (f.getCargo().equalsIgnoreCase(cargo)) out.add(f);
        }
        return out;
    }
    public void registrarLivro() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o título do livro:");
        String titulo = scanner.nextLine();
        System.out.println("Digite o autor do livro:");
        String autor = scanner.nextLine();
        Livro livro = new Livro(titulo, autor, true);
        addLivro(livro);
        System.out.println("Livro registrado com sucesso!");
    }
    public void registrarFuncionario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do funcionario:");
        String nome = scanner.nextLine();
        int idade = 0;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.println("Digite a idade do funcionario:");
                idade = scanner.nextInt();
                scanner.nextLine();
                inputValido = idade > 0;
            } catch (InputMismatchException e) {
                System.out.println("Idade inválida! Tente novamente:");
                scanner.nextLine();
            }
        }
        System.out.println("Digite o cargo do funcionario:");
        String cargo = scanner.nextLine();
        Funcionario f = new Funcionario(nome, idade, cargo);
        addFuncionario(f);
        System.out.println("Funcionário registrado com sucesso!");
    }
    public void registrarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        int idade = 0;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.println("Digite a idade do cliente:");
                idade = scanner.nextInt();
                scanner.nextLine();
                inputValido = idade > 0;
            } catch (InputMismatchException e) {
                System.out.println("Idade inválida! Tente novamente:");
                scanner.nextLine();
            }
        }
        Cliente cliente = new Cliente(nome, idade, false, "Nenhum");
        addCliente(cliente);
        System.out.println("Cliente registrado com sucesso!");
    }

    public boolean alugarLivroParaCliente(String clienteNome, String tituloLivro) {
        if (clienteNome == null || tituloLivro == null) return false;

        Cliente cliente = buscarClienteNome(clienteNome);
        Livro livro = buscarLivroTitulo(tituloLivro);

        if (cliente == null) {
            System.out.println("Cliente não encontrado: " + clienteNome);
            return false;
        }
        if (livro == null) {
            System.out.println("Livro não encontrado: " + tituloLivro);
            return false;
        }
        if (!livro.getDisponibilidade()) {
            System.out.println("Livro já indisponível: " + tituloLivro);
            return false;
        }

        livro.setDisponibilidade(false);
        cliente.setLivroAlugado(livro.getTitulo());
        atualizarCatalogo();
        atualizarClientes();
        return true;
    }
    public void addRequest(String clienteNome, String tituloLivro) {
        if (clienteNome == null || clienteNome.trim().isEmpty() || tituloLivro == null || tituloLivro.trim().isEmpty())
            return;
        if (requests == null) requests = new ArrayList<>();
        // evita duplicar pedido pendente
        for (Request r : requests) {
            if (r.getClienteNome().equalsIgnoreCase(clienteNome)
                    && r.getTituloLivro().equalsIgnoreCase(tituloLivro)
                    && r.getStatus() == Request.Status.PENDENTE) {
                System.out.println("Pedido já pendente: " + clienteNome + " / " + tituloLivro);
                return;
            }
        }
        Request nova = new Request(clienteNome, tituloLivro, Request.Status.PENDENTE, null);
        requests.add(nova);
        atualizarRequests();
    }
    public List<Request> getPendingRequests() {
        List<Request> out = new ArrayList<>();
        if (requests == null) return out;
        for (Request r : requests) if (r.getStatus() == Request.Status.PENDENTE) out.add(r);
        return out;
    }

    public boolean approveRequest(String clienteNome, String tituloLivro, int daysUntilDue) {
        if (clienteNome == null || tituloLivro == null) return false;

        Request found = null;
        if (requests != null) {
            for (Request r : requests) {
                if (r.getClienteNome().equalsIgnoreCase(clienteNome)
                        && r.getTituloLivro().equalsIgnoreCase(tituloLivro)
                        && r.getStatus() == Request.Status.PENDENTE) {
                    found = r; break;
                }
            }
        }
        if (found == null) return false;

        Cliente cliente = buscarClienteNome(clienteNome);
        Livro livro = buscarLivroTitulo(tituloLivro);
        if (cliente == null || livro == null) return false;
        if (!livro.getDisponibilidade()) return false; // já alugado

        livro.setDisponibilidade(false);
        cliente.setLivroAlugado(livro.getTitulo());
        // NÃO marcar como devendo aqui

        LocalDate due = LocalDate.now().plusDays(daysUntilDue);
        found.setStatus(Request.Status.APROVADO);
        found.setDueDate(due);

        atualizarCatalogo();
        atualizarClientes();
        atualizarRequests();
        return true;
    }

    public boolean rejectRequest(String clienteNome, String tituloLivro) {
        if (clienteNome == null || tituloLivro == null) return false;
        Request found = null;
        if (requests != null) {
            for (Request r : requests) {
                if (r.getClienteNome().equalsIgnoreCase(clienteNome)
                        && r.getTituloLivro().equalsIgnoreCase(tituloLivro)
                        && r.getStatus() == Request.Status.PENDENTE) {
                    found = r; break;
                }
            }
        }
        if (found == null) return false;
        found.setStatus(Request.Status.REJEITADO);
        atualizarRequests();
        return true;
    }
    public void checarVencimentosEAtualizarDevendo() {
        if (requests == null || clientesCadastrados == null) return;
        LocalDate hoje = LocalDate.now();
        boolean mudou = false;
        for (Request r : requests) {
            if (r.getStatus() == Request.Status.APROVADO && r.getDueDate() != null && r.getDueDate().isBefore(hoje)) {
                // cliente com aluguel vencido -> marcar devendo
                Cliente cl = buscarClienteNome(r.getClienteNome());
                if (cl != null && !cl.getStatusDevendo()) {
                    cl.setStatusDevendo(true);
                    mudou = true;
                }
            }
        }
        if (mudou) atualizarClientes();
    }
}
