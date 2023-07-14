package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.CreateCardContractDocument;

/**
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CreateCardContractDocumentImpl extends ConcludeEDBODocumentImpl implements CreateCardContractDocument
{
	private String contractBranchId;
	private String contractAgencyId;
	private String contractRegionId;
	private String contractCreditCardoffice;
	private Long personRegionId;

	/**
	 * @param contractBranchId ����� �������.
	 */
	public void setContractBranchId(String contractBranchId)
	{
		this.contractBranchId = contractBranchId;
	}

	/**
	 * @return ����� �������.
	 */
	public String getContractBranchId()
	{
		return contractBranchId;
	}

	/**
	 * @param contractAgencyId ����� ���������.
	 */
	public void setContractAgencyId(String contractAgencyId)
	{
		this.contractAgencyId = contractAgencyId;
	}

	/**
	 * @return ����� ���������.
	 */
	public String getContractAgencyId()
	{
		return contractAgencyId;
	}

	public void setContractEmbossedText(String contractEmbossedText)
	{
		for (CardInfoImpl card : getCardInfos())
		{
			card.setContractEmbossedText(contractEmbossedText);
		}
	}

	public String getContractEmbossedText()
	{
		return getCardInfos().isEmpty() ? "" : getCardInfos().get(0).getContractEmbossedText();
	}

	/**
	 * @param contractRegionId ��� ��������.
	 */
	public void setContractRegionId(String contractRegionId)
	{
		this.contractRegionId = contractRegionId;
	}

	/**
	 * @return ��� ��������.
	 */
	public String getContractRegionId()
	{
		return contractRegionId;
	}

	/**
	 * @param contractCreditCardOffice �������� ���������.
	 */
	public void setContractCreditCardOffice(String contractCreditCardOffice)
	{
		this.contractCreditCardoffice = contractCreditCardOffice;
	}

	/**
	 * @return contractCreditCardOffice �������� ���������.
	 */
	public String getContractCreditCardOffice()
	{
		return contractCreditCardoffice;
	}

	public Long getPersonRegionId()
	{
		return personRegionId;
	}

	public void setPersonRegionId(Long personRegionId)
	{
		this.personRegionId = personRegionId;
	}
}
