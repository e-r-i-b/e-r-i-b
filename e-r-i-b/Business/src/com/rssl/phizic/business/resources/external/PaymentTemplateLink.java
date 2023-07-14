package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;

/**
 * @author Erkin
 * @ created 11.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ������ ������� (�� ������ �� ������� �������)
 * ������ ������ (� ����������� ���������� �������)
 * ��� ������ (��� ���������� ���������� �������)
 */
public class PaymentTemplateLink extends ExternalResourceLinkBase
{
	public static final String CODE_PREFIX = "template";
	private BillingServiceProvider serviceProvider;

	private GatePaymentTemplate template = null;

	/**
	 * ��� ���������� � ��
	 */
	private String payerFieldName;

	/**
	 * ������������� ����������� � ��
	 */
	private String payerFieldValue;

	/**
	 * ������ � ��������� ��������, ���� � �������� ���� ��������
	 */
	private String error;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ���������� ������, ������������ � ������� �������
	 * @return ��������� ������
	 */
	public BillingServiceProvider getServiceProvider()
	{
		return serviceProvider;
	}

	public void setServiceProvider(BillingServiceProvider serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}

	/**
	 * ���������� ������ �������, �� ������� ��������� ������
	 * @return ������ �������
	 * ���� null, ���� ������ �� ����������
	 */
	public GatePaymentTemplate getTemplate()
	{
		return template;
	}

	public void setTemplate(GatePaymentTemplate template)
	{
		this.template = template;
	}

	public Object getValue()
	{
		return template;
	}

	public void reset()
	{
		// TODO: ����� ����, ��� ����� ������� ��� ��������-����� ������������
	}

	public String getPayerFieldName()
	{
		return payerFieldName;
	}

	public void setPayerFieldName(String payerFieldName)
	{
		this.payerFieldName = payerFieldName;
	}

	public String getPayerFieldValue()
	{
		return payerFieldValue;
	}

	public void setPayerFieldValue(String payerFieldValue)
	{
		this.payerFieldValue = payerFieldValue;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$paymentTemplateName:" + this.getId();
	}
}
