package com.rssl.phizic.business;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import com.rssl.phizic.security.PermissionUtil;

import java.util.*;

/**
 * @author vagin
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 * Данные о клиенских инвойсах.
 */
public class ClientInvoiceData extends InvoiceDataAbstractBase
{
	private static final String DEFAULT_UNAVAILABLE_INVOICE_TEXT = "В списке показаны не все Ваши счета к оплате. Информация по ним временно недоступна.";

	private Calendar lastUpdateDate;

	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	boolean needRefresh()
	{
		InvoiceConfig invoiceConfig = ConfigFactory.getConfig(InvoiceConfig.class);

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -invoiceConfig.getInvoicesTimeToLive());
		return lastUpdateDate == null || now.compareTo(lastUpdateDate) > 0;
	}

	@Override
	protected void updateRefreshInfo()
	{
		this.lastUpdateDate = Calendar.getInstance();
	}

	@Override
	protected boolean isRemindersManagementAllowed()
	{
		return PermissionUtil.impliesService("ReminderManagment");
	}

	@Override
	protected String getDefaultUnavailableInvoiceText()
	{
		return DEFAULT_UNAVAILABLE_INVOICE_TEXT;
	}

	@Override
	synchronized void refreshInvoices(Set<String> errorCollector) throws BusinessException
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		int limit = config.getInvoiceLimit();

		this.invoices = refreshData(limit, errorCollector, InvoiceStatus.NEW, InvoiceStatus.DELAYED);
	}

	@Override
	protected List<ShopProfile> getShopProfiles() throws BusinessException
	{
		return ShopHelper.get().getProfileHistory(AuthenticationContext.getContext());
	}

	@Override
	protected ActivePerson getPerson()
	{
		return PersonHelper.getContextPerson();
	}
}
