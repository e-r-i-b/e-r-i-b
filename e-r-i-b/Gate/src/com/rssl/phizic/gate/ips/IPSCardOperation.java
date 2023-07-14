package com.rssl.phizic.gate.ips;

import com.rssl.phizic.gate.clients.Client;

/**
 * @author Erkin
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция по карте
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public interface IPSCardOperation extends com.rssl.phizic.gate.bankroll.CardOperation
{
	/**
	 * @return клиент, который провёл операцию
	 */
	Client getOwner();

	/**
	 * @return MCC-код операции
	 */
	long getMccCode();

	/**
	 * @return признак "операция с наличными / с безналичными средствами"
	 */
	boolean isCash();

	String getAuthCode();
}
