package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.depo.SecurityOperationType;

import java.util.List;

/**
 * @author lukina
 * @ created 29.09.2010
 * @ $Author$
 * @ $Revision$
 */

public interface SecurityRegistrationClaim extends DepoAccountClaimBase
{
	/**
	 * ������������ ����������� ����������, ����������� ������ ��� ��������
	 * 231 � EXTERNAL_TRANSFER � 240 � EXTERNAL_RECEPTION
	 * @return corrDepositary
	 */
	String getCorrDepositary();
	
	/**
	 * ������������  ��� ������� ������ ������
	 * @return insideCode
	 */
	String getInsideCode();

	/**
	 * ������������ ������ ������
	 * @return securityName
	 */
	String getSecurityName();

	/**
	 * ��������������� ����� ������ ������ ��� �����������,
	 * ��� ������������� �����(��� �������) ������� � �����
	 * @return securityNumber
	 */
	String getSecurityNumber();

	/**
	 * ������� ������ ������
	 * @return issu
	 */
	String getIssuer();

	/**
	 * ����������� �������� ��� ������ �������
	 * @return operations
	 */
	List<SecurityOperationType> getOperations();

	/**
	 * ������������ �������� ������� ����� �������
	 * @return clientOperationDescription
	 */
	String getClientOperationDescription();

	/**
	 * ������������ �������� �������
	 * @return ������ clientOperationDescription'��
	 */
	List<String> getClientOperationDescriptionsList();
}

