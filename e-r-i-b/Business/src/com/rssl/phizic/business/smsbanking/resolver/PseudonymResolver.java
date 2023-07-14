package com.rssl.phizic.business.smsbanking.resolver;

import com.rssl.phizic.business.resources.external.ExternalResourceLinkBase;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author eMakarov
 * @ created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public interface PseudonymResolver
{
	ExternalResourceLinkBase getLink(Pseudonym pseudonym) throws BusinessException, BusinessLogicException;

	Class<? extends Pseudonym> getPseudonymClass();
}
