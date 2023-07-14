package com.rssl.phizic.web.migration;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ ����������� ��������
 */

public class ListMigrationClientsForm extends ListFormBase
{
	public static final String CLIENT_FIELD_NAME           = "client";
	public static final String DOCUMENT_FIELD_NAME         = "document";
	public static final String DEPARTMENT_FIELD_NAME       = "department";
	public static final String BIRTHDAY_FIELD_NAME         = "birthday";
	public static final String AGREEMENT_TYPE_FIELD_NAME   = "agreementType";
	public static final String AGREEMENT_NUMBER_FIELD_NAME = "agreementNumber";

	public static final Form FILTER_FORM = getFilterForm();
	private List<Department> departments;
	private List<CreationType> agreementTypes;

	private static Form getFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(FieldBuilder.buildStringField(CLIENT_FIELD_NAME,           "������",
				new RegexpFieldValidator("[�-��-߸�a-zA-Z- ]{3,255}", "����������, ������� ��� ������� �� ����� 3 ��������, ���������� ����� �������� ��� ���������� ��������, ����� ��� ������")));
		formBuilder.addField(FieldBuilder.buildStringField(DOCUMENT_FIELD_NAME,         "�������� (���)"));
		formBuilder.addField(FieldBuilder.buildStringField(DEPARTMENT_FIELD_NAME,       "��������"));
		formBuilder.addField(FieldBuilder.buildStringField(AGREEMENT_NUMBER_FIELD_NAME, "����� ��������"));
		formBuilder.addField(FieldBuilder.buildDateField(BIRTHDAY_FIELD_NAME,           "���� ��������"));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AGREEMENT_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setParser(new EnumParser<CreationType>(CreationType.class));
		fieldBuilder.addValidators(new EnumFieldValidator<CreationType>(CreationType.class, "����������� ��� ��������."));
		formBuilder.addField(fieldBuilder.build());

		MultiFieldsValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIELD_NAME, CLIENT_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_FIELD_NAME, DOCUMENT_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(AGREEMENT_NUMBER_FIELD_NAME, AGREEMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("����������, ��������� ���� �� ���� �� ����� �������: ������, ��������(���), ����� ��������.");
		formBuilder.addFormValidators(requiredMultiFieldValidator);

		return formBuilder.build();
	}

	/**
	 * ����� ������ ������������� ��� ����������
	 * @param departments ����� �������������
	 */
	public void setDepartments(List<Department> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}

	/**
	 * @return ������ ������������� ��� ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<Department> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * ����� ������ ����� ��������� ��� ����������
	 * @param agreementTypes ����
	 */
	public void setAgreementTypes(List<CreationType> agreementTypes)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.agreementTypes = agreementTypes;
	}

	/**
	 * @return ������ ����� ��������� ��� ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<CreationType> getAgreementTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return agreementTypes;
	}
}
