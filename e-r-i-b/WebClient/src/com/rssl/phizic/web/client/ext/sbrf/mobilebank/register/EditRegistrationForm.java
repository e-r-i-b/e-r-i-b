package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RangeFieldValidator;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditRegistrationForm extends EditFormBase implements RegistrationForm
{
	static final String FIELD_TARIFF = "tariff";

	static final String FIELD_PHONE = "phone";

	static final String FIELD_CARD = "card";

	private static final FieldValidator REQUIRED_VALIDATOR = new RequiredFieldValidator();

	private static final FieldValidator TARIFF_VALIDATOR = new EnumFieldValidator<MobileBankTariff>(MobileBankTariff.class);

	private String returnURL;

	/**
	 * Маскированные номера карт
	 */
	private Collection<String> maskedCards;

	/**
	 * Адрес экшена предыдущего шага
	 */
	private String backActionPath;

	///////////////////////////////////////////////////////////////////////////

	public String getReturnURL()
	{
		return returnURL;
	}

	public void setReturnURL(String returnURL)
	{
		this.returnURL = returnURL;
	}

	public String getBackActionPath()
	{
		return backActionPath;
	}

	public void setBackActionPath(String backActionPath)
	{
		this.backActionPath = backActionPath;
	}

	public Collection<String> getMaskedCards()
	{
		return maskedCards;
	}

	public void setMaskedCards(Collection<String> maskedCards)
	{
		this.maskedCards = maskedCards;
	}

	static Form getEditForm(String maskedPhone, Collection<String> maskedCards)
	{
		FormBuilder formBuilder = new FormBuilder();

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		// 1. Тариф
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_TARIFF);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Тариф");
		fieldBuilder.addValidators(REQUIRED_VALIDATOR, TARIFF_VALIDATOR);
		formBuilder.addField(fieldBuilder.build());

		// 2. Номер Телефона
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_PHONE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Номер Телефона");
		fieldBuilder.addValidators(REQUIRED_VALIDATOR, new RangeFieldValidator(maskedPhone));
		fieldBuilder.setInitalValueExpression(new ConstantExpression(maskedPhone));
		formBuilder.addField(fieldBuilder.build());

		// 3. Номер Карты
		String firstCard = maskedCards.iterator().next();
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_CARD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Номер Карты");
		fieldBuilder.addValidators(REQUIRED_VALIDATOR, new RangeFieldValidator(maskedCards));
		fieldBuilder.setInitalValueExpression(new ConstantExpression(firstCard));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}