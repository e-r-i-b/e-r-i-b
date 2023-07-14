package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.common.forms.*;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.strategy.SmsDocumentValidationStrategy;
import com.rssl.phizic.business.ext.sbrf.mobilebank.PaymentTemplateUpdate;
import com.rssl.phizic.business.ext.sbrf.mobilebank.SmsCommand;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erkin
 * @ created 17.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class SmsTemplateForm extends EditPaymentForm
{
	private String phoneCode;

	private String cardCode;

	private Long updateId;

	private PaymentTemplateUpdate update;

	private CardLink cardLink;

	private List<SmsCommand> smsCommands;

	private Long selectedTemplateId;

	private boolean needSelectProvider = true;

	///////////////////////////////////////////////////////////////////////////

	public Long getSelectedTemplateId()
	{
		return selectedTemplateId;
	}

	public void setSelectedTemplateId(Long selectedTemplateId)
	{
		this.selectedTemplateId = selectedTemplateId;
	}

	public String getPhoneCode()
	{
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode)
	{
		this.phoneCode = phoneCode;
	}

	public String getPhoneNumber()
	{
		return update.getPhoneNumber();
	}

	public String getCardCode()
	{
		return cardCode;
	}

	public void setCardCode(String cardCode)
	{
		this.cardCode = cardCode;
	}

	public Long getUpdateId()
	{
		return updateId;
	}

	public void setUpdateId(Long updateId)
	{
		this.updateId = updateId;
	}

	public PaymentTemplateUpdate getUpdate()
	{
		return update;
	}

	public void setUpdate(PaymentTemplateUpdate update)
	{
		this.update = update;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public List<SmsCommand> getSmsCommands()
	{
		return smsCommands;
	}

	public void setSmsCommands(List<SmsCommand> smsCommands)
	{
		this.smsCommands = smsCommands;
	}

	/**
	 * @return  нужно ли выбирать поставщика на первом шаге создания шаблона
	 */
	public boolean isNeedSelectProvider()
	{
		return needSelectProvider;
	}

	public void setNeedSelectProvider(boolean needSelectProvider)
	{
		this.needSelectProvider = needSelectProvider;
	}

	/**
	 * @return ID платежа
	 */
	public Long getId()
	{
		// возвращаем null, т.к. платёжа нет
		return null;
	}

	public static Form getForm(List<FieldDescription> keyFields)
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName("<SmsTemplateForm>");
		formBuilder.addFields(buildFields(keyFields));
		return formBuilder.build();
	}

	private static List<Field> buildFields(List<FieldDescription> keyFields)
	{
		List<Field> fields = new ArrayList<Field>();
		for (com.rssl.phizic.gate.payments.systems.recipients.Field field : keyFields)
		{
			if (field.isKey())
			{
				fields.add(ExtendedFieldBuilderHelper.adaptField(field, mobilebankFieldBuilderFactory));
			}
			else
			{
				fields.add(ExtendedFieldBuilderHelper.adaptField(field));
			}
		}
		return fields;
	}

	private static class SpecificFieldBuilder extends FieldBuilder
	{
		private final String validateMode;

		private SpecificFieldBuilder(String validateMode)
		{
			this.validateMode = validateMode;
		}

		public void addValidators(FieldValidator... validators)
		{
			for (FieldValidator validator : validators)
				validator.setMode(validateMode);
			super.addValidators(validators);
		}
	}

	private static final FieldBuilderFactory mobilebankFieldBuilderFactory = new FieldBuilderFactory()
	{
		public FieldBuilder createFieldBuilder()
		{
			return new SpecificFieldBuilder(SmsDocumentValidationStrategy.MODE);
		}
	};
}
