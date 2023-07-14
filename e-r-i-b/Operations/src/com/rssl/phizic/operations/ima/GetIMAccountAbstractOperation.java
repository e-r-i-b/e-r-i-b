package com.rssl.phizic.operations.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.IMAccountServiceHelper;
import com.rssl.phizic.business.bankroll.TransactionComparator;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.GroupResultHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 * �������� ������ ������������ ������������� ������ ������� � ������ ��������� �� ���
 */
public class GetIMAccountAbstractOperation extends OperationBase implements ViewEntityOperation
{
	private static IMAccountService service = GateSingleton.getFactory().service(IMAccountService.class);;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private boolean isBackError;
	private boolean isUseStoredResource;
	private boolean isError;

	private List<IMAccountLink> links;

	private boolean isFullAbstractCreated = false;

	//��������� ��������� �� ������ ��� ����������� ������������ ��� ��������� ������� �� ���
	private final Map<IMAccountLink, String> imAccountAbstractMsgErrorMap = new HashMap<IMAccountLink, String>();
	/**
	 * ���������������� ������ ������ �� ���
	 * @param link ������ ��� ������
	 */
	public void initialize(List<IMAccountLink> link, boolean isFullAbstractCreated)
	{
		this.isFullAbstractCreated = isFullAbstractCreated;
		this.links = link;

		if (links != null)
		{
			for(IMAccountLink item : links)
			{
				isUseStoredResource = (item.getImAccount() instanceof AbstractStoredResource);

				if (isUseStoredResource)
				{
					break;
				}
			}
		}
	}

	/**
	 * �������������� �������� ��� ������ �� ��� � ��������������� imAccountLinkId
	 * @param imAccountLinkId - ������������� ��� ���������������� ������ �� ��� �������
	 * @throws BusinessException
	 */
	public void initialize(Long imAccountLinkId, boolean isFullAbstractCreated) throws BusinessException
	{
		this.isFullAbstractCreated = isFullAbstractCreated;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		this.links = new ArrayList<IMAccountLink>();

		IMAccountLink link = personData.getImAccountLinkById(imAccountLinkId);
		this.links.add(link);

		isUseStoredResource = (link.getImAccount() instanceof AbstractStoredResource);
	}
	/**
	 * ���������� ������ ������ �� ��� � ��������� � ���
	 * @param count - ���������� ��������
	 * @return ������ ������ � ������� � ���
	 */
	public Map<IMAccountLink, IMAccountAbstract> getIMAccountAbstractMap(Long count)
	{
		IMAccount[] imAccounts = imAccountsArray();
		if(ArrayUtils.isEmpty(imAccounts))
	        return Collections.emptyMap();

		if (isUseStoredResource())
		{
			isError = true;
			for (IMAccountLink link : links)
			{
				imAccountAbstractMsgErrorMap.put(link, StoredResourceMessages.getUnreachableStatement());
			}
			return Collections.emptyMap();
		}

		Map<IMAccountLink, IMAccountAbstract> imAccountAbstractMap = new HashMap<IMAccountLink, IMAccountAbstract>();
		GroupResult<IMAccount, IMAccountAbstract> baseAbstract = service.getAbstract(count, imAccounts);

		Map<IMAccount,     IMAccountAbstract>         results = baseAbstract.getResults();
	    Map<IMAccount,     IKFLException>          exceptions = baseAbstract.getExceptions();

		for(Map.Entry<IMAccount, IMAccountAbstract> resultEntry : results.entrySet())
		{
			IMAccount imAccount = resultEntry.getKey();
			IMAccountLink imAccountLink = IMAccountServiceHelper.getIMAccountLinkByNumber(imAccount.getNumber(), links);

			if (imAccountLink == null)
			{
				log.error("�������� ������� �� �� ���� ���. ��� �" + imAccount.getNumber());
				continue;
			}

			IMAccountAbstract imAccountAbstract = resultEntry.getValue();
			if (imAccountAbstract != null)
			{
				List<TransactionBase> transactions = imAccountAbstract.getTransactions();
				//���������, ���� ���� ��� �����������
				if (transactions != null)
					Collections.sort(transactions, new TransactionComparator());
			}

			imAccountAbstractMap.put(imAccountLink, imAccountAbstract);
		}
		for (Map.Entry<IMAccount, IKFLException>  exceptionEntry : exceptions.entrySet())
		{
			IMAccount imAccount = exceptionEntry.getKey();
			IMAccountLink imAccountLink = IMAccountServiceHelper.getIMAccountLinkByNumber(imAccount.getNumber(), links);
			IKFLException ikflException = exceptionEntry.getValue();

			if (imAccountLink != null)
			{
				imAccountAbstractMap.put(imAccountLink, null);
				if (ikflException instanceof GateLogicException)
					imAccountAbstractMsgErrorMap.put(imAccountLink,ikflException.getMessage());
			}
			log.error("��������� ������ ��� ��������� ������� �� ����� ��� �" + imAccount.getNumber(), ikflException);
		}
		//����� ������ ������ ���� �� ���� ��� ������ ������
	    if (MapUtils.isEmpty(results) && !MapUtils.isEmpty(exceptions))
		    isBackError = true;

		return imAccountAbstractMap;
	}

