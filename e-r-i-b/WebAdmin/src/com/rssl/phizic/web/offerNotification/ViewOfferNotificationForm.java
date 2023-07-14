package com.rssl.phizic.web.offerNotification;

import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * ����� ��� ���������������� ��������� ����������� � ������������� ���������� � ��� ����������
 * @author lukina
 * @ created 04.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class ViewOfferNotificationForm  extends EditFormBase
{
	private PersonalOfferNotification personalOfferNotification;

	public PersonalOfferNotification getPersonalOfferNotification()
	{
		return personalOfferNotification;
	}

	public void setPersonalOfferNotification(PersonalOfferNotification personalOfferNotification)
	{
		this.personalOfferNotification = personalOfferNotification;
	}
}
