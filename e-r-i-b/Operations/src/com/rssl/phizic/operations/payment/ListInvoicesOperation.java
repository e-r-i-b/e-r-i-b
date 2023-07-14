package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.common.types.basket.InvoicesSubscriptionsPromoMode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author tisov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 * Операция для выведения списка инвойсов( форма "счета к оплате")
 */

public class ListInvoicesOperation extends OperationBase implements ListEntitiesOperation
{
	public static final String DEFAULT_UNAVAILABLE_INVOICE_TEXT = "В списке показаны не все Ваши счета к оплате. Информация по ним временно недоступна.";

	List<FakeInvoice> data = new ArrayList<FakeInvoice>();
	private BigDecimal sumValue = BigDecimal.ZERO;
	private String bannerText = "";
	private Long hiddenInvoicesNumber;


	public void initialize(Set<String> errorCollector, boolean show) throws BusinessException, DocumentException
	{
		InvoiceConfig invoiceConfig = ConfigFactory.getConfig(InvoiceConfig.class);

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<FakeInvoice> allData = personData.getClientInvoiceData(errorCollector, true).getInvoices();

		int length = Math.min(allData.size(), getLimit(show, invoiceConfig));
		data = allData.subList(0, length);

		if (show)
		{
			for (FakeInvoice item:data)
			{
				sumValue = sumValue.add(item.getSum());
			}
		}

		InvoicesSubscriptionsPromoMode promoMode = personData.getPromoMode();
		if (promoMode == InvoicesSubscriptionsPromoMode.NOT_EXIST)
			bannerText = invoiceConfig.getNoInvoicesText();
		else if (promoMode == InvoicesSubscriptionsPromoMode.ONLY_AUTO)
			bannerText = invoiceConfig.getOnlyAutoInvoicesText();

		if (!show)
		{
			hiddenInvoicesNumber = Long.valueOf(allData.size()- data.size());
		}
	}

	/**
	 * Инициализация операции для mAPI
	 * @param errorCollector - коллектор ошибок
	 * @param invoicesCount - максимальное кол-во счетов которые неоходимо вернуть
	 */
	public void initializeMobile(Set<String> errorCollector, Long invoicesCount) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<FakeInvoice> allData = personData.getClientInvoiceData(errorCollector, true).getInvoices();
		data = (invoicesCount != null && invoicesCount.intValue() < allData.size()) ? allData.subList(0, invoicesCount.intValue()) : allData;
	}

	private Integer getLimit(boolean show, InvoiceConfig invoiceConfig)
	{
		if (show)
			return invoiceConfig.getInvoiceLimit();
		else
			return invoiceConfig.getFirstInvoicesLimit();
	}

	public List<FakeInvoice> getData()
	{
		return data;
	}

	public void setSumValue(BigDecimal sumValue)
	{
		this.sumValue = sumValue;
	}

	public BigDecimal getSumValue()
	{
		return sumValue;
	}

	public String getBannerText()
	{
		return bannerText;
	}

	public void setBannerText(String bannerText)
	{
		this.bannerText = bannerText;
	}

	public Long getHiddenInvoicesNumber()
	{
		return hiddenInvoicesNumber;
	}

	public void setHiddenInvoicesNumber(Long hiddenInvoicesNumber)
	{
		this.hiddenInvoicesNumber = hiddenInvoicesNumber;
	}
}
