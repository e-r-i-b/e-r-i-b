package com.rssl.phizicgate.esberibgate.longoffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LongOfferCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 * Хедпер для создания запросов по длительным поручениям
 */
public class LongOfferRequestHelper extends ClientRequestHelperBase
{
	public LongOfferRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	//Заполнение запроса GFL на получение длительных поручений клиента 
	public ProductContainer createBankAcctInqRq(Client client, ClientDocument document) throws GateLogicException, GateException
	{
		return createBankAcctInqRq(client, document, BankProductType.LongOrd);
	}

	public IFXRq_Type createSvcAcctAudRq(Client client, SvcAcctAudRq_TypeSvcAcct[] svcAccts) throws GateLogicException, GateException
	{
		return createSvcAcctAudRq(getRbTbBrch(client.getInternalOwnerId()), svcAccts);
	}

	public IFXRq_Type createSvcAcctAudRq(LongOffer longOffer, SvcAcctAudRq_TypeSvcAcct[] svcAccts) throws GateLogicException, GateException
	{
		LongOfferCompositeId longOfferCompositeId = EntityIdHelper.getLongOfferCompositeId(longOffer);
		return createSvcAcctAudRq(getRbTbBrch(longOfferCompositeId), svcAccts);
	}

	public IFXRq_Type createSvcAcctAudRq(LongOfferCompositeId compositeId) throws GateLogicException, GateException
	{
		SvcAcctAudRq_TypeSvcAcct[] arraySvcAcct = new SvcAcctAudRq_TypeSvcAcct[1];
		SvcAcctAudRq_TypeSvcAcct svcAcct = new SvcAcctAudRq_TypeSvcAcct();
		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();

		BankInfo_Type bankInfo = new BankInfo_Type(compositeId.getBranchId(),compositeId.getAgencyId(),
				compositeId.getRegionId(),null,compositeId.getRbBrchId());

		svcAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		svcAcctId.setSvcAcctNum(Long.parseLong(compositeId.getEntityId()));
		svcAcctId.setBankInfo(bankInfo);
		svcAcctId.setSvcType(compositeId.getSvcType());

		svcAcct.setSvcAcctId(svcAcctId);
		arraySvcAcct[0] = svcAcct;
		return createSvcAcctAudRq(getRbTbBrch(compositeId), arraySvcAcct);
	}

