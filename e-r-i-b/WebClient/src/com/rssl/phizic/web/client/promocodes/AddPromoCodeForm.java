package com.rssl.phizic.web.client.promocodes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма добавления промокода со страницы "Вклады"
 * @author Jatsky
 * @ created 23.12.14
 * @ $Author$
 * @ $Revision$
 */

public class AddPromoCodeForm extends EditFormBase
{
	public static final Form ADD_PROMOCODE_FORM = createForm();
	private PromoCodesMessage errorMessage;
	private PromoCodesMessage error12Message;

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Промокод");
		fieldBuilder.setName("promoCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public PromoCodesMessage getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(PromoCodesMessage errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public PromoCodesMessage getError12Message()
	{
		return error12Message;
	}

	public void setError12Message(PromoCodesMessage error12Message)
	{
		this.error12Message = error12Message;
	}
}
