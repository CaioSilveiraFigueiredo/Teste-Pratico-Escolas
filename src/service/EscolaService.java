package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Escola;

public class EscolaService {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public List<Escola> carregaEscolasDoArquivo(String csvFile) {
		
		List<Escola> listaEscolas = new ArrayList<>();
		String line;
        String splitBy = ";"; 
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "ISO-8859-1"))) {
            
            br.readLine(); // Pula o cabeçalho

            while ((line = br.readLine()) != null) {
                String[] campos = line.split(splitBy, -1);
                
                Escola escola = geraObjeto(campos);
                listaEscolas.add(escola);
            }
        }
        
        catch (IOException e) {
            System.err.println("Erro crítico: Não foi possível ler o arquivo. Verifique o caminho.");
        }
        
        catch (Exception e) {
            System.err.println("Erro ao processar o arquivo CSV: " + e.getMessage());
        }

        return listaEscolas;
	}
	
	private Escola geraObjeto(String[] data) {
		Escola e = new Escola();
        try {
            e.setDre(data[0]);
            e.setCodesc(converterParaLong(data[1]));
            e.setTipoesc(data[2]);
            e.setNomes(data[3]);
            e.setNomescofi(data[4]);
            e.setCeu(data[5]);
            e.setDiretoria(data[6]);
            e.setSubpref(data[7]);
            e.setEndereco(data[8]);
            e.setNumero(data[9]);
            e.setBairro(data[10]);
            e.setCep(converterParaLong(data[11]));
            e.setTel1(data[12]);
            e.setTel2(data[13]);
            e.setFax(converterParaLong(data[14]));
            e.setSituacao(data[15]);
            e.setCoddist(converterParaLong(data[16]));
            e.setDistrito(data[17]);
            e.setSetor(converterParaLong(data[18]));
            e.setCodinep(converterParaLong(data[19]));
            e.setCdCie(converterParaLong(data[20]));
            e.setEh(converterParaLong(data[21]));
            e.setFxEtaria(data[22]);
            
            if (!data[23].isBlank()) e.setDtCriacao(dateFormat.parse(data[23]));
            e.setAtoCriacao(data[24]);
            if (!data[25].isBlank()) e.setDomCriacao(dateFormat.parse(data[25]));
            
            e.setDtIniConv(data[26]);
            e.setDtAutoriza(data[27]);
            e.setDtExtincao(converterParaLong(data[28]));
            e.setNomeAnt(data[29]);
            e.setRede(data[30]);
            
            e.setLatitude(converterParaBigDecimal(data[31]));
            e.setLongitude(converterParaBigDecimal(data[32]));
            
            if (!data[33].isBlank()) e.setDatabase(dateFormat.parse(data[33]));

        } catch (Exception ex) {
            System.err.println("Erro ao mapear escola (codesc): " + data[1] + " - " + ex.getMessage());
        }
        return e;
    }


    private Long converterParaLong(String valor) {
        if (valor == null || valor.isBlank() || valor.trim().equals("-")) return null;
        try {
            String limpo = valor.trim().replace(".", "").replace(",", ".");
            return new BigDecimal(limpo).longValue();
        } catch (Exception e) { return null; }
    }

    private BigDecimal converterParaBigDecimal(String valor) {
        if (valor == null || valor.isBlank() || valor.trim().equals("-")) return null;
        try {
            String limpo = valor.trim().replace(",", ".");
            return new BigDecimal(limpo);
        } catch (Exception e) { return null; }
    }
	
    
}
