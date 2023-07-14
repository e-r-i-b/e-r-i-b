package com.rssl.phizicgate.manager.services.persistent.documents;

import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Omeliyanchuk
 * @ created 06.08.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� �������� � ���� ����, � ������� �� ������ ���� � �����]
 * ������ � ����������!!!!
 */
public class DocumentManagerHelper
{
	private RouteInfoBean routeInfoBean = new RouteInfoBean();
	private RouteInfoBean witdrawRouteInfoBean = new RouteInfoBean();
	/**
	 * ������� ������ ��� ������ � �����������. ���������������� ���������� ������� �� ����.
	 */
	public DocumentManagerHelper() throws GateLogicException, GateException
	{
		routeInfoBean.setUid(GateSingleton.getFactory().service(GateInfoService.class).getUID(null));
	}

	/**
	 * �������� ������� ��� ������ � �����������. ���������������� ���������� ������� �� ���������.
	 * @param document ��������, � ������� ����� ��������.
	 */
	public DocumentManagerHelper (GateDocument document) throws GateLogicException, GateException
	{
		routeInfoBean.setUid(GateSingleton.getFactory().service(GateInfoService.class).getUID(null));
		routeInfoBean.setRouteInfo(IDHelper.restoreRouteInfo((String) document.getOffice().getSynchKey()));
	}

	/**
	 * @return ���������� �������������.
	 */
	public String getRouteInfo()
	{
		return routeInfoBean.getRouteInfo();
	}

	/**
	 * ����������� � �����, ������ ��������� �� �������
	 * @param document ��������.
	 * @return ��������������� ��������.
	 */
	public GateDocument removeRouteInfo(GateDocument document) throws GateException
	{

		GateDocument gateDocument = routeInfoBean.removeRouteInfo(document);
		if(WithdrawDocument.class.equals(document.getType()))
		{
			WithdrawDocument withdrawDocument = (WithdrawDocument) document;
			if(withdrawDocument.getTransferPayment() != null)
				witdrawRouteInfoBean.removeRouteInfo(withdrawDocument.getTransferPayment());
		}
		return gateDocument;


	}

	/**
	 * ��������������� ������ �� �����, � ����������� ���������
	 * @param document
	 * @return
	 */
	public GateDocument storeRouteInfo(GateDocument document) throws GateException
	{
		GateDocument gateDocument = routeInfoBean.storeRouteInfo(document);
		if(WithdrawDocument.class.equals(document.getType()))
		{
			WithdrawDocument withdrawDocument = (WithdrawDocument) document;
			if(withdrawDocument.getTransferPayment() != null)
				witdrawRouteInfoBean.storeRouteInfo(withdrawDocument.getTransferPayment());
		}
		return gateDocument;
	}
}
