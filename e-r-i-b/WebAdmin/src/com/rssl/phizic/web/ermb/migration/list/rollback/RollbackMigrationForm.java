package com.rssl.phizic.web.ermb.migration.list.rollback;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * ����� ������ �������� ���/��� � ����
 * @author Puzikov
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 */

public class RollbackMigrationForm extends FilterActionForm
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private static final String TIME_FORMAT = "HH:mm:ss";

	private static final String DATE_FIELD = "date";
	private static final String TIME_FIELD = "time";

	/**
	 * ������ ������
	 */
	private String status;

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;
		DateParser dateParser = new DateParser(DATE_FORMAT);
		DateParser timeParser = new DateParser(TIME_FORMAT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(DATE_FIELD);
		fieldBuilder.setDescription("���� ������ ��������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� ���� ������ ��������"),
				new DateFieldValidator(DATE_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(TIME_FIELD);
		fieldBuilder.setDescription("����� ������ ��������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� ����� ������ ��������"),
				new DateFieldValidator(TIME_FORMAT, "����� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
