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
 * ������ �� ���������� �����
 */

public interface CardBlockingClaim  extends Claim
{
	/**
	 *
	 * @return  ����� ����������� �����
	 */
	public String getCardNumber();

	/**
	 *
	 * @return ������� ����������
	 */
	public BlockReason getBlockingReason();

	/**
	 *
	 * @return externalId ����������� �����
	 */
	public String getCardExternalId();
}
