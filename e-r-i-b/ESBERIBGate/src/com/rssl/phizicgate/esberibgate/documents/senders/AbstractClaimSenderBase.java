package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author egorova
 * @ created 07.10.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class AbstractClaimSenderBase extends DocumentSenderBase
{
	private static final String SEPARATOR = "\\|";
	public AbstractClaimSenderBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @deprecated используем RequestHelperBase#getRbTbBrch()
	 */
	@Deprecated
	protected String getClientDepartmentCode(GateDocument document) throws GateLogicException, GateException
	{
		BackRefClientService service = getFactory().service(BackRefClientService.class);
		String[] codeArray = service.getClientDepartmentCode(document.getInternalOwnerId()).split(SEPARATOR);
		return ArrayUtils.isEmpty(codeArray) ? null : codeArray[0];
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	@Override
	public void prepare(GateDocument document){}
}
