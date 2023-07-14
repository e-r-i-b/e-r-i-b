package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.PersonalFinanceProfileService;
import com.rssl.phizic.operations.pfp.EditPfpPersonalDataOperation;
import com.rssl.phizic.operations.pfp.ReplanPFPOperation;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 23.05.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class PFPZeroStepActionBase extends PassingPFPActionBase
{
	protected static final String FORWARD_AGREE = "Agree";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.startReplanning",  "startReplanning");
		map.put("button.continuePlanning", "continuePlanning");
		return map;
	}

	public ActionForward startReplanning(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createOperation(ReplanPFPOperation.class);
		return mapping.findForward(FORWARD_AGREE);
	}

	public ActionForward continuePlanning(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpPersonalDataOperation operation = createOperation(EditPfpPersonalDataOperation.class);
		Map<String, PersonalFinanceProfile> allProfiles = operation.getAllProfiles();
		PersonalFinanceProfile notCompletedPFP = allProfiles.get(PersonalFinanceProfileService.NOT_COMPLETED_PFP);
		if (notCompletedPFP == null)
			throw new BusinessException("Ќе найдено незавершенных планирований");
		
		operation.initialize(notCompletedPFP);
		return getRedirectForward(operation);
	}
}