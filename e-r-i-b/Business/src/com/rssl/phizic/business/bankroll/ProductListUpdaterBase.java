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
 * ������������ �� ������ ���� ���������
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
		// 1. ��������, ��� ������� ����-�� �����
		if (!isProductUsed())
			return;

		// (2) ���������� �������������� ���������� => ���������
		if (forceUpdate)
		{
			markOutdated();
			return;
		}

		Calendar lastUpdate = getLastUpdate();
		long lifetime = getProductListLifetime();

		// (3) ������ �� ���� �� ���������� => ���������
		if (lastUpdate == null)
			markOutdated();

		// (4) ���� "�����" ������ �������� ����� => ���������
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
