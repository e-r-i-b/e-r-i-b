package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.connectUdbo.UDBOClaimRules;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigInteger;
import java.util.List;

/**
 * форма для настройи удалленого подключения УДБО
 * @author basharin
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConnectUDBOSettingsForm extends EditPropertiesFormBase
{
	private static final BigInteger maxLength = BigInteger.valueOf(1000);
	private static final BigInteger MAX_LENGTH_500 = BigInteger.valueOf(500);
	private static Form EDIT_FORM = createForm();
	private List<Department> departments;
	private List<UDBOClaimRules> claimRulesList;

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
		fieldBuilder.setName(RemoteConnectionUDBOConfig.ALLOWED_TB_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("ТБ, в которых разрешено подключение УДБО");
		fieldBuilder.setValidators(
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.TIME_COLD_PERIOD_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Продолжительность в часах холодного периода");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.WORK_WITHOUT_UDBO_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Возможность продолжить работу в СБОЛ при отказе от подключения УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.CANCEL_CONNECT_UDBO_MESSAGE_TITLE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Заголовок сообщения при отказе от подключения УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.CANCEL_CONNECT_UDBO_MESSAGE_TEXT_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст сообщения при отказе от подключения УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.CANCEL_ACCEPT_INFO_UDBO_MESSAGE_TITLE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Заголовок сообщения при отказе от подтверждения информации при подключении УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.CANCEL_ACCEPT_INFO_UDBO_MESSAGE_TEXT_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст сообщения при отказе от подтверждения информации при подключении УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.ALL_CONNECTION_UDBO_NEED_WAIT_CONFIRM_STATE_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Требовать подтверждение всех заявок в ЕРКЦ");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.AGE_CLIENT_FOR_NEED_WAIT_CONFIRM_STATE_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Критерии безопасности для обязательного подтверждения заявки на подключение УДБО в КЦ: возраст клиента");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.MONEY_CLIENT_FOR_NEED_WAIT_CONFIRM_STATE_KEY);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("критерии безопасности для обязательного подтверждения заявки на подключение УДБО в КЦ: размер активов");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_INFO_MESSAGE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст оповещения клиента о том, что он работает в холодном периоде");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.NEED_GO_VSP_MESSAGE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст о необходимости клиенту обратиться в ВСП для подключения УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.ACCEPT_CONNECT_UDBO_MESSAGE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст чекбокса согласия с условиями договора");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.SMS_MESSAGE_CONNECTION_UDBO_SUCCESS_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("смс сообщение об успешном подключени УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(maxLength)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.TERM_TEXT_FOR_CONNECTION_UDBO_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст условий для заявления о подключении УДБО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(MAX_LENGTH_500)
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_OPEN_ACCOUNT_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Открытие вкладов");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_OPEN_IMACCOUNT_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Открытие ОМС");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_CARD_TO_IMACCOUNT_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод карта – ОМС");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_CARD_TO_ACCOUNT_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод карта – вклад/счет");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_CARD_TO_CARD_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод карта – карта");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_ACCOUNT_TO_CARD_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод вклад/счет – карта");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_IMACCOUNT_TO_CARD_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод ОМС – карта");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_TO_OTHER_BANK_ACCOUNT_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод на счет в другом банке");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_TO_OTHER_BANK_CARD_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод на карту в другом банке (Visa transfer, MC money send)");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_TO_OUR_BANK_CARD_PHONE_NUMBER_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод на карту в Сбербанке, на счет в Сбербанке, на карту в Сбербанке по номеру телефона");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_TO_JUR_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("перевод юр. лицу по свободным реквизитам");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RemoteConnectionUDBOConfig.COLD_PERIOD_OPERATION_TO_DEPO_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("операции по счетам ДЕПО");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
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

	/**
	 * @return список условий для заявления о подключении УДБО
	 */
	public List<UDBOClaimRules> getClaimRulesList()
	{
		return claimRulesList;
	}

	/**
	 * Задать список условий для заявления подключения УДБО
	 * @param claimRulesList - список
	 */
	public void setClaimRulesList(List<UDBOClaimRules> claimRulesList)
	{
		this.claimRulesList = claimRulesList;
	}

}
