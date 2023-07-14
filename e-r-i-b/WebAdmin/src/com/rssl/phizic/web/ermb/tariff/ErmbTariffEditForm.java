package com.rssl.phizic.web.ermb.tariff;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Moshenko
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 * Форма редактирования тарифов ЕРМБ
 */
public class ErmbTariffEditForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		RequiredFieldValidator requiredFieldValidator  = new RequiredFieldValidator();
		MoneyFieldValidator moneyFieldValidator  = new MoneyFieldValidator();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Название");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,50}", "Название тарифа не должен превышать 50 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("connectionCost");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Подключение");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("graceClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Абонентская плата. Класс продукта 'Льготный'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("premiumClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Абонентская плата. Класс продукта 'Премиум'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("socialClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Абонентская плата. Класс продукта 'Социальный'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new MoneyFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("standardClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Абонентская плата. Класс продукта 'Стандарт'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("chargePeriod");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Период взымания абонентской платы");
		NumericRangeValidator numericRangeValidator = new  NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setMessage("Период взымания абонентской платы должен быть больше нуля");

		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator("\\d*", "Поле 'Период взымания абонентской платы' должно состоять только из целых чисел."),
				numericRangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("gracePeriod");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Льготный период");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator("\\d*", "Поле 'Льготный период' должно состоять только из целых чисел.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("gracePeriodCost");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Размер платы в льготный период");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("noticeConsIncomCardOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Уведомление о  расходных/приходных операциях, авторизациях по счету карты");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("noticeConsIncomAccountOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Уведомление о расходных/приходных операциях по счету вклада");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Предоставление по запросу клиента информации по карте");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Предоставление по запросу клиента информации по счету");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardMiniInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Предоставление по запросу клиента краткой истории по карте (мини-выписка)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountMiniInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Предоставление по запросу клиента краткой истории по счету (мини-выписка)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("reIssueCardOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Блокировка/запрос на перевыпуск карты по запросу клиента");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("jurPaymentOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Платежи в пользу организаций (оплата услуг)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("transfersToThirdPartiesOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Услуга: Переводы в пользу третьих лиц (по шаблону)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Описание");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,300}", "Описание тарифа не должен превышать 300 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
