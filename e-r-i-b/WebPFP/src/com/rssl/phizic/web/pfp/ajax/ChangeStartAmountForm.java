package com.rssl.phizic.web.pfp.ajax;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author lepihina
 * @ created 23.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangeStartAmountForm extends EditFormBase
{
	public static final Form EDIT_FORM = getEditForm();

	private Long profileId; //идентификатор ПФП
	private Long portfolioId; //идентификатор портфеля

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Long getPortfolioId()
	{
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId)
	{
		this.portfolioId = portfolioId;
	}

	private static Form getEditForm()
	{
		MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
		moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999.99");
		moneyFieldValidator.setMessage("Вы неправильно указали сумму. Пожалуйста, введите сумму до 9999999999999.99 руб.");

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма для формирования портфеля");
		fieldBuilder.setName("changedStartAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
