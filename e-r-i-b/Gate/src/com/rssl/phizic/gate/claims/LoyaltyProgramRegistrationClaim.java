package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.util.Calendar;

/**
 * заявка на регистрацию в программе лояльности
 * @author gladishev
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgramRegistrationClaim extends SynchronizableDocument
{
	/**
	 * @return номер карты клиента
	 */
	String getCardNumber();

	/**
	 * @return номер телефона клиента
	 */
	String getPhoneNumber();

	/**
	 * @return email клиента
	 */
	String getEmail();
}
