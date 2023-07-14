package com.rssl.phizic.gate.clients;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public enum CancelationType
{
	//по запросу клиента
	client_request,
	//по запросу администратора
	admin_request,
	//по запросу администратора без взимания платы
	without_charge
}
