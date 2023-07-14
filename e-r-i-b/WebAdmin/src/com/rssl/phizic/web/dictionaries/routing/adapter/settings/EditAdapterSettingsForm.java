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
	 * ���������� ���������������� �����.
	 * @return �����.
	 * @param nodeType ��� ����.
	 * @return �����.
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

		throw new FormException("������� ������������ �������� ������� " + nodeType.name());
	}

	/**
	 * ���������� ���������������� �����.
	 *
	 * @param nodeType ��� ����.
	 * @param wrongOfficeBranchesPropertiesNumbers - ������ �������� ��� ��������� wrong_office_branches
	 * @param ourTbCodesPropertiesNumbers - ������ �������� ��� ��������� ourTbCodesPropertiesNumbers
	 * @param fieldsExcludedMessagesPropertiesNumber - ������ �������� ��� ��������� excluded.messages
	 * @return �����.
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
		throw new FormException("������� ������������ �������� ������� " + nodeType.name());
	}

	/**
	 * ������������� ��������.
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
		String loggingTypeDescriptionPrefix = isExtended ? "(����������� �����������)" : "(����������� �����������)";

		FieldBuilder fieldBuilder;
		//���� com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Core
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"����\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Core");
		fieldBuilder.setType("integer");
		RangeFieldValidator validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Gate
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"����\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Gate");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Scheduler
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"���������� ����������\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Scheduler");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.<extendedPrefix>level.Cache
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"������� �����������\"" + loggingTypeDescriptionPrefix);
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog." + extendedPrefix + "level.Cache");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.messagesLog.<extendedPrefix>level.<nodeType.system>
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������� �������������� ���� � " + nodeType.getDescription() + loggingTypeDescriptionPrefix);
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
		//���� com.rssl.phizic.logging.writers.SystemLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ����������� ������� ��������� ��������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.SystemLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� ����������� ������� ��������� ��������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.backup");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		for (Integer i : fieldsExcludedMessagesPropertiesNumber)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��������� ����������� ���������");
			fieldBuilder.setName(EditAdapterSettingsOperation.EXCLUDED_MESSAGES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator(".{1,200}", "���� \"" + fieldBuilder.getDescription() + "\" ������ ��������� �� 1 �� 200 ��������."));
			fb.addField(fieldBuilder.build());
		}

		return fb;
	}

	private static FormBuilder createFormCOD(FormBuilder fb, List<Integer> wrongOfficeBranchesPropertiesNumbers,
	                                         List<Integer> ourTbCodesPropertiesNumbers)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.phisicgate.sbrf.ws.cod.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� web-������� �������� �� ���Ļ.");
		fieldBuilder.setName("com.rssl.phisicgate.sbrf.ws.cod.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� web-������� �������� �� ���Ļ.\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.connection.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � ��\"���\"");
		fieldBuilder.setName("com.rssl.gate.connection.timeout");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � ��\"���\"\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.use.depo.cod
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� � ��\"���\"");
		fieldBuilder.setName("com.rssl.gate.use.depo.cod");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		createFormSofiaVMS(fb, wrongOfficeBranchesPropertiesNumbers, ourTbCodesPropertiesNumbers);

		return fb;
	}

	private static FormBuilder createFormRetailV6(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.phizic.gate.rs-retail-v64.remote.host
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������ ��� ip-����� �������, �� ������� ���������� ������ RetailJNI");
		fieldBuilder.setName("com.rssl.phizic.gate.rs-retail-v64.remote.host");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"��� ������ ��� ip-����� �������, �� ������� ���������� ������ RetailJNI\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.gate.rs-retail-v64.remote.port
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������, �� ������� ���������� ������ RetailJNI");
		fieldBuilder.setName("com.rssl.phizic.gate.rs-retail-v64.remote.port");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".\\d{0,10}$", "���� \"���� �������, �� ������� ���������� ������ RetailJNI\" ������� ��������� �� ����� 10 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.gate.rs-retail-v64.version
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������� RS-Retail");
		fieldBuilder.setName("com.rssl.phizic.gate.rs-retail-v64.version");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".\\d{0,4}", "���� \"������ ������� RS-Retail\" ������� ��������� �� ����� 4 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.office.code.region.number
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��, ������� �������������� ������ ����");
		fieldBuilder.setName("com.rssl.gate.office.code.region.number");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "���� \"����� ��, ������� �������������� ������ ����\" ������� �������� �� ���� � �� ������� ���� �������, ��� 2 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.iccs.our.bank.name
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ����� ��� ����������� � ������");
		fieldBuilder.setName("com.rssl.iccs.our.bank.name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"�������� ����� ��� ����������� � ������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.iccs.our.bank.bic
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����");
		fieldBuilder.setName("com.rssl.iccs.our.bank.bic");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{9}", "���� \"��� �����\" ������� �������� �� ���� � ������ ���� 9 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.iccs.our.bank.coracc
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������������� ���� �����");
		fieldBuilder.setName("com.rssl.iccs.our.bank.coracc");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{20}", "���� \"����������������� ���� �����\" ������� �������� �� ���� � ������ ���� 20 ����.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormGorod(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.gate.connection.user.name
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������ ");
		fieldBuilder.setName("com.rssl.gate.connection.user.name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{16}", "���� \"��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������ \" ������� �������� �� ���� � ������ ���� 16 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.terminal.id
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ��������� ����� \"�����\"");
		fieldBuilder.setName("com.rssl.gate.terminal.id");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{10}", "���� \"������������� ��������� ����� \"�����\"\" ������� �������� �� ���� � ������ ���� 10 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� gorod.host
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������ ��� IP-����� �������, �� ������� ���������� XML-���� ��� ����� ������");
		fieldBuilder.setName("gorod.host");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"��� ������ ��� IP-����� �������, �� ������� ���������� XML-���� ��� ����� ������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� gorod.port
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����, ����� ������� �������� ���� ��� �������������� � ����� ������");
		fieldBuilder.setName("gorod.port");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����, ����� ������� �������� ���� ��� �������������� � ����� ������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� gorod.ikfl.pan
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������");
		fieldBuilder.setName("gorod.ikfl.pan");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{16}", "���� \"��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������\" ������� �������� �� ���� � ������ ���� 16 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� gorod.ikfl.pin
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������ ");
		fieldBuilder.setName("gorod.ikfl.pin");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{10}", "���� \"��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������ \" ������� �������� �� ���� � ������ ���� 10 ����.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.gorod.is.debt.negative
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ������������� ����� �������������");
		fieldBuilder.setName("com.rssl.gate.gorod.is.debt.negative");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormCPFL(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.gate.connection.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� �� \"����\"");
		fieldBuilder.setName("com.rssl.gate.connection.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� �� \"����\"\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.connection.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � �� \"����\"");
		fieldBuilder.setName("com.rssl.gate.connection.timeout");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � �� \"����\"\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormEnisey(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.gate.connection.host
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������ ��� IP-����� �������, �� ������� ���������� XML-���� ��� �� \"������\".");
		fieldBuilder.setName("com.rssl.gate.connection.host");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"��� ������ ��� IP-����� �������, �� ������� ���������� XML-���� ��� �� \"������\".\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.connection.port
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����, ����� ������� �������� ���� ��� �������������� � �� \"������\".");
		fieldBuilder.setName("com.rssl.gate.connection.port");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����, ����� ������� �������� ���� ��� �������������� � �� \"������\".\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormSofia(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.gate.connection.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� �� \"�����\"");
		fieldBuilder.setName("com.rssl.gate.connection.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� �� \"�����\"\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormIQWave(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.gate.connection.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ������� IQWave");
		fieldBuilder.setName("com.rssl.gate.connection.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ������� IQWave\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.connection.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � �� \"IQWave\"");
		fieldBuilder.setName("com.rssl.gate.connection.timeout");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � �� \"IQWave\"\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.credits.routes.dictionary.path
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �  �����, � ������� ���������� ���������� ��������� ��������");
		fieldBuilder.setName("com.rssl.gate.credits.routes.dictionary.path");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"���� �  �����, � ������� ���������� ���������� ��������� ��������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.receivers.routes.dictionary.path
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� � �����, � ������� ���������� ���������� ��������� ����������� ��������");
		fieldBuilder.setName("com.rssl.gate.receivers.routes.dictionary.path");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"���� � �����, � ������� ���������� ���������� ��������� ����������� ��������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static FormBuilder createFormSofiaVMS(FormBuilder fb, List<Integer> wrongOfficeBranchesPropertiesNumbers,
	                                              List<Integer> ourTbCodesPropertiesNumbers)
	{
		FieldBuilder fieldBuilder;

		//���� com.rssl.phisicgate.sbrf.ws.sbol.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� web-������� ��������� ��� �������� �������������� ���������");
		fieldBuilder.setName("com.rssl.phisicgate.sbrf.ws.sbol.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� web-������� ��������� ��� �������� �������������� ���������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.office.code.region.number
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��, ������� �������������� ������ ����");
		fieldBuilder.setName("com.rssl.gate.office.code.region.number");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "���� \"����� ��, ������� �������������� ������ ����\" ������� �������� �� ���� � �� ������� ���� �������, ��� 2 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.gate.payments.AccountJurTransfer.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������������ ���������� ��������� (������� �� ����� ������� ������������ ���� � ������ ����)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.AccountJurTransfer.usePaymentOrder");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� �� ������ �� ��������� ���������� ��������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������������ ���������� ��������� (������� ������������ ���� c� ����� �� ���� ������ ��������� ������)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer.usePaymentOrder");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� �� ������ �� ��������� ���������� ��������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.gate.payments.AccountRUSTaxPayment.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������������ ���������� ��������� (������ ������� �� �����)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.AccountRUSTaxPayment.usePaymentOrder");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� �� ������ �� ��������� ���������� ��������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment.usePaymentOrder
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������������ ���������� ��������� (����������� ������ �� �����)");
		fieldBuilder.setName("com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment.usePaymentOrder");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� �� ������ �� ��������� ���������� ��������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� wrong_office_region
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������, ��� �������� ����� ��������� ������� ��� � �����������");
		fieldBuilder.setName("wrong_office_region");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "���� \"�������, ��� �������� ����� ��������� ������� ��� � �����������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 2 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� right_office_branch
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���, ������� ������������ ��� ������");
		fieldBuilder.setName("right_office_branch");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,4}", "���� \"���, ������� ������������ ��� ������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 4 ��������.")
		);
		fb.addField(fieldBuilder.build());

		for (Integer i : wrongOfficeBranchesPropertiesNumbers)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��� ���");
			fieldBuilder.setName(EditAdapterSettingsOperation.WRONG_OFFICE_BRANCHES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator("\\d{1,4}", "���� \"��� ���\" ������ ��������� �� 1 �� 4 ����."));
			fb.addField(fieldBuilder.build());
		}

		for (Integer i : ourTbCodesPropertiesNumbers)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��� ��������");
			fieldBuilder.setName(EditAdapterSettingsOperation.OUR_TB_CODES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator("[-\\d]{1}\\d{1}", "���� \"��� ��������\" ������ ��������� 2 �����."));
			fb.addField(fieldBuilder.build());
		}

		return fb;
	}

	private static FormBuilder createFormXBank(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;
		//���� com.rssl.phizic.wsgate.listener.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-������� ��� ��������� ���������� � ��������� ������� �������");
		fieldBuilder.setName("com.rssl.phizic.wsgate.listener.url");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� ���-������� ��� ��������� ���������� � ��������� ������� �������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.gate.office.code.region.number
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��, ������� �������������� ������ ����");
		fieldBuilder.setName("com.rssl.gate.office.code.region.number");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,2}", "���� \"����� ��, ������� �������������� ������ ����\" ������� �������� �� ���� � �� ������� ���� �������, ��� 2 ��������.")
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
