package com.rssl.phizic.web.dictionaries.routing.adapter.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RangeFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.LogLevelConfig;
import com.rssl.phizic.operations.dictionaries.routing.adapter.settings.EditAdapterSettingsOperation;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import com.rssl.phizicgate.manager.routing.NodeType;

import java.util.List;

import static com.rssl.phizic.logging.system.LogLevelConfig.EXTENDED_PREFIX;

/**
 * User: Balovtsev
 * Date: 13.07.2012
 * Time: 8:41:17
 */
public class EditAdapterSettingsForm extends EditPropertiesFormBase
{
	@Override
	public Form getForm()
	{
		List<Integer> fieldsExcludedMessagesPropertiesNumbers =
				PropertyHelper.getTableSettingNumbers(getFields(), EditAdapterSettingsOperation.EXCLUDED_MESSAGES_CODE_KEY);

		if (NodeType.COD == nodeType || NodeType.SOFIA == nodeType)
		{
			List<Integer> wrongOfficeBranchesPropertiesNumbers =
					PropertyHelper.getTableSettingNumbers(getFields(), EditAdapterSettingsOperation.WRONG_OFFICE_BRANCHES_CODE_KEY);
			List<Integer> ourTbCodesPropertiesNumbers =
					PropertyHelper.getTableSettingNumbers(getFields(), EditAdapterSettingsOperation.OUR_TB_CODES_CODE_KEY);
			return EditAdapterSettingsForm.getForm(nodeType, wrongOfficeBranchesPropertiesNumbers,
					ourTbCodesPropertiesNumbers, fieldsExcludedMessagesPropertiesNumbers);
		}
		return EditAdapterSettingsForm.getForm(nodeType, fieldsExcludedMessagesPropertiesNumbers);
	}

	/**
	 * Возвращает пользовательскую форму.
	 * @return форма.
	 * @param nodeType тип узла.
	 * @return форма.
	 */
	private static Form getForm(NodeType nodeType, List<Integer> fieldsExcludedMessagesPropertiesNumber)
	{
		if (nodeType == NodeType.RETAIL_V6)
			return createFormRetailV6(createFormLogger(createForm(), NodeType.RETAIL_V6, fieldsExcludedMessagesPropertiesNumber)).build();
		if (nodeType == NodeType.GOROD)
			return createFormGorod(createFormLogger(createForm(), NodeType.GOROD, fieldsExcludedMessagesPropertiesNumber)).build();
		if (nodeType == NodeType.CPFL)
			return createFormCPFL(createFormLogger(createForm(), NodeType.CPFL, fieldsExcludedMessagesPropertiesNumber)).build();
		if (nodeType == NodeType.ENISEY)
			return createFormEnisey(createFormLogger(createForm(), NodeType.ENISEY, fieldsExcludedMessagesPropertiesNumber)).build();
		if (nodeType == NodeType.IQWAVE)
			return createFormIQWave(createFormLogger(createForm(), NodeType.IQWAVE, fieldsExcludedMessagesPropertiesNumber)).build();
		if (nodeType == NodeType.SOFIA_BILLING)
			return createFormSofia(createFormLogger(createForm(), NodeType.SOFIA_BILLING, fieldsExcludedMessagesPropertiesNumber)).build();
		if (nodeType == NodeType.XBANK)
			return createFormXBank(createFormLogger(createForm(), NodeType.XBANK, fieldsExcludedMessagesPropertiesNumber)).build();

		throw new FormException("Указано некорректное название системы " + nodeType.name());
	}

	/**
	 * Возвращает пользовательскую форму.
	 *
	 * @param nodeType тип узла.
	 * @param wrongOfficeBranchesPropertiesNumbers - список индексов для настройки wrong_office_branches
	 * @param ourTbCodesPropertiesNumbers - список индексов для настройки ourTbCodesPropertiesNumbers
	 * @param fieldsExcludedMessagesPropertiesNumber - список индексов для настройки excluded.messages
	 * @return форма.
	 */
	private static Form getForm(NodeType nodeType, List<Integer> wrongOfficeBranchesPropertiesNumbers,
	                           List<Integer> ourTbCodesPropertiesNumbers, List<Integer> fieldsExcludedMessagesPropertiesNumber)
	{
		if (nodeType == NodeType.COD)
			return createFormCOD(createFormLogger(createForm(), NodeType.COD, fieldsExcludedMessagesPropertiesNumber),
					wrongOfficeBranchesPropertiesNumbers, ourTbCodesPropertiesNumbers).build();
		if (nodeType == NodeType.SOFIA)
			return createFormSofiaVMS(createFormLogger(createForm(), NodeType.SOFIA, fieldsExcludedMessagesPropertiesNumber),
					wrongOfficeBranchesPropertiesNumbers, ourTbCodesPropertiesNumbers).build();
		throw new FormException("Указано некорректное название системы " + nodeType.name());
	}

