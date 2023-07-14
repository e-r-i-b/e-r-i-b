package com.rssl.phizic.business.ermb;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;

/**
 * ������ ���� ������� � ��������� ���������� �����
 * @author Puzikov
 * @ created 30.07.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbPermissionCalculator
{
	private final ErmbTariff tariff;
	private final boolean paymentBlock;

	/**
	 * ctor
	 * @param profile ������� ����
	 */
	public ErmbPermissionCalculator(ErmbProfileImpl profile)
	{
		this.tariff = profile.getTarif();
		this.paymentBlock = profile.isPaymentBlocked();
	}

	/**
	 * ctor
	 * @param profile ������� ����
	 * @param tariff �����
	 */
	public ErmbPermissionCalculator(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		this.tariff = tariff;
		this.paymentBlock = profile.isPaymentBlocked();
	}

	/**
	 * @return ����������� � ���������/��������� ���������, ������������ �� ����� �����.
	 */
	public boolean impliesCardNotification()
	{
		return impliesOperation(tariff.getNoticeConsIncomCardOperation());
	}

	/**
	 * @return ����������� � ���������/��������� ��������� �� ����� ������.
	 */
	public boolean impliesAccountNotification()
	{
		return impliesOperation(tariff.getNoticeConsIncomAccountOperation());
	}

	/**
	 * !!! � ������� �� ������ ���� ������������� �� �������, � ����������
	 *
	 * @return ����������� �� ��������
	 */
	public boolean impliesLoanNotification()
	{
		return ConfigFactory.getConfig(ErmbConfig.class).getLoanNotificationAvailability();
	}

	/**
	 * @return �������������� �� ������� ������� ���������� �� �����.
	 */
	public boolean impliesCardInfoOperation()
	{
		return impliesOperation(tariff.getCardInfoOperation());
	}

	/**
	 * @return �������������� �� ������� ������� ���������� �� �����.
	 */
	public boolean impliesAccountInfoOperation()
	{
		return impliesOperation(tariff.getAccountInfoOperation());
	}

	/**
	 * @return �������������� �� ������� ������� ������� ������� �� ����� (����-�������)
	 */
	public boolean impliesCardHistoryOperation()
	{
		return impliesOperation(tariff.getCardMiniInfoOperation());
	}

	/**
	 * @return �������������� �� ������� ������� ������� ������� �� ����� (����-�������).
	 */
	public boolean impliesAccountHistoryOperation()
	{
		return impliesOperation(tariff.getAccountMiniInfoOperation());
	}

	/**
	 * @return ����������/������ �� ���������� ����� �� ������� �������.
	 */
	public boolean impliesReIssueCardOperation()
	{
		return impliesOperation(tariff.getReIssueCardOperation());
	}

	/**
	 * @return ������� � ������ ����������� (������ �����).
	 */
	public boolean impliesJurPaymentOperation()
	{
		return impliesOperation(tariff.getJurPaymentOperation());
	}

	/**
	 * @return �������� � ������ ������� ��� (�� �������).
	 */
	public boolean impliesTransfersToThirdPartiesOperation()
	{
		return impliesOperation(tariff.getTransfersToThirdPartiesOperation());
	}

	private boolean impliesOperation(ErmbOperationStatus operation)
	{
		switch (operation)
		{
			case PROVIDED:
				return true;
			case NOT_PROVIDED:
				return false;
			case NOT_PROVIDED_WHEN_NO_PAY:
				return !paymentBlock;
			default:
				throw new IllegalArgumentException("����������� ����� �������� ����");
		}
	}
}
