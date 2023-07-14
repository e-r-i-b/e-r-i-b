package com.rssl.phizic.business.documents.payments;

/**
 * “ипы шинных автоплатежей(автоподписок)
 * @author niculichev
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */
public enum AutoSubType
{
	/*
	регул€рный по фиксированной сумме
	 */
	ALWAYS,

	/*
	 регул€рный по выставленному счету
	  */
	INVOICE,

	/*
	  пороговый
	  */
	THRESHOLD;
}