	/**
	 * Идентификатор адаптера.
	 */
	private NodeType nodeType;

	public String getNodeType()
	{
		return nodeType.name();
	}

	public void setNodeType(String nodeType)
	{
		this.nodeType = NodeType.valueOf(nodeType);
	}

	public NodeType getNodeTypeObject()
	{
		return nodeType;
	}

	private static void createFormLevelLogger(FormBuilder fb, NodeType nodeType, boolean isExtended)
	{
		String extendedPrefix = isExtended ? EXTENDED_PREFIX : "";
		String loggingTypeDescriptionPrefix = isExtended ? "(Расширенное логирование)" : "(Стандартное логирование)";

		FieldBuilder fieldBuilder;
		//Поле com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Core
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Ядро\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Core");
		fieldBuilder.setType("integer");
		RangeFieldValidator validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Gate
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Шлюз\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Gate");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Scheduler
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Обработчик расписания\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Scheduler");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Cache
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Система кеширования\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Cache");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.messagesLog.<extendedPrefix>level.<nodeType.system>
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логирование сообщений взаимодействия ЕРИБ и " + nodeType.getDescription() + loggingTypeDescriptionPrefix);
		fieldBuilder.setName(Constants.MESSAGE_LOG_PREFIX + extendedPrefix + LogLevelConfig.LEVEL_PREFIX + nodeType.getSystem());
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());
	}

	//Generated code for logic fields
	private static FormBuilder createFormLogger(FormBuilder fb, NodeType nodeType, List<Integer> fieldsExcludedMessagesPropertiesNumber)
	{
		createFormLevelLogger(fb, nodeType, false);
		createFormLevelLogger(fb, nodeType, true);

		FieldBuilder fieldBuilder;
		//Поле com.rssl.phizic.logging.writers.SystemLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.backup");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		for (Integer i : fieldsExcludedMessagesPropertiesNumber)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Настройка исключаемых сообщений");
			fieldBuilder.setName(EditAdapterSettingsOperation.EXCLUDED_MESSAGES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator(".{1,200}", "Поле \"" + fieldBuilder.getDescription() + "\" должно содержать от 1 до 200 символов."));
			fb.addField(fieldBuilder.build());
		}

		return fb;
	}

	private static FormBuilder createFormCOD(FormBuilder fb, List<Integer> wrongOfficeBranchesPropertiesNumbers,
	                                         List<Integer> ourTbCodesPropertiesNumbers)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.phisicgate.sbrf.ws.cod.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес web-сервиса адаптера АС «ЦОД».");
		fieldBuilder.setName("com.rssl.phisicgate.sbrf.ws.cod.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес web-сервиса адаптера АС «ЦОД».\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.connection.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с АС\"ЦОД\"");
		fieldBuilder.setName("com.rssl.gate.connection.timeout");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с АС\"ЦОД\"\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.use.depo.cod
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Интеграция с АС\"ЦОД\"");
		fieldBuilder.setName("com.rssl.gate.use.depo.cod");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		createFormSofiaVMS(fb, wrongOfficeBranchesPropertiesNumbers, ourTbCodesPropertiesNumbers);

		return fb;
	}

	private static FormBuilder createFormRetailV6(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.phizic.gate.rs-retail-v64.remote.host
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя домена или ip-адрес сервера, на котором установлен сервис RetailJNI");
		fieldBuilder.setName("com.rssl.phizic.gate.rs-retail-v64.remote.host");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Имя домена или ip-адрес сервера, на котором установлен сервис RetailJNI\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.gate.rs-retail-v64.remote.port
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порт сервера, на котором установлен сервис RetailJNI");
		fieldBuilder.setName("com.rssl.phizic.gate.rs-retail-v64.remote.port");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".\\d{0,10}$", "Поле \"Порт сервера, на котором установлен сервис RetailJNI\" должено содержать не более 10 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.gate.rs-retail-v64.version
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Версия системы RS-Retail");
		fieldBuilder.setName("com.rssl.phizic.gate.rs-retail-v64.version");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".\\d{0,4}", "Поле \"Версия системы RS-Retail\" должено содержать не более 4 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.office.code.region.number
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер ТБ, который автоматизирует данный узел");
		fieldBuilder.setName("com.rssl.gate.office.code.region.number");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "Поле \"Номер ТБ, который автоматизирует данный узел\" должено состоять из цифр и не должено быть длиннее, чем 2 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.iccs.our.bank.name
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название банка для отображения в штампе");
		fieldBuilder.setName("com.rssl.iccs.our.bank.name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Название банка для отображения в штампе\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.iccs.our.bank.bic
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("БИК банка");
		fieldBuilder.setName("com.rssl.iccs.our.bank.bic");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{9}", "Поле \"БИК банка\" должено состоять из цифр и должно быть 9 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.iccs.our.bank.coracc
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Корреспондентский счет банка");
		fieldBuilder.setName("com.rssl.iccs.our.bank.coracc");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{20}", "Поле \"Корреспондентский счет банка\" должено состоять из цифр и должно быть 20 цифр.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormGorod(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.gate.connection.user.name
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ПАН карты, под которой работает шлюз для взаимодействия с ИСППН «Город» ");
		fieldBuilder.setName("com.rssl.gate.connection.user.name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{16}", "Поле \"ПАН карты, под которой работает шлюз для взаимодействия с ИСППН «Город» \" должено состоять из цифр и должно быть 16 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.terminal.id
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор терминала ИСППН \"Город\"");
		fieldBuilder.setName("com.rssl.gate.terminal.id");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{10}", "Поле \"Идентификатор терминала ИСППН \"Город\"\" должено состоять из цифр и должно быть 10 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле gorod.host
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя домена или IP-адрес сервера, на котором установлен XML-шлюз для ИСППН «Город»");
		fieldBuilder.setName("gorod.host");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Имя домена или IP-адрес сервера, на котором установлен XML-шлюз для ИСППН «Город»\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле gorod.port
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порт, через который настроен шлюз для взаимодействия с ИСППН «Город»");
		fieldBuilder.setName("gorod.port");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Порт, через который настроен шлюз для взаимодействия с ИСППН «Город»\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле gorod.ikfl.pan
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ПАН карты, под которой работает шлюз для взаимодействия с ИСППН «Город»");
		fieldBuilder.setName("gorod.ikfl.pan");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{16}", "Поле \"ПАН карты, под которой работает шлюз для взаимодействия с ИСППН «Город»\" должено состоять из цифр и должно быть 16 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле gorod.ikfl.pin
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ПИН карты, под которой работает шлюз для взаимодействия с ИСППН «Город» ");
		fieldBuilder.setName("gorod.ikfl.pin");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{10}", "Поле \"ПИН карты, под которой работает шлюз для взаимодействия с ИСППН «Город» \" должено состоять из цифр и должно быть 10 цифр.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.gorod.is.debt.negative
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показатель отрицательной суммы задолженности");
		fieldBuilder.setName("com.rssl.gate.gorod.is.debt.negative");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormCPFL(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.gate.connection.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес веб-сервиса АС \"ЦПФЛ\"");
		fieldBuilder.setName("com.rssl.gate.connection.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес веб-сервиса АС \"ЦПФЛ\"\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.connection.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с БС \"ЦПФЛ\"");
		fieldBuilder.setName("com.rssl.gate.connection.timeout");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с БС \"ЦПФЛ\"\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormEnisey(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.gate.connection.host
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя домена или IP-адрес сервера, на котором установлен XML-шлюз для БС \"Енисей\".");
		fieldBuilder.setName("com.rssl.gate.connection.host");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Имя домена или IP-адрес сервера, на котором установлен XML-шлюз для БС \"Енисей\".\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.connection.port
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порт, через который настроен шлюз для взаимодействия с БС \"Енисей\".");
		fieldBuilder.setName("com.rssl.gate.connection.port");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Порт, через который настроен шлюз для взаимодействия с БС \"Енисей\".\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormSofia(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.gate.connection.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес веб-сервиса БС \"София\"");
		fieldBuilder.setName("com.rssl.gate.connection.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес веб-сервиса БС \"София\"\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormIQWave(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.gate.connection.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес веб-сервиса системы IQWave");
		fieldBuilder.setName("com.rssl.gate.connection.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес веб-сервиса системы IQWave\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.connection.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с АС \"IQWave\"");
		fieldBuilder.setName("com.rssl.gate.connection.timeout");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с АС \"IQWave\"\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.credits.routes.dictionary.path
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Путь к  файлу, в котором содержится справочник маршрутов кредитов");
		fieldBuilder.setName("com.rssl.gate.credits.routes.dictionary.path");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Путь к  файлу, в котором содержится справочник маршрутов кредитов\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.receivers.routes.dictionary.path
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Путь к файлу, в котором содержится справочник маршрутов получателей платежей");
		fieldBuilder.setName("com.rssl.gate.receivers.routes.dictionary.path");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Путь к файлу, в котором содержится справочник маршрутов получателей платежей\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormSofiaVMS(FormBuilder fb, List<Integer> wrongOfficeBranchesPropertiesNumbers,
	                                              List<Integer> ourTbCodesPropertiesNumbers)
	{
		FieldBuilder fieldBuilder;

		//Поле com.rssl.phisicgate.sbrf.ws.sbol.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес web-сервиса Сбербанка для передачи необработанных сообщений");
		fieldBuilder.setName("com.rssl.phisicgate.sbrf.ws.sbol.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес web-сервиса Сбербанка для передачи необработанных сообщений\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.office.code.region.number
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер ТБ, который автоматизирует данный узел");
		fieldBuilder.setName("com.rssl.gate.office.code.region.number");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "Поле \"Номер ТБ, который автоматизирует данный узел\" должено состоять из цифр и не должено быть длиннее, чем 2 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.gate.payments.AccountJurTransfer.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Способ формирования платежного поручения (перевод со счета клиента юридическому лицу в другой банк)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.AccountJurTransfer.usePaymentOrder");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение не входит во множество допустимых значений");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Способ формирования платежного поручения (перевод юридическому лицу cо счета на счет внутри Сбербанка России)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer.usePaymentOrder");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение не входит во множество допустимых значений");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.gate.payments.AccountRUSTaxPayment.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Способ формирования платежного поручения (оплата налогов со счета)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.AccountRUSTaxPayment.usePaymentOrder");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение не входит во множество допустимых значений");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Способ формирования платежного поручения (биллинговый платеж со счета)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment.usePaymentOrder");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение не входит во множество допустимых значений");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле wrong_office_region
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тербанк, для которого нужно проверять наличие ОСБ в справочнике");
		fieldBuilder.setName("wrong_office_region");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "Поле \"Тербанк, для которого нужно проверять наличие ОСБ в справочнике\" должено состоять из цифр и не должено быть длиннее, чем 2 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле right_office_branch
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ОСБ, которые используются для замены");
		fieldBuilder.setName("right_office_branch");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,4}", "Поле \"ОСБ, которые используются для замены\" должено состоять из цифр и не должено быть длиннее, чем 4 символов.")
		);
		fb.addField(fieldBuilder.build());

		for (Integer i : wrongOfficeBranchesPropertiesNumbers)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Код ОСБ");
			fieldBuilder.setName(EditAdapterSettingsOperation.WRONG_OFFICE_BRANCHES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator("\\d{1,4}", "Поле \"Код ОСБ\" должно содержать от 1 до 4 цифр."));
			fb.addField(fieldBuilder.build());
		}

		for (Integer i : ourTbCodesPropertiesNumbers)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Код тербанка");
			fieldBuilder.setName(EditAdapterSettingsOperation.OUR_TB_CODES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator("[-\\d]{1}\\d{1}", "Поле \"Код тербанка\" должно содержать 2 цифры."));
			fb.addField(fieldBuilder.build());
		}

		return fb;
	}

	private static FormBuilder createFormXBank(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//Поле com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес бэк-сервиса для получения информации и изменения статуса платежа");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес бэк-сервиса для получения информации и изменения статуса платежа\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.gate.office.code.region.number
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер ТБ, который автоматизирует данный узел");
		fieldBuilder.setName("com.rssl.gate.office.code.region.number");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "Поле \"Номер ТБ, который автоматизирует данный узел\" должено состоять из цифр и не должено быть длиннее, чем 2 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}
	//End of generated code

	private static FormBuilder createForm()
	{
		return new FormBuilder();
	}
}
