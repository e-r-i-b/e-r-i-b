package com.rssl.phizic.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.validators.ERMBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация платежной задачи "Оплата по шаблону"
 * @author Rtischeva
 * @created 19.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TemplatePaymentTaskImpl extends PaymentTaskBase implements TemplatePaymentTask
{
	private transient TemplateDocument template; //шаблон
	private String cardAlias; //алиас карты списания
	private BigDecimal amount; //сумма списания

	private transient BankrollProductLink cardLink;

	@Override
	protected String getFormName()
	{
		return template.getFormType().getName();
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(PaymentFieldKeys.TEMPLATE_ID_KEY, String.valueOf(template.getId()));

		if (cardLink != null)
		{
			map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
			map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, cardLink.getCode());
			map.put("fromResourceCurrency", cardLink.getCurrency().getCode());
		}
		else
		{
			try
			{
				PaymentAbilityERL templateResourceLink = template.getChargeOffResourceLink();
				//попытка оплаты по шаблону с "потерянным" ресурсом списания
				if (templateResourceLink == null)
				{
					String templateName = template.getTemplateInfo().getName();
					Money templateAmount = amount != null ? new Money(amount, template.getChargeOffCurrency()) : template.getExactAmount();
					//алиас карты утерян, считаем что это был дефолтный автоматический алиас (последние 4 цифры)
					String templateCardAlias = StringUtils.right(template.getChargeOffCard(), 4);
					throw new UserErrorException(messageBuilder.buildTemplateCardExpired(templateName, templateAmount, templateCardAlias));
				}

				map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, templateResourceLink.getCode());
				map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, templateResourceLink.getResourceType().name());
				map.put("fromResourceCurrency", templateResourceLink.getCurrency().getCode());
			}
			catch (BusinessException e)
			{
				throw new InternalErrorException(e);
			}
			catch (GateException e)
			{
				throw new InternalErrorException(e);
			}
		}

		if (amount != null)
		{
			try
			{
				//если клиент указал в запросе сумму, кладем ее на форму в зависимости от типа формы в шаблоне
				FormType templateFormType = template.getFormType();

				//шаблон для оплаты поставщикам услуг
				if (templateFormType == FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER || templateFormType == FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER)
				{
					List<Field> extendedFields = template.getExtendedFields();

					for (Field field : extendedFields)
					{
						if (field.isMainSum())
						{
							map.put(field.getExternalId(), amount.toPlainString());
							break;
						}
					}
				}
				//шаблон для оплаты кредита
				else if (templateFormType == FormType.LOAN_PAYMENT)
				{
					map.put(PaymentFieldKeys.AMOUNT, amount.toPlainString());
				}
				//другие виды шаблонов
				else
				{
					map.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, AbstractPaymentDocument.CHARGE_OFF_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE);
					map.put(PaymentFieldKeys.SELL_AMOUNT, amount.toPlainString());
					map.put(PaymentFieldKeys.BUY_AMOUNT, amount.toPlainString());
				}
			}
			catch (DocumentException e)
			{
				throw new InternalErrorException(e);
			}
		}

		return new MapValuesSource(map);
	}

	@Override
	protected FieldValuesSource getValidateFieldValuesSource()
	{
		try
		{
			return new CompositeFieldValuesSource(requestFieldValuesSource, new DocumentFieldValuesSource(documentSource.getMetadata(), documentSource.getDocument()), new TemplateFieldValueSource(documentSource.getDocument()));
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (FormException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public void setTemplate(TemplateDocument template)
	{
		this.template = template;
	}

	@Override
	protected NewDocumentSource createNewDocumentSource()
	{
		PaymentEngine paymentEngine = getModule().getPaymentEngine();
		try
		{
			CreationType creationType = paymentEngine.getDocumentCreationType();
			return new NewDocumentSource(template, creationType, new ERMBTemplateValidator(), new OwnerTemplateValidator());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	@Override
	protected EditDocumentOperation createEditOperation()
	{
		try
		{
			String serviceKey = documentSource.getMetadata().getName();
			FormType formType = template.getFormType();
			if (formType == FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER  || formType == FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER)
			{
				EditServicePaymentOperation operation = createOperation(EditServicePaymentOperation.class, serviceKey);
				operation.initialize(documentSource, template.getReceiverInternalId());
				return operation;
			}
			return super.createEditOperation();
		}
		catch (TemporalBusinessException e)
		{
			String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже";
			throw new UserErrorException(new TextMessage(exceptionMessage), e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	@Override
	protected void setExtendedDocumentFields(BusinessDocument document)
	{
		super.setExtendedDocumentFields(document);
		document.setTemplateName(template.getTemplateInfo().getName());
	}

	@Override
	protected boolean needSendMBOperCode(BusinessDocument document)
	{
		if (!ConfigFactory.getConfig(ErmbConfig.class).needSendPaymentSmsNotification())
			return false;
		try
		{
			if (FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName()) && DocumentHelper.isIQWaveDocument(document))
				return true;
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public void setCardAlias(String cardAlias)
	{
		this.cardAlias = cardAlias;
	}

	public String getCardAlias()
	{
		return cardAlias;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.TEMPLATE_PAYMENT;
	}

	public void setCardLink(BankrollProductLink cardLink)
	{
		this.cardLink = cardLink;
	}

	@Override
	protected boolean needCheckCardBeforeConfirm(BusinessDocument document)
	{
		return FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName());
	}
}
