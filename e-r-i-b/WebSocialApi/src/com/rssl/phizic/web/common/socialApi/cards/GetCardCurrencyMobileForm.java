package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.CardNumberChecksumFieldValidator;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Dorzhinov
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetCardCurrencyMobileForm extends EditFormBase
{
	public static final Form CARD_CURRENCY_FORM = createForm();

	public static final String CARD_NUMBER_FIELD_NAME = "cardNumber";
	public static final String PHONE_NUMBER_FIELD_NAME = "phoneNumber";
	public static final String DICT_FIELD_ID_FIELD_NAME = "dictFieldId";

	//out
	private Card card;

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARD_NUMBER_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{16})|(\\d{18})|(\\d{15})$", "Вы неправильно указали номер карты. Пожалуйста, введите 15, 16 либо 18 цифр."),
				new CardNumberChecksumFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PHONE_NUMBER_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DICT_FIELD_ID_FIELD_NAME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CARD_NUMBER_FIELD_NAME, CARD_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(PHONE_NUMBER_FIELD_NAME, PHONE_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DICT_FIELD_ID_FIELD_NAME, DICT_FIELD_ID_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("Должен быть задан либо номер карты, либо номер телефона");
		fb.addFormValidators(requiredMultiFieldValidator);

		return fb.build();
	}
}
