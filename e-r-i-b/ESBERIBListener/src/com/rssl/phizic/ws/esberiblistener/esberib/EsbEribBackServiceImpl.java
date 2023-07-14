package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRq_Type;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRs_Type;

/**
 * @author osminin
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribBackServiceImpl implements EsbEribBackService
{
	public IFXRs_Type doIFX(IFXRq_Type parameters)
	{
		return getEsbEribUpdater(parameters).doIFX();
	}

	/**
	 * Получить необходимую реализацию апдейтера
	 * @param parameters параметры запроса
	 * @return EsbEribUpdaterBase
	 */
	private EsbEribUpdaterBase getEsbEribUpdater(IFXRq_Type parameters)
	{
		if (parameters.getDocStateUpdateRq() != null)
			return getDocumentStateUpdater(parameters.getDocStateUpdateRq());

		return getSecurityDicUpdater(parameters);
	}

	protected DocumentStateUpdaterBase getDocumentStateUpdater(DocStateUpdateRq_Type docStateUpdateRq)
	{
		return new DocumentStateUpdater(docStateUpdateRq);
	}

	protected SecurityDicUpdater getSecurityDicUpdater(IFXRq_Type iFXRq)
	{
		return new SecurityDicUpdater(iFXRq);
	}
}
