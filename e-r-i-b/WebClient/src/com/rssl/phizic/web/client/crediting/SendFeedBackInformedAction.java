package com.rssl.phizic.web.client.crediting;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 22.01.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн отправки отклика "Проинформирован"
 */
public class SendFeedBackInformedAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		SendFeedBackInformedForm frm = (SendFeedBackInformedForm)form;
		List<OfferId>  offerIds = new ArrayList<OfferId>(1);
		offerIds.add(OfferId.fromString(frm.getId()));
		if (frm.isCardOffer())
			personData.sendLoanCardOffersFeedback(offerIds, FeedbackType.INFORM);
		else
			personData.sendLoanOffersFeedback(offerIds, FeedbackType.INFORM);
		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
