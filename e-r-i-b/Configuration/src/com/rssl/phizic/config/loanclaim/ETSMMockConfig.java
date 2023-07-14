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
 *  онфиг заглушки Transact SM
 */
@XmlType(name = "ETSMMockConfigType")
public class ETSMMockConfig
{
	@XmlElement(name = "loanclaim-status-code", required = true)
	private int loanClaimStatusCode = ETSMLoanClaimStatus.APPROVED;

	@XmlElement(name = "loanclaim-error-code", required = true)
	private int loanClaimErrorCode = -1;

	@XmlElement(name = "loanclaim-error-message", required = true)
	private String loanClaimErrorMessage = "“екст ошибки";

	private final BigDecimal approvedInterestRate = new BigDecimal("10.00");

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return такой статус заглушка будет выставл€ть всем за€вкам на кредит
	 */
	public int getLoanClaimStatusCode()
	{
		return loanClaimStatusCode;
	}

	/**
	 * @return такой код ошибки заглушка будет выставл€ть всем за€вкам на кредит (0 = ошибка выставл€тьс€ не будет)
	 */
	public int getLoanClaimErrorCode()
	{
		return loanClaimErrorCode;
	}

	/**
	 * @return такой текст ошибки заглушка будет выставл€ть всем за€вкам на кредит (null = ошибка выставл€тьс€ не будет)
	 */
	public String getLoanClaimErrorMessage()
	{
		return loanClaimErrorMessage;
	}

	/**
	 * @return такую процентную ставку заглушка будет возвращать по всем одобренным за€вкам на кредит (never null)
	 */
	public BigDecimal getApprovedInterestRate()
	{
		return approvedInterestRate;
	}
}
