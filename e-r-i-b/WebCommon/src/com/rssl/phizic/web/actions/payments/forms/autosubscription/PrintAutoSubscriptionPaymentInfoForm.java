package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Date;

/**
 * @author basharin
 * @ created 27.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrintAutoSubscriptionPaymentInfoForm extends EditFormBase
{
	private AutoSubscriptionDetailInfo payment;
	private CardLink cardLink;
	private long subscriptionId;
	private Date datePayment;
	private ActivePerson activePerson;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public AutoSubscriptionDetailInfo getPayment()
	{
		return payment;
	}

	public void setPayment(AutoSubscriptionDetailInfo payment)
	{
		this.payment = payment;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public long getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public Date getDatePayment()
	{
		return datePayment;
	}

	public void setDatePayment(Date datePayment)
	{
		this.datePayment = datePayment;
	}
}
