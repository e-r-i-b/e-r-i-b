package com.rssl.phizic.logging.logon;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class NullLogonLogWriter implements LogonLogWriter
{
	public static final LogonLogWriter INSTANCE = new NullLogonLogWriter();

	public void writeFindProfile(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo){}

	public void writeLogon(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo){}
}
