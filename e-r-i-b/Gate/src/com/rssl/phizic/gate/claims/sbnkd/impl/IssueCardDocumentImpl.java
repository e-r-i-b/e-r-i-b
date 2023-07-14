package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardClaim;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * Документ на выпуск карты.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class IssueCardDocumentImpl extends CreateCardContractDocumentImpl implements IssueCardClaim
{
	private String systemId;
	private String messageDeliveryType;
	private int stageNumber;
	private double autopayment;
	private String balanceLessThan; // пороговое значение баланса, при котором совершается автоплатеж. Определяется из справочника CELL_OPERATORS
	private boolean autopaymentIsAvailable; // доступность автоплатежа. Определяется из справочника CELL_OPERATORS
	private long minAutopaymentSum; // минимальная доступная сумма автоплатежа. Определяется из справочника CELL_OPERATORS
	private long maxAutopaymentSum; // максимальная доступная сумма автоплатежа. Определяется из справочника CELL_OPERATORS
	private String operationUID;

	/**
	 * @return идентификатор системы, предоставляющей услугу (провайдер сервиса).
	 */
    public String getSystemId()
    {
	    return systemId;
    }

	/**
	 * @param systemId идентификатор системы, предоставляющей услугу (провайдер сервиса).
	 */
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	/**
	 * @param messageDeliveryType Способ отправки отчетов по счету карты.
	*/
	public void setMessageDeliveryType(String messageDeliveryType)
	{
		this.messageDeliveryType = messageDeliveryType;
	}

	/**
	 * @return Способ отправки отчетов по счету карты.
	*/
	public String getMessageDeliveryType()
	{
		return messageDeliveryType;
	}

	/**
	 * @return Номер стадии заполнения заявки
	 */
	public int getStageNumber()
	{
		return stageNumber;
	}

	/**
	 * @param stageNumber Номер стадии заполнения заявки
	 */
	public void setStageNumber(int stageNumber)
	{
		this.stageNumber = stageNumber;
	}

	public void setCardAcctAutoPayInfo(double cardAcctAutoPayInfo)
	{
		this.autopayment = cardAcctAutoPayInfo;
	}

	public double getCardAcctAutoPayInfo()
	{
		return autopayment;
	}

	/**
	 * @return пороговое значение срабатывания автоплатежа
	 */
	public String getBalanceLessThan()
	{
		return balanceLessThan;
	}

	public void setBalanceLessThan(String balanceLessThan)
	{
		this.balanceLessThan = balanceLessThan;
	}

	public boolean getAutopaymentIsAvailable()
	{
		return autopaymentIsAvailable;
	}

	public void setAutopaymentIsAvailable(boolean autopaymentIsAvailable)
	{
		this.autopaymentIsAvailable = autopaymentIsAvailable;
	}

	public long getMaxAutopaymentSum()
	{
		return maxAutopaymentSum;
	}

	public void setMaxAutopaymentSum(long maxAutopaymentSum)
	{
		this.maxAutopaymentSum = maxAutopaymentSum;
	}

	public long getMinAutopaymentSum()
	{
		return minAutopaymentSum;
	}

	public void setMinAutopaymentSum(long minAutopaymentSum)
	{
		this.minAutopaymentSum = minAutopaymentSum;
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType){}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}
}
