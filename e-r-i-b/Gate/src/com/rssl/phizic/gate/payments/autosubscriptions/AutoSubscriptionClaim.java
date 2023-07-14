package com.rssl.phizic.gate.payments.autosubscriptions;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;

import java.util.Calendar;

/**
 * ������ �� ���������� � ����� � �� �����������
 *
 * @author khudyakov
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 */
public interface AutoSubscriptionClaim extends AutoSubscription, AbstractCardTransfer, PossibleAddingOperationUIDObject
{
	/**
	 * ��������� �� ������ �������� � ���������� �����
	 * @return true - ���������
	 */
	boolean isConnectChargeOffResourceToMobileBank();

	/**
	 * @return ��������� �����?(true - ��)
	 */
	public Boolean isExecutionNow();

	/**
	 * @return ���� � ����� ��������� ��������
	 */
	public Calendar getUpdateDate();

	/**
	 * @return ���� � ����� �������� ��������
	 */
	public Calendar getCreateDate();

	/**
	 * @return ������������� ������������� ��������
	 */
	public boolean isNeedConfirmation();

	/**
	 * @return ����� �������� ��������(IB - �������� ����, VSP - ���, US - ���������� ����������������)
	 */
	public ChannelType getChannelType();

	/**
	 * ���������� ����� �������� ��������
	 * (IB - �������� ����, VSP - ���, US - ���������� ����������������)
	 * @param channelType ��� ������
	 */
	public void setChannelType(ChannelType channelType);

	/**
	 * ������������ ����� �������� � �����
	 * @return ������������ ����� �������� � �����
	 */
	Money getMaxSumWritePerMonth();

	/**
	 * ������� ���������� ����������� � ������ ���������
	 * @return ������� ���������� ����������� � ������ ���������
	 */
	String getReasonDescription();

	/**
	 * @return  ����� ��, � �������� ����� ������
	 */
	String getCodeATM();

	/**
	 * @return ��������� ���������� �������
	 */
	String getMessageToRecipient();

	/**
	 * ��������� ���������� ����� �������� � ���������� � ����� ��
	 * @return true - ��
	 */
	boolean isSameTB() throws GateException, GateLogicException;
}
