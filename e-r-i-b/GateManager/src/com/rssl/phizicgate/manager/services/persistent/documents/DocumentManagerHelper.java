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
 * Преобразует документ к тому виду, в котором он должен быть в гейте]
 * ОБЪЕКТ С СОСТОЯНИЕМ!!!!
 */
public class DocumentManagerHelper
{
	private RouteInfoBean routeInfoBean = new RouteInfoBean();
	private RouteInfoBean witdrawRouteInfoBean = new RouteInfoBean();
	/**
	 * Создает хелпер для работы с документами. Маршрутизирующая информация берется из базы.
	 */
	public DocumentManagerHelper() throws GateLogicException, GateException
	{
		routeInfoBean.setUid(GateSingleton.getFactory().service(GateInfoService.class).getUID(null));
	}

	/**
	 * Создание хелпера для работы с документами. Маршрутизирующая информация берется из документа.
	 * @param document документ, с которым будем работать.
	 */
	public DocumentManagerHelper (GateDocument document) throws GateLogicException, GateException
	{
		routeInfoBean.setUid(GateSingleton.getFactory().service(GateInfoService.class).getUID(null));
		routeInfoBean.setRouteInfo(IDHelper.restoreRouteInfo((String) document.getOffice().getSynchKey()));
	}

	/**
	 * @return информацию маршрутизации.
	 */
	public String getRouteInfo()
	{
		return routeInfoBean.getRouteInfo();
	}

	/**
	 * преобразуем к гейте, нового документа не создаем
	 * @param document документ.
	 * @return преобразованный документ.
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
	 * Восстанавливает объект из гейта, к предыдущему состоянию
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
