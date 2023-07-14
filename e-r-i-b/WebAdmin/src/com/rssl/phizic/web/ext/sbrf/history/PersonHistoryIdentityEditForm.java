package com.rssl.phizic.web.ext.sbrf.history;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.List;

/**
 * User: moshenko
 * Date: 22.03.2013
 * Time: 14:14:48
 * �����. �������������� ������� ��������� ����������������� ������ �������
 */
public class PersonHistoryIdentityEditForm extends EditFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	/**
	 * ������ ��� ��������� ��� ������������� ������������ ����.
	 */
	private static final String EMPTY_FIELD_MESSAGE_TEMPLATE = "���� \"%s\" ����������� ��� ����������, ������� �������� ������� ����";

	private List<String> documentTypes;
	private Long person;
	private ActivePerson activePerson;
	private boolean modified = false;
	private Long identityId;

	public Long getIdentityId()
	{
		return identityId;
	}

	public void setIdentityId(Long identityId)
	{
		this.identityId = identityId;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	public List<String> getDocumentTypes()
	{
		return documentTypes;
	}

	public void setDocumentTypes(List<String> documentTypes)
	{
		this.documentTypes = documentTypes;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser dateParser = new DateParser(DATESTAMP);
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("surName");
		fieldBuilder.setDescription("���");
		fieldBuilder.addValidators(new RequiredFieldValidator(getEmptyMessage(fieldBuilder)),
				new LengthFieldValidator(new BigInteger("120")));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("firstName");
		fieldBuilder.setDescription("�������");
		fieldBuilder.addValidators(new RequiredFieldValidator(getEmptyMessage(fieldBuilder)),
				new LengthFieldValidator(new BigInteger("120")));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("patrName");
		fieldBuilder.setDescription("��������");
		fieldBuilder.addValidators(new LengthFieldValidator(new BigInteger("120")));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ��������");
		fieldBuilder.setName("birthDay");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(getEmptyMessage(fieldBuilder)),
				new DateFieldValidator(DATESTAMP, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������");
		fieldBuilder.setName("documentType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator(getEmptyMessage(fieldBuilder)));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("documentSeries");
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(getEmptyMessage(fieldBuilder)),
				new RegexpFieldValidator(".{0,12}", "���� ����� ��������� �� ������ ��������� 12 ��������"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*", "���� ����� ��������� �� ������ ��������� ������������")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("documentNumber");
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(getEmptyMessage(fieldBuilder)),
				new RegexpFieldValidator("\\d*", "���� ����� ��������� ������ ��������� ������ �����"),
				new RegexpFieldValidator(".{0,12}", "���� ����� ��������� �� ������ ��������� 12 ����")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * ���������� ��������� �� ������ ��� ������������� ������������ ����.
	 *
	 * @param fb ���������� ����.
	 * @return ���������.
	 */
	private static String getEmptyMessage(FieldBuilder fb)
	{
		return String.format(EMPTY_FIELD_MESSAGE_TEMPLATE, fb.getDescription());
	}
}
