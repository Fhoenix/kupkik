package com.kupkik.messages;

public enum HandlerMessagesEnum {

	   ERROR ("ErrorMessage"),
	   SUCCESS ("SuccessMessage"),
	   WARNING ("WarningMessage");

	    private final String httpRequestString;       

	    private HandlerMessagesEnum(String s) {
	    	httpRequestString = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false : httpRequestString.equals(otherName);
	    }

	    public String toString(){
	       return httpRequestString;
	    }


}
