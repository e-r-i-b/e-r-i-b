package com.rssl.phizic.common.types;

/**
 * @author Evgrafov
* @ created 03.01.2007
* @ $Author: miklyaev $
* @ $Revision: 54531 $
*/
public enum ConfirmStrategyType
{
	none,
	card,
	crypto,
	sbrf_custom,
	captcha,
	sms,
	push,
	composite,
	conditionComposite,
	need,    // нужно для вывода в аудите до подтверждения документа статуса подтверждения "Нуждается"
	plasticCardClient,  //клиент проводит свой пластиковой картой по считывающему устройству
	cap,
	DENY
}
