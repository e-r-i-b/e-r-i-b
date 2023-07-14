package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.config.limits.LimitsConfig;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigInteger;
import java.util.List;

/**
 * Настройки СБНКД
 * @author basharin
 * @ created 08.01.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDSettingsForm extends EditPropertiesFormBase
{
	private static final BigInteger maxLength = BigInteger.valueOf(500);
	private static final BigInteger maxLengthInt = BigInteger.valueOf(3);
	private static Form EDIT_FORM = createForm();
	private List<Department> departments;

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	@SuppressWarnings("ReuseOfLocalVariable")
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LimitsConfig.LIMIT_CLAIME_FROM_ONE_PHONE_NUMBER_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Лимит количества заявок с одного номера мобильного телефона в месяц");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLengthInt)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LimitsConfig.LIMIT_CLAIME_CARD_PER_MONTH_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Лимит количества заказанных карт в месяц");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLengthInt)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LimitsConfig.TEXT_MESSAGE_CARD_CLAIME_LIMIT_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст отображаемого сообщения при достижении лимита");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.ALLOWED_TB_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("ТБ, в которых разрешено подключение гостей");
		fieldBuilder.setValidators(
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.TEXT_MESSAGE_VIP_CLIENT_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст отображаемого vip клиенту сообщения");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.TEXT_MESSAGE_START_PROCESSING_EXTERNAL_SYSTEM_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображаемое сообщение после подтверждения заявки");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.MAX_DEBIT_CARD_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Максимальное количество дебетовых карт");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLengthInt)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.MAX_DEBIT_CARD_MESSAGE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображаемое сообщение при достижении максимального количества заказанных карт");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.TEXT_MESSAGE_ERROR_FORBIDDEN_REGION);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображаемое сообщение при оформлении заявки в неразрешенном регионе");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.DEFAULT_PACKAGE_MOBILE_BANK_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Пакет мобильного банка по умолчанию");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Информирование клиента по обработке заявки");
		fb.addField(fieldBuilder.build());

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form['"+SBNKDConfig.INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY+"']===true"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.SMS_MESSAGE_SUCCESS_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("SMS-сообщение в случае успешной обработки заявки");
		fieldBuilder.setValidators(
				requiredFieldValidator,
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form['"+SBNKDConfig.INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY+"']===true"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.TEXT_MESSAGE_ERROR_EXTERNAL_SYSTEM_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображаемое сообщение в случае возникновения ошибок");
		fieldBuilder.setValidators(
				requiredFieldValidator,
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form['"+SBNKDConfig.INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY+"']===true"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.SMS_MESSAGE_ERROR_EXTERNAL_SYSTEM_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("SMS-сообщение в случае возникновения ошибок");
		fieldBuilder.setValidators(
				requiredFieldValidator,
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form['"+SBNKDConfig.INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY+"']===true"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.SMS_MESSAGE_NOT_COMPLETED_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("SMS-сообщение в случае неполной обработки заявки");
		fieldBuilder.setValidators(
				requiredFieldValidator,
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SBNKDConfig.TEXT_MESSAGE_WAIT_ANSWER_EXTERNAL_SYSTEM_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображаемое сообщение во время ожидания ответа от смежных АС");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public void setDepartments(List<Department> departments)
	{
		this.departments = departments;
	}

	public List<Department> getDepartments()
	{
		return departments;
	}
}
