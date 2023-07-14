package com.rssl.phizic.web.client.abstr;

import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class AsyncExtractForm extends ActionFormBase
{
	static final String Account = "acc";
	static final String Card = "card";
	static final String Loan = "loan";
	static final String Ima = "ima";
	static final String PFR = "pfr";

	/**
	 * Тип ресурса
	 * card - карта
	 * acc - счет
	 * loan - кредит
	 */
	private String type;
	/**
	 * идентификатор линка
	 */
	private String id;

	/**
	 * Была ли какая либо ошибка при получении
	 */
	private boolean isError;
	//Сообщение об ощибке для отображения пользователю
	private String abstractMsgError;

	private AccountAbstract accountAbstract;
	private CardAbstract cardAbstract;
	private ScheduleAbstract loanAbstract;
	private IMAccountAbstract imAccountAbstract;
	private List<PFRStatementClaim> pfrClaims = new ArrayList<PFRStatementClaim>();
	private Card cardEntity;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public AccountAbstract getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(AccountAbstract accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public CardAbstract getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(CardAbstract cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}

	public ScheduleAbstract getLoanAbstract()
	{
		return loanAbstract;
	}

	public void setLoanAbstract(ScheduleAbstract loanAbstract)
	{
		this.loanAbstract = loanAbstract;
	}

	public IMAccountAbstract getImAccountAbstract()
	{
		return imAccountAbstract;
	}

	public void setImAccountAbstract(IMAccountAbstract imAccountAbstract)
	{
		this.imAccountAbstract = imAccountAbstract;
	}

	public List<PFRStatementClaim> getPfrClaims()
	{
		return pfrClaims;
	}

	public void setPfrClaims(List<PFRStatementClaim> pfrClaims)
	{
		this.pfrClaims = pfrClaims;
	}

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}

	public Card getCardEntity()
	{
		return cardEntity;
	}

	public void setCardEntity(Card cardEntity)
	{
		this.cardEntity = cardEntity;
	}
}
