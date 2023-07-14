package com.rssl.phizicgate.esberibgate.longoffer;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.LongOfferService;
import com.rssl.phizic.gate.longoffer.ScheduleItem;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.LongOfferResponseSerializer;
import com.rssl.phizicgate.esberibgate.types.LongOfferImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LongOfferCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferServiceImpl extends AbstractService implements LongOfferService
{
	private LongOfferRequestHelper longOfferRequestHelper = new LongOfferRequestHelper(getFactory());
	private LongOfferResponseSerializer longOfferResponseSerializer = new LongOfferResponseSerializer();

	protected LongOfferServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public List<LongOffer> getClientsLongOffers(Client client) throws GateException, GateLogicException
	{
		List<LongOffer> longOffers = new ArrayList<LongOffer>();
		List<? extends ClientDocument> documents = client.getDocuments();

		Collections.sort(documents, new DocumentTypeComparator());

		for (ClientDocument document : documents)
		{
			if (document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
				continue;

			ProductContainer productContainer = longOfferRequestHelper.createBankAcctInqRq(client, document);
			if (productContainer.getIfxRq_type() == null)
				throw new InactiveExternalSystemException(productContainer.getProductError(BankProductType.LongOrd));

			IFXRs_Type ifxRs_GFL = getRequest(productContainer.getIfxRq_type());
			//заполн€ем поручени€ данными, доступниыми из GFL-запроса
			longOffers = longOfferResponseSerializer.fillLongOffers(productContainer.getIfxRq_type(), client, ifxRs_GFL);
			if(!longOffers.isEmpty())
			{
				for(SvcsAcct_Type svcsAcct: ifxRs_GFL.getBankAcctInqRs().getSvcsAcct())
				{
					//получем недостающие данные из запроса на дополнительную информацию по поручению.
					//¬ременное решение, можно передавать только один номер вклада в ÷ќƒ. ѕоэтому весь список формируем путем получени€ данных
					//по каждому длительному поручению. BUG032489
					SvcAcctAudRq_TypeSvcAcct[] svcAcctAudRq_typeSvcAccts = longOfferRequestHelper.createSvcsAcctArray(new SvcsAcct_Type[]{svcsAcct});
				    IFXRq_Type ifxRq_CODGSI = longOfferRequestHelper.createSvcAcctAudRq(client, svcAcctAudRq_typeSvcAccts);
				    IFXRs_Type ifxRs_CODGSI = getRequest(ifxRq_CODGSI);
				    longOfferResponseSerializer.fillLongOffers(client, longOffers, ifxRs_CODGSI.getSvcAcctAudRs().getSvcActInfo());
				}
				//получем недостающие данные из запроса на дополнительную информацию по поручени€м
				//SvcAcctAudRq_TypeSvcAcct[] svcAcctAudRq_typeSvcAccts = longOfferRequestHelper.createSvcsAcctArray(svcsAccts);
				//IFXRq_Type ifxRq_CODGSI = longOfferRequestHelper.createSvcAcctAudRq(client, svcAcctAudRq_typeSvcAccts);
				//IFXRs_Type ifxRs_CODGSI = getRequest(ifxRq_CODGSI);
				//longOfferResponseSerializer.fillLongOffers(client, longOffers, ifxRs_CODGSI.getSvcAcctAudRs().getSvcActInfo());

				return longOffers;
			}
		}
		return longOffers;
	}

	public GroupResult<LongOffer, AbstractTransfer> getLongOfferInfo(LongOffer... longOffers)
	{
		GroupResult<LongOffer, AbstractTransfer> longOfferInfo = new GroupResult<LongOffer, AbstractTransfer>();
		for (LongOffer longOffer: longOffers)
		{
			try
			{
				SvcAcctAudRq_TypeSvcAcct[] svcAcctAudRq_typeSvcAccts = longOfferRequestHelper.createSvcsAcctArray(longOffer);

				IFXRq_Type ifxRq = longOfferRequestHelper.createSvcAcctAudRq(longOffer, svcAcctAudRq_typeSvcAccts);
				IFXRs_Type ifxRs = getRequest(ifxRq);

				SvcActInfo_Type[] svcActInfo = ifxRs.getSvcAcctAudRs().getSvcActInfo();
				longOfferInfo.putResult(longOffer, longOfferResponseSerializer.getAbstractTransfer(longOffer, svcActInfo[0]));
			}
			catch (GateLogicException e)
			{
				longOfferInfo.putException(longOffer, e);
			}
			catch (GateException e)
			{
				longOfferInfo.putException(longOffer, e);
			}
		}
		return longOfferInfo;
	}

	public List<ScheduleItem> getSheduleReport(LongOffer longOffer, Calendar begin, Calendar end) throws GateException, GateLogicException
	{
		SvcAcctAudRq_TypeSvcAcct[] typeSvcAccts = longOfferRequestHelper.createSvcsAcctArray(longOffer);

		IFXRq_Type ifxRq_GSI = longOfferRequestHelper.createSvcAcctAudRq(longOffer, typeSvcAccts);
		IFXRs_Type ifxRs_GSI = getRequest(ifxRq_GSI);
		SvcActInfo_Type[] svcActInfo = ifxRs_GSI.getSvcAcctAudRs().getSvcActInfo();

		IFXRq_Type ifxRq_SAS = longOfferRequestHelper.createServiceStmtRq(longOffer, begin, end);
		IFXRs_Type ifxRs_SAS = getRequest(ifxRq_SAS);

		ServiceStmtRs_Type serviceStmtRs = ifxRs_SAS.getServiceStmtRs();
		if (serviceStmtRs.getStatus().getStatusCode() != 0)
		{
			ESBERIBExceptionStatisticHelper.throwErrorResponse(serviceStmtRs.getStatus(), ServiceStmtRs_Type.class, ifxRq_SAS);
		}
		ExctractLine_Type[] operations = serviceStmtRs.getExctractLine();
		return longOfferResponseSerializer.getScheduleReport(operations, svcActInfo[0], EntityIdHelper.getLongOfferCompositeId(longOffer.getExternalId()).getLoginId());
	}

	public GroupResult<String, LongOffer> getLongOffer(String... externalId)
	{
		GroupResult<String, LongOffer> result = new GroupResult<String, LongOffer>();
		if (externalId.length == 0)
			return result;
		for (int i = 0; i < externalId.length; i++)
		{
			try
			{
				LongOfferCompositeId compositeId = EntityIdHelper.getLongOfferCompositeId(externalId[i]);
				IFXRq_Type ifxRq = longOfferRequestHelper.createSvcAcctAudRq(compositeId);
				IFXRs_Type ifxRs = getRequest(ifxRq);
				LongOfferImpl longOffer = new LongOfferImpl();
				longOffer.setExternalId(externalId[i]);
				SvcActInfo_Type[] svcActInfo = ifxRs.getSvcAcctAudRs().getSvcActInfo();
				if (svcActInfo[0].getStatus().getStatusCode() != 0)
				{
					ESBERIBExceptionStatisticHelper.throwErrorResponse(svcActInfo[0].getStatus(), SvcActInfo_Type.class, ifxRq);
				}
				longOfferResponseSerializer.fillLongOffer(compositeId.getLoginId(), longOffer, svcActInfo[0]);
				result.putResult(externalId[i], longOffer);

				StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);
				storedResourcesService.updateStoredLongOffer(compositeId.getLoginId(), longOffer);
			}
			catch (GateLogicException e)
			{
				result.putException(externalId[i], e);
			}
			catch (GateException e)
			{
				result.putException(externalId[i], e);
			}
		}
		return result;
	}
}
