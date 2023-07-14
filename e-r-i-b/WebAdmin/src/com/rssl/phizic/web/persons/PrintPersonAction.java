package com.rssl.phizic.web.persons;

import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.messaging.info.NotificationChannel;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.operations.person.EditPersonOperation;
import com.rssl.phizic.operations.person.PrintPersonOperation;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roshka
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */

public class PrintPersonAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String,String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PrintPersonForm frm = (PrintPersonForm) form;
        PrintPersonOperation operation  =  createOperation(PrintPersonOperation.class);
        operation.setPersonId(frm.getPerson());

        List<ActivePerson> empoweredPersons = operation.getEmpoweredPersons();
        Map<Long, List<AccountLink>> empoweredPersonsAccounts = operation.getEmpoweredPersonsAccounts();
        Map<Long, List<Service>>     empoweredPersonsServices = operation.getEmpoweredPersonsServices();
        List<Service> services = operation.getServices();
	    List<PaymentReceiverBase> receiverList = operation.getPaymentReceivers();
        frm.setActivePerson(operation.getPerson());
        frm.setAccountLinks(operation.getAccountLinks());
	    frm.setCardLinks(operation.getCardLinks());
        frm.setEmpoweredPersons(empoweredPersons);
        frm.setEmpoweredPersonsAccounts(empoweredPersonsAccounts);
        frm.setEmpoweredPersonsServices(empoweredPersonsServices);
        frm.setServices(services);
	    frm.setReceivers(receiverList);
        frm.setPrincipalPerson(operation.getPrincipalPerson());
        PINEnvelope pin = operation.getCurrentLinkedPINEnvelope();
        if (pin != null)
          frm.setPin(pin.getUserId());
        else
          frm.setPin("");
        frm.setDepartment(operation.getDepartment());
	    Set<PersonDocument> personDocuments = operation.getPerson().getPersonDocuments();
		if (personDocuments != null)
			{
				for(PersonDocument document : personDocuments)
				{
					if (document.getDocumentMain())
					{
						frm.setActiveDocument(document);
						break;
					}
				}
			}

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getPerson()));

	    SetupNotificationOperation notificationOperation = createOperation(SetupNotificationOperation.class);
	    notificationOperation.initialize(operation.getPerson(), UserNotificationType.operationNotification);
	    String channel = notificationOperation.getChannel(UserNotificationType.operationNotification);
	    if (!NotificationChannel.none.name().equals(channel))
	        frm.setMessageService(channel);

        return mapping.findForward(FORWARD_START);
    }
}
