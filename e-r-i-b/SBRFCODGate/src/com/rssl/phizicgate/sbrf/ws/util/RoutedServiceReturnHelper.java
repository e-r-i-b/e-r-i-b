package com.rssl.phizicgate.sbrf.ws.util;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.RefreshGateConfig;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.PersistentGateManager;
import com.rssl.phizicgate.manager.builder.endService.GateFactoryImpl;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;
import com.rssl.phizicgate.manager.services.persistent.documents.DocumentManagerHelper;
import com.rssl.phizicgate.manager.services.routablePersistent.documents.UpdateDocumentServiceImpl;

/**
 * ��������, ������������ ������, ������������ ���������� �������������.
 *
 * @author bogdanov
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class RoutedServiceReturnHelper extends ServiceReturnHelper
{
	//����� ������ ���������� ����� UpdateDocumentService.find
	public GateFactory factoryFor(AbstractAccountTransfer document) throws GateException
	{
		DocumentManagerHelper documentManagerHelper = UpdateDocumentServiceImpl.getPersister(document);
		return PersistentGateManager.getInstance().getGate(documentManagerHelper.getRouteInfo()).getFactory();
	}

	public <S extends Service> S service(Object routeInfoReturner, Class<S> serviceClass) throws GateException
	{
		if (!(routeInfoReturner instanceof RouteInfoReturner))
			throw new GateException("������ �� �������� ���������� ���������� � �������������");

		return PersistentGateManager.getInstance().getGate(((RouteInfoReturner)routeInfoReturner).getRouteInfo()).getFactory().service(serviceClass);
	}

	public String getEndURL(GateFactory factory) throws GateException
	{
		if (!(factory instanceof GateFactoryImpl))
			throw new GateException("��� ������������� ������������������� ������� ������ ������ ���� ������� " + GateFactoryImpl.class.getName());

		GateFactoryImpl gateFactory = (GateFactoryImpl) factory;
		return gateFactory.config(RefreshGateConfig.class).getURL();
	}
}
