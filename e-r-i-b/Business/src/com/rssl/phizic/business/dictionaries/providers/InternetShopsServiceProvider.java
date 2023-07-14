package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.einvoicing.OrderKind;
import com.rssl.phizic.gate.einvoicing.ShopProvider;
import com.rssl.phizic.utils.BeanHelper;

/**
 * @author Mescheryakova
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */

public class InternetShopsServiceProvider  extends BillingServiceProviderBase implements ShopProvider
{
	private boolean checkOrder;
	private String codeRecipientSBOL;
	private String url;
	private String backUrl;
	private boolean afterAction;
	private String formName; // ��� ��������� �����
	private boolean sendChargeOffInfo; // ������ "���������� � ��������-������� ���������� � ����� ��������"
	private boolean availableMobileCheckout; //�������������� �� Mobile Checkout.
	private boolean facilitator; //�������� �� ��������� �������������

	/**
	 * ��� �������
	 */
	public String getCodeRecipientSBOL()
	{
		return codeRecipientSBOL;
	}

	public void setCodeRecipientSBOL(String codeRecipientSBOL)
	{
		this.codeRecipientSBOL = codeRecipientSBOL;
	}

	/**
	 * ��� ��� �������� �����
	 */
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}


	/**
	 * URL �������� ����� ������
	 */
	public String getBackUrl()
	{
		return backUrl;
	}

	public void setBackUrl(String backUrl)
	{
		this.backUrl = backUrl;
	}

	/**
	 * �������� ����� ������
	 */
	public boolean isAfterAction()
	{
		return afterAction;
	}

	public void setAfterAction(boolean afterAction)
	{
		this.afterAction = afterAction;
	}

	/**
	 * ��� ����������: �������
	 */
	public ServiceProviderType getType()
	{
		return ServiceProviderType.EXTERNAL;
	}

	public String getServiceName()
	{
		return null;
	}

	public void updateFrom(DictionaryRecord that)
	{
		((InternetShopsServiceProvider) that).setId(this.id);
		BeanHelper.copyPropertiesFull(this, that);
	}

	/**
	 * ������� ������������ ���������� ������� ����� ������ �������.
	 * ���� ������� �1� - �����������.
	 * @return 0/1
	 */
	public boolean isFederal()
	{
		return true;
	}

	public void setFederal(boolean federal)
	{
		super.setFederal(true);
	}

	/**
	 * �������� ������ ����� �������
	 * @return
	 */
	public boolean isCheckOrder()
	{
		return checkOrder;
	}

	public void setCheckOrder(boolean checkOrder)
	{
		this.checkOrder = checkOrder;
	}

	/*
	 * ��� ��������� �����
	 */
	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public boolean isSendChargeOffInfo()
	{
		return sendChargeOffInfo;
	}

	public void setSendChargeOffInfo(boolean sendChargeOffInfo)
	{
		this.sendChargeOffInfo = sendChargeOffInfo;
	}

	public boolean isAvailableMobileCheckout()
	{
		return availableMobileCheckout;
	}

	public void setAvailableMobileCheckout(boolean availableMobileCheckout)
	{
		this.availableMobileCheckout = availableMobileCheckout;
	}

	public boolean isFacilitator()
	{
		return facilitator;
	}

	public void setFacilitator(boolean facilitator)
	{
		this.facilitator = facilitator;
	}
}
