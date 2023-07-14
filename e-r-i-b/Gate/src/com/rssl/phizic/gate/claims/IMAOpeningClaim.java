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
	 * @return ���� ����������.
	 */
	public String getReceiverAccount();

	/**
	 * ��������� ����� ����������
	 * @param receiverAccount ���� ����������.
	 */
	public void setReceiverAccount(String receiverAccount);

	/**
	 * ��� ���
	 * @return
	 */
	public Long getIMAProductType();

	/**
	 * ������ ���
	 * @return
	 */
	public Long getIMAProductSubType();
}
