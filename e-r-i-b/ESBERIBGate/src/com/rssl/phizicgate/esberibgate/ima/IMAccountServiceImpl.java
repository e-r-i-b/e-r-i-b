package com.rssl.phizicgate.esberibgate.ima;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.BackRefIMAccountService;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.cache.CacheKeyConstants;
import com.rssl.phizicgate.esberibgate.cache.ESBCacheKeyGenerator;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.IMAResponseSerializer;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.*;

/**
 * @author Balovtsev
 * @created 15.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountServiceImpl extends AbstractService implements IMAccountService
{
	private IMAResponseSerializer respSerializer;
	private IMAccountRequestHelper requestHelper;

	private static Cache clientIMAccountsCache;
	private static Cache abstractCache;
	private static Cache groupAbstractCache;
	private static Cache detailsCache;

	static
	{
		clientIMAccountsCache  = CacheProvider.getCache(CacheKeyConstants.IMA_CLIENT_CACHE);
		abstractCache          = CacheProvider.getCache(CacheKeyConstants.IMA_ABSTRACT_CACHE);
		groupAbstractCache     = CacheProvider.getCache(CacheKeyConstants.IMA_SHORT_ABSTRACT_CACHE);
		detailsCache           = CacheProvider.getCache(CacheKeyConstants.IMA_DETAILS_CACHE);
	}

	protected IMAccountServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);

		requestHelper  = new IMAccountRequestHelper(getFactory());
		respSerializer = new IMAResponseSerializer();
	}

	public List<IMAccount> getClientIMAccounts(Client client) throws GateException, GateLogicException
	{
		List<? extends ClientDocument> documents = client.getDocuments();

		if (documents.isEmpty())
		{
			throw new GateException("Не заполнены документы для клиента с id = " + client.getId());
		}
		Collections.sort(documents, new DocumentTypeComparator());

		List<IMAccount> result = new ArrayList<IMAccount>();
		for (ClientDocument document : documents)
		{
			IFXRs_Type ifxRs = getIMAccountsListRequest(client, document);
			result = respSerializer.extractIMAccountsFormResponse(ifxRs, client.getInternalOwnerId());

			if (!result.isEmpty())
			{
				return result;
			}
		}
		return result;
	}

	public GroupResult<String, IMAccount> getIMAccount(String... ids)
	{
		GroupResult<String, IMAccount> result = new GroupResult<String, IMAccount>();

		if (ids == null || ids.length == 0)
		{
			return result;
		}

		/*
		 * На данный момент передавать может только один номер
		 */
		for (String id : ids)
		{
			try
			{
				IFXRs_Type ifxRs = getIMAccountDetailsRequest(id);
				result.add(respSerializer.fillIMAccounts(ifxRs, id));
			}
			catch (GateException e)
			{
				result.putException(id, e);
			}
			catch (GateLogicException gle)
			{
				result.putException(id, gle);
			}
		}
		return result;
	}

	public GroupResult<IMAccount, Office> getLeadOffice(IMAccount... imAccounts)
	{
		GroupResult<IMAccount, Office> result = new GroupResult<IMAccount, Office>();

		if (imAccounts != null && imAccounts.length > 0)
		{
			for (IMAccount imAccount : imAccounts)
			{
				try
				{
					IFXRs_Type ifxRs = getIMAccountDetailsRequest(imAccount.getId());
					Office office = respSerializer.getFilledOfficeFromIMARequest(ifxRs);

					result.putResult(imAccount, office);
				}
				catch (GateException ge)
				{
					result.putException(imAccount, ge);
				}
				catch (GateLogicException gle)
				{
					result.putException(imAccount, gle);
				}
			}
		}
		return result;
	}

	public GroupResult<IMAccount, Client> getOwnerInfo(IMAccount... imAccounts)
	{
		GroupResult<IMAccount, Client> result = new GroupResult<IMAccount, Client>();

		if (imAccounts.length == 0)
		{
			return result;
		}

		for (IMAccount imAccount : imAccounts)
		{
			try
			{
				IFXRs_Type ifxRs = getIMAccountDetailsRequest(imAccount.getId());
				result.add(respSerializer.fillClientFromIMAResponse(ifxRs, imAccounts));
			}
			catch (GateException ge)
			{
				result.putException(imAccount, ge);
			}
			catch (GateLogicException gle)
			{
				result.putException(imAccount, gle);
			}
		}
		return result;
	}

	public IMAccountAbstract getAbstract(IMAccount imAccount, Calendar fromDate, Calendar toDate) throws GateLogicException, GateException
	{
			Pair<IFXRq_Type, IFXRs_Type> ifx = getFullAbstractRequest(imAccount, fromDate, toDate);
			return respSerializer.fillFullIMAccountAbstract(ifx.getFirst(), ifx.getSecond(), imAccount);
	}
	
	public GroupResult<IMAccount, IMAccountAbstract> getAbstract(Long count, IMAccount... imAccounts)
	{
		try
		{
			GroupResult<IMAccount, IMAccountAbstract> result = new GroupResult<IMAccount, IMAccountAbstract>();
			for (IMAccount imAccount : imAccounts)
			{
				if(imAccount != null)
				{
					//noinspection NestedTryStatement
					try
					{
						Pair<IFXRq_Type, IFXRs_Type> ifx = getShortIMAccountAbstractRequest(imAccount);
						IMAccountAbstract imAccountAbstract = respSerializer.fillShortIMAccountAbstract(ifx.getFirst(), ifx.getSecond(), imAccount);
						result.putResult(imAccount, imAccountAbstract);
					}
					catch (GateLogicException gle)
					{
						//Локальная ошибка (шина вернула по данному ОМС ответ с ошибкой при получении выписки)
						result.putException(imAccount, gle);
					}
				}
			}
			return result;
		}
		catch (GateException ge)
		{
			//Глобальная ошибка (шина вернула выписку не по тому ОМС или ничего не вернула)
			return GroupResultHelper.getOneErrorResult(imAccounts, ge);
		}
	}

	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
	public GroupResult<String, IMAccount> getIMAccountByNumber(Client client, String... imAccountNumbers)
	{
		GroupResult<String, IMAccount> result = new GroupResult<String, IMAccount>();

		if (imAccountNumbers == null || imAccountNumbers.length == 0)
			return result;

		BackRefIMAccountService backRefIMAccountService = GateSingleton.getFactory().service(BackRefIMAccountService.class);
		GroupResult<String, String> groupExternalIds = backRefIMAccountService.findIMAccountExternalId(client.getInternalOwnerId(), imAccountNumbers);
		if (groupExternalIds.isOneError())
		{
			return GroupResultHelper.getOneErrorResult(imAccountNumbers, groupExternalIds.getOneErrorException());
		}

		List<String> externalIds = GroupResultHelper.getResults(groupExternalIds);
		GroupResult<String, IMAccount> idRes = getIMAccount(externalIds.toArray(new String[externalIds.size()]));
		for (String externalId : externalIds)
		{
			IMAccount idResult = idRes.getResult(externalId);
			IKFLException idException = idRes.getException(externalId);
			String number = EntityIdHelper.getCommonCompositeId(externalId).getEntityId();
			if (idResult != null)
			{
				result.putResult(number, idResult);
			}
			else if (idException != null)
			{
				result.putException(number, idException);
			}
		}

		Map<String, IKFLException> exceptions = groupExternalIds.getExceptions();
		for (String imAccountNumber : exceptions.keySet())
		{
			result.putException(imAccountNumber, exceptions.get(imAccountNumber));
		}

		return result;
	}

	/**
	 * Если это необходимо кэшируется сообщение содержащее список ОМС счетов
	 * @param imAccount ОМС счёт
	 * @return результат запроса
	 */
	private Pair<IFXRq_Type, IFXRs_Type> getShortIMAccountAbstractRequest(IMAccount imAccount) throws GateException, GateLogicException
	{
		if(imAccount == null)
		{
			return new Pair<IFXRq_Type, IFXRs_Type>();
		}

		String cacheKey = imAccount.getId();
		Element element = groupAbstractCache.get(cacheKey);

		if(element == null)
		{
			IFXRq_Type ifxRq = requestHelper.createShortIMAccountAbstractRequest(imAccount);
			IFXRs_Type ifxRs = getRequest(ifxRq);

			// Кладем сообщение в кеш только в случае ответа без ошибок
			if (checkShortIMAAbstract(ifxRs))
				store2Cache(groupAbstractCache, new Element(cacheKey, ifxRs));
			return new Pair<IFXRq_Type, IFXRs_Type>(ifxRq, ifxRs);
		}
		return new Pair<IFXRq_Type, IFXRs_Type>(null, (IFXRs_Type) element.getObjectValue());
	}

	private boolean checkShortIMAAbstract(IFXRs_Type ifxRs)
	{
		BankAcctStmtInqRs_Type bankAcctStmtInqRs = ifxRs.getBankAcctStmtInqRs();
		if (bankAcctStmtInqRs == null)
			return false;

		for (IMSAcctRec_Type imsAcctRec: bankAcctStmtInqRs.getIMSAcctRec())
		{
			if (imsAcctRec.getStatus().getStatusCode() != 0L)
				return false;
		}

		return true;
	}

	/**
	 * Если это необходимо кэшируется сообщение содержащее список ОМС счетов
	 * @param client клиент
	 * @param document документ клиента
	 * @return результат запроса
	 */
	private IFXRs_Type getIMAccountsListRequest(Client client, ClientDocument document) throws GateLogicException, GateException
	{
		String cacheKey = ESBCacheKeyGenerator.getIMAClientDocumentKey(client, document);
		Element element = clientIMAccountsCache.get(cacheKey);

		if (element == null)
		{
			ProductContainer productContainer = requestHelper.createIMAccountsListRequest(client, document);
			if (productContainer.getIfxRq_type() == null)
			{
				//если на внешнюю систему навешан тех. перерыв
				throw new InactiveExternalSystemException(productContainer.getProductError(BankProductType.IMA));
			}

			IFXRs_Type ifxRs = getRequest(productContainer.getIfxRq_type());

			// Кладем сообщение в кеш только в случае ответа без ошибок
			if (checkClientIMAccounts(ifxRs))
				store2Cache(clientIMAccountsCache, new Element(cacheKey, ifxRs));
			return ifxRs;
		}
		return (IFXRs_Type) element.getObjectValue();
	}

	private boolean checkClientIMAccounts(IFXRs_Type ifxRs)
	{
		if (ifxRs.getBankAcctInqRs() == null)
			return false;

		if (ifxRs.getBankAcctInqRs().getStatus().getStatusCode() == 0L)
			return true;

		return false;
	}

	/**
	 * Если это необходимо кэшируется сообщение содержащее детальную информацию
	 * @param id идентификатор ОМС счёта
	 * @return результат запроса
	 */
	private IFXRs_Type getIMAccountDetailsRequest(String id) throws GateLogicException, GateException
	{
		Element element = detailsCache.get(id);

		if(element == null)
		{
			IFXRq_Type ifxRq = requestHelper.createIMAccountDetailsRequest(id);
			IFXRs_Type ifxRs = getRequest(ifxRq);

			// Кладем сообщение в кеш только в случае ответа без ошибок
			if (checkIMADetails(ifxRs))
				store2Cache(detailsCache, new Element(id, ifxRs));
			return ifxRs;
		}
		return (IFXRs_Type) element.getObjectValue();
	}

	private boolean checkIMADetails(IFXRs_Type ifxRs)
	{
		ImaAcctInRs_Type imaAcctInRs = ifxRs.getImaAcctInRs();
		if (imaAcctInRs == null)
			return false;

		for (ImsRec_Type imsRec : imaAcctInRs.getImsRec())
		{
			if (imsRec.getStatus().getStatusCode() != 0L)
				return false;
		}

		return true;
	}

	/**
	 * Если это необходимо кэшируется сообщение содержащее полную выписку 
	 * @param imAccount ОМС счёт
	 * @param fromDate дата начиная с которой формировать выписку
	 * @param toDate дата по которую формировать выписку
	 * @return результат запроса
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private Pair<IFXRq_Type, IFXRs_Type> getFullAbstractRequest(IMAccount imAccount, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		String cacheKey  = ESBCacheKeyGenerator.getIMAPeriodKey(imAccount, fromDate, toDate);
		Element element = abstractCache.get(cacheKey);

		if(element == null)
		{
			IFXRq_Type ifxRq = requestHelper.createFullIMAccountAbstractRequest(imAccount, fromDate, toDate);
			IFXRs_Type ifxRs = getRequest(ifxRq);

			store2Cache(abstractCache, new Element(cacheKey, ifxRs));
			return new Pair<IFXRq_Type, IFXRs_Type>(ifxRq, ifxRs);
		}
		return new Pair<IFXRq_Type, IFXRs_Type>(null, (IFXRs_Type) element.getObjectValue());
	}

	private void store2Cache(Cache cache, Element element)
	{
		cache.put(element);
	}
}
