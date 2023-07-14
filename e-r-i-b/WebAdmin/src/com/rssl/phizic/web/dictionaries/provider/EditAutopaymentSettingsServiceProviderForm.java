package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;

import java.math.BigInteger;

/**
 * @author krenev
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditAutopaymentSettingsServiceProviderForm extends EditServiceProviderFormBase
{
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME = "autoPaymentSupported";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ATM = "autoPaymentSupportedInATM";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_SOCIAL_API = "autoPaymentSupportedInSocialApi";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_API = "autoPaymentSupportedInAPI";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ERMB = "autoPaymentSupportedInERMB";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME = "autoPaymentVisible";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_SOCIAL_API = "autoPaymentVisibleInSocialApi";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_ATM = "autoPaymentVisibleInATM";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_API = "autoPaymentVisibleInAPI";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_ERMB = "autoPaymentVisibleInERMB";

	static final String BANKOMAT_SUPPORTED_FIELD_NAME = "bankomatSupported";

	static final String ALWAYS_AUTO_PAY_FIELD_NAME = "alwaysAutoPay";
	static final String MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME = "minSumAlwaysAutoPay";
	static final String MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME = "maxSumAlwaysAutoPay";
	static final String CLIENT_HINT_ALWAYS_AUTO_PAY_FIELD_NAME = "clientHintAlwaysAutoPay";

	static final String THRESHOLD_AUTO_PAY_FIELD_NAME = "thresholdAutoPay";
	static final String THRESHOLD_VALUES_TYPE_FIELD_NAME = "thresholdValuesType";
	static final String MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME = "minSumThresholdAutoPay";
	static final String MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME = "maxSumThresholdAutoPay";
	static final String MAX_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME = "maxValueThresholdAutoPay";
	static final String MIN_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME = "minValueThresholdAutoPay";
	static final String CLIENT_HINT_THRESHOLD_AUTO_PAY_FIELD_NAME = "clientHintThresholdAutoPay";
	static final String THRESHOLD_DISCRETE_VALUES_FIELD_NAME = "thresholdDiscreteValues";
	static final String ACCESS_TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME  = "accessTotalMaxSumThresholdAutoPay";
	static final String TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME         = "totalMaxSumThresholdAutoPay";
	static final String PERIOD_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME        = "periodMaxSumThresholdAutoPay";

	static final String INVOICE_AUTO_PAY_FIELD_NAME = "invoiceAutoPay";
	static final String CLIENT_HINT_INVOICE_AUTO_PAY_FIELD_NAME = "clientHintInvoiceAutoPay";

	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		//Доступность автоплатежа
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен 'Автоплатеж'");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимость автоплатежа в Интернет-банке");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//Доступность автоплатежа в mApi
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен 'Автоплатеж' в mApi");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимость автоплатежа в mApi");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//Доступность автоплатежа  в atmApi
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен 'Автоплатеж' в atmApi");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ATM);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимость автоплатежа в atmApi");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ATM);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//Доступность автоплатежа  в socialApi
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен 'Автоплатеж' в socialApi");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_SOCIAL_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимость автоплатежа в socialApi");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_SOCIAL_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//Доступность автоплатежа в ЕРМБ
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен 'Автоплатеж' в ЕРМБ");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ERMB);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимость автоплатежа в в ЕРМБ");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ERMB);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Банкомат поддерживается");
		fieldBuilder.setName(BANKOMAT_SUPPORTED_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		buildFormForFloorAytopaymentSettings(fb); //Пороговый автоплатеж
		buildFormForAlwaysAutoPaymentSettings(fb); //Регулярный автоплатеж
		buildFormForInvoiceAutoPaymentSettings(fb); //Автоплатеж по выставленному счету

		//если отмечен флажок "доступен Автоплатеж" то необходимо чтоб был выбран хотя бы один тип автоплатежа.
		MultiFieldsValidator checkedMultiFieldValidator = new IsCheckedMultiFieldValidator();
		checkedMultiFieldValidator.setBinding("ptr1", ALWAYS_AUTO_PAY_FIELD_NAME);
		checkedMultiFieldValidator.setBinding("ptr2", THRESHOLD_AUTO_PAY_FIELD_NAME);
		checkedMultiFieldValidator.setBinding("ptr3", INVOICE_AUTO_PAY_FIELD_NAME);
		checkedMultiFieldValidator.setMessage("Вы неправильно указали параметры автоплатежа. Пожалуйста, выберите тип автоплатежа и параметры.");
		checkedMultiFieldValidator.setEnabledExpression(new RhinoExpression("form.autoPaymentSupported==true || form.autoPaymentSupportedInATM==true || form.autoPaymentSupportedInSocialApi==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true "));
		fb.addFormValidators(checkedMultiFieldValidator);

		return fb.build();
	}

	/**
	 * Построить форму для параметров порогового автоплатежа
	 * @param fb билдер формы
	 */
	private static void buildFormForFloorAytopaymentSettings(FormBuilder fb)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Пороговый");
		fieldBuilder.setName(THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//выражение "выбран пороговый автоплатеж"
		final Expression thresholdAutoPaySelectedExpression = new RhinoExpression("(form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true ) && form.thresholdAutoPay==true");
		//вылидатор на для заполнения обязательных полей
		final FieldValidator requiredFieldValidator = new RequiredFieldValidator("Для пороговой схемы автоплатежа необходимо заполнить поля:\\\\n" +
				"Минимальное значение суммы, Максимальное значение суммы и Пороговое значение");

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("Укажите минимальную сумму порогового автоплажа в правильном формате: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальное значение суммы порогового автоплатежа");
		fieldBuilder.setName(MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("Укажите максимальную сумму порогового автоплажа в правильном формате: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное значение суммы порогового автоплатежа");
		fieldBuilder.setName(MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		// проверяем чтобы минимальная сумма автоплатежа была меньше максимальной
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		compareValidator.setMessage("Минимальное значение суммы порогового автоплатежа не должно превышать максимальное значение.");
		fb.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальная сумма автоплатежа в месяц");
		fieldBuilder.setName(ACCESS_TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		final Expression accessTotalMaxSumSelectedExpression = new RhinoExpression("form.accessTotalMaxSumThresholdAutoPay==true");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальная сумма автоплатежа");
		fieldBuilder.setName(TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(accessTotalMaxSumSelectedExpression);
		fieldBuilder.addValidators(maxAmountValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период максимальной суммы автплатежа");
		fieldBuilder.setName(PERIOD_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(accessTotalMaxSumSelectedExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип порогового значения (интервал или допустимые значения)");
		fieldBuilder.setName(THRESHOLD_VALUES_TYPE_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		// выражение, при вычислении которого определяется выбран ли тип пороговых значений ввиде интервала значений
		final Expression intervalValuesExpression = new RhinoExpression("(form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true )&& form.thresholdAutoPay==true && form.thresholdValuesType==true");
		FieldValidator requireFieldValidator = new RequiredFieldValidator("Для пороговой схемы автоплатежа необходимо заполнить поля:\\\\n  " +
						"Минимальное значение порога и Максимальное значение порога или указать допустимые значения");

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("Укажите минимальное значение порога порогового автоплажа в правильном формате: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальное значение порога порогового автоплатежа");
		fieldBuilder.setName(MIN_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requireFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(intervalValuesExpression);
		fb.addField(fieldBuilder.build());

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("Укажите максимальное значение порога порогового автоплажа в правильном формате: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное значение порога порогового автоплатежа");
		fieldBuilder.setName(MAX_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requireFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(intervalValuesExpression);
		fb.addField(fieldBuilder.build());

		// проверяем чтоб минимальное значение порога порогового автоплатежа была меньше максимальной
		compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minValueThresholdAutoPay");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxValueThresholdAutoPay");
	    compareValidator.setMessage("Минимальное значение порогового интервала не должно превышать максимальное значение.");
		compareValidator.setEnabledExpression(intervalValuesExpression);
	    fb.addFormValidators(compareValidator);

		//дискретные значения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Допустимые значения");
		fieldBuilder.setName(THRESHOLD_DISCRETE_VALUES_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("(form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true ) && form.thresholdAutoPay==true && form.thresholdValuesType==false"));
		fieldBuilder.addValidators(requireFieldValidator,
				new RegexpFieldValidator("^\\d{1,7}((\\.|,)\\d{0,2})?(\\|\\d{1,7}((\\.|,)\\d{0,2})?)*$", "Не правильно указаны допустимые значения"),
				new RegexpFieldValidator(".{0,256}", "Превышено максимальное количество допустимых значений порогового автоплатежа")
		);
		fb.addField(fieldBuilder.build());

		fb.addField(createClientHintField(CLIENT_HINT_THRESHOLD_AUTO_PAY_FIELD_NAME));
	}

	/**
	 * Построить форму для параметров регулярного автоплатежа
	 * @param fb билдер формы
	 */
	private static void buildFormForAlwaysAutoPaymentSettings(FormBuilder fb)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Регулярный");
		fieldBuilder.setName(ALWAYS_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//выражение "выбран регулярный автоплатеж"
		final Expression alwaysAutoPaySelectedExpression = new RhinoExpression("form.alwaysAutoPay==true && (form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true )");
		//вылидатор на для заполнения обязательных полей
		final FieldValidator requiredFieldValidator = new RequiredFieldValidator("Для регулярной схемы автоплатежа необходимо заполнить поля:\\\\n" +
				"Минимальное значение суммы и Максимальное значение суммы");

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("Укажите минимальную сумму регулярного автоплажа в правильном формате: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальное значение суммы регулярного автоплатежа");
		fieldBuilder.setName(MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(alwaysAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("Укажите максимальную сумму регулярного автоплажа в правильном формате: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное значение суммы регулярного автоплатежа");
		fieldBuilder.setName(MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(alwaysAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		fb.addField(createClientHintField(CLIENT_HINT_ALWAYS_AUTO_PAY_FIELD_NAME));

		//проверяем чтоб максимальная сумма регулярного автоплатежа была больше минимальной
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		compareValidator.setMessage("Минимальное значение суммы регулярного автоплатежа не должно превышать максимальное значение.");
		fb.addFormValidators(compareValidator);
	}

	/**
	 * Построить форму для параметров автоплатежа по выставленному счету
	 * @param fb билдер формы
	 */
	private static void buildFormForInvoiceAutoPaymentSettings(FormBuilder fb)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("По выставленному счету");
		fieldBuilder.setName(INVOICE_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fb.addField(createClientHintField(CLIENT_HINT_INVOICE_AUTO_PAY_FIELD_NAME));
	}

	private static Field createClientHintField(String fieldName)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сообщение клиенту при выборе данного типа автоплатежа");
		fieldBuilder.setName(fieldName);
		fieldBuilder.setType(StringType.INSTANCE.getName());

		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", BigInteger.valueOf(500));
		lengthValidator.setMessage("Сообщение клиенту при выборе данного типа автоплатежа должно содержать не более 500 символов.");
		fieldBuilder.addValidators(lengthValidator);
		return fieldBuilder.build();
	}
}
