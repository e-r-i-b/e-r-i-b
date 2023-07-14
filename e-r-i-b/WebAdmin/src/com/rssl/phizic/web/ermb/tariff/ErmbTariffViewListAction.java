package com.rssl.phizic.web.ermb.tariff;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.DisconnectNotAvailibleErmbTariffException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff.ErmbTariffRemoveOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff.ErmbTariffViewListOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 * Экшен просмотра списка тарифов ЕРМБ
 */
public class ErmbTariffViewListAction extends ListActionBase
{
	protected static final String CHANGE_TARIFF = "ChangeTariff";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ErmbTariffViewListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		List<Long> ids = StrutsUtils.parseIds(frm.getSelectedIds());

		ActionMessages errors = new ActionMessages();

		try
		{
			RemoveEntityOperation operation = createOperation(ErmbTariffRemoveOperation.class);
			for (Long id : ids)
			{
				operation.initialize(id);
				try
				{
					operation.remove();
					//Фиксируем удаляемые сущности.
					addLogParameters(new BeanLogParemetersReader("Данные удаленной сущности", operation.getEntity()));;
				}
				catch (DisconnectNotAvailibleErmbTariffException e)
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("ermb.tariff.message.noRemove ", id));
					saveErrors(request, errors);
					//форвард на страницу с выбаром смены тарифа
					return new ActionForward( getCurrentMapping().findForward(CHANGE_TARIFF).getPath() + "?id=" +id,true);
				}
				catch (BusinessLogicException e)
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage()));
				}
				//Фиксируем удаляемые сущности.
				addLogParameters(new BeanLogParemetersReader("Данные удаленной сущности", operation.getEntity()));
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		stopLogParameters();
		saveErrors(request, errors);
		return filter(mapping, form, request, response);
	}
}

