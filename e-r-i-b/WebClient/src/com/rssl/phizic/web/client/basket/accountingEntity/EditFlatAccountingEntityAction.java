package com.rssl.phizic.web.client.basket.accountingEntity;

import com.rssl.phizic.common.types.basket.AccountingEntityType;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Создание/редактирование объекта учета - квартира
 */
public class EditFlatAccountingEntityAction extends EditAccountEntityActionBase
{
	@Override
	protected AccountingEntityType getType()
	{
		return AccountingEntityType.FLAT;
	}
}
