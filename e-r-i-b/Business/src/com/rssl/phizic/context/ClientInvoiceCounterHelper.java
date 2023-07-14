package com.rssl.phizic.context;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ClientInvoiceData;
import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author vagin
 * @ created 17.08.14
 * @ $Author$
 * @ $Revision$
 * ������ ������ � ��������������� ������� �� ������� �������� �� �������� "������� � ��������" �������� �������.
 */
public class ClientInvoiceCounterHelper
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * ��������� �������� ����� ��������.
	 * @param invoice - ������ ������� ���� ������������� ��������.
	 */
	public synchronized static void decreaseInvoiceCounterNew(Invoice invoice) throws BusinessException
	{
		ClientInvoiceData invoiceData = PersonContext.getPersonDataProvider().getPersonData().getClientInvoiceData(null, true);
		for(FakeInvoice fake: invoiceData.getInvoices())
		{
			if(fake.getId()!=null && fake.getId().equals(invoice.getId()) && fake.getUuid() == null)
			{
				if(fake.getIsNew())
				{
					fake.setIsNew(false);
					invoiceData.setCounterNew(invoiceData.getCounterNew()-1);
				}
				break;
			}
		}
	}

	/**
	 * ������� ����������� ������ � �������� �������.
	 */
	public synchronized static void resetCounterValue() throws BusinessException
	{
		ClientInvoiceData invoiceData = PersonContext.getPersonDataProvider().getPersonData().getClientInvoiceData(null,  false);
		invoiceData.setLastUpdateDate(null);
	}

	/**
	 * ��������� �� ���� ��� ���������� �����������
	 * @param id - ������������ �����������
	 * @return true - ���������
	 * @throws BusinessException
	 */
	public synchronized static Calendar getResidualReminderDate(Long id) throws BusinessException
	{
		ClientInvoiceData invoiceData = PersonContext.getPersonDataProvider().getPersonData().getClientInvoiceData(null, false);
		List<FakeInvoice> invoices = invoiceData.getInvoices();

		if(CollectionUtils.isEmpty(invoices))
			return null;

		for(FakeInvoice fakeInvoice: invoices)
		{
			if("reminder".equals(fakeInvoice.getType()) && fakeInvoice.getId().equals(id))
				return fakeInvoice.getCreatingDate();
		}

		return null;
	}

	/**
	 * �������� ����������� ������ � ������ ���� � � ��.
	 * @param orderId - ������������� ������
	 * @throws BusinessException
	 */
	public synchronized static void updateShopOrdersStoredData(String orderId) throws BusinessException
	{
		try
		{
			//��������� ����������� ������
			ClientInvoiceData invoiceData = PersonContext.getPersonDataProvider().getPersonData().getClientInvoiceData(null, true);
			for(FakeInvoice fake: invoiceData.getInvoices())
			{
				if(fake.getUuid().equals(orderId))
				{
					if(fake.getIsNew())
					{
						//������ ������� � ��.
						GateSingleton.getFactory().service(ShopOrderService.class).markViewed(orderId);
						fake.setIsNew(false);
						//������ ��� ����� �� ���� ����������� � ����������� ������, ���� � ��.
						GateSingleton.getFactory().service(CacheService.class).clearShopOrderCache(orderId);
						invoiceData.setCounterNew(invoiceData.getCounterNew()-1);
					}
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
