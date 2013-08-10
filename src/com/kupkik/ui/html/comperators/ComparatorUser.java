package com.kupkik.ui.html.comperators;

import java.util.Comparator;

import com.kupkik.model.User;

	public class ComparatorUser implements Comparator<User>
	{
		@Override
		public int compare( User pUser1, User pUser2 )
		{
			return pUser1.getSurname().compareTo(pUser2.getSurname());
		}
	}

