package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;

/**
 * @author osminin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма изменения статуса обработки входящего запрос на сбор средств
 */
public class ChangeStateFundSenderResponseForm extends CreatePaymentForm
{
	private static final String INVALID_CARD_NUMBER_MESSAGE = "Вы неправильно указали номер карты. Пожалуйста, проверьте количество цифр введенного номера карты и их последовательность.";

	static final String ID_FIELD_NAME               = "id";
	static final String STATE_FIELD_NAME            = "state";
	static final String SUM_FIELD_NAME              = "sum";
	static final String MESSAGE_FIELD_NAME          = "message";
	static final String CLOSE_CONFIRM_FIELD_NAME    = "closeConfirm";
	static final String FROM_RESOURCE_FIELD_NAME    = "fromResource";

	static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(ID_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Идентификатор входящего запроса на сбор средств");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STATE_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Статус обработки запроса на сбор средств");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("(READ)|(REJECT)|(SUCCESS)", "Некорректный статус обработки запроса на сбор средств"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SUM_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.state == 'SUCCESS'"));
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MESSAGE_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Сообщение");

		RequiredFieldValidator messageRequiredFieldValidator = new RequiredFieldValidator();
		messageRequiredFieldValidator.setEnabledExpression(new RhinoExpression("form.state == 'REJECT'"));
		fieldBuilder.addValidators(messageRequiredFieldValidator);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLOSE_CONFIRM_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Подтвержден ли перевод для закрытого запроса");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.state == 'SUCCESS'"));
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FROM_RESOURCE_FIELD_NAME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("Карта списания");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.state == 'SUCCESS'"));
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
