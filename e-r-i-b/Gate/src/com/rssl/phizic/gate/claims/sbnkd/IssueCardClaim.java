package com.rssl.phizic.gate.claims.sbnkd;

import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * ������ �� ������ �����.
 *
 * @author bogdanov
 * @ created 15.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface IssueCardClaim extends CreateCardContractDocument, ConfirmableObject, PossibleAddingOperationUIDObject
{
	/**
	 * @return ������������� �������, ��������������� ������ (��������� �������).
	 */
    String getSystemId();

	/**
	 * @param systemId ������������� �������, ��������������� ������ (��������� �������).
	 */
	void setSystemId(String systemId);

	/**
	 * @param messageDeliveryType ������ �������� ������� �� ����� �����.
	*/
	public void setMessageDeliveryType(String messageDeliveryType);
	/**
	 * @return ������ �������� ������� �� ����� �����.
	*/
	public String getMessageDeliveryType();

	/**
	 * @return ����� ������ ���������� ������
	 */
	public int getStageNumber();

	/**
	 * @param stageNumber ����� ������ ���������� ������
	 */
	public void setStageNumber(int stageNumber);

	/**
	 * @param cardAcctAutoPayInfo ���������� �� �����������.
	 */
	public void setCardAcctAutoPayInfo(double cardAcctAutoPayInfo);

	/**
	 * @return ���������� �� �����������.
	 */
	public double getCardAcctAutoPayInfo();
}
