package com.rssl.phizic.web.persons.search.pfp;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.operations.person.search.SearchPersonOperation;
import com.rssl.phizic.web.persons.SearchPersonForm;
import com.rssl.phizic.web.persons.search.SearchPersonActionBase;
import org.apache.struts.action.ActionForward;

/**
 * @author akrenev
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Экшен поиска клиента для ПФП
 */
public class SearchPersonForPFPAction extends SearchPersonActionBase
{
	private static final String FORWARD_SHOW_PFP = "ShowPFP";
	private static final String FORWARD_SHOW_POTENTIAL_PERSON_INFO = "ShowPotentialPersonInfo";

	public SearchPersonOperation createSearchOperation() throws BusinessException
	{
		CSAAdminGateConfig config = ConfigFactory.getConfig(CSAAdminGateConfig.class);
		if(config.isMultiBlockMode())
		{
			if (checkAccess("SearchVIPForPfpMultiNodeOperation"))
				return createOperation("SearchVIPForPfpMultiNodeOperation");
			else
				return createOperation("SearchPersonForPfpMultiNodeOperation");
		}
		else
		{
			if (checkAccess("SearchVIPForPFPOperation"))
				return createOperation("SearchVIPForPFPOperation");
			else
				return createOperation("SearchPersonForPFPOperation");
		}
	}

	@Override
	protected UserVisitingMode getUserVisitingMode(SearchPersonForm frm)
	{
		return UserVisitingMode.EMPLOYEE_INPUT_FOR_PFP;
	}

	protected ActionForward getStartActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ActionForward getErrorActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ActionForward getNextActionForward(SearchPersonForm frm)
	{
		String forward = FORWARD_SHOW_PFP;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData != null && personData.getPerson() != null && personData.getPerson().getCreationType() == CreationType.POTENTIAL)
		{
			forward = FORWARD_SHOW_POTENTIAL_PERSON_INFO;
			boolean clientNotFound = personData.getPerson().getId() == null;
			if (clientNotFound)
			{
				frm.setField("clientNotFound", clientNotFound);
				forward = FORWARD_START;
			}
		}
		return getCurrentMapping().findForward(forward);
	}
}
