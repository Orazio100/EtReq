package contratti;

public class Metriche {
	
	private String SLOC;
	private String avgAssignment;
	private String avgBlankLines;
	private String avgCommas;
	private String avgComments;
	private String avgComparisons;
	private String avgIdentifiersLength;
	private String avgConditionals;
	private String avgIndentationLength;
	private String avgKeywords;
	private String avgLineLength;
	private String avgLoops;
	private String avgNumberOfIdentifiers;
	private String avgNumbers;
	private String avgOperators;
	private String avgParenthesis;
	private String avgPeriods;
	private String avgSpaces;
	private String maxIdentifiersLength;
	private String maxIndentation;
	private String maxKeywords;
	private String maxLineLength;
	private String maxNumberOfIdentifiers;
	private String maxNumbers;
	private String maxChar;
	private String maxWords;
	
	
	public Metriche(String SLOC,String avgAssignment, String avgBlankLines, String avgCommas, String avgComments,
			String avgComparisons, String avgIdentifiersLength, String avgConditionals, String avgIndentationLength,
			String avgKeywords, String avgLineLength, String avgLoops, String avgNumberOfIdentifiers, String avgNumbers,
			String avgOperators, String avgParenthesis, String avgPeriods, String avgSpaces,
			String maxIdentifiersLength, String maxIndentation, String maxKeywords, String maxLineLength,
			String maxNumberOfIdentifiers, String maxNumbers, String maxChar, String maxWords) {
	
		this.SLOC=SLOC;
		this.avgAssignment = avgAssignment;
		this.avgBlankLines = avgBlankLines;
		this.avgCommas = avgCommas;
		this.avgComments = avgComments;
		this.avgComparisons = avgComparisons;
		this.avgIdentifiersLength = avgIdentifiersLength;
		this.avgConditionals = avgConditionals;
		this.avgIndentationLength = avgIndentationLength;
		this.avgKeywords = avgKeywords;
		this.avgLineLength = avgLineLength;
		this.avgLoops = avgLoops;
		this.avgNumberOfIdentifiers = avgNumberOfIdentifiers;
		this.avgNumbers = avgNumbers;
		this.avgOperators = avgOperators;
		this.avgParenthesis = avgParenthesis;
		this.avgPeriods = avgPeriods;
		this.avgSpaces = avgSpaces;
		this.maxIdentifiersLength = maxIdentifiersLength;
		this.maxIndentation = maxIndentation;
		this.maxKeywords = maxKeywords;
		this.maxLineLength = maxLineLength;
		this.maxNumberOfIdentifiers = maxNumberOfIdentifiers;
		this.maxNumbers = maxNumbers;
		this.maxChar = maxChar;
		this.maxWords = maxWords;
	}


	@Override
	public String toString() {
		return "\n\t\t\t\tSLOC=: " + SLOC + "\n\t\t\t\tavgAssignment=" + avgAssignment + "\n\t\t\t\tavgBlankLines=" + avgBlankLines
				+ "\n\t\t\t\tavgCommas=" + avgCommas + "\n\t\t\t\tavgComments=" + avgComments + "\n\t\t\t\tavgComparisons=" + avgComparisons
				+ "\n\t\t\t\tavgIdentifiersLength=" + avgIdentifiersLength + "\n\t\t\t\tavgConditionals=" + avgConditionals
				+ "\n\t\t\t\tavgIndentationLength=" + avgIndentationLength + "\n\t\t\t\tavgKeywords=" + avgKeywords + "\n\t\t\t\tavgLineLength="
				+ avgLineLength + "\n\t\t\t\tavgLoops=" + avgLoops + "\n\t\t\t\tavgNumberOfIdentifiers=" + avgNumberOfIdentifiers
				+ "\n\t\t\t\tavgNumbers=" + avgNumbers + "\n\t\t\t\tavgOperators=" + avgOperators + "\n\t\t\t\tavgParenthesis=" + avgParenthesis
				+ "\n\t\t\t\tavgPeriods=" + avgPeriods + "\n\t\t\t\tavgSpaces=" + avgSpaces + "\n\t\t\t\tmaxIdentifiersLength="
				+ maxIdentifiersLength + "\n\t\t\t\tmaxIndentation=" + maxIndentation + "\n\t\t\t\tmaxKeywords=" + maxKeywords
				+ "\n\t\t\t\tmaxLineLength=" + maxLineLength + "\n\t\t\t\tmaxNumberOfIdentifiers=" + maxNumberOfIdentifiers
				+ "\n\t\t\t\tmaxNumbers=" + maxNumbers + "\n\t\t\t\tmaxChar=" + maxChar + "\n\t\t\t\tmaxWords=" + maxWords;
	}
	
	
	public String toCSV() {
		
		 return SLOC+";"+avgAssignment+";"+avgBlankLines+";"+ avgCommas+";"+avgComments+";"+
		 avgComparisons+";"+ avgIdentifiersLength+";"+avgConditionals+";"+ avgIndentationLength+";"+
		 avgKeywords+";"+avgLineLength+";"+avgLoops+";"+avgNumberOfIdentifiers+";"+avgNumbers+";"+
		 avgOperators+";"+avgParenthesis+";"+avgPeriods+";"+avgSpaces+";"+
		 maxIdentifiersLength+";"+maxIndentation+";"+maxKeywords+";"+maxLineLength+";"+
		 maxNumberOfIdentifiers+";"+maxNumbers+";"+maxChar+";"+maxWords;
		
	}
	
	

	
	
	
	

}
