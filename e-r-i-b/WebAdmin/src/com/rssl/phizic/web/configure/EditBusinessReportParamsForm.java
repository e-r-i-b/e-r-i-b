package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateBeforeTodayValidator;
import com.rssl.common.forms.validators.DatePeriodMultiFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.ArrayList;
import java.util.List;

import static com.rssl.phizic.config.reports.BusinessReportConfig.*;

/**
 * Форма редактирования параметров отчета по бизнес операциям
 * @author gladishev
 * @ created 12.02.2015
 * @ $Author$
 * @ $Revision$
 */

public class EditBusinessReportParamsForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();
	private static final Form UPDATE_FORM = createUploadForm();
	private static final long MAX_PERIOD = 60 * 24 * 60 * 60 * 1000L;


	@Override
	public Form getForm()
	{
		return FORM;
	}

	public Form getUploadForm()
	{
		return UPDATE_FORM;
	}

	/**
	 * Собирвет поля основной формы, которые юзаются на обеих страницах
	 * @return поля основной формы
	 */
	private static List<Field> getMainFields()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder = new FieldBuilder();

		//отчеты для выгрузки
		fieldBuilder.setName(REPORTS_TRANSFER_ON_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_PAYMENT_ON_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_OPEN_ACCOUNT_ON_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		//отклонения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_DEVIATION_ALL_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_DEVIATION_TRANSFER_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_DEVIATION_PAYMENT_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_DEVIATION_OPEN_ACCOUNT_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		//блок
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_BLOCK_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fields.add(fieldBuilder.build());

		//канал
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPORTS_CHANNEL_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("all|mobile|atm|internet")
		);
		fields.add(fieldBuilder.build());

		//параметры доставки отчетов
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_RECEIVERS_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_THEME_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_TEXT_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_TIME_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d\\d\\:\\d\\d"));
		fields.add(fieldBuilder.build());

		//включение/отключение рассылок
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MonitoringOperationConfig.ACTIVE_MONITORING_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AGGREGATE_ON_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SEND_ON_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());
		return fields;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(getMainFields());
		return formBuilder.build();
	}

	private static Form createUploadForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addFields(getMainFields());

		FieldBuilder fieldBuilder = new FieldBuilder();

		//период рассылки
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FROM_PERIOD);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateBeforeTodayValidator()
		);
		fieldBuilder.setDescription("Период с");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TO_PERIOD);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateBeforeTodayValidator()
		);
		fieldBuilder.setDescription("Период по");
		formBuilder.addField(fieldBuilder.build());

		DatePeriodMultiFieldValidator dateMultiFieldValidator = new DatePeriodMultiFieldValidator();
		dateMultiFieldValidator.setMaxTimeSpan(MAX_PERIOD);
		dateMultiFieldValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, FROM_PERIOD);
		dateMultiFieldValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, TO_PERIOD);
		dateMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 60 дней");

		formBuilder.addFormValidators(dateMultiFieldValidator);

		return formBuilder.build();
	}
}
