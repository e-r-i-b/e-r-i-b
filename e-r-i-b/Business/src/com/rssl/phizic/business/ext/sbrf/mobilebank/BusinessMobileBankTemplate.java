package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * @author Erkin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class BusinessMobileBankTemplate implements MobileBankTemplate
{
	private final MobileBankCardInfo cardInfo;

	private final String recipient;

	private final String[] payerCodes;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param cardInfo
	 * @param recipient
	 * @param payerCodes
	 */
	public BusinessMobileBankTemplate(MobileBankCardInfo cardInfo, String recipient, String[] payerCodes)
	{
		this.cardInfo = cardInfo;
		this.recipient = recipient;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.payerCodes = payerCodes;
	}

	public MobileBankCardInfo getCardInfo()
	{
		return cardInfo;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public String[] getPayerCodes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return payerCodes;
	}

	public int hashCode()
	{
		return recipient != null ? recipient.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BusinessMobileBankTemplate that = (BusinessMobileBankTemplate) o;

		if (StringUtils.isBlank(recipient) || StringUtils.isBlank(that.recipient))
			return false;
		if (payerCodes == null || that.payerCodes == null)
			return false;

		return recipient.equals(that.recipient)
				&& Arrays.equals(payerCodes, that.payerCodes);
	}

	public String toString()
	{
		return "BusinessMobileBankTemplate{" +
				"recipient='" + recipient + '\'' +
				", payerCodes=" + (payerCodes == null ? null : Arrays.asList(payerCodes)) +
				'}';
	}
}
