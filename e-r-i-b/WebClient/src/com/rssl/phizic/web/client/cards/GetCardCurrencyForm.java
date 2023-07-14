package com.rssl.phizic.web.client.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.CardNumberChecksumFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author hudyakov
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetCardCurrencyForm extends EditFormBase
{
	private String currency;
	public static final String PARAM_TYPE = "type";
	public static final String CARD_NUMBER = "cardNumber";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//поле type
		FieldBuilder fb = new FieldBuilder();
		fb.setName(PARAM_TYPE);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Тип номера(карта/телефон)");
		fb.addValidators(new RequiredFieldValidator("Неверный тип платежа"));
		formBuilder.addField(fb.build());
		//поле cardNumber
		fb = new FieldBuilder();
		fb.setName(CARD_NUMBER);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Номер карты");
		CardNumberChecksumFieldValidator cardChecksumFieldValidator = new CardNumberChecksumFieldValidator();
		cardChecksumFieldValidator.setMessage("Вы указали некорректный номер карты получателя. Пожалуйста, проверьте номер карты.");
		fb.addValidators(new RegexpFieldValidator("(\\d{16})|(\\d{18})|(\\d{15})","Вы указали неверный номер карты получателя. Пожалуйста, проверьте номер карты."),
				new RequiredFieldValidator("Не указан номер карты для перевода."),
				cardChecksumFieldValidator);
		fb.setEnabledExpression(new RhinoExpression("form.type == 'card'"));
		formBuilder.addField(fb.build());
		//поле phoneNumber
		fb = new FieldBuilder();
		fb.setName(PHONE_NUMBER);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Номер телефона");
		fb.addValidators(new RegexpFieldValidator("^\\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})$","Пожалуйста, укажите в поле Номер телефона. Например, (906) 555-22-33."),
				new RequiredFieldValidator("Не указан номер телефона для перевода на карту."));
		fb.setEnabledExpression(new RhinoExpression("form.type == 'phone'"));
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
}
