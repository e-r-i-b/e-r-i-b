package com.rssl.phizic.web.client.offerNotifications;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.business.personalOffer.PersonalOfferProduct;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.offerNotification.NotificationLogEntryType;
import com.rssl.phizic.logging.offerNotification.OfferNotificationLogHelper;
import com.rssl.phizic.operations.offerNotification.ViewOfferNotificationOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class ViewOfferNotificationAction extends AsyncOperationalActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.click", "click");
		map.put("button.operation", "operation");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewOfferNotificationForm frm = (ViewOfferNotificationForm) form;
		ViewOfferNotificationOperation operation = createOperation(ViewOfferNotificationOperation.class);
		operation.initialize(frm.getCurrentNotificationId());
		PersonalOfferNotification offerNotification = operation.getOfferNotification();
		frm.setOfferNotification(offerNotification);
		frm.setNotificationsList(operation.getNotificationsList());
		if (offerNotification != null)
		{
			if (offerNotification.getProductType() == PersonalOfferProduct.LOAN)
				frm.setLoanOffer(operation.getLoanOffer());
			else
				frm.setLoanCardOffer(operation.getLoanCardOffer());
			OfferNotificationLogHelper.writeEntryToLog(offerNotification.getId(), offerNotification.getName(), NotificationLogEntryType.SHOW);
		}
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward click(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewOfferNotificationForm frm = (ViewOfferNotificationForm) form;
		List<OfferId> offerIds = new ArrayList<OfferId>();

		String offerId = frm.getOfferId();
		if (StringHelper.isNotEmpty(offerId))
		{
			offerIds.add(OfferId.fromString(offerId));
		}

		String offerType = frm.getOfferType();
		if (StringHelper.isNotEmpty(offerType))
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

			if (PersonalOfferProduct.valueOf(offerType) == PersonalOfferProduct.LOAN)
				personData.sendLoanOffersFeedback(offerIds, FeedbackType.PRESENTATION);
			else
				personData.sendLoanCardOffersFeedback(offerIds, FeedbackType.PRESENTATION);
		}
		OfferNotificationLogHelper.writeEntryToLog(frm.getCurrentNotificationId(), frm.getCurrentName(), NotificationLogEntryType.CLICK);
		return null;
	}
}
