package com.rssl.phizicgate.manager.services.routablePersistent.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.documents.DocumentManagerHelper;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class DocumentServiceImpl extends RoutablePersistentServiceBase<DocumentService> implements DocumentService
{
	public DocumentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected DocumentService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(DocumentService.class);
	}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			endService(helper.getRouteInfo()).calcCommission(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			endService(helper.getRouteInfo()).send(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			endService(helper.getRouteInfo()).repeatSend(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			return endService(helper.getRouteInfo()).update(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);

		try
		{
			endService(helper.getRouteInfo()).prepare(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			endService(helper.getRouteInfo()).confirm(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		 {
			endService(helper.getRouteInfo()).validate(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			endService(helper.getRouteInfo()).recall(document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}
}
