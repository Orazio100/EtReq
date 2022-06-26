package contratti;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import EthScan.EtReq.NormalTransaction;

public class GestoreContratti implements Iterable<Contratto> {
	
	
	public static final GestoreContratti ISTANZA= new GestoreContratti();
	
	private TreeMap<String,Contratto> contratti;
	private String cartellaRisultati;
	
	private GestoreContratti() {
		contratti= new TreeMap<>();
	}
	
	public void add(String nomeContratto, String hashContratto,String nomeFile, String nomeMetodo, String hashMetodo, Metriche metriche) {
		
		if(contratti.containsKey(hashContratto)) {
			Contratto contratto= contratti.get(hashContratto);
			contratto.add(nomeContratto,nomeMetodo, hashMetodo, metriche);
		}else {
			Contratto contratto= new Contratto(hashContratto,nomeFile);
			contratto.add(nomeContratto,nomeMetodo, hashMetodo, metriche);
			this.contratti.put(hashContratto, contratto);
			
		}
				
	}
	
	public int TransactionTotaleIn() {
		int n=0;
		for(Contratto c :this.contratti.values()) {
			n= n+ c.getTotalTransactionNumberIn();
		}
		return n;
	}
	
	public int TransactionTotaleOut() {
		int n=0;
		for(Contratto c :this.contratti.values()) {
			n= n+ c.getTotalTransactionNumberOut();
		}
		return n;
	}
	
	public int numeroContratti() {
		return this.contratti.values().size();
	}
	
	public int FallBackOut() {
		int n=0;
		for(Contratto c :this.contratti.values()) {
			n= n+ c.getFallBackTransactionOut();
		}
		return n;
	}
	
	public boolean containsContratto(String hash) {
		return this.contratti.containsKey(hash);
	}

	

	@Override
	public Iterator<Contratto> iterator() {
		return this.contratti.values().iterator();
	}
	
	public void putTransaction(String id, NormalTransaction ts) {
		this.contratti.get(id).putTransaction(ts);
		
	}

	@Override
	public String toString() {			
		String temp ="\nGestoreContratti\n\tContratti: ";
		
		for(Contratto c : contratti.values())
			temp=temp+"\t"+c.toString();
				
		return temp;
	}
	
	
	
	public List<String> getCSVAll (String hash) {
		return this.contratti.get(hash).CSVAll();
	}
	
	public List<String> getCSVSimple(String hash) {
		return this.contratti.get(hash).CSV();
	}
	
	public List<NormalTransaction> getTransactionBag(String hash) {
		return this.contratti.get(hash).getTransactionBag();
	}
	
	
	public void sganciaTransazioni(String hash) {
		this.contratti.get(hash).sganciaTransazioni();
	}
	
	
	

}
