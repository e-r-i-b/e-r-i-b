package com.rssl.phizic.business.limits.link;

/**
 * @author khudyakov
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */
public enum LimitPaymentsType
{
	PHYSICAL_EXTERNAL_ACCOUNT_PAYMENT_LINK,          //оплата на счет частному лицу в другой банк
	PHYSICAL_EXTERNAL_CARD_PAYMENT_LINK,             //оплата на карту частному лицу в другой банк
	PHYSICAL_INTERNAL_PAYMENT_LINK,                  //оплата на счет/карту частному лицу внутнри —берЅанка
	INTERNAL_SOCIAL_TRANSFER_LINK,                   //перевод между своими счетами на соц. карту
	CONVERSION_OPERATION_PAYMENT_LINK,               //конверсионные операции
	JURIDICAL_PAYMENT_LINK                           //оплата юридическому лицу без учета конверсии
}
