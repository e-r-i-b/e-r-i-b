package com.rssl.phizic.gate.depo;

/**
 * @author lukina
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */

public enum SecurityOperationType
{
	DEPOSITARY_OPERATION, //депозитарные операции
	DEPOSIT_OPERATION,    //залоговые операции
	ACCOUNT_OPERATION,    //только учет и хранение
	CLIENT_OPERATION,     //операция клиента
	TRADE_OPERATION       //торговые операции
}