	/**
	 * Создание запроса на получение информации по длительному поручению
	 * @param rbTbBrch Сложный тип данных для Номера территориального банка в формате RbTbBrch – Код ТБ-Головное отделение ТБ–Номер ОСБ ТБ.
	 * @param svcAcctAudRq_typeSvcAccts список длительных поручений
	 * @return IFXRq_Type
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createSvcAcctAudRq(String rbTbBrch, SvcAcctAudRq_TypeSvcAcct[] svcAcctAudRq_typeSvcAccts) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		SvcAcctAudRq_Type svcAcctAudRq = new SvcAcctAudRq_Type();

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(rbTbBrch);

		svcAcctAudRq.setRqUID(generateUUID());
		svcAcctAudRq.setRqTm(generateRqTm());
		svcAcctAudRq.setOperUID(generateOUUID());
		svcAcctAudRq.setSPName(SPName_Type.BP_ERIB);
		svcAcctAudRq.setBankInfo(bankInfo);
		svcAcctAudRq.setSvcAcct(svcAcctAudRq_typeSvcAccts);

		ifxRq.setSvcAcctAudRq(svcAcctAudRq);
		return ifxRq;
	}

	public SvcAcctAudRq_TypeSvcAcct[] createSvcsAcctArray(SvcsAcct_Type[] svcsAccts) throws GateException
	{
		SvcAcctAudRq_TypeSvcAcct[] svcAcctAudRq_typeSvcAccts = new SvcAcctAudRq_TypeSvcAcct[svcsAccts.length];
		for (int i = 0; i < svcsAccts.length; i++)
		{
			SvcAcctAudRq_TypeSvcAcct svcAcctAudRq_typeSvcAcct = new SvcAcctAudRq_TypeSvcAcct();
			SvcsAcct_TypeSvcAcctId typeSvcAcctId = svcsAccts[i].getSvcAcctId();

			SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
			svcAcctId.setBankInfo(typeSvcAcctId.getBankInfo());

			svcAcctId.setSystemId(ExternalSystemHelper.getCode(svcsAccts[i].getSystemId()));
			svcAcctId.setSvcAcctNum(typeSvcAcctId.getSvcAcctNum());
			svcAcctId.setSvcType(typeSvcAcctId.isSvcType());

			svcAcctAudRq_typeSvcAcct.setSvcAcctId(svcAcctId);
			svcAcctAudRq_typeSvcAccts[i] = svcAcctAudRq_typeSvcAcct;
		}
		return svcAcctAudRq_typeSvcAccts;
	}

	public SvcAcctAudRq_TypeSvcAcct[] createSvcsAcctArray(LongOffer... longOffers) throws GateLogicException, GateException
	{
		SvcAcctAudRq_TypeSvcAcct[] svcAccts = new SvcAcctAudRq_TypeSvcAcct[longOffers.length];
		int i = 0;
		for (LongOffer longOffer: longOffers)
		{
			SvcAcctAudRq_TypeSvcAcct svcAcctAudRq_typeSvcAcct = new SvcAcctAudRq_TypeSvcAcct();

			SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
			BankInfo_Type bankInfo = new BankInfo_Type();

			LongOfferCompositeId longOfferCompositeId = EntityIdHelper.getLongOfferCompositeId(longOffer);

			bankInfo.setBranchId(longOfferCompositeId.getBranchId());
			bankInfo.setAgencyId(longOfferCompositeId.getAgencyId());
			bankInfo.setRegionId(longOfferCompositeId.getRegionId());
			bankInfo.setRbBrchId(longOfferCompositeId.getRbBrchId());
			svcAcctId.setBankInfo(bankInfo);

			svcAcctId.setSystemId(longOfferCompositeId.getSystemIdActiveSystem());
			svcAcctId.setSvcAcctNum(Long.decode(longOfferCompositeId.getEntityId()));
			svcAcctId.setSvcType(longOfferCompositeId.getSvcType());
			svcAcctAudRq_typeSvcAcct.setSvcAcctId(svcAcctId);

			svcAccts[i++] = svcAcctAudRq_typeSvcAcct;
		}
		return svcAccts;
	}

	/**
	 * Интерфейс получения шрафика исполнения длительного поручения
	 * @param longOffer длительное поручение
	 * @param stDate дата начала периода выписки
	 * @param endDate дата окончания преиода выписки
	 * @return IFXRq_Type
	 */
	public IFXRq_Type createServiceStmtRq(LongOffer longOffer, Calendar stDate, Calendar endDate) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		ServiceStmtRq_Type serviceStmtRq = new ServiceStmtRq_Type();

		LongOfferCompositeId compositeId = EntityIdHelper.getLongOfferCompositeId(longOffer);

		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
		svcAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		svcAcctId.setSvcAcctNum(Long.parseLong(compositeId.getEntityId()));
		svcAcctId.setBankInfo(new BankInfo_Type(compositeId.getBranchId(), compositeId.getAgencyId(), 
				compositeId.getRegionId(), null, compositeId.getRbBrchId()));
		svcAcctId.setSvcType(compositeId.getSvcType());

		serviceStmtRq.setRqUID(generateUUID());
		serviceStmtRq.setRqTm(generateRqTm());
		serviceStmtRq.setOperUID(generateOUUID());
		serviceStmtRq.setSPName(getSPName());
		serviceStmtRq.setBankInfo(createAuthBankInfo(compositeId.getLoginId()));
		serviceStmtRq.setSvcAcctId(svcAcctId);
		serviceStmtRq.setDtBegin(getStringDate(stDate));
		serviceStmtRq.setDtEnd(getStringDate(endDate));

		ifxRq.setServiceStmtRq(serviceStmtRq);
		return ifxRq;
	}
}
