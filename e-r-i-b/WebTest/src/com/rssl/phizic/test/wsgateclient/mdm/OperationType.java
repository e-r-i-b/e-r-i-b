package com.rssl.phizic.test.wsgateclient.mdm;

/**
 * @author egorova
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

public enum OperationType
{
	CustAddRq, // оповещение MDM о создании клиента в ЕРИБ
	CustModRq, // оповещение MDM об изменении данных клиента в ЕРИБ
	CustModNf // оповещение ЕРИБ об изменении данных клиента в MDM
	
}
