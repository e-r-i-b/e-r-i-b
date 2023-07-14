package com.rssl.auth.csa.back.servises;

/**
 * [Гостевой вход] Тип целевой заявки при гостевом входе
 * @author niculichev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum GuestClaimType
{
	/**
	 * Заявка на дебетовую карту
	 */
	debet_card("/guest/sberbankForEveryDay.do"),

	/**
	 * Заявка на кредитную карту
	 */
	credit_card("/guest/payments/payment.do?form=LoanCardClaim"),

	/**
	 * Заявка на кредит
	 */
	credit("/guest/payments/payment.do?form=ExtendedLoanClaim");


	private String actionPath;

	GuestClaimType(String actionPath)
	{
		this.actionPath = actionPath;
	}

	/**
	 * @return экшен путь
	 */
	public String getActionPath()
	{
		return actionPath;
	}
}
