package com.rssl.phizic.web.chargeOff;

import com.rssl.phizic.business.chargeoff.ChargeOffState;
import com.rssl.phizic.business.chargeoff.ChargeOffLog;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.chargeOff.GetPaymentRegisterOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.persons.PersonUtils;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author egorova
 * @ created 21.01.2009
 * @ $Author$
 * @ $Revision$
 */
//TODO отнаследоваться от ListActionBase
public class ChargeOffListAction  extends OperationalActionBase
{
	private static final String FORWARD_SHOW_LIST = "ShowList";
	private static PersonService personService = new PersonService();
	

	protected Map getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ChargeOffListForm frm = (ChargeOffListForm) form;
		GetPaymentRegisterOperation op = createOperation(GetPaymentRegisterOperation.class);
		ActivePerson person = (ActivePerson) personService.findById(PersonUtils.getPersonId(request));
		

		op.initialize(PersonUtils.getPersonId(request));

		String dateString = (String) frm.getFilter("operationDate");
		Calendar operationDate = (!StringHelper.isEmpty(dateString))?DateHelper.parseCalendar(dateString):null;

		String stateString = (String) frm.getFilter("operationState");
		ChargeOffState state = (!StringHelper.isEmpty(stateString))?ChargeOffState.valueOf(stateString):null;

		String maxRows = (String)frm.getFilter("maxRows");
		int listLimit = (!StringHelper.isEmpty(maxRows))?Integer.parseInt((String)frm.getFilter("maxRows")):20;

		List<ChargeOffLog> chargeOffLogs = op.getChargeOffLogs(operationDate, state, listLimit+1);
		frm.setChargeOffLogs(chargeOffLogs);

		if (listLimit == -1)
		{
			listLimit = frm.getChargeOffLogs().size();
		}
		frm.setListLimit(listLimit);
		frm.setFilter("maxRows", listLimit);
		frm.setActivePerson(op.getPerson());

		frm.setModified(personService.getPersonInstanceName(person.getId())!=null);

		addLogParameters(new CollectionLogParametersReader("Обрабатываемая сущность", chargeOffLogs));

		return PersonUtils.redirectWithPersonId(mapping.findForward(FORWARD_SHOW_LIST), request);
	}
}
