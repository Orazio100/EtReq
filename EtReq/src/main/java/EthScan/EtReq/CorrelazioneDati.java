package EthScan.EtReq;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.math3.stat.correlation.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.*;

import contratti.GestoreContratti;
import contratti.Metriche;

public class CorrelazioneDati {
	
	public static final int DIM=10000;
	private double [][] matrice;
	private HashMap<String,Integer> dizionario;
	private double risultati[];
	private int numeroIstanze;
	
	public CorrelazioneDati(){
		risultati= new double[27];
		matrice=new double[DIM][27];
		numeroIstanze=0;
		dizionario= new HashMap<String,Integer>();
		dizionario.put("Avg-Gas-Cost",0 );
		dizionario.put("SLOC", 1);
		dizionario.put("Avg-Assignment", 2);
		dizionario.put("Avg-blank-lines", 3);
		dizionario.put("Avg-commas", 4);
		dizionario.put("Avg-comments", 5);
		dizionario.put("Avg-comparisons", 6);
		dizionario.put("Avg-Identifiers-Length", 7);
		dizionario.put("Avg-conditionals", 8);
		dizionario.put("Avg-indentation-length", 9);
		dizionario.put("Avg-keywords", 10);
		dizionario.put("Avg-line-length", 11);
		dizionario.put("Avg-loops", 12);
		dizionario.put("Avg-number-of-identifiers", 13);
		dizionario.put("Avg-numbers", 14);
		dizionario.put("Avg-operators", 15);
		dizionario.put("Avg-parenthesis", 16);
		dizionario.put("Avg-periods", 17);
		dizionario.put("Avg-spaces", 18);
		dizionario.put("Max-Identifiers-Length", 19);
		dizionario.put("Max-indentation", 20);
		dizionario.put("Max-keywords", 21);
		dizionario.put("Max-line-length",22 );
		dizionario.put("Max-number-of-identifiers", 23);
		dizionario.put("Max-numbers",24 );
		dizionario.put("Max-char",25 );
		dizionario.put("Max-words",26 );
		
	
		
		
		
		
	}
	
	
	public void caricaDati(String pathFile) throws IOException, ParseException {
		
		
		
		Reader in = new FileReader(pathFile);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';').parse(in);
		
		for (CSVRecord record : records) {
			
			String numeroTransazioni= record.get("Transaction-Number");
			String GasTotale= record.get("Gas-Used-Total");
			
			if(numeroTransazioni.equalsIgnoreCase("0")) continue;
	    
			
			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			
			double CostoGasMedio =  (format.parse(GasTotale.trim()).doubleValue())/(format.parse(numeroTransazioni.trim()).doubleValue()) ;
			
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-Gas-Cost")]=CostoGasMedio;
			this.matrice[this.numeroIstanze][this.dizionario.get("SLOC")]=format.parse(record.get("SLOC")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-Assignment")]=format.parse(record.get("Avg-Assignment")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-blank-lines")]=format.parse(record.get("Avg-blank-lines")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-commas")]=format.parse(record.get("Avg-commas")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-comments")]=format.parse(record.get("Avg-comments")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-comparisons")]=format.parse(record.get("Avg-comparisons")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-Identifiers-Length")]=format.parse(record.get("Avg-Identifiers-Length")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-conditionals")]=format.parse(record.get("Avg-conditionals")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-indentation-length")]=format.parse(record.get("Avg-indentation-length")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-keywords")]=format.parse(record.get("Avg-keywords")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-line-length")]=format.parse(record.get("Avg-line-length")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-loops")]=format.parse(record.get("Avg-loops")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-number-of-identifiers")]=format.parse(record.get("Avg-number-of-identifiers")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-numbers")]=format.parse(record.get("Avg-numbers")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-operators")]=format.parse(record.get("Avg-operators")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-parenthesis")]=format.parse(record.get("Avg-parenthesis")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-periods")]=format.parse(record.get("Avg-periods")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Avg-spaces")]=format.parse(record.get("Avg-spaces")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-Identifiers-Length")]=format.parse(record.get("Max-Identifiers-Length")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-indentation")]=format.parse(record.get("Max-indentation")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-keywords")]=format.parse(record.get("Max-keywords")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-line-length")]=format.parse(record.get("Max-line-length")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-number-of-identifiers")]=format.parse(record.get("Max-number-of-identifiers")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-numbers")]=format.parse(record.get("Max-numbers")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-char")]=format.parse(record.get("Max-char")).doubleValue();
			this.matrice[this.numeroIstanze][this.dizionario.get("Max-words")]=format.parse(record.get("Max-words")).doubleValue();
	
			this.numeroIstanze++;

		}
		
		
	}
	
	public void copiaMatrice() {
		
		double temp [][]= new double[this.numeroIstanze][27];
		
		for(int i=0;i<this.numeroIstanze;i++) {
			for(int j=0; j<27;j++) {
				temp[i][j]= this.matrice[i][j];
			}
		}
		
		this.matrice=temp;
		
		
	}
	
	
	public void calcolaCorrelazione() {
		
		
		
		double [] gasCost = new double [this.numeroIstanze];
		for(int j=0;j<this.numeroIstanze;j++)
			gasCost[j]=this.matrice[j][0];
		
		
		for(int i=1;i<=26;i++) {
		double [] temp = new double [this.numeroIstanze];
		for(int j=0;j<this.numeroIstanze;j++)
			temp[j]=this.matrice[j][i];
		
		this.risultati[i]= new SpearmansCorrelation().correlation(temp,gasCost);

		
		
		}
		
		
	}
	
	@Override
	public String toString() {
		String ret="Numero di istanze: "+this.numeroIstanze+"\n\n";
		for(String s :this.dizionario.keySet()) {
			if(s.equals("Avg-Gas-Cost")) continue;
			ret= ret+ s+": "+this.risultati[this.dizionario.get(s)]+"\n";
		}
		return ret;
		
		
		
	}
	
	
	
	public static void main(String[]   args) throws IOException, ParseException {
		
		CorrelazioneDati cr = new CorrelazioneDati();
		cr.caricaDati("C:\\Users\\orazi\\Desktop\\R1\\CSVSimple.csv");
		cr.calcolaCorrelazione();
		System.out.println(cr.toString());
		/*
		 * 
		 * Avg-Identifiers-Length: 0.19565093115673515
Avg-line-length: 0.20487133575794544
Avg-loops: 0.22138271570509527
Max-indentation: 0.14087926875611595
Max-numbers: 0.3652690965749191
Max-number-of-identifiers: 0.3623675884565197
SLOC: 0.367126721121235
Max-Identifiers-Length: 0.34382835056289546
Avg-operators: 0.1767278146821242
Max-char: 0.3280747674731981
Max-line-length: 0.25870781923514446
Avg-comments: 0.15666571756186914
Avg-periods: 0.1350914178148601
Avg-numbers: 0.28671193432939285
Avg-parenthesis: -0.14015376722904163
Avg-blank-lines: 0.21339210256629246
Avg-conditionals: 0.0802366088592647
Avg-number-of-identifiers: 0.18507122449741828
Max-keywords: 0.14247574123688972
Avg-keywords: -0.28737317262491485
Avg-indentation-length: 0.24031593599815163
Avg-Assignment: 0.12998856940865533
Avg-commas: 0.21809217181366394
Avg-comparisons: 0.037383885761707245
Max-words: 0.3200993846227112
Avg-spaces: 0.03648778545458078
		 */
		
		
	}

}
