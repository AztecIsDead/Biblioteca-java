public class RepositorioLivroCsv extends RepositorioCsvBase<Livro> {

    public RepositorioLivroCsv(String arquivo) {
        super(arquivo);
    }

    @Override
    protected Livro fromCSV(String[] campos) {
        return Livro.fromCSV(campos);
    }

    @Override
    protected String toCSV(Livro obj) {
        return obj.toCSV();
    }

    @Override
    protected String getCabecalho() {
        return "id,titulo,autor,raro,totalCopias,copiasDisponiveis";
    }

    public java.util.Optional<Livro> findById(String id) {
        return findAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }
}
