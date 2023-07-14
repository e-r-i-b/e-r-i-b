package com.rssl.phizic.gate.claims.sbnkd;

/**
 * ������ �� �������� ��������� �� �����.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface CreateCardContractDocument extends CustAddDocument
{
	/**
	 * @param contractBranchId ����� �������.
	 */
	public void setContractBranchId(String contractBranchId);

	/**
	 * @return ����� �������.
	 */
	public String getContractBranchId();

	/**
	 * @param contractAgencyId ����� ���������.
	 */
	public void setContractAgencyId(String contractAgencyId);

	/**
	 * @return ����� ���������.
	 */
	public String getContractAgencyId();

	/**
	 * @param contractEmbossedText ��������������� �����.
	 */
	public void setContractEmbossedText(String contractEmbossedText);

	/**
	 * @return ��������������� �����.
	 */
	public String getContractEmbossedText();

	/**
	 * @param contractRegionId ��� ��������.
	 */
	public void setContractRegionId(String contractRegionId);

	/**
	 * @return ��� ��������.
	 */
	public String getContractRegionId();

	/**
	 * @param contractCreditCardOffice �������� ���������.
	 */
	public void setContractCreditCardOffice(String contractCreditCardOffice);

	/**
	 * @return �������� ���������.
	 */
	public String getContractCreditCardOffice();

	/**
	 * @return Id �������
	 */
	public Long getPersonRegionId();
}
