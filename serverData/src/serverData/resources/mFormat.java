package serverData.resources;

class mFormat {
	public boolean bold;
	public boolean italic;
	public boolean underline;
	public boolean strikeThrough;
	
	public mFormat(boolean isBold, boolean isItalic, boolean isUnderline, boolean isStrikeThrough){
		bold = isBold;
		italic = isItalic;
		underline = isUnderline;
		strikeThrough = isStrikeThrough;
	}
	public mFormat(){
		bold = false;
		italic = false;
		underline = false;
		strikeThrough = false;
	}
}