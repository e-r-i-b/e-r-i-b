package com.rssl.phizic.web.common.mobile.offer;

import com.rssl.phizic.web.common.client.offer.LoanOfferCommonForm;

/**
 * ������ ����������� ����� (�� ����� � �����)
 * @author Dorzhinov
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class BankOffersMobileForm extends LoanOfferCommonForm
{
	private boolean isLogin; //������� ��������� ����������� �� ����� � ����������

	private String claimType; //��� �����������, loan - �� ��������; card - �� ������

	public boolean getIsLogin()
	{
		return isLogin;
	}

	public void setIsLogin(boolean login)
	{
		isLogin = login;
	}

	public String getClaimType()
	{
		return claimType;
	}

	public void setClaimType(String claimType)
	{
		this.claimType = claimType;
	}
}
