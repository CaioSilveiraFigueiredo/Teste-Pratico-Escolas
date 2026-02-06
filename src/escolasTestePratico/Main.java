package escolasTestePratico;

import java.util.List;

import bd.EscolasDao;
import model.Escola;
import service.EscolaService;

public class Main {
	
	public static void main(String[] args) {
		
		try {
		
		String csvFile = "escolas122022.csv";
		
		EscolaService service = new EscolaService();
	    List<Escola> lista = service.carregaEscolasDoArquivo(csvFile);
	    
	    EscolasDao escolasDao = new EscolasDao();
	    escolasDao.inserirDadosNoBanco(lista);
	    
		} catch (Exception e) {
	        System.err.println("Ocorreu um erro inesperado na aplicação:");
	        e.printStackTrace();
	    }
	    
	}
}
