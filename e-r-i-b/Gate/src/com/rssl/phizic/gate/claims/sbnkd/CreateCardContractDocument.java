package com.rssl.phizic.gate.claims.sbnkd;

/**
 * Заявка на создание контракта на карту.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface CreateCardContractDocument extends CustAddDocument
{
	/**
	 * @param contractBranchId Номер филиала.
	 */
	public void setContractBranchId(String contractBranchId);

	/**
	 * @return Номер филиала.
	 */
	public String getContractBranchId();

	/**
	 * @param contractAgencyId Номер отделения.
	 */
	public void setContractAgencyId(String contractAgencyId);

	/**
	 * @return Номер отделения.
	 */
	public String getContractAgencyId();

	/**
	 * @param contractEmbossedText Эмбоссированный текст.
	 */
	public void setContractEmbossedText(String contractEmbossedText);

	/**
	 * @return Эмбоссированный текст.
	 */
	public String getContractEmbossedText();

	/**
	 * @param contractRegionId Код тербанка.
	 */
	public void setContractRegionId(String contractRegionId);

	/**
	 * @return Код тербанка.
	 */
	public String getContractRegionId();

	/**
	 * @param contractCreditCardOffice Название отделения.
	 */
	public void setContractCreditCardOffice(String contractCreditCardOffice);

	/**
	 * @return Название отделения.
	 */
	public String getContractCreditCardOffice();

	/**
	 * @return Id региона
	 */
	public Long getPersonRegionId();
}
