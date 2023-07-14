package com.rssl.phizic.messaging.mail.rsalarm;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: kosyakov $
 * @ $Revision: 3224 $
 */
interface Property
{
	public	int			getId		();
	public	int			getSize		();
	public	int			getType		();
	public	int			getSupp		();
	public	int			getFlags	();
	public	int			toNumber	();
	public	String		toString	();
	public	String		toString	(int base);
}