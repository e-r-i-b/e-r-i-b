package com.rssl.phizic.business;

import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.gate.einvoicing.ShopProfile;

import java.util.*;

/**
 * @author tisov
 * @ created 01.06.15
 * @ $Author$
 * @ $Revision$
 * ������ �� �������� ������� ��� ����������� ����������
 */
public class EmployeeInvoiceData extends InvoiceDataAbstractBase
{
	private ActivePerson person;                                                                                   //������� ��� ������� ���� �������
	private Map<Calendar, List<FakeInvoice>> delayedInvoicesByDate = new TreeMap<Calendar, List<FakeInvoice>>();   //���� ��� ��������� ������ ���������� �������� �� ����
	private List<Calendar> delayedInvoicesDates = new ArrayList<Calendar>();                                       //������ ��� ���������� ��������
	private Set<String> errorCollector = new TreeSet<String>();                                                    //��������� ��� ����������� ������
	private boolean showAllCommonInvoices;                                                                         //����� �� �������� ��� ������������ �������
	private boolean showAllDelayedInvoices;                                                                        //����� �� �������� ��� ���������� �������
	private int hiddenInvoicesCount;                                                                               //���������� ������� ��������
	private int delayedInvoicesCount;                                                                              //���������� ���������� ��������

	public EmployeeInvoiceData(ActivePerson person, boolean extendedInvoicesList, boolean showAllDelayedInvoices) throws BusinessException
	{
		this.person = person;
		this.showAllCommonInvoices = extendedInvoicesList;
		this.showAllDelayedInvoices = showAllDelayedInvoices;
		this.refreshInvoices(errorCollector);
	}

	@Override
	protected String getDefaultUnavailableInvoiceText()
	{
		return "";
	}

	private int getViewLimit()
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		if (this.showAllCommonInvoices)
		{
			return config.getInvoiceLimit();
		}
		else
		{
			return config.getFirstInvoicesLimit();
		}
	}

	private void refreshNewInvoices(Set<String> errorCollector) throws BusinessException
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		int limit = config.getInvoiceLimit();

		this.invoices = refreshData(limit, errorCollector, InvoiceStatus.NEW);

		int viewLimit = getViewLimit();

		if (this.invoices.size() > viewLimit)
		{
			this.hiddenInvoicesCount = this.invoices.size() - viewLimit;
			this.invoices = this.invoices.subList(0, viewLimit);
		}
		else
		{
			this.hiddenInvoicesCount = 0;
		}
	}

	private void refreshDelayedInvoices(Set<String> errorCollector) throws BusinessException
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		int limit;
		if (this.showAllDelayedInvoices)
		{
			limit = config.getInvoiceLimit();
		}
		else
		{
			limit = config.getDelayedInvoicesInitialLimit();
		}

		List<FakeInvoice> delayedInvoices = refreshData(limit, errorCollector, InvoiceStatus.DELAYED);

		this.delayedInvoicesCount = delayedInvoices.size();

		for (FakeInvoice item : delayedInvoices)
		{
			putToDelayedInvoicesMap(item);
		}

		fillDatesList();
	}

	private void putToDelayedInvoicesMap(FakeInvoice invoice)
	{
		List<FakeInvoice> list = this.delayedInvoicesByDate.get(invoice.getDelayedDate());
		if (list == null)
		{
			list = new ArrayList<FakeInvoice>();
			this.delayedInvoicesByDate.put(invoice.getDelayedDate(), list);
		}
		list.add(invoice);
	}

	private void fillDatesList()
	{
		this.delayedInvoicesDates.addAll(this.delayedInvoicesByDate.keySet());
		Collections.sort(this.delayedInvoicesDates);
	}

	@Override
	synchronized void refreshInvoices(Set<String> errorCollector) throws BusinessException
	{
		refreshNewInvoices(errorCollector);
		refreshDelayedInvoices(errorCollector);
	}

	@Override
	protected List<ShopProfile> getShopProfiles() throws BusinessException
	{
		return ShopHelper.get().getProfileHistory(this.person);
	}

	@Override
	protected ActivePerson getPerson()
	{
		return this.person;
	}

	@Override protected boolean isRemindersManagementAllowed()
	{
		return true;
	}

	/**
	 * @return ������������� ������ �� �������� ���������� �������� �� ���� ������������
	 */
	public Map<Calendar, List<FakeInvoice>> getDelayedInvoicesByDate()
	{
		return delayedInvoicesByDate;
	}

	/**
	 * @return ������ ��� ������������
	 */
	public List<Calendar> getDelayedInvoicesDates()
	{
		return delayedInvoicesDates;
	}

	/**
	 * @return ����� �� ������� ���������� �������� ������ ��������
	 */
	public boolean isShowAllCommonInvoices()
	{
		return showAllCommonInvoices;
	}

	/**
	 * @return ����� �� ������� ���������� ������ ���������� ��������
	 */
	public boolean isShowAllDelayedInvoices()
	{
		return showAllDelayedInvoices;
	}

	/**
	 * @return ���������� ������� ��������
	 */
	public int getHiddenInvoicesCount()
	{
		return this.hiddenInvoicesCount;
	}

	/**
	 * @return ��������� ������
	 */
	public Set<String> getErrors()
	{
		return errorCollector;
	}

	/**
	 * @return �������� �� ���������� ������������ ��������� ������ �� ����������� (���������� �� ������������� ������������� ��������)
	 */
	public boolean getDelayedInvoicesCountEqualsLimit()
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		return this.delayedInvoicesCount == config.getDelayedInvoicesInitialLimit();
	}
}
