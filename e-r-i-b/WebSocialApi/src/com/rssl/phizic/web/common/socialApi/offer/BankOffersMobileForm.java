package com.rssl.phizic.web.common.socialApi.offer;

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

	public boolean getIsLogin()
	{
		return isLogin;
	}

	public void setIsLogin(boolean login)
	{
		isLogin = login;
	}
}
