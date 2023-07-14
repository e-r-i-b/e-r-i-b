package com.rssl.phizic.business.basket.accountingEntity;

import com.rssl.phizic.common.types.basket.AccountingEntityType;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Объект учета - квартира
 */
public class FlatAccountingEntity extends AccountingEntityBase
{
	public AccountingEntityType getType()
	{
		return AccountingEntityType.FLAT;
	}
}
