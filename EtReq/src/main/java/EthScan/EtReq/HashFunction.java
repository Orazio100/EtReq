package EthScan.EtReq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;


public class HashFunction {
	public static final String Function="function";
	public static final String Modifier="modifier";
	public static final String Fallback="fallback";
	public static final String Hash="hash";
	public static final String OverLoad="overload";
	public static final String Void="void";
	
	private String nomeMetodo;
	private String nomeFile;
	private HashSet<String> duplicati;
	private String hash;
	private String result;
	
	

	public HashFunction(String nomeFile, String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
		this.nomeFile = nomeFile;
		this.duplicati = new HashSet<String>();
		
		
		if(this.isFallback()) {
			hash=Fallback;
			result=Fallback;
		}else if(this.modifier()) {
			hash="";
			result=Modifier;
		}else {
			this.calcolaHash();
			if(duplicati.size()==1) {
				hash=duplicati.iterator().next();
				result=Hash;	
			}else if (duplicati.size()>=2) {
				hash="";
				result=OverLoad;
			}else {
				hash="";
				result=Void;
			}
			
		}
		
	}
	
	public boolean isHashPresent() {
		return (result.equals(Hash)||result.equals(Fallback));
	}
	
	public String getResult() {
		return result;
	}
	
	public String getHash() {
		return hash;
	}
	
	
	public boolean isFallback() {
		StringTokenizer parentesi = new StringTokenizer(this.nomeMetodo);
		int n= parentesi.countTokens();
		if (n==1 && nomeMetodo.trim().equals("()")) return true;
		else if (n==2 && (parentesi.nextToken().trim()+parentesi.nextToken().trim()).equals("()")) return true;
		else return false;
	}

	
	private boolean modifier() {
		
		try {
			String content = new String(Files.readAllBytes(Paths.get(this.nomeFile)));
			StringTokenizer tokens = new StringTokenizer(content);
		
			while(tokens.hasMoreTokens()) {
				String tok = tokens.nextToken();
		
				if(tok.equals(Modifier)) {
				
					String inizioFunzione= tokens.nextToken();
					while(inizioFunzione.equals(Modifier)) {
						inizioFunzione= tokens.nextToken();
					}
				
				
					if(inizioFunzione.contains(nomeMetodo)&& ( nomeMetodo.length()==inizioFunzione.length() || inizioFunzione.charAt(nomeMetodo.length())==' ' || inizioFunzione.charAt(nomeMetodo.length())=='{' || inizioFunzione.charAt(nomeMetodo.length())=='(') ) {
						return true;
					}
				}
			}
		
		} catch (IOException e) {
			System.out.println("Errore lettura file HashFunction");
			}
		
		return false;
	}


	private void calcolaHash() {
		
		try {
			String content = new String(Files.readAllBytes(Paths.get(this.nomeFile)));
			StringTokenizer tokens = new StringTokenizer(content);
		
			while(tokens.hasMoreTokens()) {
				String tok = tokens.nextToken();
		
				if(tok.equals(Function)) {
				
					String inizioFunzione= tokens.nextToken();
					while(inizioFunzione.equals(Function)) {
						inizioFunzione= tokens.nextToken();
					}
				
				
					if(inizioFunzione.contains(nomeMetodo)&& ( nomeMetodo.length()==inizioFunzione.length() || inizioFunzione.charAt(nomeMetodo.length())==' ' || inizioFunzione.charAt(nomeMetodo.length())=='(' ) ) {
						while(!inizioFunzione.contains(")")) {
							inizioFunzione = inizioFunzione +" "+ tokens.nextToken();
						}
					
						int pa=inizioFunzione.indexOf("(");
						int pc=inizioFunzione.indexOf(")");
						
		
						String argomenti=inizioFunzione.substring(pa+1,pc);
					
					
						String protArg="(";
						if(argomenti.trim().length()!=0) {
						
							StringTokenizer argTok=  new StringTokenizer(argomenti,",");
					
							while(argTok.hasMoreTokens()) {
						
								StringTokenizer argomentiSingoli  = new StringTokenizer (argTok.nextToken());
						
								if(argomentiSingoli.hasMoreTokens()) {
									String as= argomentiSingoli.nextToken();
									if(as.trim().equalsIgnoreCase("uint")) as="uint256";
									protArg=protArg+as+",";
								}
						
							}
						
						}
					
						if(protArg.charAt(protArg.length()-1)==',') protArg= protArg.substring(0,protArg.length()-1);
						protArg= protArg+")";
					
						String prototipoDaConvertire= (this.nomeMetodo+protArg).trim();
					
						String hash= org.web3j.crypto.Hash.sha3String(prototipoDaConvertire).substring(0,10);
					
						this.duplicati.add(hash);
					
				
					}
				}
			}
		
		} catch (IOException e) {
			System.out.println("Errore lettura file HashFunction");
			}
		
		
		
		
	}
	
	public static HashFunction factoryHash(String nomeFile, String nomeMetodo) {
		return new HashFunction(nomeFile,nomeMetodo);
	}
	
	
	public static void main(String[]  args) throws FileNotFoundException {
		
		//System.out.println(factoryHash("C:\\Users\\orazi\\Desktop\\Contract\\Dataset\\0x00000000e86b5156e8fd624255bf7a6d722a8f1f_ARIYAX.sol","transferFrom").result);
	
		
	}
	


}
