package com.kupkik.messages;

public enum HandlerMessages {

	   ERROR ("ErrorMessage"),
	   SUCCESS ("SuccessMessage"),
	   WARNING ("WarningMessage");

	    private final String httpRequestString;       

	    private HandlerMessages(String s) {
	    	httpRequestString = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false : httpRequestString.equals(otherName);
	    }

	    public String toString(){
	       return httpRequestString;
	    }


}
