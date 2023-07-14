package com.rssl.phizic.config.loanclaim;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Erkin
 * @ created 25.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������� Transact SM
 */
@XmlType(name = "ETSMMockConfigType")
public class ETSMMockConfig
{
	@XmlElement(name = "loanclaim-status-code", required = true)
	private int loanClaimStatusCode = ETSMLoanClaimStatus.APPROVED;

	@XmlElement(name = "loanclaim-error-code", required = true)
	private int loanClaimErrorCode = -1;

	@XmlElement(name = "loanclaim-error-message", required = true)
	private String loanClaimErrorMessage = "����� ������";

	private final BigDecimal approvedInterestRate = new BigDecimal("10.00");

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ����� ������ �������� ����� ���������� ���� ������� �� ������
	 */
	public int getLoanClaimStatusCode()
	{
		return loanClaimStatusCode;
	}

	/**
	 * @return ����� ��� ������ �������� ����� ���������� ���� ������� �� ������ (0 = ������ ������������ �� �����)
	 */
	public int getLoanClaimErrorCode()
	{
		return loanClaimErrorCode;
	}

	/**
	 * @return ����� ����� ������ �������� ����� ���������� ���� ������� �� ������ (null = ������ ������������ �� �����)
	 */
	public String getLoanClaimErrorMessage()
	{
		return loanClaimErrorMessage;
	}

	/**
	 * @return ����� ���������� ������ �������� ����� ���������� �� ���� ���������� ������� �� ������ (never null)
	 */
	public BigDecimal getApprovedInterestRate()
	{
		return approvedInterestRate;
	}
}
