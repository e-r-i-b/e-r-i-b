package com.rssl.phizic.web.common.client.offert;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;

/**
 * @author Moshenko
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CreditOffertConditionsForm extends EditFormBase
{
	public static final String MAIL_ADDRESS = "field(mailAddress)";
	public static final String MAIL_SUBJECT = "field(mailSubject)";
	public static final String MAIL_TEXT = "field(mailText)";
	private static final BigInteger TEXT_LENGTH = new BigInteger("200");

	// ������������ �� ������
	private boolean isOfferConfirmed;
	// Id ��������� ������
	private Long offerId;
	// �������������  ������������� ������
	private String appNum;
	// ����� ������
	private String offerText;
	private boolean emailSended = false;
	private Department department;

    private String formatedConfirmOfferDate;
    private String claimETSMId;

	static final Form SEND_MAIL_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_ADDRESS);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("E-mail ����������");
		fieldBuilder.addValidators(requiredFieldValidator, new EmailFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_TEXT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("�����������");
		LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(TEXT_LENGTH);
		lengthFieldValidator.setMessage("����� ����������������� ������ �� ������ ��������� " + TEXT_LENGTH + " ��������.");
		fieldBuilder.addValidators(lengthFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public boolean isOfferConfirmed()
	{
		return isOfferConfirmed;
	}

	public void setOfferConfirmed(boolean offerConfirmed)
	{
		isOfferConfirmed = offerConfirmed;
	}

	public Long getOfferId()
	{
		return offerId;
	}

	public void setOfferId(Long offerId)
	{
		this.offerId = offerId;
	}

	public String getAppNum()
	{
		return appNum;
	}

	public void setAppNum(String appNum)
	{
		this.appNum = appNum;
	}

	public String getOfferText()
	{
		return offerText;
	}

	public void setOfferText(String offerText)
	{
		this.offerText = offerText;
	}

	public boolean isEmailSended()
	{
		return emailSended;
	}

	public void setEmailSended(boolean emailSended)
	{
		this.emailSended = emailSended;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

    public String getFormatedConfirmOfferDate()
    {
        return formatedConfirmOfferDate;
    }

    public void setFormatedConfirmOfferDate(String formatedConfirmOfferDate)
    {
        this.formatedConfirmOfferDate = formatedConfirmOfferDate;
    }

    public String getClaimETSMId()
    {
        return claimETSMId;
    }

    public void setClaimETSMId(String claimETSMId)
    {
        this.claimETSMId = claimETSMId;
	}
}
