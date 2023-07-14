package com.rssl.phizic.business.dictionaries.billing;

import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author akrenev
 * @ created 21.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class TestBilling extends RSSLTestCaseBase
{
	public void test() throws Exception
	{
		//Задаем id биллинговаой системы
		Long billingId = 23L;
		BillingService billingService = new BillingService();
		//Получаем сущность биллинга из БД
		Billing billing = billingService.getById(billingId);
		//Проверяем, что в сущности есть информация о маршрутизации
		assertNotNull(billing.restoreRouteInfo());
		//Сохраняем значение, возвращаемое методом restoreRouteInfo() и проверяет, тот факт, что оно не равно null
		String infoBilling = billing.restoreRouteInfo();
		assertNotNull(infoBilling);
		//Сохраняем значение, возвращаемое методом removeRouteInfo()
		String removedBillingRouteInfo = billing.removeRouteInfo();
		//Сравниваем это значение со значением, полученным методом restoreRouteInfo()
		assertEquals(infoBilling, removedBillingRouteInfo);
		//Проверяем, что restoreRouteInfo() удаляет информацию о маршрутизации
		assertNull(billing.restoreRouteInfo());
		//Сохраняем в сущности информацию о маршрутизации и проверяем, тот факт, что оно не равно null
		billing.storeRouteInfo(removedBillingRouteInfo);
		assertNotNull(billing.restoreRouteInfo());

		//Эмуляция работы гейтовой сущности
		//Создаем и инициализируем сущность
		BillingImpl billingImpl = new BillingImpl();
		billingImpl.setName(billing.getName());
		billingImpl.setSynchKey(billing.getSynchKey());
		//Проверяем, что в сущности есть информация о маршрутизации
		assertNotNull(billingImpl.restoreRouteInfo());
		//Сохраняем значение, возвращаемое методом restoreRouteInfo() и проверяет, тот факт, что оно не равно null
		String infoBillingImpl = billing.restoreRouteInfo();
		assertNotNull(infoBillingImpl);
		//Проверяем тот факт, что значения возвращаемые методом restoreRouteInfo() для биллинговой и гейтовой сущности одинаковы
		assertEquals(infoBilling, infoBillingImpl);
		//Сохраняем значение, возвращаемое методом removeRouteInfo()
		String removedBillingImplRouteInfo = billingImpl.removeRouteInfo();
		//Сравниваем это значение со значением, полученным методом restoreRouteInfo()
		assertEquals(infoBillingImpl, removedBillingImplRouteInfo);
		//Проверяем, что restoreRouteInfo() удаляет информацию о маршрутизации
		assertNull(billingImpl.restoreRouteInfo());
		//Сохраняем в сущности информацию о маршрутизации и проверяем, тот факт, что оно не равно null
		billingImpl.storeRouteInfo(removedBillingImplRouteInfo);
		assertNotNull(billingImpl.restoreRouteInfo());
	}
}

class BillingImpl implements com.rssl.phizic.gate.dictionaries.billing.Billing, Routable
{
	private String name;
	private Comparable synchKey;

	public String getName()
	{
		return name;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public void storeRouteInfo(String info)
	{
		synchKey =IDHelper.storeRouteInfo((String) synchKey, info);
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo((String)synchKey);
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo((String) synchKey);
		setSynchKey(IDHelper.restoreOriginalId((String) synchKey));
		return info;
	}
}

