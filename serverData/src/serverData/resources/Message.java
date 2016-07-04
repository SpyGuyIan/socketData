package serverData.resources;

import java.awt.Color;
import java.util.ArrayList;

class mChar {
	public Color color;
	public mFormat format;
	public char charValue;

	
	public mChar(char ch, Color c, mFormat f){
		color = c;
		format = f;
		charValue = ch;
	}
}


public class Message {

	public ArrayList<mChar> mCharList = new ArrayList<mChar>();

	public Message(String message, Color c, mFormat format) {
		for (int i = 0; i < message.length(); i++) {
			mCharList.add(new mChar(message.charAt(i), c, format));// for the console
		}
	}

	public Message append(Message msg){
		mCharList.addAll(msg.mCharList);
		return this;
	}
	
	public String getText() {
		String text = "";
		for(mChar c : mCharList) {
			text += c.charValue;
		}
		return text;
	}

}
