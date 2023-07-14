package com.rssl.phizic.business.ext.sbrf.mobilebank;

/**
 * @author Erkin
 * @ created 22.06.2010
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Шаблон платежа, из которого можно сделать SMS-шаблон
 */
public class PaymentTemplateShortcut
{
	private Long id;

	private String name;

	private String recipientCode;

	private String payerCode;

	private Long providerId;

	private String keyFieldName;

	/**
	 * SMS-шаблон, соответствующий этому шаблону платежа
	 */
	private SmsCommand smsTemplate;


	public PaymentTemplateShortcut(TemplateDocument template) throws BusinessException
	{
		id   = template.getId();
		name = template.getTemplateInfo().getName();

		BillingServiceProvider provider = (BillingServiceProvider) TemplateHelper.getTemplateProvider(template);
		providerId    = provider.getId();
		recipientCode = provider.getMobilebankCode();


		List<FieldDescription> fieldDescriptions = provider.getFieldDescriptions();
		if (CollectionUtils.isEmpty(fieldDescriptions) || fieldDescriptions.size() != 1)
		{
			throw new BusinessException("Поставщик услуг id = " + providerId + " должен содержать только одно ключевое дополнительное поле.");
		}

		FieldDescription fieldDescription = fieldDescriptions.get(0);
		if (!fieldDescription.isKey())
		{
			throw new BusinessException("Поставщик услуг id = " + providerId + " должен содержать только одно ключевое дополнительное поле.");
		}

		try
		{
			ExtendedAttribute attribute = template.getExtendedAttributes().get(fieldDescription.getExternalId());
			if (attribute == null)
			{
				throw new BusinessException("Внешние идентификаторы ключевого доп. поля поставщика providerId = " + providerId + " шаблона документа templateId = " + id + " не совпадают.");
			}

			keyFieldName = attribute.getName();
			payerCode    = attribute.getStringValue();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	public String getKeyFieldName()
	{
		return keyFieldName;
	}

	public void setKeyFieldName(String keyFieldName)
	{
		this.keyFieldName = keyFieldName;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRecipientCode()
	{
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode)
	{
		this.recipientCode = recipientCode;
	}

	public String getPayerCode()
	{
		return payerCode;
	}

	public void setPayerCode(String payerCode)
	{
		this.payerCode = payerCode;
	}

	public SmsCommand getSmsTemplate()
	{
		return smsTemplate;
	}

	public void setSmsTemplate(SmsCommand smsTemplate)
	{
		this.smsTemplate = smsTemplate;
	}
}
