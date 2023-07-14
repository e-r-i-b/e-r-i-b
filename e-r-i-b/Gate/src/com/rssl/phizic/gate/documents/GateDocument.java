/***********************************************************************
 * Module:  GateDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface GateDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;

import java.util.Calendar;
import java.util.List;

/**
 * ������� ��������� ��� ���� ����������
 */
public interface GateDocument
{
	/**
	 * ���������� ID ��������� - �������� � ��� ������� ����������� ���� ����������
	 * @return ���������� �������������
	 */
	Long getId();

	/**
	 * �������� ����� ��������� � ����, ������� ����������� ������������.
	 *
	 * @return ����� ���������
	 */
	String getDocumentNumber();

	/**
	 * ����-����� �������� ���������
	 * Domain: DateTime
	 *
	 * @return ���� ��������
	 */
	Calendar getClientCreationDate();

	/**
	 * @return ���� ������������� ��������
	 */
	Calendar getClientOperationDate();

	/**
	 * ���������� ���� ������������� ��������
	 * @param clientOperationDate ���� ������������� ��������
	 */
	void setClientOperationDate(Calendar clientOperationDate);

	/**
	 * @return ���� ������������� �����������/� �� (�� ��������)
	 */
	Calendar getAdditionalOperationDate();

	/**
	 * ���� ��������� ���������� ������� ������
	 *
	 * @return ���� ��������� ���������� �������
	 */
	Calendar getAdmissionDate();

	/**
	 * ���������� ����, � ������� ��� ��������� ��������
	 * Domain: ExternalID
	 *
	 * @return ������������� �����
	 */
	Office getOffice() throws GateException;

	/**
	 * ���������� ����, � ������� ��� ��������� ��������
	 * �� ��� ������, ���� ���� �������� �������� � ������ ����.
	 * Domain: ExternalID
	 * @param office ����
	 */
	void setOffice(Office office);

	/**
	 * ���������� ��� ���������
	 *
	 * @return ���������� ��� ���������
	 */
	Class<? extends GateDocument> getType();

	/**
	 * @return ��� ����� ������� �������
	 */
	FormType getFormType();

	/**
	 * �������� ��������� �� ��������(��������� etc). ���� �������� ����������� == null.
	 * @return ��������
	 */
	Money getCommission();

	/**
	 * ���������� ��������. ��������, ������������� ����� ��������� ����� ����
	 * ��������������� by underlying system.
	 * � ������ ������� ��� �������� ������������� ��� �������������� ������������ � ������� ��������.
	 *
	 * @param commission ������ ��������. ���� �������� �� ��������� == null
	 */
	void setCommission(Money commission);

	/**
	 * ��������� �������� ��������.
	 * ���������� ������� ��������� ���������� ����������� �� ��������� ��������.
	 *
	 * �� ��������� �������������� ������ �������� �������� � ���������� ����� (other)
	 * @return ��������
	 */
	CommissionOptions getCommissionOptions();

	/**
	 * ���������� ID �������-��������� (���������) ���������
	 *
	 * @return ������������� �����������
	 */
	Long getInternalOwnerId() throws GateException;

	/**
	 * ������� ID �������-��������� (���������) ���������
	 *
	 * @return ������������� �����������
	 */
	String getExternalOwnerId();

	/**
	 * ���������� ������� ������������� ���������
	 * @param externalOwnerId ������� ������������� ���������
	 */
	void setExternalOwnerId(String externalOwnerId);

	/**
	 * @return ���������� ���������� �������� ����������
	 */
	EmployeeInfo getCreatedEmployeeInfo() throws GateException;

	/**
	 * @return ���������� �� �������������� �������� (� ��� ���������� (�����������)) ����������
	 */
	EmployeeInfo getConfirmedEmployeeInfo() throws GateException;

	/**
	 * @return ����� �������� ��������
	 */
	CreationType getClientCreationChannel();

	/**
	 * @return ����� ������������� ��������
	 */
	CreationType getClientOperationChannel();

	/**
	 * @return ����� ������������� �����������
	 */
	CreationType getAdditionalOperationChannel();

	/**
	 * �������� �� �������� ��������
	 * @return true ��������
	 */
	boolean isTemplate();

	/**
	 * @return ������ �������������(��������)
	 */
	List<WriteDownOperation> getWriteDownOperations();

	/**
	 * ���������� ������ �������������(��������)
	 * @param list - ���������� ������
	 */
	void setWriteDownOperations(List<WriteDownOperation> list);

	/**
	 * @return ��������� ���������
	 */
	String getNextState();

	/**
	 * ���������� ��������� ���������
	 * @param nextState - ��������� ���������
	 */
	void setNextState(String nextState);

	/**
	 * @return ����� ���������� ��� ���������
	 */
	ExternalSystemIntegrationMode getIntegrationMode();
}