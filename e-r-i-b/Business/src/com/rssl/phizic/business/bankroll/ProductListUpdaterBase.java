package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Актуализатор по одному виду продуктов
 */
abstract class ProductListUpdaterBase implements ProductListUpdater
{
	private final BankrollConfig bankrollConfig = getBankrollConfig();

	private final ProductChangeSet changeSet;

	///////////////////////////////////////////////////////////////////////////

	protected ProductListUpdaterBase(ProductChangeSet changeSet)
	{
		this.changeSet = changeSet;
	}

	protected ProductChangeSet getChangeSet()
	{
		return changeSet;
	}

	protected abstract BankrollConfig getBankrollConfig();

	protected abstract Calendar getLastUpdate();

	protected abstract void markOutdated();

	public void updateProductList(boolean forceUpdate)
	{
		// 1. Убедимся, что продукт кому-то нужен
		if (!isProductUsed())
			return;

		// (2) Необходимо принудительное обновление => обновляем
		if (forceUpdate)
		{
			markOutdated();
			return;
		}

		Calendar lastUpdate = getLastUpdate();
		long lifetime = getProductListLifetime();

		// (3) Список ни разу не обновлялся => обновляем
		if (lastUpdate == null)
			markOutdated();

		// (4) Срок "жизни" списка продукта вышел => обновляем
		else
		{
			Calendar now = changeSet.getTimestamp();
			Calendar nextUpdate = DateHelper.addSeconds(lastUpdate, (int) lifetime);
			if (nextUpdate.before(now))
				markOutdated();
		}
	}

	private boolean isProductUsed()
	{
		return bankrollConfig.isProductUsed();
	}

	private long getProductListLifetime()
	{
		return bankrollConfig.getProductListLifetime();
	}
}
