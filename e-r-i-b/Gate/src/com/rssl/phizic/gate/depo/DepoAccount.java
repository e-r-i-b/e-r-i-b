package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

public interface DepoAccount extends Serializable
{
	/**
	 * ������� ID ����� ����
	 * @return Id
	 */
	String getId();

	/**
	 * ������ ����� ���� �������
	 * @return DepoAccountState
	 */
    DepoAccountState getState();

	/**
	 * ����� ����� ���� �������
	 * @return AccountNumber
	 */
	String getAccountNumber();

	/**
	 * ����� ��������
	 * @return AgreementNumber
	 */
	String getAgreementNumber();

	/**
	 * ���� ��������
	 * @return agreementDate
	 */
	Calendar getAgreementDate();

	/**
	 * ����� ����� �������������
	 * @return debt
	 */
	Money getDebt();

	/**
	 * ��������� �� �������� �� ������
	 * @return isOperationAllowed
	 */
	boolean getIsOperationAllowed();

	/**
	 * ���� � ������� ������� ����
	 * @return office
	 */
	Office getOffice();
}
