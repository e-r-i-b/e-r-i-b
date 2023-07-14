package com.rssl.phizic.web.persons;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionOperation;
import com.rssl.phizic.operations.person.search.SearchPersonOperation;
import com.rssl.phizic.operations.person.search.multinode.SearchPersonMultiNodeOperation;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.persons.search.SearchPersonActionBase;
import org.apache.struts.action.ActionForward;

import java.security.AccessControlException;
import java.util.List;

/**
 * @author khudyakov
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class SearchPersonAction extends SearchPersonActionBase
{
	private static final String FORWARD_SHOW_AUTOPAYMENTS_LIST      = "ShowAutopayments";
	private static final String FORWARD_SHOW_AUTOTRANSFERS_LIST     = "ShowAutotransfers";
	private static final String FORWARD_SHOW_MONEY_BOX_LIST         = "ShowMoneyBox";

	public SearchPersonOperation createSearchOperation() throws BusinessException
	{
		CSAAdminGateConfig config = ConfigFactory.getConfig(CSAAdminGateConfig.class);
		if(config.isMultiBlockMode())
		{
			return createOperation(SearchPersonMultiNodeOperation.class);
		}

		return createOperation(SearchPersonOperation.class);
	}

	protected ActionForward getStartActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ActionForward getErrorActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ActionForward getNextActionForward(SearchPersonForm frm) throws BusinessLogicException, BusinessException
	{
		if (checkAccess(ListAutoSubscriptionOperation.class, "AutoSubscriptionManagment"))
		{
			UrlBuilder builder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_SHOW_AUTOPAYMENTS_LIST).getPath());
			return new ActionForward(builder.getUrl(), true);
		}
		if (checkAccess(ListAutoSubscriptionOperation.class, "AutoTransfersManagement"))
		{
			UrlBuilder builder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_SHOW_AUTOTRANSFERS_LIST).getPath());
			return new ActionForward(builder.getUrl(), true);
		}
		if (checkAccess(ListAutoSubscriptionOperation.class, "EmployeeMoneyBoxManagement"))
		{
			UrlBuilder builder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_SHOW_MONEY_BOX_LIST).getPath());
			return new ActionForward(builder.getUrl(), true);
		}
		throw new AccessControlException("Доступ к операции запрещен.");
	}

	protected void clearClientCache() throws BusinessException, BusinessLogicException
	{
		PersonData personData= PersonContext.getPersonDataProvider().getPersonData();
		if (personData != null && personData.getPerson() != null)
		{
			List<CardLink> cardLinks = personData.getCards();
			for (CardLink link : cardLinks)
				XmlEntityListCacheSingleton.getInstance().clearCache(link.getCard(), Card.class);
		}
	}

	@Override
	protected UserVisitingMode getUserVisitingMode(SearchPersonForm frm)
	{
		return UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT;
	}
}
