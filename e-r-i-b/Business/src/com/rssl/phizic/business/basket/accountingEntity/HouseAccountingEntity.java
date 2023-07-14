package com.rssl.phizic.business.basket.accountingEntity;

import com.rssl.phizic.common.types.basket.AccountingEntityType;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ОБъект учета - дом
 */
public class HouseAccountingEntity extends AccountingEntityBase
{
	public AccountingEntityType getType()
	{
		return AccountingEntityType.HOUSE;
	}
}
