package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.payments.AccountOrIMAOpeningClaimBase;

import java.math.BigDecimal;

/**
 * @author Mescheryakova
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */

public interface IMAOpeningClaim extends AbstractAccountTransfer, AccountOrIMAOpeningClaimBase
{
	public BigDecimal getCourse();

	public BigDecimal getCourseSale();

	public String getOfficeName();

	public String getOfficeAddress();

	public String getOfficeRegion();

	public String getOfficeBranch();

	public String getOfficeVSP();

	/**
	 * @return счет получателя.
	 */
	public String getReceiverAccount();

	/**
	 * Установка счета получателя
	 * @param receiverAccount счет получателя.
	 */
	public void setReceiverAccount(String receiverAccount);

	/**
	 * Вид ОМС
	 * @return
	 */
	public Long getIMAProductType();

	/**
	 * Подвид ОМС
	 * @return
	 */
	public Long getIMAProductSubType();
}
