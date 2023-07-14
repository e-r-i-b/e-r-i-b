package com.rssl.phizic.gate.cms.claims;

import com.rssl.phizic.gate.cms.BlockReason;
import com.rssl.phizic.gate.documents.Claim;

/**
 * @author: Pakhomova
 * @created: 18.12.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * заявка на блокировку карты
 */

public interface CardBlockingClaim  extends Claim
{
	/**
	 *
	 * @return  номер блокируемой карты
	 */
	public String getCardNumber();

	/**
	 *
	 * @return причину блокировки
	 */
	public BlockReason getBlockingReason();

	/**
	 *
	 * @return externalId блокируемой карты
	 */
	public String getCardExternalId();
}
