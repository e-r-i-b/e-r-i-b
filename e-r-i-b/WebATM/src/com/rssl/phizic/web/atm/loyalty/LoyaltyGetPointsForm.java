package com.rssl.phizic.web.atm.loyalty;

import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма содержащая информацию по накопительным баллам "Спасибо"
 *
 * @author Balovtsev
 * @version 30.08.13 19:19
 */
public class LoyaltyGetPointsForm extends ActionFormBase
{
	private String              thanks;
	private LoyaltyProgramState loyaltyProgramState;

	public String getThanks()
	{
		return thanks;
	}

	public void setThanks(String thanks)
	{
		this.thanks = thanks;
	}

	public LoyaltyProgramState getLoyaltyProgramState()
	{
		return loyaltyProgramState;
	}

	public void setLoyaltyProgramState(LoyaltyProgramState loyaltyProgramState)
	{
		this.loyaltyProgramState = loyaltyProgramState;
	}
}
