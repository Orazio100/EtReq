package contratti;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import EthScan.EtReq.HashFunction;
import EthScan.EtReq.NormalTransaction;

public class Contratto {
	
	
	private String hashContratto;
	private String nomeFile;
	private HashMap<String,SubContratto> subContratti;
	private HashMap<String,SubContratto> mapMetodi;
	private List<NormalTransaction> transactionBag; //transazioni che non sono state associate a nessun metodo noto
	private int fallBackTransactionOut;
	private int fallBackTransactionIn;
	private int fuoriContratto;
	
	
	

	public Contratto(String hashContratto, String nomeFile) {
		this.hashContratto = hashContratto;
		this.nomeFile = nomeFile;
		this.subContratti= new HashMap<String,SubContratto>();
		this.mapMetodi= new HashMap<String,SubContratto>();
		this.transactionBag= new LinkedList<NormalTransaction>();
		fallBackTransactionOut=0;
		fallBackTransactionIn=0;
		fuoriContratto=0;
	}


	public void putTransaction(NormalTransaction ts) {
		
		if(ts.getTo().equalsIgnoreCase(this.hashContratto)) {
			String input= ts.getInput().trim();
			if(input.equalsIgnoreCase("0x")||input.length()==0) {// inviata alla fallback function
				SubContratto sub = this.mapMetodi.get(HashFunction.Fallback);
				if(sub!=null) {sub.putTransaction(ts, HashFunction.Fallback); this.fallBackTransactionIn++;}
				else {this.transactionBag.add(ts);this.fallBackTransactionOut++;}
			}else if(input.length()>=10) {
				String hash= input.substring(0,10);
				SubContratto sub = this.mapMetodi.get(hash);
				if(sub!=null) sub.putTransaction(ts, hash);
				else this.transactionBag.add(ts);
				
			}else this.transactionBag.add(ts);
				
			
		}else this.fuoriContratto++;
		
	}


	public void add(String nomeContratto,String nomeMetodo, String hashMetodo, Metriche metriche) {
		
		if(subContratti.containsKey(nomeContratto)) {
			SubContratto subContratto= subContratti.get(nomeContratto);
			subContratto.add(nomeMetodo, hashMetodo, metriche);
			mapMetodi.put(hashMetodo, subContratto);
		}else {
			SubContratto subContratto= new SubContratto(nomeContratto);
			subContratto.add(nomeMetodo, hashMetodo, metriche);
			this.subContratti.put(nomeContratto, subContratto);
			mapMetodi.put(hashMetodo, subContratto);
		}
		
	
		
		
	}
	
	public int getFuoriContratto() {
		return this.fuoriContratto;
	}
	
	
	public int getFallBackTransactionOut() {
		return fallBackTransactionOut;
	}
	
	public int getFallBackTransactionIn() {
		return fallBackTransactionIn;
	}



	public int getNumeroMetodi() {
		
		return this.mapMetodi.size();
		
	}
	public int getTotalTransactionNumberIn() {
		int n=0;
		for(SubContratto sub: this.subContratti.values()) {
			n= n+sub.getTransactionTotal();
		}
		return n;
	}
	public int getTotalTransactionNumberOut() {
		return this.transactionBag.size();
	}
	
	public String getHash() {
		return this.hashContratto;
	}

	@Override
	public String toString() {
		String tmp= "\n\tHashContratto: " + hashContratto + "\n\tnomeFile: " + nomeFile + "\n\tNumeroTransazioniContrattoIn: " + this.getTotalTransactionNumberIn() + "\n\tNumeroTransazioniContrattoOut: " + this.getTotalTransactionNumberOut()  +"\n\tSubContratti: " ;
		for(SubContratto sc : this.subContratti.values()) {
			tmp= tmp+ sc.toString();
		}
		
		return tmp;
	}
	
	
	
	public List<String> CSVAll() {
		List<String> lista= new LinkedList<String>();
		
		for(SubContratto sc : this.subContratti.values()) {
			for(Metodo m : sc) {
				for(NormalTransaction nt : m) {
					lista.add(this.nomeFile+";"+sc.getNomeSubContratto()+";"+m.getNomeMetodo()+";"+nt.getHash()+";"+nt.getGasUsed()+";"+m.getCSVMetriche()+"\n");
				}
			}
		}
		return lista;
	}
	
	public List<String> CSV() {
		List<String> lista= new LinkedList<String>();
		
		for(SubContratto sc : this.subContratti.values()) {
			for(Metodo m : sc) {
				long numeroTransazioni=0,totalGasUses=0;
				for(NormalTransaction nt : m) {
					numeroTransazioni++;
					totalGasUses= totalGasUses+Long.parseLong(nt.getGasUsed());
				}
				lista.add(this.nomeFile+";"+sc.getNomeSubContratto()+";"+m.getNomeMetodo()+";"+Long.toString(numeroTransazioni)+";"+Long.toString(totalGasUses)+";"+m.getCSVMetriche()+"\n");
			}
		}
		return lista;
	}


	public List<NormalTransaction> getTransactionBag() {
		return transactionBag;
	}


	public String getNomeFile() {
		return nomeFile;
	}
	
	
	public void sganciaTransazioni()
	{
		this.mapMetodi=null;
		this.subContratti=null;
		this.transactionBag=null;
	}
	


		
		
	
	
	
	
	

}
