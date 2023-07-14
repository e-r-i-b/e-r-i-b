package com.rssl.phizic.web.common.mobile.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @ author: Gololobov
 * @ created: 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class GetCardIssuerByNumberMobileForm extends EditFormBase
{
	public static final Form   FORM        = createForm();
	public static final String CARD_NUMBER = "cardNumber";

	private static final String INPUT_PARAMS_ERROR_MSG = "message.payment.operation.inputparams.error.message";

	private String  cardNumber;
	private boolean sbrfCardIssuer;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public boolean isSbrfCardIssuer()
	{
		return sbrfCardIssuer;
	}

	public void setSbrfCardIssuer(boolean sbrfCardIssuer)
	{
		this.sbrfCardIssuer = sbrfCardIssuer;
	}

	private static Form createForm()
	{
		FormBuilder  builder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARD_NUMBER);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Номер карты");

		fieldBuilder.addValidators(
			new RequiredFieldValidator(StrutsUtils.getMessage(INPUT_PARAMS_ERROR_MSG, "paymentsBundle")),
			new RegexpFieldValidator("(\\d{16})|(\\d{18})|(\\d{15})", "Вы указали неверный номер карты")
		);

		builder.addField(fieldBuilder.build());
		return builder.build();
	}
}
