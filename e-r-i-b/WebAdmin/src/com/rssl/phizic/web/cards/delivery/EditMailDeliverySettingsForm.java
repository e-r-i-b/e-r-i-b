package com.rssl.phizic.web.cards.delivery;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigInteger;

/**
 * @author akrenev
 * @ created 13.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования управления оповещением
 */

public class EditMailDeliverySettingsForm extends EditPropertiesFormBase
{
	public static final BigInteger TWO_HUNDRED = BigInteger.valueOf(200L);
	public static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.card.report.delivery.is.available.message.text");
		fieldBuilder.setDescription("Текст оповещения");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new LengthFieldValidator(TWO_HUNDRED));
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.card.report.delivery.is.available.message.show");
		fieldBuilder.setDescription("Отображать оповещение");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
