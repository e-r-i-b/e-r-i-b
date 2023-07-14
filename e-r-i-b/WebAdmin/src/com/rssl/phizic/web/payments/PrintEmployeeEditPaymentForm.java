package com.rssl.phizic.web.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.AutoSubscriptionPaymentBase;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: vagin
 * @ created: 03.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class PrintEmployeeEditPaymentForm extends ViewEmployeePaymentForm
{
	//оригинальные данные платежа(те что изменяем)
	private Map<String,Object> originalData = new HashMap<String,Object>();
	//измененные данные платежа
	private Map<String,Object> changedData = new HashMap<String,Object>();

	public Map<String, Object> getOriginalData()
	{
		return Collections.unmodifiableMap(originalData);
	}

	private void setOriginalData(AutoSubscriptionClaim originalPayment)
	{
		originalData.put("from-resource",originalPayment.getCardNumber());
		originalData.put("long-offer-event-type",originalPayment.getExecutionEventType());
		originalData.put("auto-sub-friendy-name",originalPayment.getFriendlyName());
		originalData.put("auto-sub-next-pay-date", originalPayment.getNextPayDate());
		if(originalPayment.getSumType()!= SumType.BY_BILLING)
		{
			originalData.put("destination-amount", originalPayment.getAmount());
		}
		else
		{
			originalData.put("auto-sub-invoice-max-decimal",originalPayment.getMaxSumWritePerMonth());
		}
	}

	public Map<String, Object> getChangedData()
	{
		return Collections.unmodifiableMap(changedData);
	}

	private void setChangedData(AutoSubscriptionClaim changedPayment)
	{
		changedData.put("from-resource",notEquals(originalData.get("from-resource"), changedPayment.getCardNumber())?changedPayment.getCardNumber():null);
		changedData.put("long-offer-event-type",notEquals(originalData.get("long-offer-event-type"),changedPayment.getExecutionEventType())?changedPayment.getExecutionEventType():null);
		changedData.put("auto-sub-friendy-name",notEquals(originalData.get("auto-sub-friendy-name"),changedPayment.getFriendlyName())?changedPayment.getFriendlyName():null);
		changedData.put("auto-sub-next-pay-date",notEquals(originalData.get("auto-sub-next-pay-date"), changedPayment.getNextPayDate())?changedPayment.getNextPayDate():null);

		if(changedPayment.getSumType()!= SumType.BY_BILLING)
		{
			changedData.put("destination-amount",notEquals(originalData.get("destination-amount"), changedPayment.getAmount())?changedPayment.getAmount():null);
		}
		else
		{
			changedData.put("auto-sub-invoice-max-decimal",notEquals(originalData.get("auto-sub-invoice-max-decimal"),changedPayment.getMaxSumWritePerMonth())?changedPayment.getMaxSumWritePerMonth():null);
		}
	}

	public void setFormData(AutoSubscriptionClaim changedPayment, AutoSubscriptionClaim originalPayment)
	{
		//порядок вызовов обязателен!
		setOriginalData(originalPayment);
		setChangedData(changedPayment);
	}

	private static boolean notEquals(final Object original,final Object changed)
	{
		if(original == null && changed == null)
			return false;
		if(original == null || changed == null)
			return true;
		if(original instanceof Calendar && changed instanceof Calendar)
			return (DateHelper.clearTime((Calendar)original)).compareTo(DateHelper.clearTime((Calendar)changed)) != 0;
		return !original.equals(changed);
	}

}
