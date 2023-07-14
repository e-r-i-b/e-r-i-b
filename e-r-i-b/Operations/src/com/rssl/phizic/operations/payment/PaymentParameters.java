package com.rssl.phizic.operations.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 22.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �������
 */
public class PaymentParameters
{
	/**
	 * ID �������
	 */
	private Long paymentId;

	/**
	 * ID ������� �������
	 */
	private Long paymentTemplateId;

	/**
	 * ������� ��� �����
	 *  (RurPayJur � �.�.)
	 */
	private String paymentFormName;

	/**
	 * ID ����������
	 */
	private Long providerId;

	/**
	 * true, ���� ������������ �����
	 */
	private boolean personalPayment = false;

	private boolean copy = false;

	private String fromResource;

	/**
	 * �������� ����� �������
	 */
	private final Map<String, Object> paymentFields
			= new HashMap<String, Object>();

	///////////////////////////////////////////////////////////////////////////

	public Long getPaymentId()
	{
		return paymentId;
	}

	public void setPaymentId(Long paymentId)
	{
		this.paymentId = paymentId;
	}

	public Long getPaymentTemplateId()
	{
		return paymentTemplateId;
	}

	public void setPaymentTemplateId(Long paymentTemplateId)
	{
		this.paymentTemplateId = paymentTemplateId;
	}

	public String getPaymentFormName()
	{
		return paymentFormName;
	}

	public void setPaymentFormName(String paymentFormName)
	{
		this.paymentFormName = paymentFormName;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	public boolean isPersonalPayment()
	{
		return personalPayment;
	}

	public void setPersonalPayment(boolean personalPayment)
	{
		this.personalPayment = personalPayment;
	}

	public boolean isCopy()
	{
		return copy;
	}

	public void setCopy(boolean copy)
	{
		this.copy = copy;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public Map<String, Object> getPaymentFields()
	{
		return Collections.unmodifiableMap(paymentFields);
	}

	public void setPaymentFields(Map<String, Object> paymentFields)
	{
		this.paymentFields.clear();
		for (Map.Entry<String, Object> entry : paymentFields.entrySet()) {
			if (entry.getValue() != null)
				this.paymentFields.put(entry.getKey(), entry.getValue());
		}
	}
}
