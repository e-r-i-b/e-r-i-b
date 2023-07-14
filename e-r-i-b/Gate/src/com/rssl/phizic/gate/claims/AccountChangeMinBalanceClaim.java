package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.Claim;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;

import java.math.BigDecimal;

/**
 * ������ �� ��������� ������������ ������� ������
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface AccountChangeMinBalanceClaim extends Claim, AccountOrIMATransferBase
{
	/**
	 * @return ����� �����, ��� �������� ����������� ������
	 */
	String getAccountNumber();

	/**
	 * @return ����� ����������� �������
	 */
	BigDecimal getMinimumBalance();

	/**
	 * @return ����� ���������� ������
	 */
	BigDecimal getInterestRate();
}
