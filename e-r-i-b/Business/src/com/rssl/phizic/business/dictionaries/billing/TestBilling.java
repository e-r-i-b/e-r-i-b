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
		//������ id ������������ �������
		Long billingId = 23L;
		BillingService billingService = new BillingService();
		//�������� �������� �������� �� ��
		Billing billing = billingService.getById(billingId);
		//���������, ��� � �������� ���� ���������� � �������������
		assertNotNull(billing.restoreRouteInfo());
		//��������� ��������, ������������ ������� restoreRouteInfo() � ���������, ��� ����, ��� ��� �� ����� null
		String infoBilling = billing.restoreRouteInfo();
		assertNotNull(infoBilling);
		//��������� ��������, ������������ ������� removeRouteInfo()
		String removedBillingRouteInfo = billing.removeRouteInfo();
		//���������� ��� �������� �� ���������, ���������� ������� restoreRouteInfo()
		assertEquals(infoBilling, removedBillingRouteInfo);
		//���������, ��� restoreRouteInfo() ������� ���������� � �������������
		assertNull(billing.restoreRouteInfo());
		//��������� � �������� ���������� � ������������� � ���������, ��� ����, ��� ��� �� ����� null
		billing.storeRouteInfo(removedBillingRouteInfo);
		assertNotNull(billing.restoreRouteInfo());

		//�������� ������ �������� ��������
		//������� � �������������� ��������
		BillingImpl billingImpl = new BillingImpl();
		billingImpl.setName(billing.getName());
		billingImpl.setSynchKey(billing.getSynchKey());
		//���������, ��� � �������� ���� ���������� � �������������
		assertNotNull(billingImpl.restoreRouteInfo());
		//��������� ��������, ������������ ������� restoreRouteInfo() � ���������, ��� ����, ��� ��� �� ����� null
		String infoBillingImpl = billing.restoreRouteInfo();
		assertNotNull(infoBillingImpl);
		//��������� ��� ����, ��� �������� ������������ ������� restoreRouteInfo() ��� ����������� � �������� �������� ���������
		assertEquals(infoBilling, infoBillingImpl);
		//��������� ��������, ������������ ������� removeRouteInfo()
		String removedBillingImplRouteInfo = billingImpl.removeRouteInfo();
		//���������� ��� �������� �� ���������, ���������� ������� restoreRouteInfo()
		assertEquals(infoBillingImpl, removedBillingImplRouteInfo);
		//���������, ��� restoreRouteInfo() ������� ���������� � �������������
		assertNull(billingImpl.restoreRouteInfo());
		//��������� � �������� ���������� � ������������� � ���������, ��� ����, ��� ��� �� ����� null
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

