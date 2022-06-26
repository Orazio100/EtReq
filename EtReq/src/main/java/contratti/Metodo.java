package contratti;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import EthScan.EtReq.NormalTransaction;

public class Metodo implements Iterable<NormalTransaction>{
	
	private String nomeMetodo;
	private String hashPrototipo;
	private Metriche metriche;
	private List<NormalTransaction> transazioni;
	
	
	public Metodo(String nomeC, String hash, Metriche metriche ) {
		
		this.nomeMetodo= nomeC;
		this.hashPrototipo= hash;
		this.metriche= metriche;
		this.transazioni= new LinkedList<NormalTransaction>();
		
	}

	public void putTransaction(NormalTransaction ts) {
		transazioni.add(ts);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashPrototipo == null) ? 0 : hashPrototipo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metodo other = (Metodo) obj;
		if (hashPrototipo == null) {
			if (other.hashPrototipo != null)
				return false;
		} else if (!hashPrototipo.equals(other.hashPrototipo))
			return false;
		return true;
	}

	public int getNumTransaction() {
		return this.transazioni.size();
	}

	@Override
	public String toString() {
		return "\n\t\t\tnomeMetodo: " + nomeMetodo + "\n\t\t\thashPrototipo: "
	           + hashPrototipo +  "\n\t\t\tN. Transazioni: " + this.getNumTransaction() + "\n\t\t\tmetriche: "+metriche.toString();
	}

	@Override
	public Iterator<NormalTransaction> iterator() {
		return this.transazioni.iterator();
	}

	
	public String getCSVMetriche() {
		return this.metriche.toCSV();
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}
	
	

	
	
	
	
	

}
