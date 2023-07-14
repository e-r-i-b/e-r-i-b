package com.rssl.phizicgate.esberibgate.security;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.SecurityAccountCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author lukina
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public class SecurityAccountRequestHelper   extends ClientRequestHelperBase
{
	private static String RB_TB_BRCH_ID = "99000000"; //фиксированное значение RbTbBrchId передается в SecuritiesInfoInqRq

	public SecurityAccountRequestHelper(GateFactory factory)
	{
		super(factory);
	}
	/**
	 * Составление запроса для получения списка сберсертификатов
	 * @param client - клиент
	 * @param clientDocument - документ клиента
	 * @return IFXRq_Type - сообщение для запроса
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public ProductContainer createSecurityAccountListRequest(Client client, ClientDocument clientDocument) throws GateLogicException, GateException
	{
		return createBankAcctInqRq(client, clientDocument, BankProductType.Securities);
	}

	/**
	 * Получение детальной информации по внешниему ID сберегательного сертификата
	 * @param externalId ID сберегательного сертификата
	 * @return детальная информация по сертификату
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createSecurityAccount(String externalId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		SecuritiesInfoInqRq_Type securitiesInfoInqRq = new SecuritiesInfoInqRq_Type();
		securitiesInfoInqRq.setRqUID(generateUUID());
		securitiesInfoInqRq.setRqTm(generateRqTm());
		securitiesInfoInqRq.setSPName(SPName_Type.BP_ERIB);

		SecurityAccountCompositeId compositeId = EntityIdHelper.getSecurityAccountCompositeId(externalId);
		securitiesInfoInqRq.setSystemId(compositeId.getSystemIdActiveSystem());

		BankInfoESB_Type bankInfoESB = new BankInfoESB_Type();
		//По CHG062642 всегда передавать только "RbTbBrchId" с фикс. значением
		bankInfoESB.setRbTbBrchId(RB_TB_BRCH_ID);
		securitiesInfoInqRq.setBankInfo(bankInfoESB);

		securitiesInfoInqRq.setOperName("SrvBSGetInfo");

		BlankInfo_Type blankInfo = new BlankInfo_Type();
		blankInfo.setBlankType(compositeId.getSecType());
		blankInfo.setSerialNum(compositeId.getEntityId());
		securitiesInfoInqRq.setBlankInfo(blankInfo);

		ifxRq.setSecuritiesInfoInqRq(securitiesInfoInqRq);
		return ifxRq;
	}
}
