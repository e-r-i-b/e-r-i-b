package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.person.Person;

/**
 * @author Gainanov
 * @ created 10.04.2007
 * @ $Author$
 * @ $Revision$
 */
public class NewDocumentHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	private static final PersonService personService = new PersonService();
	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (isAnonymous())
		{
			return;//если анонимный клиент, прекращаем выполнение метода
		}

		if(!PersonContext.isAvailable())
		{
			return;
		}

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ActivePerson person = personData.getPerson();
			document.setOwner(personData.createDocumentOwner());
			((BusinessDocumentBase) document).setPayerName(person.getFullName());

			if (document instanceof GateExecutableDocument)
			{
				String clientId = person.getClientId();
				if (person.getTrustingPersonId() != null)
				{
					Person temp = personService.findById(person.getTrustingPersonId());
					clientId = temp.getClientId();
				}
				((GateExecutableDocument) document).setExternalOwnerId(clientId);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	protected boolean isAnonymous()
	{
		return AuthModule.getAuthModule().getPrincipal().getAccessType().equals(AccessType.anonymous);
	}

}
