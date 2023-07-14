package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * селектор метода доставки сообщений обновления документов во внешнюю систему
 */

public class DocumentUpdaterWaySelector extends AbstractWaySelector<DocumentUpdater> implements DocumentUpdater
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public DocumentUpdaterWaySelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected void initializeDelegate(DocumentUpdater delegate, Map<String, ?> params)
	{
		delegate.setParameters(params);
	}

	@Override
	protected Class getServiceType()
	{
		return WaySelectorHelper.DOCUMENT_UPDATER;
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		return getServiceDelegate(document).execute(document);
	}
}