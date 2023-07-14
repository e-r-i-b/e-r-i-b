package com.rssl.phizicgate.esberibgate.insurance;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.InsuranceCompositeId;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 * Хелпер для создания запросов по страховым и НПФ продуктам
 */

public class InsuranceAppRequestHelper  extends ClientRequestHelperBase
{
	public InsuranceAppRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * получение списка страховых продуктов клиента
	 * @param client - клиент
	 * @param clientDocument  - ДУЛ
	 * @return список продуктов
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public IFXRq_Type createInsuranceListRq(Client client, ClientDocument clientDocument) throws GateException, GateLogicException
	{

		GetInsuranceListRq_Type listRq = new GetInsuranceListRq_Type();
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(getPersonInfo(client, clientDocument, false, false));
		listRq.setCustInfo(custInfo);

		listRq.setBankInfo(getBankInfo(getRbTbBrch(client), null));

		listRq.setOperUID(generateOUUID());
		listRq.setRqTm(generateRqTm());
		listRq.setRqUID(generateUUID());
		listRq.setSPName(getSPName());

		IFXRq_Type ifxRq = new IFXRq_Type();

		ifxRq.setGetInsuranceListRq(listRq);

		return ifxRq;
	}

	/**
	 * Получение детальной информации по внешниему ID страхового продукта
	 * @param externalId ID страхового продукта
	 * @return детальная информация по продукту
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createInsuranceAppRq(String externalId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		GetInsuranceAppRq_Type getInsuranceAppRq = new GetInsuranceAppRq_Type();
		getInsuranceAppRq.setRqUID(generateUUID());                                                                                     
		getInsuranceAppRq.setRqTm(generateRqTm());
		getInsuranceAppRq.setOperUID(generateOUUID());
		getInsuranceAppRq.setSPName(SPName_Type.BP_ERIB);
		InsuranceCompositeId compositeId = EntityIdHelper.getInsuranceCompositeId(externalId);
		Long loginId = compositeId.getLoginId();
	
		getInsuranceAppRq.setBankInfo(getBankInfo(getRbTbBrch(loginId), null));

		InsuranceApp_Type insuranceApp = new InsuranceApp_Type();
		insuranceApp.setReference(compositeId.getEntityId());
		getInsuranceAppRq.setInsuranceApp(insuranceApp);

		ifxRq.setGetInsuranceAppRq(getInsuranceAppRq);
		return ifxRq;
	}
}
