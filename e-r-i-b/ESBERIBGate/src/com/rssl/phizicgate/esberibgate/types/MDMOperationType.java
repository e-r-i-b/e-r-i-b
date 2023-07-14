package com.rssl.phizicgate.esberibgate.types;

/**
 * @author egorova
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum MDMOperationType
{
	CustAddRq, // оповещение MDM о создании клиента в ЕРИБ
	CustModRq, // оповещение MDM об изменении данных клиента в ЕРИБ
	CustModNf // оповещение ЕРИБ об изменении данных клиента в MDM
}
