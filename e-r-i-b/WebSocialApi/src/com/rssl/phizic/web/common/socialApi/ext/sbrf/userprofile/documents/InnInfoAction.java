package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile.documents;

import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн для добавления, редактирования и удаления ИНН клиента
 *
 * @author EgorovaA
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class InnInfoAction extends AdditionalDocumentInfoAction
{
	/**
	 * Добавление ИНН. Если доступна операция редактирования идентификаторов корзины - добавляем через нее.
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return super.add(mapping, form, request, response);
		}
		catch (AccessControlException e)
		{
			AdditionalDocumentInfoForm frm = (AdditionalDocumentInfoForm) form;
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			UserDocumentService.get().resetUserDocument(personData.getPerson().getLogin(), DocumentType.INN, frm.getNumber());
			return super.start(mapping, form, request, response);
		}
	}

	/**
	 * Редактирование ИНН. Если операция редактирования идентификаторов корзины недоступна - обновляем через personData (при наличии уже заполненного ИНН)
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return super.update(mapping, form, request, response);
		}
		catch (AccessControlException e)
		{
			AdditionalDocumentInfoForm frm = (AdditionalDocumentInfoForm) form;
			ActivePerson activePerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();

			UserDocument inn = UserDocumentService.get().getUserDocumentByLoginAndType(activePerson.getLogin().getId(), DocumentType.INN);
			if (inn != null && inn.getId().equals(frm.getId()))
				UserDocumentService.get().resetUserDocument(activePerson.getLogin(), DocumentType.INN, frm.getNumber());
			else
				throw new ResourceNotFoundBusinessException("Документ с id=" + frm.getId() + " не найден.", UserDocument.class);

			return super.start(mapping, form, request, response);
		}
	}

	protected DocumentType getDocumentType()
	{
		return DocumentType.INN;
	}
}
