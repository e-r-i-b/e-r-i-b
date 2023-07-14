package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.operations.depo.GetDepoAccountPositionOperation;
import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.depo.DepoAccountDivision;
import com.rssl.phizic.gate.depo.DepoAccountSecurity;
import com.rssl.phizic.gate.depo.DepoAccountSecurityStorageMethod;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 14.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowClientSecuritiesAction extends OperationalActionBase
{
	public static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.EMPTY_MAP;  
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowClientSecuritiesForm frm = (ShowClientSecuritiesForm) form;
	    String depoAcc        = frm.getDepoAccount();
		String divisionType   = frm.getDivisionType();
		String divisionNumber = frm.getDivisionNumber();

	    GetDepoAccountPositionOperation operation = createOperation(GetDepoAccountPositionOperation.class);
	    operation.initialize(depoAcc);
		DepoAccountPosition position = operation.getDepoAccountPositionInfo();

		updateFormData(frm, divisionNumber, divisionType, position);
		return mapping.findForward(FORWARD_START);
	}

	private void updateFormData(ShowClientSecuritiesForm frm, String divisionNumber, String divisionType, DepoAccountPosition position)
	{
	    List<DepoAccountDivision> accountDivisions = position.getDepoAccountDivisions();		
		List<DepoAccountSecurity> securityList = new ArrayList();
		for(DepoAccountDivision accountDivision : accountDivisions)
		{
			if(accountDivision.getDivisionNumber().equals(divisionNumber) &&
			   accountDivision.getDivisionType().equals(divisionType))
			{
				List<DepoAccountSecurity> list = accountDivision.getDepoAccountSecurities();
				for(DepoAccountSecurity security : list)
				{
					if(security.getStorageMethod() == DepoAccountSecurityStorageMethod.open)
					{
						securityList.add(security);
					}
				}
			}
		}
		frm.setSecurityList(securityList);
	}
}
