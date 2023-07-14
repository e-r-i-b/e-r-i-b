package com.rssl.phizicgate.manager.services.persistent.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author Krenev
 * @ created 29.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class DocumentServiceImpl extends PersistentServiceBase<DocumentService> implements DocumentService
{
	public DocumentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			delegate.calcCommission(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			delegate.send(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			delegate.repeatSend(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			return delegate.update(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);

		try
		{
			delegate.prepare(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			delegate.confirm(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			delegate.validate(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			delegate.recall(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}
}
