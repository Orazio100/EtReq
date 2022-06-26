package EthScan.EtReq;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import contratti.Contratto;
import contratti.GestoreContratti;

public class GestoreRichieste {
	
	
	private String chiave;


	public static final String SimpleCSV= "Solidity-File;Contract-Name;Function-Name;Transaction-Number;Gas-Used-Total;SLOC;Avg-Assignment;Avg-blank-lines;Avg-commas;Avg-comments;Avg-comparisons;Avg-Identifiers-Length;Avg-conditionals;Avg-indentation-length;Avg-keywords;Avg-line-length;Avg-loops;Avg-number-of-identifiers;Avg-numbers;Avg-operators;Avg-parenthesis;Avg-periods;Avg-spaces;Max-Identifiers-Length;Max-indentation;Max-keywords;Max-line-length;Max-number-of-identifiers;Max-numbers;Max-char;Max-words\n";
	public static final String AllCSV= "Solidity-File;Contract-Name;Function-Name;Transaction-Hash;Gas-Used;SLOC;Avg-Assignment;Avg-blank-lines;Avg-commas;Avg-comments;Avg-comparisons;Avg-Identifiers-Length;Avg-conditionals;Avg-indentation-length;Avg-keywords;Avg-line-length;Avg-loops;Avg-number-of-identifiers;Avg-numbers;Avg-operators;Avg-parenthesis;Avg-periods;Avg-spaces;Max-Identifiers-Length;Max-indentation;Max-keywords;Max-line-length;Max-number-of-identifiers;Max-numbers;Max-char;Max-words\n";
	public static final String Bag= "Solidity-File;Transaction-Hash;Gas-Used;Function-Hash\n";
	private String cartellaRisultati;
	
	
	
	
	
	public GestoreRichieste(String chiave,String pathCartellaRisultati) {

		this.chiave = chiave;
		this.cartellaRisultati=pathCartellaRisultati;

	}
	
	
	public void avviaRichieste() throws IOException {
	
		PrintWriter CSVAll = new PrintWriter(new BufferedWriter(new FileWriter(this.cartellaRisultati+"\\"+"CSVAll.txt", true)));
		PrintWriter CSVSimple = new PrintWriter(new BufferedWriter(new FileWriter(this.cartellaRisultati+"\\"+"CSVSimple.txt", true)));
		PrintWriter TransacrtionBag = new PrintWriter(new BufferedWriter(new FileWriter(this.cartellaRisultati+"\\"+"TransacrtionBag.txt", true)));
		
		
	    
		CSVAll.print(AllCSV);
		CSVSimple.print( SimpleCSV);
		TransacrtionBag.print(Bag);
		
		long nt=0;
		int nc=1,nci=GestoreContratti.ISTANZA.numeroContratti(),nm=0,cfc=0;
		for(Contratto ct  : GestoreContratti.ISTANZA ) {
			int pag=0,off=1000;
			boolean stato=true;
			while(  stato==true) {
				try {
					GetNormalTransaction  gnt = GetNormalTransaction.Get(UrlT(ct.getHash(),pag,off,this.chiave));
					if(gnt.getStatus().trim().equalsIgnoreCase("1")) {
						List<NormalTransaction> transazioni=gnt.getResult();
						for(NormalTransaction ts : transazioni) {
							ct.putTransaction(ts);
							nt++;
						}
					pag++;	
						
					}else stato=false;
					
				} catch (IOException e) {
					System.err.println("Errore HTTP GET in avviaRichieste");
				}
				
				
			}
		
		nm=nm+ct.getNumeroMetodi();
		cfc=cfc+ct.getFuoriContratto();
		System.out.println("Contratto :" +(nc++)+"/"+nci+"    Transazioni Cumulate:  "+ nt+"  Totale Metodi Cumulati:  "+ nm+"  FBO:  "+ ct.getFallBackTransactionOut()+"  FBI:  "+ ct.getFallBackTransactionIn()+"  cumulativeOutContract:  "+cfc);
		
		
		for(String s : GestoreContratti.ISTANZA.getCSVAll(ct.getHash())) {
			CSVAll.print(s);
		}
		
		for(String s : GestoreContratti.ISTANZA.getCSVSimple(ct.getHash())) {
			CSVSimple.print(s);
		}
		
		for(NormalTransaction tx : GestoreContratti.ISTANZA.getTransactionBag(ct.getHash())) {
			String inp= (tx.getInput().trim().length()>=10)?(tx.getInput().substring(0,10)):tx.getInput().trim();
			
			TransacrtionBag.print(ct.getNomeFile()+";"+tx.getHash()+";"+tx.getGasUsed()+";"+inp+"\n");
		}
		
		
		GestoreContratti.ISTANZA.sganciaTransazioni(ct.getHash());
			
		}
		
		CSVAll.close();
		CSVSimple.close();
		TransacrtionBag.close();

		
	}



	public static String UrlT(String hashContratto,int pagina,int offset,String chiave) {
		return "https://api.etherscan.io/api?module=account&action=txlist&address="+hashContratto
				+"&startblock=0&endblock=99999999&page="+pagina+"&offset="+offset+"&sort=asc&apikey="+chiave;
	}
	
	

}
