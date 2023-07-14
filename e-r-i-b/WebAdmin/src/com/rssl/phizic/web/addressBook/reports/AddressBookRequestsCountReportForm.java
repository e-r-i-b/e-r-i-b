package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.addressBook.reports.RequestsCountReportEntity;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����������� ������ "�� �������� � �������"
 */

public class AddressBookRequestsCountReportForm extends ListFormBase<RequestsCountReportEntity>
{
	public static final String COUNT_FIELD_NAME     = "count";
	public static final String FROM_DATE_FIELD_NAME = "fromDate";
	public static final String TO_DATE_FIELD_NAME   = "toDate";

	private static final Form FILTER_FORM = createForm();

	/**
	 * @return ���������� ����� ����������
	 */
	public static Form getFilterForm()
	{
		return FILTER_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(FieldBuilder.buildLongField(COUNT_FIELD_NAME,     "���������� ��������", new RequiredFieldValidator("���������� ������ ���������� ��������.")));
		formBuilder.addField(FieldBuilder.buildDateField(FROM_DATE_FIELD_NAME, "������ �������",      new RequiredFieldValidator("���������� ������ ������ �������.")));
		formBuilder.addField(FieldBuilder.buildDateField(TO_DATE_FIELD_NAME,   "��������� �������",   new RequiredFieldValidator("���������� ������ ��������� �������.")));

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareDateValidator.setMessage("�������� ���� ������ ���� ������ ���������!");
		formBuilder.addFormValidators(compareDateValidator);
		return formBuilder.build();
	}
}
