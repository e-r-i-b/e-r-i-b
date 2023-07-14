package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Omeliyanchuk
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class PasswordCardUnblockPasswordForm extends ActionFormBase
{
	private Long personId;
	private Long cardId;
	String cardNumber;
    private String unblockPassword;
	private Integer unusedPasswordNumber;
	private ActivePerson activePerson;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;			
	}

	public void setUnusedPasswordNumber(Integer unusedPasswordNumber)
	{
		this.unusedPasswordNumber = unusedPasswordNumber;
	}

	public Integer getUnusedPasswordNumber()
	{
		return unusedPasswordNumber;
	}

	public String getUnblockPassword()
	{
		return unblockPassword;
	}

	public void setUnblockPassword(String unblockPassword)
	{
		this.unblockPassword = unblockPassword;
	}

	public void setPerson( Long personId)
	{
		this.personId = personId;
	}

	public Long getPerson()
	{
		return personId;
	}

	public void setCard( Long cardId)
	{
		this.cardId = cardId;
	}

	public Long getCard()
	{
		return cardId;
	}
}
