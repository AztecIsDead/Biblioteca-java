import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


    public class Catalogo {
        ArrayList<Livro> catalogoLivros = CSVUtil.lerCSV("livros.csv", Livro.class);
        ArrayList<Funcionario> funcionariosCadastrados = CSVUtil.lerCSV("funcionarios.csv", Funcionario.class);
        ArrayList<Cliente> clientesCadastrados = CSVUtil.lerCSV("clientes.csv", Cliente.class);

        public ArrayList<Livro> getCatalogoLivros(){ return catalogoLivros; }
        public ArrayList<Funcionario> getFuncionariosCadastrados(){ return funcionariosCadastrados; }
        public ArrayList<Cliente> getClientesCadastrados(){ return clientesCadastrados; }

        public void atualizarClientes(){ CSVUtil.gravarCSV(clientesCadastrados, "clientes.csv"); }
        public void atualizarCatalogo(){ CSVUtil.gravarCSV(catalogoLivros, "livros.csv"); }

        // métodos sem Scanner: adicione objetos diretamente
        public void addLivro(Livro livro) {
            if (livro == null) return;
            catalogoLivros.add(livro);
            atualizarCatalogo();
        }

        public void addFuncionario(Funcionario f) {
            if (f == null) return;
            funcionariosCadastrados.add(f);
            CSVUtil.gravarCSV(funcionariosCadastrados, "funcionarios.csv");
        }

        public void addCliente(Cliente c) {
            if (c == null) return;
            clientesCadastrados.add(c);
            atualizarClientes();
        }

        // busca (simples)
        public Livro buscarLivroTitulo(String titulo){
            if (titulo == null) return null;
            for (Livro l : catalogoLivros){
                if (l.getTitulo().equalsIgnoreCase(titulo)){
                    return l;
                }
            }
            return null;
        }

        public Livro buscarLivroAutor(String autor){
            if (autor == null) return null;
            for (Livro l : catalogoLivros){
                if (l.getAutor().equalsIgnoreCase(autor)){
                    return l;
                }
            }
            return null;
        }

        public Cliente buscarClienteNome(String nome){
            if (nome == null) return null;
            for (Cliente c : clientesCadastrados){
                if (c.getNome().equalsIgnoreCase(nome)){
                    return c;
                }
            }
            return null;
        }

        public boolean alugarLivroParaCliente(String nomeCliente, String tituloLivro) {
            Cliente cliente = buscarClienteNome(nomeCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado: " + nomeCliente);
                return false;
            }
            Livro livro = buscarLivroTitulo(tituloLivro);
            if (livro == null) {
                System.out.println("Livro não encontrado: " + tituloLivro);
                return false;
            }
            if (!livro.getDisponibilidade()) {
                System.out.println("Livro indisponível: " + tituloLivro);
                return false;
            }
            livro.setDisponibilidade(false);
            cliente.setLivroAlugado(livro.getTitulo());
            cliente.setStatusDevendo(true);
            atualizarCatalogo();
            atualizarClientes();
            return true;
        }
    }


