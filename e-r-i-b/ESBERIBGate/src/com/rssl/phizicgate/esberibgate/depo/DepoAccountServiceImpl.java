package com.rssl.phizicgate.esberibgate.depo;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.depo.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.DepoAccountsResponseSerializer;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mihaylov
 * @ created 19.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountServiceImpl  extends AbstractService implements DepoAccountService
{
	private DepoAccountRequestHelper helper = new DepoAccountRequestHelper(getFactory());
	private DepoAccountsResponseSerializer serializer = new DepoAccountsResponseSerializer();

	public DepoAccountServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public void register(Client client) throws GateException, GateLogicException
	{
		ClientDocument document = getClientDocument(client);
		IFXRq_Type ifxRq = helper.createDepoClientRegRq(client, document);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		Status_Type status = ifxRs.getDepoClientRegRs().getStatus();
		if(status.getStatusCode() != 0)
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, DepoClientRegRsType.class, ifxRq);
		//клиент зарегистрирован в депозитарии
		if(!ifxRs.getDepoClientRegRs().getIsExist())
			throw new GateLogicException(" лиент "+client.getFullName()+" не существует в депозитарии");
	}

	public List<DepoAccount> getDepoAccounts(Client client) throws GateException, GateLogicException
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents.isEmpty())
		{
			throw new GateException("Ќе заполнены документы дл€ клиента с id = " + client.getId());
		}
		Collections.sort(documents, new DocumentTypeComparator());
		List<DepoAccount> depoAccounts = new ArrayList<DepoAccount>();
		for (ClientDocument document : documents)
		{
			ProductContainer productContainer = helper.createDepoAccountsListRequest(client, document);
			if (productContainer.getIfxRq_type() == null)
				throw new InactiveExternalSystemException(productContainer.getProductError(BankProductType.DepoAcc));

			IFXRs_Type ifxRs = getRequest(productContainer.getIfxRq_type());
			depoAccounts = serializer.fillDepoAccounts(ifxRs,client.getInternalOwnerId());;

			if (!depoAccounts.isEmpty())
			{
				break;
			}
		}
		return depoAccounts;

	}

	public GroupResult<String, DepoAccount> getDepoAccount(String... depoAccountId)
	{
		IFXRq_Type ifxRq;
		IFXRs_Type ifxRs;
		try
		{
			ifxRq = helper.createDepoAccInfoRq(depoAccountId);
			ifxRs = getRequest(ifxRq);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(depoAccountId,e);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(depoAccountId,e);
		}

		return serializer.fillDepoAccounts(ifxRq, ifxRs, depoAccountId);
	}

	public DepoDebtInfo getDepoDebtInfo(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = helper.createDepoDeptsInfoRq(depoAccount);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		DepoDeptsInfoRsType depoDeptsInfoRs = ifxRs.getDepoDeptsInfoRs();
		Status_Type status = depoDeptsInfoRs.getDepoAcctDeptInfoRec().getDepoDeptRes().getStatus();
		if(status.getStatusCode() !=0)
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, DepoDeptRes_Type.class, ifxRq);
		DepoAcctId_Type depoAccRec = depoDeptsInfoRs.getDepoAcctDeptInfoRec().getDepoDeptRes().getDepoAcctId();
		if(!depoAccount.getAccountNumber().equals(depoAccRec.getAcctId()))
			throw new GateException("¬ернулась информаци€ не по тому счету депо");
		return serializer.fillDepoDebtInfo(depoDeptsInfoRs);
	}

	public GroupResult<DepoDebtItem, DepoDebtItemInfo> getDepoDebtItemInfo(DepoAccount depoAccount,DepoDebtItem... debtItem)
	{

		IFXRs_Type ifxRs;
		try
		{
			IFXRq_Type ifxRq = helper.createDepoDeptDetInfoRq(depoAccount,debtItem);
			ifxRs = getRequest(ifxRq);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(debtItem,e);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(debtItem,e);
		}

		DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec depoDeptDet = ifxRs.getDepoDeptDetInfoRs().getDepoAcctDeptInfoRec();
		DepoDeptResZad_Type[] depoDeptRes = depoDeptDet.getDepoDeptRes();
		return serializer.fillDepoDebtItemInfo(depoAccount, depoDeptRes, debtItem);
	}

	public DepoAccountPosition getDepoAccountPosition(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = helper.createDepoAccountPositionRq(depoAccount);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		DepoAccSecInfoRsType depoAccSecInfo = ifxRs.getDepoAccSecInfoRs();
		Status_Type status = depoAccSecInfo.getDepoAccSecInfoRec().getStatus();
		if(status.getStatusCode() !=0)
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, DepoAccSecInfoRsTypeDepoAccSecInfoRec.class, ifxRq);
		DepoAcctId_Type depoAccRec = depoAccSecInfo.getDepoAccSecInfoRec().getDepoAcctId();
		if(!depoAccount.getAccountNumber().equals(depoAccRec.getAcctId()))
			throw new GateException("¬ернулась информаци€ не по тому счету депо");
		return serializer.fillDepoAccountPosition(depoAccSecInfo);		  
	}

	public GroupResult<DepoAccount, Client> getDepoAccountOwner(DepoAccount... depoAccount)
	{
		IFXRs_Type ifxRs;
		try
		{
			IFXRq_Type ifxRq = helper.createDepoAccInfoRq(depoAccount);
			ifxRs = getRequest(ifxRq);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(depoAccount,e);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(depoAccount,e);
		}

		return serializer.fillDepoAccountsClients(ifxRs, depoAccount);		
	}

	/**
	 * ¬озвращает паспорт клиента
	 * если его нет, то первый из списка
	 * @param client - клиент
	 * @return документ
	 */
	public ClientDocument getClientDocument(Client client)
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents == null || documents.isEmpty())
			return null;

		Collections.sort(documents, new DocumentTypeComparator());
		return documents.get(0);
	}
}
