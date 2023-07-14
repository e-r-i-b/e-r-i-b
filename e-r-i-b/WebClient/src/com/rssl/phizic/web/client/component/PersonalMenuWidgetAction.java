package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.operations.widget.PersonalMenuWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

import java.util.List;

/** Ёкшн виджета "Ћичное меню"
 * @author gulov
 * @ created 16.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PersonalMenuWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(PersonalMenuWidgetOperation.class);
	}
	

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);
		PersonalMenuWidgetOperation oper = (PersonalMenuWidgetOperation)operation;
		List<TemplateDocument> showTemplates = oper.getShowTemplates();
		List<TemplateDocument> allTemplates = oper.getAllTemplates();
		List<FavouriteLink> showFavouriteLinks = oper.getShowFavouriteLinks();
		List<FavouriteLink> allFavouriteLinks = oper.getAllFavouriteLinks();
		PersonalMenuWidgetForm frm = (PersonalMenuWidgetForm) form;
		frm.setShowTemplates(showTemplates);
		frm.setAllTemplates(allTemplates);
		frm.setShowFavouriteLinks(showFavouriteLinks);
		frm.setAllFavouriteLinks(allFavouriteLinks);
	}
}
