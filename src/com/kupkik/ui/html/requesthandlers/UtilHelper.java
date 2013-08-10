package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.List;

import com.kupkik.model.UserWithPassword;

public class UtilHelper {

	
	public static List<String> convertStringArrayToListArray(String[] stringArray){
		List<String> returnList = new ArrayList<String>();
		
		for (String item : stringArray) {
			returnList.add(item);
		}
		return returnList;
		
	}

}
