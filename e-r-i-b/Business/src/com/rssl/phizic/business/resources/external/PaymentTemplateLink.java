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
 * Ссылка на шаблон платежа (на шаблон во внешней системе)
 * Бывает полной (с содержанием экземпляра шаблона)
 * или пустой (без содержания экземпляра шаблона)
 */
public class PaymentTemplateLink extends ExternalResourceLinkBase
{
	public static final String CODE_PREFIX = "template";
	private BillingServiceProvider serviceProvider;

	private GatePaymentTemplate template = null;

	/**
	 * Код получателя в МБ
	 */
	private String payerFieldName;

	/**
	 * Идентификатор плательщика в МБ
	 */
	private String payerFieldValue;

	/**
	 * Строка с описанием проблемы, если с шаблоном есть проблемы
	 */
	private String error;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает поставщика услуги, относящегося к шаблону платежа
	 * @return поставщик услуги
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
	 * Возврашает шаблон платежа, на который ссылается ссылка
	 * @return шаблон платежа
	 * либо null, если шаблон не проставлен
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
		// TODO: может быть, тут стоит удалять все темплейт-линки пользователя
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
