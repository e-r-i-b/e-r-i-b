package com.rssl.phizic.web.client.loyalty;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 15.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramInfoForm extends EditFormBase
{
	private static final String LOYALTY_FILTER_DATE_FORMAT = "dd/MM/yyyy";
	private static final Long MAX_PERIOD = 31 * 24 * 3600 * 1000L; //31 ���� � �������������

	private LoyaltyProgramLink link;
	private List<LoyaltyProgramOperation> operations;
    private boolean regError = false; //�������, ��� ����������� ������������� �������(���� �������� ������ ��������)
    private boolean isBackError = false;
    private List<String> validateErrors;  //������ ��������� ����� �������
    private String callCentrePassw; //������ ����������� ������
	private boolean overMaxItem = false; //������� ����, ��� ������ ������ 200 �������� ��� �����������.
	private boolean showPeriod = false; //������� � ���, ���������� ������ � ������� ��� ���. � ��������� ������ ������������ �����.

	public static final Form FILTER_FORM = createFilterForm();

	public LoyaltyProgramLink getLink()
	{
		return link;
	}

	public void setLink(LoyaltyProgramLink link)
	{
		this.link = link;
	}

	public List<LoyaltyProgramOperation> getOperations()
	{
		return operations;
	}

	public void setOperations(List<LoyaltyProgramOperation> operations)
	{
		this.operations = operations;
	}

	public boolean getBackError()
	{
		return isBackError;
	}

	public void setBackError(boolean backError)
	{
		isBackError = backError;
	}

	public List<String> getValidateErrors()
	{
		return validateErrors;
	}

	public void setValidateErrors(List<String> validateErrors)
	{
		this.validateErrors = validateErrors;
	}

	public boolean isRegError()
	{
		return regError;
	}

	public void setRegError(boolean regError)
	{
		this.regError = regError;
	}

	public boolean isOverMaxItem()
	{
		return overMaxItem;
	}

	public void setOverMaxItem(boolean overMaxItem)
	{
		this.overMaxItem = overMaxItem;
	}

	public String getCallCentrePassw()
	{
		return callCentrePassw;
	}

	public void setCallCentrePassw(String callCentrePassw)
	{
		this.callCentrePassw = callCentrePassw;
	}

	public boolean getShowPeriod()
	{
		return showPeriod;
	}

	public void setShowPeriod(boolean showPeriod)
	{
		this.showPeriod = showPeriod;
	}


	private static Form createFilterForm()
	{
		DateParser dataParser = new DateParser(LOYALTY_FILTER_DATE_FORMAT);

		DateFieldValidator dataValidator = new DateFieldValidator(LOYALTY_FILTER_DATE_FORMAT);
		dataValidator.setMessage("������� ���� � ���� ������ � ������� ��/��/����.");

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(LOYALTY_FILTER_DATE_FORMAT);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.setName("toDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		DatePeriodMultiFieldValidator datePeriodValidator = new DatePeriodMultiFieldValidator();
		datePeriodValidator.setMaxTimeSpan(MAX_PERIOD);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, "fromDate");
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, "toDate");
		datePeriodValidator.setMessage("�� ������ ��������� ����� �������� �� ��������� ����� �� ������, �� ����������� 31 ����.");

		formBuilder.addFormValidators(compareValidator, datePeriodValidator);

		return formBuilder.build();
	}
}
