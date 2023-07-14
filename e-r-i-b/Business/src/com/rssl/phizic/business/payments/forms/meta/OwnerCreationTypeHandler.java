package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.persons.LightPerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.StringHelper;

/**
 *
 * Хендлер для проверки на совпадение типа создания владельца платежа и заданного в параметре creationType типа
 *
 * @author egorova
 * @ created 01.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class OwnerCreationTypeHandler extends BusinessDocumentHandlerBase
{
	private static final PersonService personService = new PersonService();

	private static final String CREATION_TYPE_NAME = "creationType";

	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(stateObject instanceof BusinessDocumentBase))
			throw new DocumentException("Неверный тип платежа. Ожидается BusinessDocumentBase");
		BusinessDocumentBase document = (BusinessDocumentBase) stateObject;

		String creationType = getParameter(CREATION_TYPE_NAME);
		if (StringHelper.isEmpty(creationType))
			throw new DocumentException("Не заполнен обязательный параметр " + CREATION_TYPE_NAME + ". Платеж не может быть обработан.");

		if (getCreationType(document) != CreationType.valueOf(creationType))
		{
			MessageCreatorHelper messageHelper = new MessageCreatorHelper();
			messageHelper.setParameter(MessageCreatorHelper.NEED_SPECAIL_KEY_FOR_TEXT_ERROR, getParameter(MessageCreatorHelper.NEED_SPECAIL_KEY_FOR_TEXT_ERROR));
			throw messageHelper.buildException(stateObject, this.getClass());
		}
	}

	private CreationType getCreationType(BusinessDocumentBase document) throws DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Login login = documentOwner.getLogin();
			if (login == null)
			{
				throw new DocumentException("Не указан владелец для бизнес-документа ID=" + document.getId());
			}

			LightPerson lightPerson = personService.getLightPersonByLogin(login.getId());
			if (lightPerson == null)
			{
				throw new DocumentException("Не найден пользователь с LOGIN_ID=" + login.getId());
			}

			return lightPerson.getCreationType();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
