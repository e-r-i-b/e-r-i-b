package com.rssl.phizic.gate.claims;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.documents.Claim;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;

/**
 * ������ �� ��������� ������� ������ ��������� �� ������
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface AccountChangeInterestDestinationClaim extends Claim, AccountOrIMATransferBase
{
	/**
	 * @return ����� �����, ��� �������� ����������� ������
	 */
	String getChangePercentDestinationAccountNumber() throws DocumentException;

	/**
	 * @return ����� �����, �� ������� ������� ����������� ��������, ���� null, ���� �������� ���� ����������� �� ����.
	 */
	String getPercentCardNumber();

	/**
	 * ��������� �� ����������
	 * @param promoterId
	 */
	void setPromoterId(String promoterId);

	/**
	 * ��������� ������ �����, ��� ������� ����� ������
	 * @param cardNumber
	 */
	void setLogonCardNumber(String cardNumber);
}
