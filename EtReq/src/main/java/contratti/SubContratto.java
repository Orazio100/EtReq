package contratti;

import java.util.HashMap;
import java.util.Iterator;

import org.web3j.crypto.Hash;

import EthScan.EtReq.NormalTransaction;

public class SubContratto implements Iterable<Metodo> {
	private String nomeSubContratto;
	private HashMap<String, Metodo> metodi;
	
	
	
	public SubContratto(String nome) {
		this.nomeSubContratto=nome;
		this.metodi= new HashMap<String, Metodo> ();
	}
	
	public void add(String nomeMetodo, String hashMetodo, Metriche metriche ) {

		if(metodi.containsKey(hashMetodo)) {
			System.err.println("Errore metodo duplicato: nome Metodo: "+ nomeMetodo);
		}else {
			Metodo metodo = new Metodo(nomeMetodo,hashMetodo,metriche);
			this.metodi.put(hashMetodo, metodo);
		}
	
	}
	
	public void putTransaction(NormalTransaction ts,String hash) {
		
		Metodo metodo= this.metodi.get(hash);
		
		if(metodo!=null) {
			metodo.putTransaction(ts);
		}else {
			System.out.println("Errore hash rimpiazzato, hashMetodo: "+hash);
		}
		
	}
	
	public int getTransactionTotal() {
		int n=0;
		for(Metodo m : this.metodi.values()) {
			n=n+m.getNumTransaction();
		}
		return n;
	}

	@Override
	public String toString() {
		String tmp= "\n\t\tNomeContratto: "+this.nomeSubContratto+"\n\t\tN. transazioniSubContratto: "+this.getTransactionTotal()+"\n\t\tMetodi: ";
		for(Metodo m : metodi.values()) {
			tmp= tmp+ m.toString();
		}
		
		return tmp;
	}
	
	@Override
	public Iterator<Metodo> iterator() {
		return this.metodi.values().iterator();
	}

	public String getNomeSubContratto() {
		return nomeSubContratto;
	}

	
	
	
	

}
