package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.person.Person;

/**
 * @author Roshka
 * @ created 29.11.2006
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"JavaDoc"})
public class ViewDocumentForm extends EditDocumentForm
{
	private boolean archive;
	private boolean comment;
    private boolean printDocuments;
	private boolean isExternalAccountPaymentAllowed;

	private String guid;
	private String html;
	private String title;
	private String templateName; // ��� �������, � ������ ���� ������ ������� ���(������) �������

	private Person owner;
	private Department department;
	private boolean canWithdraw = false;

	//����, ����� ��� ������. ������������ � ����������� �� ��������� �������
	private String dateString;
	//��������� �����
	private String bankName;
	private String bankBIC;
	private String bankCorrAcc;

	// ����� �� �������� ��� (�� ��������� ���)
	private boolean printCheck = false;

	private boolean externalPayment = false; // ������ �� ��� (��������-�������, ���), �� ��������� ���
	private boolean fnsPayment = false; // ������ �� ���
	private String providerName; // �������� ����������

	private String additionPaymentInfo; // �������������� ���� �� �������

	private boolean autopayable = false; // ������� ����������� �������� ����������� �� ������� ���������.

	public boolean isAutopayable()
	{
		return autopayable;
	}

	public void setAutopayable(boolean autopayable)
	{
		this.autopayable = autopayable;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Person getOwner()
	{
		return owner;
	}

	public void setOwner(Person owner)
	{
		this.owner = owner;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment (Department department)
	{
		this.department = department;
	}

    public boolean getCanWithdraw()
	{
		return canWithdraw;
	}

	public void setCanWithdraw(boolean canWithdraw)
	{
		this.canWithdraw = canWithdraw;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getBankBIC()
	{
		return bankBIC;
	}

	public void setBankBIC(String bankBIC)
	{
		this.bankBIC = bankBIC;
	}

	public String getDateString()
	{
		return dateString;
	}

	public void setDateString(String dateString)
	{
		this.dateString = dateString;
	}

	public static Form REFUSE_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		// ������� ������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ������");
		fieldBuilder.setName("selectedRefusingReason");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �������");
		fieldBuilder.setName("otherRefusingReason");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());
		return fb.build();
	}

	public boolean isPrintDocuments()
	{
		return printDocuments;
	}

	public void setPrintDocuments(boolean printDocuments)
	{
		this.printDocuments = printDocuments;
	}
	
	public boolean isArchive()
	{
		return archive;
	}

	public void setArchive(boolean archive)
	{
		this.archive = archive;
	}

	public boolean isComment()
	{
		return comment;
	}

	public void setComment(boolean comment)
	{
		this.comment = comment;
	}

	public String getBankCorrAcc()
	{
		return bankCorrAcc;
	}

	public void setBankCorrAcc(String bankCorrAcc)
	{
		this.bankCorrAcc = bankCorrAcc;
	}

	public void setPrintCheck(boolean printCheck)
	{
		this.printCheck = printCheck;
	}

	public boolean getPrintCheck()
	{
		return printCheck;
	}

	public boolean getExternalPayment()
	{
		return externalPayment;
	}

	public void setExternalPayment(boolean externalPayment)
	{
		this.externalPayment = externalPayment;
	}

	public boolean getFnsPayment()
	{
		return fnsPayment;
	}

	public void setFnsPayment(boolean fnsPayment)
	{
		this.fnsPayment = fnsPayment;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public boolean isExternalAccountPaymentAllowed()
	{
		return isExternalAccountPaymentAllowed;
	}

	public void setExternalAccountPaymentAllowed(boolean externalAccountPaymentAllowed)
	{
		isExternalAccountPaymentAllowed = externalAccountPaymentAllowed;
	}

	public String getAdditionPaymentInfo()
	{
		return additionPaymentInfo;
	}

	public void setAdditionPaymentInfo(String additionPaymentInfo)
	{
		this.additionPaymentInfo = additionPaymentInfo;
	}
}
