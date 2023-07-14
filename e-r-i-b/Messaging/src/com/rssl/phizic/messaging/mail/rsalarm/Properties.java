package com.rssl.phizic.messaging.mail.rsalarm;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: kosyakov $
 * @ $Revision: 3224 $
 */
interface Properties
{
	public	Property	getProperty			(int i);
	public	Property	getProperty			(int id, int type);
	public	Property	getPropertyByVal	(int id, String name);
	public	Property	getPropertyByVal	(int id, int val);

	public	int			count				();
}