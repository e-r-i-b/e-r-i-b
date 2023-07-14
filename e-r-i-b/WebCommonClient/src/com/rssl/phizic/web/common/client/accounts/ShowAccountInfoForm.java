package com.rssl.phizic.web.common.client.accounts;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.forms.validators.DuplicateAliasValidator;
import com.rssl.phizic.business.ermb.forms.validators.IdenticalToSmsCommandValidator;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.account.AccountMessageConfig;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountInfoForm extends EditFormBase
{
	public static final Form EDIT_ACCOUNT_NAME_FORM = editAccountNameForm();

	private AccountLink accountLink;
	private AccountTarget target;
	private List<AccountLink> otherAccounts;
	private String accountMaxBalanceMessage;

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public AccountTarget getTarget()
	{
		return target;
	}

	public void setTarget(AccountTarget target)
	{
		this.target = target;
	}

	public List<AccountLink> getOtherAccounts()
	{
		return otherAccounts;
	}

	public void setOtherAccounts(List<AccountLink> otherAccounts)
	{
		this.otherAccounts = otherAccounts;
	}

	public String getAccountMaxBalanceMessage()
	{
		return accountMaxBalanceMessage;
	}

	public void setAccountMaxBalanceMessage(String accountMaxBalanceMessage)
	{
		this.accountMaxBalanceMessage = accountMaxBalanceMessage;
	}

    public String getAccountCloseDateDisclaimMessage()
    {
        return ConfigFactory.getConfig(AccountMessageConfig.class).getAccountCloseDateDisclaimMessage();
	}

	private static Form editAccountNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("accountName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,56}", "������������ ������ ���� �� ����� 56 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	protected Form editAccountAliasForm() throws BusinessException
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("SMS-�������������");
		fieldBuilder.setName("clientSmsAlias");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DuplicateAliasValidator(this.getId(), AccountLink.class),
				new IdenticalToSmsCommandValidator(),
				//����� ����� �������� ������ �� ����, � ����� ������ ����� �.�. �� ����� 4 ��������.
				//��� �� ����� ����� �������� �� ���� �/��� ����, � ����� ������ �.�. �� ������ 3 ��������.
				new RegexpFieldValidator("\\d{4,}|(?=.{3,})(\\d*[A-z�-���][A-z�-���0-9]*)", "����� ������ ��������� " +
						"������ ����� � ����� � ���� �� ������ 3 �������� (�� ������ 4, ���� �������� ������ �����)")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
