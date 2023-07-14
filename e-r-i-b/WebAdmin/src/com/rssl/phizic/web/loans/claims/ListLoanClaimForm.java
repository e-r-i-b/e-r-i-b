package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;

import java.util.List;

/**
 * @author gladishev
 * @ created 20.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanClaimForm extends ListLimitActionForm
{
	private List<LoanKind> loanKinds;
	private List<LoanOffice> loanOffices;
	private boolean employeeFromMainOffice; //������� ��������� �� ��������� �����

	public List<LoanKind> getLoanKinds()
	{
		return loanKinds;
	}

	public void setLoanKinds(List<LoanKind> loanKinds)
	{
		this.loanKinds = loanKinds;
	}

	public List<LoanOffice> getLoanOffices()
	{
		return loanOffices;
	}

	public void setLoanOffices(List<LoanOffice> loanOffices)
	{
		this.loanOffices = loanOffices;
	}

	public boolean isEmployeeFromMainOffice()
	{
		return employeeFromMainOffice;
	}

	public void setEmployeeFromMainOffice(boolean employeeFromMainOffice)
	{
		this.employeeFromMainOffice = employeeFromMainOffice;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("����� ������");
	    fieldBuilder.setName("claimNumber");
	    fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("���� (�)");
	    fieldBuilder.setName("fromDate");
	    fieldBuilder.setType("date");
        fieldBuilder.setValidators(new DateFieldValidator());
        fieldBuilder.setParser(new DateParser());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("���� (��)");
	    fieldBuilder.setName("toDate");
	    fieldBuilder.setType("date");
        fieldBuilder.setValidators(new DateFieldValidator());
        fieldBuilder.setParser(new DateParser());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������");
	    fieldBuilder.setName("state");
	    fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������� ��������� ������");
		fieldBuilder.setName("clientType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� � ����� �������� (���������)");
		fieldBuilder.setName("document");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �������������� �������� ������� (�)");
		fieldBuilder.setName("fromClientRequestAmount");
	    fieldBuilder.setType("money");
	    fieldBuilder.setValidators(new MoneyFieldValidator());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �������������� �������� ������� (��)");
		fieldBuilder.setName("toClientRequestAmount");
	    fieldBuilder.setType("money");
	    fieldBuilder.setValidators(new MoneyFieldValidator());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName("loanKind");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �����");
		fieldBuilder.setName("office");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

        return formBuilder.build();
    }
}
