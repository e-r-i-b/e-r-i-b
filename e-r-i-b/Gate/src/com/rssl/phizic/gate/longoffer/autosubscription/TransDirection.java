package com.rssl.phizic.gate.longoffer.autosubscription;

/**
 * @author tisov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public enum TransDirection
{
	TCC_OWN,          //перевод между своими картами
	TCC_P2P,          //перевод стороннему лицу на карту СБ РФ внутри одного ТБ
	TCC_P2P_FOREIGN;  //перевод стороннему лицу на карту СБ РФ в другой ТБ
}
