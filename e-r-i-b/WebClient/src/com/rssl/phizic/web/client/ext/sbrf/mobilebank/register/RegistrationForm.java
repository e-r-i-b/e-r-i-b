package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

/**
 * @author Erkin
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 */
interface RegistrationForm
{
	/**
	 * ���������� ����� ��������,
	 * ���� ����� ������� �� ���������� ����������� ��� � ������ � ������
	 * @return ����� �������� ��� null, ���� �� ������
	 */
	public String getReturnURL();

	public void setReturnURL(String returnURL);
}
