package com.kupkik.messages;

public enum MessageHandlerEnum {

	   ERROR ("ErrorMessage"),
	   SUCCESS ("SuccessMessage"),
	   WARNING ("WarningMessage");

	    private final String httpRequestString;       

	    private MessageHandlerEnum(String s) {
	    	httpRequestString = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false : httpRequestString.equals(otherName);
	    }

	    public String toString(){
	       return httpRequestString;
	    }


}
