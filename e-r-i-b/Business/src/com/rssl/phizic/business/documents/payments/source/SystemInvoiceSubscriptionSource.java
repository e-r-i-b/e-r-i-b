package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.payments.forms.meta.NewDocumentHandler;
import com.rssl.phizic.common.types.client.LoginType;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Автоматическое создание заявки для подписки на инвойсы
 * @author niculichev
 * @ created 30.09.14
 * @ $Author$
 * @ $Revision$
 */
public class SystemInvoiceSubscriptionSource extends NewAutoInvoiceSubscriptionSource
{
	//Количество дней, через которое состоится следующий платёж
	private final int DEFAULT_PLUS_DAY = 30;
	/**
	 * Коструктор
	 * @param subscription сущность подписки на инвойсы
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public SystemInvoiceSubscriptionSource(InvoiceSubscription subscription) throws BusinessException, BusinessLogicException
	{
		super(subscription, CreationType.system, CreationSourceType.ordinary);
	}

	protected Long getNodeTemporaryNumber()
	{
		return null;
	}

	protected LoginType getLoginType()
	{
		return null;
	}

	protected boolean isEmployeeCreate()
	{
		return false;
	}

	protected boolean isAnonymous()
	{
		return false;
	}

	protected Map<String, String> getInitialValues(InvoiceSubscription subscription) throws BusinessException
	{
		Calendar nextPayDay = new GregorianCalendar();
		subscription.setPayDate(nextPayDay);
		Map<String, String> res = super.getInitialValues(subscription);
		res.put("isAutoSubExecuteNow", "true");
		nextPayDay.add(Calendar.DAY_OF_MONTH, DEFAULT_PLUS_DAY);
		res.put("nextPayDayOfMonthInvoice", Integer.toString(nextPayDay.get(Calendar.DAY_OF_MONTH)));
		return res;
	}

	protected NewDocumentHandler getNewDocumentHandler() throws DocumentException, DocumentLogicException
	{
		return new NewDocumentHandler()
		{
			protected boolean isAnonymous()
			{
				return false;
			}
		};
	}
}
