package com.rssl.phizic.web.payments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.SqlTimeParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.businessProperties.LoanReceptionTimeHelper;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.config.invoicing.InvoicingConfig;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.operations.payment.EditPaymentRestrictionsOperation;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Time;
import java.util.*;

/**
 * @author gladishev
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentRestrictionsForm extends EditPropertiesFormBase
{
	public static final String LOAN_SETTING_ID_KEY = "loanRestrictionProperties_Id_";
	public static final String LOAN_SETTING_SYSTEM_NAME_KEY = "loanRestrictionProperties_loanSystemName_";
	public static final String LOAN_SETTING_FROM_TIME_KEY = "loanRestrictionProperties_loanFromTime_";
	public static final String LOAN_SETTING_TO_TIME_KEY = "loanRestrictionProperties_loanToTime_";
	public static final String LOAN_SETTING_TIME_ZONE_KEY = "loanRestrictionProperties_timeZone_";
	private static final String TIME_FORMAT = "HH:mm";
	private static final String ALL_CHENGED_KEYS = "allChengedKeys";

	private Set<String> constantKeys;
    private Limit overallLimit;

	public void setTimeField(String key, Time time)
	{
		setField(key, formatTime(time));
	}

	private String formatTime(Time time)
	{
		if (time == null)
			return "";

		return DateFormatUtils.format(time, TIME_FORMAT);
	}
	public Form createForm(List<Integer> tableSettingNumbers)
	{
		FormBuilder formBuilder = new FormBuilder();

		constantKeys = new HashSet<String>();

		FieldBuilder fieldBuilder;

		//рублевый лимит
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PaymentsConfig.PAYMENT_RESTRICTION_RUB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Лимит на сумму операций в рублях, подтверждаемых паролем с чека");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,10}", "Поле \"Лимит на сумму операций в рублях\" должно содержать не больше 10 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(PaymentsConfig.PAYMENT_RESTRICTION_RUB);

		//евро лимит
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PaymentsConfig.PAYMENT_RESTRICTION_EUR);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Лимит на сумму операций в евро, подтверждаемых паролем с чека");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,10}", "Поле \"Лимит на сумму операций в евро\" должно содержать не больше 10 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(PaymentsConfig.PAYMENT_RESTRICTION_EUR);

		//долларовый лимит
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PaymentsConfig.PAYMENT_RESTRICTION_USD);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Лимит на сумму операций в долларах, подтверждаемых паролем с чека");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,10}", "Поле \"Лимит на сумму операций в долларах\" должно содержать не больше 10 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(PaymentsConfig.PAYMENT_RESTRICTION_USD);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ClientConfig.INFO_PERSON_PAYMENT_LIMIT_KEY);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Суточный лимит на запрос информации о клиенте в операции карта-карта по номеру телефона");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,10}", "Поле \"Суточный лимит на запрос информации о клиенте в операции карта-карта по номеру телефона\" должно содержать не больше 10 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(ClientConfig.INFO_PERSON_PAYMENT_LIMIT_KEY);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ClientConfig.INFO_PERSON_PAYMENT_LIMIT_MESSAGE);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Информациооное сообщение, выводимое в клиентском приложении");
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(ClientConfig.INFO_PERSON_PAYMENT_LIMIT_MESSAGE);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(InvoicingConfig.CLEAR_ORDER_DAYS);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество дней хранения заказов, не связанных с платежами через интернет-магазины.");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,10}", "Поле \"Количество дней хранения заказов, не связанных с платежами через интернет-магазины.\" должно содержать не больше 10 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(InvoicingConfig.CLEAR_ORDER_DAYS);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(InvoicingConfig.ORDERS_MAX_NOTIFY_COUNT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество попыток уведомлений об оплате заказа (Интернет-магазины)");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,10}", "Поле \"Количество попыток уведомлений об оплате заказа (Интернет-магазины)\" должно содержать не больше 10 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(InvoicingConfig.ORDERS_MAX_NOTIFY_COUNT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PaymentsConfig.CLEAR_NOT_CONFIRM_DOCUMENTS_PERIOD);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество дней, по истечении которых удаляются неподтвержденные платежи и заявки");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,5}", "Поле \"Количество дней, по истечении которых удаляются неподтвержденные платежи и заявки\" должно содержать не больше 5 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(PaymentsConfig.CLEAR_NOT_CONFIRM_DOCUMENTS_PERIOD);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PaymentsConfig.CLEAR_WAIT_CONFIRM_DOCUMENTS_PERIOD);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество дней, по истечении которых удаляются платежи и заявки, ожидающие дополнительного подтверждения");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,5}", "Поле \"Количество дней, по истечении которых удаляются платежи и заявки, ожидающие дополнительного подтверждения\" должно содержать не больше 5 цифр."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(PaymentsConfig.CLEAR_WAIT_CONFIRM_DOCUMENTS_PERIOD);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ProvidersConfig.YANDEX_PAYMENT_ID_KEY);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Идентификатор поставщика услуг Яндекс-деньги");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d+", "Поле \"Идентификатор поставщика услуг Яндекс-деньги\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(ProvidersConfig.YANDEX_PAYMENT_ID_KEY);

		for (Integer i : tableSettingNumbers)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Id записи");
			fieldBuilder.setName(LOAN_SETTING_ID_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("Кредитная система");
			fieldBuilder.setName(LOAN_SETTING_SYSTEM_NAME_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{2}[-][a-zA-Z,:]{1,}", "Формат поля \"Кредитная система\":  [TT-XXXXXXXX], где ТТ - код тербанка, XXXXXXXX - название системы. "));
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("Время с");
			fieldBuilder.setName(LOAN_SETTING_FROM_TIME_KEY + i);
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(new SqlTimeParser());
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators
			(
				new DateFieldValidator(TIME_FORMAT, "Время начала приема документов должно быть в формате ЧЧ:ММ")
			);
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Время до");
			fieldBuilder.setName(LOAN_SETTING_TO_TIME_KEY + i);
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(new SqlTimeParser());
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators
			(
				new DateFieldValidator(TIME_FORMAT, "Время окончания приема документов должно быть в формате ЧЧ:ММ")
			);
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Временная зона");
			fieldBuilder.setName(LOAN_SETTING_TIME_ZONE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());

		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MobileApiConfig.FIRST_CONTACT_SYNC_LIMIT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Лимит на первоначальную загрузку контактов");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{0,5}", "Поле \"Лимит на первоначальную загрузку контактов\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(MobileApiConfig.FIRST_CONTACT_SYNC_LIMIT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MobileApiConfig.CONTACT_SYNC_PER_DAY_LIMIT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Суточный лимит на загрузку контактов");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{0,5}", "Поле \"Суточный лимит на загрузку контактов\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(MobileApiConfig.CONTACT_SYNC_PER_DAY_LIMIT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MobileApiConfig.CONTACT_SYNC_PER_WEEK_LIMIT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Недельный лимит на загрузку контактов");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{0,5}", "Поле \"Недельный лимит на загрузку контактов\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());
		constantKeys.add(MobileApiConfig.CONTACT_SYNC_PER_WEEK_LIMIT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EditPaymentRestrictionsOperation.BASKET_ENABLE_PROPERTY_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Обмен информацией по счетам в корзине платежей");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EditPaymentRestrictionsOperation.BASKET_QUEUE_MODE_PROPERTY_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Переключение интеграции по корзине платежей");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return createForm(Collections.<Integer>emptyList());
	}

	@Override
	public Set<String> getFieldKeys()
	{
		createForm(Collections.<Integer>emptyList());
		return constantKeys;
	}

	@Override
	public String[] getPropertiesForReplicate()
	{
		boolean replicateChangedKeys = false;
		for (String str : selectedProperties)
			if (str.equals(ALL_CHENGED_KEYS))
				replicateChangedKeys = true;

		if (replicateChangedKeys)
		{
			List<String> listSelectedProperties = new ArrayList<String>();
			listSelectedProperties.addAll(Arrays.asList(selectedProperties));
			listSelectedProperties.remove(ALL_CHENGED_KEYS);
			Map<String,Object> fields = getFields();
			Set<String> changedKeys = new HashSet<String>();
			for (String key : fields.keySet())
				if (key.startsWith(LOAN_SETTING_SYSTEM_NAME_KEY))
					changedKeys.add(LoanReceptionTimeHelper.getFullKey((String)fields.get(key)));
			listSelectedProperties.addAll(changedKeys);

			String[] newSelectedProperties = new String[listSelectedProperties.size()];
			listSelectedProperties.toArray(newSelectedProperties);
			selectedProperties = newSelectedProperties;
		}
		return selectedProperties;
	}

    public Limit getOverallLimit()
    {
        return overallLimit;
    }

    public void setOverallLimit(Limit overallLimit)
    {
        this.overallLimit = overallLimit;
	}
}
