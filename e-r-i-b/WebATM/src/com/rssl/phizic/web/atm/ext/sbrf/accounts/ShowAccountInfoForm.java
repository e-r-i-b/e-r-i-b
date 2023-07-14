package com.rssl.phizic.web.atm.ext.sbrf.accounts;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.atm.common.FilterFormBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountInfoForm extends FilterFormBase
{
	public static final Form FILTER_FORM     =  ShowAccountInfoForm.filterForm();

	private AccountLink accountLink;
	private AccountAbstract accountAbstract;
	private boolean isError = false;
	private List<AutoSubscriptionLink> moneyBoxes;

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public AccountAbstract getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(AccountAbstract accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}

	public List<AutoSubscriptionLink> getMoneyBoxes()
	{
		return moneyBoxes;
	}

	public void setMoneyBoxes(List<AutoSubscriptionLink> moneyBoxes)
	{
		this.moneyBoxes = moneyBoxes;
	}

	private static Form filterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typePeriod");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "month" } ))
		);
		formBuilder.addField(fieldBuilder.build());


		return formBuilder.build();
	}

}