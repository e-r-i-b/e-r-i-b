package com.rssl.phizic.business.finances;

import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ��������� �������� �� �����
 */
public class CardOperationClaim implements Entity
{
	private static final int LAST_ERROR_MAX_LENGTH = 255;

	private Long id;

	private String cardNumber;

	private CardOperationClaimStatus status;

	private Calendar waitingStartTime;

	private Calendar processingStartTime;

	private Calendar executingEndTime;

	private Calendar lastOperationDate;

	private Long ownerId;

	private String lastError;

	private int executionAttemptNum;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ID ������
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ����� �����, �� ������� ������������� ��������
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return ������ ������
	 */
	public CardOperationClaimStatus getStatus()
	{
		return status;
	}

	public void setStatus(CardOperationClaimStatus status)
	{
		this.status = status;
	}

	/**
	 * @return ����+����� ������ �������� ���������� ������
	 */
	public Calendar getWaitingStartTime()
	{
		return waitingStartTime;
	}

	public void setWaitingStartTime(Calendar waitingStartTime)
	{
		this.waitingStartTime = waitingStartTime;
	}

	/**
	 * @return ����+����� ������ ���������� ������
	 */
	public Calendar getProcessingStartTime()
	{
		return processingStartTime;
	}

	public void setProcessingStartTime(Calendar processingStartTime)
	{
		this.processingStartTime = processingStartTime;
	}

	/**
	 * @return ����+����� ���������� ���������� ������
	 */
	public Calendar getExecutingEndTime()
	{
		return executingEndTime;
	}

	public void setExecutingEndTime(Calendar executingEndTime)
	{
		this.executingEndTime = executingEndTime;
	}

	/**
	 * @return ����+����� ��������� ���������� ��������
	 */
	public Calendar getLastOperationDate()
	{
		return lastOperationDate;
	}

	public void setLastOperationDate(Calendar lastOperationDate)
	{
		this.lastOperationDate = lastOperationDate;
	}

	/**
	 * @return ������������� ������ ���������, ������� ������ ������
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId ������������� ������ ���������, ������� ������ ������
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return ����� ��������� ������ ���������� ������
	 */
	public String getLastError()
	{
		return lastError;
	}

	public void setLastError(String lastError)
	{
		this.lastError = StringHelper.truncate(lastError, LAST_ERROR_MAX_LENGTH);
	}

	/**
	 * @return ���������� ������� ��������� ������
	 */
	public int getExecutionAttemptNum()
	{
		return executionAttemptNum;
	}

	/**
	 * @param executionAttemptNum - ���������� ������� ��������� ������
	 */
	public void setExecutionAttemptNum(int executionAttemptNum)
	{
		this.executionAttemptNum = executionAttemptNum;
	}

	public String toString()
	{
		return "CardOperationClaim{" +
				"id=" + id +
				", cardNumber='" + MaskUtil.getCutCardNumberForLog(cardNumber) + '\'' +
				", owner=" + ownerId +
				", status=" + status +
				'}';
	}
}
