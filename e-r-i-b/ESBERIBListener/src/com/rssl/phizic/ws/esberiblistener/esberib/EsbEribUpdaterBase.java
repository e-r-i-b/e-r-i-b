package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRs_Type;

/**
 * @author osminin
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class EsbEribUpdaterBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected static final Long ERROR_STATUS_CODE = -1L;
	protected static final Long SUCCESSFULL_STATUS_CODE = 0L;

	protected static final String EMPTY_STRING = "";

	public abstract IFXRs_Type doIFX();
}
