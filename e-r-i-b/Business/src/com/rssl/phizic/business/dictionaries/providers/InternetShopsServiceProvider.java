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
	private String formName; // имя платежной формы
	private boolean sendChargeOffInfo; // флажок "Передавать в интернет-магазин информацию о карте списания"
	private boolean availableMobileCheckout; //поддерживается ли Mobile Checkout.
	private boolean facilitator; //является ли поставщик фасилитатором

	/**
	 * имя системы
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
	 * урл для обратной связи
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
	 * URL перехода после оплаты
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
	 * Действия после оплаты
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
	 * тип поставщика: внешний
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
	 * Признак федерального поставщика внешних услуг всегда включен.
	 * Если указано «1» - федеральный.
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
	 * Проверка заказа перед оплатой
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
	 * имя платежной формы
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
