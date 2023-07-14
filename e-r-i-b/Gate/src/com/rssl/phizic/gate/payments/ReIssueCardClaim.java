package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.Claim;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Заявка на перевыпуск карты.
 *
 * @author bogdanov
 * @ created 21.03.2013
 * @ $Author$
 * @ $Revision$
 */

public interface ReIssueCardClaim extends Claim
{
	/**
	 * @return индентификатор карты.
	 */
	public String getCardId();

	/**
	 * @return тип карты.
	 */
	public String getCardType() throws GateException;

	/**
	 * @return номер карты.
	 */
	public String getCardNumber();

	/**
	 * @return код отделения перевыпуска карты.
	 */
	public Code getDestinationCode();

	/**
	 * @return перекодированный по справочнку перекодировки СПООБК2 код отделения перевыпуска карты.
	 */
	public Code getConvertedDestinationCode() throws GateException;

	/**
	 * @return код отделения.
	 */
	public Code getBankInfoCode();

	/**
	 * @return перекодированный по справочнку перекодировки СПООБК2 код отделения.
	 */
	public Code getConvertedBankInfoCode() throws GateException;

	/**
	 * @return причина перевыпуска.
	 */
	public String getReasonCode();

	/**
	 * @return взимается коммиссия или нет.
	 */
	public boolean getIsCommission();
}
