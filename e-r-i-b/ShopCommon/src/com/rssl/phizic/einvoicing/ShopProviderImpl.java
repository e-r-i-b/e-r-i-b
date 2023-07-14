package com.rssl.phizic.einvoicing;

import com.rssl.phizic.gate.einvoicing.ShopProvider;

/**
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopProviderImpl implements ShopProvider
{
	private Long id;
	private String backUrl;
	private String codeRecipientSBOL;
	private String formName;
	private String url;
	private boolean afterAction;
	private boolean availableMobileCheckout;
	private boolean checkOrder;
	private boolean facilitator;
	private final boolean federal = true;
	private boolean sendChargeOffInfo;

	public boolean isAfterAction()
	{
		return afterAction;
	}

	public void setAfterAction(boolean afterAction)
	{
		this.afterAction = afterAction;
	}

	public boolean isAvailableMobileCheckout()
	{
		return availableMobileCheckout;
	}

	public void setAvailableMobileCheckout(boolean availableMobileCheckout)
	{
		this.availableMobileCheckout = availableMobileCheckout;
	}

	public String getBackUrl()
	{
		return backUrl;
	}

	public void setBackUrl(String backUrl)
	{
		this.backUrl = backUrl;
	}

	public boolean isCheckOrder()
	{
		return checkOrder;
	}

	public void setCheckOrder(boolean checkOrder)
	{
		this.checkOrder = checkOrder;
	}

	public String getCodeRecipientSBOL()
	{
		return codeRecipientSBOL;
	}

	public void setCodeRecipientSBOL(String codeRecipientSBOL)
	{
		this.codeRecipientSBOL = codeRecipientSBOL;
	}

	public boolean isFacilitator()
	{
		return facilitator;
	}

	public void setFacilitator(boolean facilitator)
	{
		this.facilitator = facilitator;
	}

	public boolean isFederal()
	{
		return federal;
	}

	public void setFederal(boolean federal){}

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isSendChargeOffInfo()
	{
		return sendChargeOffInfo;
	}

	public void setSendChargeOffInfo(boolean sendChargeOffInfo)
	{
		this.sendChargeOffInfo = sendChargeOffInfo;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
