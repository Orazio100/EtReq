package EthScan.EtReq;

import org.apache.commons.csv.*;
import org.web3j.crypto.Hash;

import contratti.Contratto;
import contratti.GestoreContratti;
import contratti.Metriche;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CaricaDati {
	
	private List<String> metodiModificatore;
	private List<String> metodiOverLoad;
	private List<String> metodiMancanti;
	private String nomeFileMetriche;
	
	
	public CaricaDati(String Filemetriche) {
		metodiModificatore= new LinkedList<String>();
		metodiOverLoad= new LinkedList<String>();
		metodiMancanti=new LinkedList<String>();
		nomeFileMetriche= Filemetriche;
		
	}
	
	
	public void carica(String pathCartellaContratti) throws IOException {
		
		
	
		Reader in = new FileReader(this.nomeFileMetriche);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';').parse(in);
		for (CSVRecord record : records) {
			String nomeFile = record.get("Solidity-File");
			String nomeContratto = record.get("Contract-Name");
			String nomeMetodo = record.get("Function-Name");
	    
	    
			Metriche metriche = new Metriche(record.get("SLOC"),record.get("Avg-Assignment"),record.get("Avg-blank-lines"),record.get("Avg-commas")
					,record.get("Avg-comments"),record.get("Avg-comparisons"),record.get("Avg-Identifiers-Length"),record.get("Avg-conditionals")
					,record.get("Avg-indentation-length"),record.get("Avg-keywords"),record.get("Avg-line-length"), record.get("Avg-loops")
					,record.get("Avg-number-of-identifiers"),record.get("Avg-numbers"),record.get("Avg-operators"),record.get("Avg-parenthesis")
					,record.get("Avg-periods"),record.get("Avg-spaces"),record.get("Max-Identifiers-Length"),record.get("Max-indentation")
					,record.get("Max-keywords"),record.get("Max-line-length"),record.get("Max-number-of-identifiers"),record.get("Max-numbers")
					, record.get("Max-char"),record.get("Max-words"));
	    
			String hashContratto= nomeFile.trim().substring(0,42);
	    
			HashFunction hf= HashFunction.factoryHash(pathCartellaContratti+"\\"+nomeFile, nomeMetodo);
			
			switch(hf.getResult()) {
			
			case HashFunction.Hash:
			case HashFunction.Fallback:
				GestoreContratti.ISTANZA.add(nomeContratto, hashContratto, nomeFile, nomeMetodo, hf.getHash(), metriche);
				break;
			case HashFunction.Modifier:
				this.metodiModificatore.add(nomeFile+";"+ nomeContratto+";" + nomeMetodo);
				break;
			case HashFunction.OverLoad:
				this.metodiOverLoad.add(nomeFile+";"+ nomeContratto+";" + nomeMetodo);
				break;
			case HashFunction.Void:
				this.metodiOverLoad.add(nomeFile+";"+ nomeContratto+";" + nomeMetodo);
				break;
			default:
			}
			
	    
	  
		}
		
		
		
	}
	
	
	
	public void salvaRisultati(String pathCartella) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pathCartella+"\\"+"Modifier.txt", false)));
		    this.scriviLista(out,this.metodiModificatore);
		    out.close();
		    out = new PrintWriter(new BufferedWriter(new FileWriter(pathCartella+"\\"+"overLoad.txt", false)));
		    this.scriviLista(out,this.metodiOverLoad);
		    out.close();
		    out = new PrintWriter(new BufferedWriter(new FileWriter(pathCartella+"\\"+"Mancanti.txt", false)));
		    this.scriviLista(out,this.metodiMancanti);
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		
		
		
		
		
	}
	
	private void scriviLista(PrintWriter pw, List<String> lista) {
		pw.print("NomeFile;NomeContratto;NomeMetodo\n");
		for(String s : lista) {
			pw.print(s+"\n");
		}
		
	}
	
	
	
	
	
	
	public static void main(String[]  args) throws IOException {
		
		CaricaDati cd = new CaricaDati("C:\\Users\\orazi\\Desktop\\metriche.csv");
		cd.carica("C:\\Users\\orazi\\Desktop\\Contract\\Dataset");
		cd.salvaRisultati("C:\\Users\\orazi\\Desktop\\Risultati");
		GestoreRichieste gr= new GestoreRichieste("5ZZ6YN5H8ZT6N9S9QDGEQX4738Y6JHFJYX","C:\\Users\\orazi\\Desktop\\RisultatiCSV");
		gr.avviaRichieste();
	}
		
		


	
	
}

