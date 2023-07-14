package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityBase;
import com.rssl.phizic.business.basket.config.ServiceCategory;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Balovtsev
 * @since 13.10.14.
 */
public class MapiViewAccountingEntitiesForm extends EditFormBase
{
	private List<AccountingEntityBase>       accountingEntities = new ArrayList<AccountingEntityBase>();
	private Map<Long, List<ServiceCategory>> serviceCategories  = new TreeMap<Long, List<ServiceCategory>>();

	public List<AccountingEntityBase> getAccountingEntities()
	{
		return Collections.unmodifiableList(accountingEntities);
	}

	public void setAccountingEntities(List<AccountingEntityBase> accountingEntities)
	{
		this.accountingEntities.addAll(accountingEntities);
	}

	public Map<Long, List<ServiceCategory>> getServiceCategories()
	{
		return Collections.unmodifiableMap(serviceCategories);
	}

	public void setServiceCategories(Map<Long, List<ServiceCategory>> serviceCategories)
	{
		this.serviceCategories.putAll(serviceCategories);
	}
}