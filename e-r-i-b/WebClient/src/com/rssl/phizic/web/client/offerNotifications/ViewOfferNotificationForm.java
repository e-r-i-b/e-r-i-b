package com.rssl.phizic.web.client.offerNotifications;

import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class ViewOfferNotificationForm  extends ActionFormBase
{
	private static final String LOAN_CARD_CLAIM_LINK = "/PhizIC/private/payments/payment.do?form=LoanCardClaim"; //ссылка на оформление предодобренного предложения по кредитной карте.
	private PersonalOfferNotification offerNotification; //текущее уведомление
	private List<PersonalOfferNotification> notificationsList; //список id уведомлений в порядке отображения
	private Long currentNotificationId; //id текущего уведомления
	private String currentName; //название текущего уведомления
	private LoanCardOffer loanCardOffer;
	private LoanOffer loanOffer;
	private String offerId;
	private String offerType;

	public PersonalOfferNotification getOfferNotification()
	{
		return offerNotification;
	}

	public void setOfferNotification(PersonalOfferNotification offerNotification)
	{
		this.offerNotification = offerNotification;
	}

	public List<PersonalOfferNotification> getNotificationsList()
	{
		return notificationsList;
	}

	public void setNotificationsList(List<PersonalOfferNotification> notificationsList)
	{
		this.notificationsList = notificationsList;
	}

	public Long getCurrentNotificationId()
	{
		return currentNotificationId;
	}

	public void setCurrentNotificationId(Long currentNotificationId)
	{
		this.currentNotificationId = currentNotificationId;
	}

	public String getCurrentName()
	{
		return currentName;
	}

	public void setCurrentName(String currentName)
	{
		this.currentName = currentName;
	}

	public LoanCardOffer getLoanCardOffer()
	{
		return loanCardOffer;
	}

	public void setLoanCardOffer(LoanCardOffer loanCardOffer)
	{
		this.loanCardOffer = loanCardOffer;
	}

	public LoanOffer getLoanOffer()
	{
		return loanOffer;
	}

	public void setLoanOffer(LoanOffer loanOffer)
	{
		this.loanOffer = loanOffer;
	}

	public String getLoanCardClaimLink()
	{
		return LOAN_CARD_CLAIM_LINK;
	}

	public String getOfferId()
	{
		return offerId;
	}

	public void setOfferId(String offerId)
	{
		this.offerId = offerId;
	}

	public String getOfferType()
	{
		return offerType;
	}

	public void setOfferType(String offerType)
	{
		this.offerType = offerType;
	}
}