package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.DeliveryType;

/**
 * @author mihaylov
 * @ created 26.08.2010
 * @ $Author$
 * @ $Revision$
 */

public interface SecuritiesTransferClaim extends DepoAccountClaimBase
{

	/**
	 * ��� ��������
	 * 231 � �������,
	 * 240 � �����,
	 * 220 � ������� ����� ��������� ����� ����
	 * @return operType
	 */
	TransferOperation getOperType();

	/**
	 * ���������� ��������, ������� �� ���� ��������
	 * 231
	 *      INTERNAL_TRANSFER � ������� �� ���� ���� ������ �����������,
	 *		LIST_TRANSFER � ������� �� ���� � �������,
	 *		EXTERNAL_TRANSFER � ������� �� ���� � ������ �����������.
	 * 240
	 *      INTERNAL_RECEPTION � ����� �������� �� ����� ���� ������ �����������,
	 *      LIST_RECEPTION � ����� �������� �� ����� � �������,
	 *      EXTERNAL_RECEPTION � ����� �������� �� ����� � ������ �����������.
	 *
	 * 220
	 *       INTERNAL_ACCOUNT_TRANSFER � ������� ����� ��������� ����� ����
	 * @return operationSubType
	 */
	TransferSubOperation getOperationSubType();

	/**
	 * ����������� � ��������
	 * @return operationDesc
	 */
	String getOperationDesc();

	/**
	 * @return ��� ������� ����� ����
	 */
	String getDivisionType();

	/**
	 * ��� � ����� ������� ����� ����
	 * @return divisionNumber
	 */
	String getDivisionNumber();

	/**
	 * ������������  ��� ������� ������ ������
	 * @return insideCode
	 */
	String getInsideCode();

	/**
	 * ���-�� ������ �����
	 * @return securityCount
	 */
	Long getSecurityCount();

	/**
	 * ��������� ��������
	 * @return operationReason
	 */
	String getOperationReason();

	/**
	 * ������������ ����������� ����������, ����������� ������ ��� ��������
	 * 231 � EXTERNAL_TRANSFER � 240 � EXTERNAL_RECEPTION
	 * @return corrDepositary
	 */
	String getCorrDepositary();

	/**
	 * ����� ����� ���� �� ������� ����������� ������� ��� � �������� ���������� ������� ������ ������.
	 * ���� �� ����������� ��� �������� 220.
	 * @return corrDepoAcctountId
	 */
	String getCorrDepoAccount();

	/**
	 * �������� ����� ���� �� ������� ����������� ������� ��� � �������� ���������� ������� ������ ������.
	 * @return corrDepoAccountOwner
	 */
	String getCorrDepoAccountOwner();

	/**
	 * �������������� ���������
	 * @return additionalInfo
	 */
	String getAdditionalInfo();

	/**
	 * ������ �������� ������������
	 * @return deliveryType
	 */
	DeliveryType getDeliveryType();

	/**
	 * ��������������� ����� ������ ������
	 * @return registrationNumber
	 */
	String getRegistrationNumber();
}
