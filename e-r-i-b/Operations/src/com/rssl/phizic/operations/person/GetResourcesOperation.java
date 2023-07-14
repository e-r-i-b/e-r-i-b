package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.pfr.PFRLinkService;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 04.10.2005
 * Time: 20:04:24
 */
public class GetResourcesOperation extends PersonOperationBase implements ListEntitiesOperation<UserRestriction>
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static final PFRLinkService pfrService = new PFRLinkService();

	public List<IMAccountLink> getIMAccounts() throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(getPerson().getLogin(), IMAccountLink.class, getInstanceName());
	}
	
	public List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(getPerson().getLogin(), AccountLink.class, getInstanceName());
	}

	public List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		 return externalResourceService.getLinks(getPerson().getLogin(), CardLink.class, null);
	}

	/**
	 * @return список кредитов клиента
	 */
	public List<LoanLink> getLoans() throws BusinessException, BusinessLogicException
	{
		 return externalResourceService.getLinks(getPerson().getLogin(), LoanLink.class, null);
	}

	/**
	 * @return список счетов депо клиента
	 */
	public List<DepoAccountLink> getDepoAccounts() throws BusinessException, BusinessLogicException
	{
		 return externalResourceService.getLinks(getPerson().getLogin(), DepoAccountLink.class, null);
	}
	/**
	 * @return список сберегательных сертификатов клиента
	 */
	public List<SecurityAccountLink> getSecurityAccounts() throws BusinessException, BusinessLogicException
	{
		 return externalResourceService.getLinks(getPerson().getLogin(), SecurityAccountLink.class, null);
	}

	/**
	 * @return Карточку ПФР клиента
	 */
	public PFRLink getPfrLink() throws BusinessException
	{
		return pfrService.findByLoginId(getPerson().getLogin().getId());
	}

	/**
	 * @return СНИЛС = Страховой Номер Индивидуального Лицевого Счёта (ПФР)
	 */
	public String getSNILS() throws BusinessException
	{
		 return getPerson().getSNILS();
	}

	public  List<AccountLink> getActiveAccounts() throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accounts = new ArrayList<AccountLink>();
		for (AccountLink accountLink : getAccounts())
			if ((new ActiveAccountFilter()).accept(accountLink) && accountLink.getShowInMain())
				accounts.add(accountLink);

		return accounts;
	}

	/**
	 * @return список основных карт клиента
	 */
	public  List<CardLink> getMainCards() throws BusinessException, BusinessLogicException
	{
		List<CardLink> cards = new ArrayList<CardLink>();
		for (CardLink cardLink : getCards())
			if (cardLink.isMain())
				cards.add(cardLink);

		return cards;
	}

	/**
	 * @return поддерживает ли подразделение клиента ESB
	 * @throws BusinessException
	 */
	public boolean isEsbSupported() throws BusinessException
	{
		return ((ExtendedDepartment) getPersonDepartment()).isEsbSupported();
	}
}
