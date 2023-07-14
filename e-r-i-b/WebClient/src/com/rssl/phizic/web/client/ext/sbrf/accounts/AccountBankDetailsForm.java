package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;

/**
 * Форма печати реквизитов банка
 * @author Pankin
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountBankDetailsForm extends EditFormBase
{
	static final String MAIL_ADDRESS = "field(mailAddress)";
	static final String MAIL_TEXT = "field(mailText)";
	private static final BigInteger TEXT_LENGTH = new BigInteger("200");

	private AccountLink accountLink;
	private CardLink cardLink;
	private boolean emailSended = false;

	static final Form SEND_MAIL_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_ADDRESS);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("E-mail получателя");
		fieldBuilder.addValidators(requiredFieldValidator, new EmailFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_TEXT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Комментарий");
		LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(TEXT_LENGTH);
		lengthFieldValidator.setMessage("Длина сопроводительного письма не должна превышать " + TEXT_LENGTH + " символов.");
		fieldBuilder.addValidators(lengthFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}


	public boolean isEmailSended()
	{
		return emailSended;
	}

	public void setEmailSended(boolean emailSended)
	{
		this.emailSended = emailSended;
	}

}
