package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 22.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditArchivingMailForm extends EditPropertiesFormBase
{
	private static Map<String,String> archiveTypes = new HashMap<String,String>();
	static
	{
		archiveTypes.put("incoming","��������");
		archiveTypes.put("outgoing","���������");
		archiveTypes.put("deleted","���������");
	}

	private static final String DATE_FORMAT = "HH:mm";
	private static Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName(MailConfig.ARCHIVE_PATH_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fb.addFields(getArchiveFields("com.rssl.iccs.mail.archive.incoming"));
		fb.addFields(getArchiveFields("com.rssl.iccs.mail.archive.outgoing"));
		fb.addFields(getArchiveFields("com.rssl.iccs.mail.archive.deleted"));

		return fb.build();
	}

	private static List<Field> getArchiveFields(String archiveType)
	{
		String description = archiveTypes.get(archiveType);
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������� "+ description +" �����");
		fieldBuilder.setName(archiveType + ".mail.period.type");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators( new RequiredFieldValidator("�� ����������� ������� ������ ��������� " + description + " �����. ����������, �������� ������ ������."),
									new ChooseValueValidator(ListUtil.fromArray(new String[]{"DAY", "WEEK", "MONTH"})));
		fb.addField(fieldBuilder.build());

		NumericRangeValidator nrvDAY = new NumericRangeValidator();
		nrvDAY.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,"1");
		nrvDAY.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,"31");
		nrvDAY.setEnabledExpression(new RhinoExpression("form['" + archiveType + ".mail.period.type']=='DAY'"));
		nrvDAY.setMessage("�������� ������������ � ���� ������ ���� � ��������� �� 1 �� 31. �������� ������������� ��������� " + description + " �����");

		NumericRangeValidator nrvWEEK = new NumericRangeValidator();
		nrvWEEK.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,"1");
		nrvWEEK.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,"4");
		nrvWEEK.setEnabledExpression(new RhinoExpression("form['" + archiveType + ".mail.period.type']=='WEEK'"));
		nrvWEEK.setMessage("�������� ������������ � ������� ������ ���� � ��������� �� 1 �� 4. �������� ������������� ��������� " + description + " �����");

		NumericRangeValidator nrvMONTH = new NumericRangeValidator();
		nrvMONTH.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,"1");
		nrvMONTH.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,"12");
		nrvMONTH.setEnabledExpression(new RhinoExpression("form['" + archiveType + ".mail.period.type']=='MONTH'"));
		nrvMONTH.setMessage("�������� ������������ � ������� ������ ���� � ��������� �� 1 �� 12. �������� ������������� ��������� " + description + " �����");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ��������� " + description + " �����");
		fieldBuilder.setName(archiveType + ".mail.period");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									nrvDAY, nrvWEEK, nrvMONTH);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� " + description + " �����");
		fieldBuilder.setName(archiveType + ".mail.archTime");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new DateFieldValidator(DATE_FORMAT, "����� ������ ���� � ������� ��:��"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������� " + description + " �����");
		fieldBuilder.setName(archiveType + ".mail.lastMonth");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new RegexpFieldValidator("\\d{1,3}"));
		fb.addField(fieldBuilder.build());

	    return fb.build().getFields();
	}
}
