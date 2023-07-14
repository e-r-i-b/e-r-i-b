package com.rssl.phizic.web.common.client.loans;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.forms.validators.DuplicateAliasValidator;
import com.rssl.phizic.business.ermb.forms.validators.IdenticalToSmsCommandValidator;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.EarlyRepayment;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.ext.sbrf.loans.LoanAccountInfo;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanInfoForm extends EditFormBase
{
	public static final Form NAME_FORM     = ShowLoanInfoForm.createNameForm();
	public static final Form FILTER_FORM     = ShowLoanInfoForm.createFilterForm();

	private static final String DATE_FORMAT = "MM/yyyy";

	private LoanLink loanLink;
	private List<LoanLink> anotherLoans;
	private ScheduleAbstract scheduleAbstract;
	//��������� �� ������ ��� ����������� ������������
	private String abstractMsgError;
    private List<LoanAccountInfo> loanAccountInfo;
	private boolean isEarlyLoanRepaymentAllowed;
	private boolean isEarlyLoanRepaymentPossible;
	private List<EarlyRepayment> earlyRepayments;
	private String terminationRequestId;
	private String applicationNumber;
	private ConfirmStrategy confirmStrategy;
	private ConfirmableObject confirmableObject;
	//������� ������������� ������������ ������ ��� �����
	private boolean oneTimePassword;
	//������������� �� ��� �������� ������
	private boolean confirmEntered;
	private boolean earlyRepCancelAllowed;

	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	public void setLoanLink(LoanLink loanLink)
	{
		this.loanLink = loanLink;
	}

	public ScheduleAbstract getScheduleAbstract()
	{
		return scheduleAbstract;
	}

	public void setScheduleAbstract(ScheduleAbstract scheduleAbstract)
	{
		this.scheduleAbstract = scheduleAbstract;
	}

	public List<LoanLink> getAnotherLoans()
	{
		return anotherLoans;
	}

	public void setAnotherLoans(List<LoanLink> anotherLoans)
	{
		this.anotherLoans = anotherLoans;
	}

	private static Form createNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("loanName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "������������ ������ ���� �� ����� 128 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	protected Form createLoanAliasForm() throws BusinessException
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("SMS-�������������");
		fieldBuilder.setName("clientSmsAlias");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DuplicateAliasValidator(this.getId(), LoanLink.class),
				new IdenticalToSmsCommandValidator(),
				//����� ����� �������� ������ �� ����, � ����� ������ ����� �.�. �� ����� 4 ��������.
				//��� �� ����� ����� �������� �� ���� �/��� ����, � ����� ������ �.�. �� ������ 3 ��������.
				new RegexpFieldValidator("\\d{4,}|(?=.{3,})(\\d*[A-z�-���][A-z�-���0-9]*)", "SMS-������������� ������ ��������� " +
						"������ ����� � ����� � ���� �� ������ 3 �������� (�� ������ 4, ���� �������� ������ �����)")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������");
	    fieldBuilder.setName("fromPeriod");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new DateFieldValidator(DATE_FORMAT, "������� ���� � ���� ������ � ������� ��/����."));
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������");
	    fieldBuilder.setName("toPeriod");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new DateFieldValidator(DATE_FORMAT, "������� ���� � ���� ������ � ������� ��/����."));
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		compareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}

    public List<LoanAccountInfo> getLoanAccountInfo()
    {
        return loanAccountInfo;
    }

    public void setLoanAccountInfo(List<LoanAccountInfo> loanAccountInfo)
    {
        this.loanAccountInfo = loanAccountInfo;
    }

	public boolean isEarlyLoanRepaymentAllowed()
	{
		return isEarlyLoanRepaymentAllowed;
	}

	public void setEarlyLoanRepaymentAllowed(boolean earlyLoanRepaymentAllowed)
	{
		isEarlyLoanRepaymentAllowed = earlyLoanRepaymentAllowed;
	}

	public List<EarlyRepayment> getEarlyRepayments()
	{
		return earlyRepayments;
	}

	public void setEarlyRepayments(List<EarlyRepayment> earlyRepayments)
	{
		this.earlyRepayments = earlyRepayments;
	}

	public boolean isEarlyLoanRepaymentPossible()
	{
		return isEarlyLoanRepaymentPossible;
	}

	public void setEarlyLoanRepaymentPossible(boolean earlyLoanRepaymentPossible)
	{
		isEarlyLoanRepaymentPossible = earlyLoanRepaymentPossible;
	}
	public String getTerminationRequestId()
	{
		return terminationRequestId;
	}

	public void setTerminationRequestId(String terminationRequestId)
	{
		this.terminationRequestId = terminationRequestId;
	}

	public String getApplicationNumber()
	{
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber)
	{
		this.applicationNumber = applicationNumber;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public boolean isOneTimePassword()
	{
		return oneTimePassword;
	}

	public void setOneTimePassword(boolean oneTimePassword)
	{
		this.oneTimePassword = oneTimePassword;
	}

	public boolean isConfirmEntered()
	{
		return confirmEntered;
	}

	public void setConfirmEntered(boolean confirmEntered)
	{
		this.confirmEntered = confirmEntered;
	}

	public boolean isEarlyRepCancelAllowed()
	{
		return earlyRepCancelAllowed;
	}

	public void setEarlyRepCancelAllowed(boolean earlyRepCancelAllowed)
	{
		this.earlyRepCancelAllowed = earlyRepCancelAllowed;
	}

}
