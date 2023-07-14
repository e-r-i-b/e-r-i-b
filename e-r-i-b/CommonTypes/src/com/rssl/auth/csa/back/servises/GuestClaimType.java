package com.rssl.auth.csa.back.servises;

/**
 * [�������� ����] ��� ������� ������ ��� �������� �����
 * @author niculichev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum GuestClaimType
{
	/**
	 * ������ �� ��������� �����
	 */
	debet_card("/guest/sberbankForEveryDay.do"),

	/**
	 * ������ �� ��������� �����
	 */
	credit_card("/guest/payments/payment.do?form=LoanCardClaim"),

	/**
	 * ������ �� ������
	 */
	credit("/guest/payments/payment.do?form=ExtendedLoanClaim");


	private String actionPath;

	GuestClaimType(String actionPath)
	{
		this.actionPath = actionPath;
	}

	/**
	 * @return ����� ����
	 */
	public String getActionPath()
	{
		return actionPath;
	}
}
