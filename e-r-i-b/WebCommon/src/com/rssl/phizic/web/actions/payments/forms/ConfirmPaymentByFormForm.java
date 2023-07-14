package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * @author Evgrafov
 * @ created 06.12.2005
 * @ $Author: gulov $
 * @ $Revision: 78233 $
 */

public class ConfirmPaymentByFormForm extends EditDocumentForm
{
    private String       html;
    private String       formName;
	private String       guid;
	private ConfirmStrategyType confirmStrategyType; //TODO Возможно стоит вынести в отдельный интерфейс
	private ConfirmStrategy confirmStrategy;
	private String stateDescription;
	private TemplateDocument template;
	private String templateName;
	private String sessionData;
	private boolean anotherStrategyAvailable;
	private boolean fns = false;    // признак платежа из ФНС
	private boolean external = false; // платеж из внешней ссылки
	private String providerName;  // название поставщика (нужно для вывода заголовка в jsp)
	private boolean hasCapButton = false;
	private boolean avaliablePayment;
	private boolean guest; // признак того, что клиент - гость
	private boolean mobileBankExist;

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public boolean isHasCapButton()
	{
		return hasCapButton;
	}

	public void setHasCapButton(boolean hasCapButton)
	{
		this.hasCapButton = hasCapButton;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getSessionData()
	{
		return sessionData;
	}

	public void setSessionData(String sessionData)
	{
		this.sessionData = sessionData;
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public void setTemplate(TemplateDocument template)
	{
		this.template = template;
	}

	public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

	@Deprecated
    public String getFormName()
    {
        return getForm();
    }

	@Deprecated
    public void setFormName(String formName)
    {
        setForm(formName);
    }

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public String getStateDescription()
	{
		return stateDescription;
	}

	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}

	public boolean getFns()
	{
		return fns;
	}

	public void setFns(boolean fns)
	{
		this.fns = fns;
	}

	public boolean getExternal()
	{
		return external;
	}

	public void setExternal(boolean external)
	{
		this.external = external;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public boolean isAvaliablePayment()
	{
		return avaliablePayment;
	}

	public void setAvaliablePayment(boolean avaliablePayment)
	{
		this.avaliablePayment = avaliablePayment;
	}

	public boolean isGuest()
	{
		return guest;
	}

	public void setGuest(boolean guest)
	{
		this.guest = guest;
	}

	public void setMobileBankExist(boolean mobileBankExist)
	{
		this.mobileBankExist = mobileBankExist;
	}

	public boolean isMobileBankExist()
	{
		return mobileBankExist;
	}
}
