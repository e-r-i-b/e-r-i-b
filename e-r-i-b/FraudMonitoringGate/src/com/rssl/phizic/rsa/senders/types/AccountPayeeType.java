package com.rssl.phizic.rsa.senders.types;

/**
 * Тип счета получателя платежа
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum AccountPayeeType
{
	BILLER,                         //используется при оплате счетов между клиентом и контрагентами
	PERSONAL_ACCOUNT,               //используется для перевода средств между личными счетам
}