	private IMAccount[] imAccountsArray()
	{
		if (CollectionUtils.isEmpty(links))
		{
			return null;
		}
		isBackError = true;
		IMAccount[] imAccounts = new IMAccount[links.size()];

		int i = 0;
		for(IMAccountLink imLink: links)
		{
			IMAccount imAccount = imLink.getImAccount();
			imAccounts[i++] = imAccount;
			isBackError = isBackError && imAccount == null;
		}
		return imAccounts;
	}

	/**
	 * ���������� ������ �� ��� ��� ������ ��������
	 * @return ������ �� ������� ��� �������
	 * @throws BusinessException
	 */
	public IMAccountLink getEntity() throws BusinessException
	{
		if (CollectionUtils.isEmpty(links))
		{
			throw new BusinessException("�� ���������� ������������� ���");
		}
		else
		{
			return links.get(0);
		}
	}

	/**
	 * ��������� ������� � ������������ � ��������� ��������
	 * @param from ���� ������� � ������� ����� ������������� �������
	 * @param to ���� ���������� � ������� ����� ������������� �������
	 * @return IMAccountAbstract ������� �� ����� ���
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public IMAccountAbstract getAbstract(Calendar from, Calendar to) throws BusinessException, BusinessLogicException
	{
		IMAccountAbstract imaAbstract = null;

		try
		{
			imaAbstract = service.getAbstract(getEntity().getImAccount(), from, to);
		}
		catch(GateException ge)
		{
			log.error("������ ��� ��������� ������� �� ��� " + getEntity().getNumber(), ge);
	        return null;
		}
		catch (GateLogicException gle)
		{
			throw new BusinessLogicException(gle);
		}
		return imaAbstract;
	}

	/**
	 * ���������� �������-��������� ������� ���
	 * @return ��������� ���
	 * @throws BusinessException
	 */
	public Client getIMAccountClient() throws BusinessException, BusinessLogicException
	{
		IMAccount imAccount = getEntity().getImAccount();

		try
		{
			return GroupResultHelper.getOneResult(service.getOwnerInfo(imAccount));
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������� ���������� � ����� � ������� ������ ���
	 * @return Office ���� � ������� ������ ���
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Office getLeadOffice() throws BusinessException, BusinessLogicException
	{
		try
		{
			return GroupResultHelper.getOneResult( service.getLeadOffice(getEntity().getImAccount()) );
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	public boolean isBackError()
	{
		return isBackError;
	}

	public boolean isFullAbstractCreated()
	{
		return isFullAbstractCreated;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	public Map<IMAccountLink, String> getAccountAbstractMsgErrorMap()
	{
		return Collections.unmodifiableMap(imAccountAbstractMsgErrorMap);
	}

	/**
	 *
	 * @return ���� ������� ������
	 */
	public boolean isError()
	{
		return isError;
	}
}
