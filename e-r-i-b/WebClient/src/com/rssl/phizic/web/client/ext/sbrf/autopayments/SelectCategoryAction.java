package com.rssl.phizic.web.client.ext.sbrf.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.LoginHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.payment.CreateAutoPaymentOperation;
import com.rssl.phizic.web.actions.payments.CatalogActionBase;
import com.rssl.phizic.web.actions.payments.IndexAction;
import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Выбор категории при создании шинного автоплатежа
 * @author niculichev
 * @ created 29.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class SelectCategoryAction extends IndexAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//проверка на доступность создания автоплатежа. Если нет то и не отображаем клиенту страницу.
		if(checkAccess(CreateESBAutoPayOperation.class,"ClientCreateAutoPayment") || checkAccess(CreateAutoPaymentOperation.class,"CreateAutoPaymentPayment"))
		{
			IndexForm frm = (IndexForm) form;
			frm.setIsAutoPaySearch(true);
			return super.start(mapping,form,request,response);
		}
		else
		{
			saveError(request, new BusinessLogicException("У вас нет прав на создание автоплатежа."));
			return new ActionForward("/private/payments.do");
		}
	}

	protected boolean isAutoPayProvider(ListPaymentServiceFormBase form)
	{
		return true;
	}

	protected boolean isInternetBank()
	{
		return true;
	}

	protected String getChanel()
	{
		return "WEB";
	}

	protected String[] getFunctionality(IndexForm frm) throws BusinessException
	{
		if (isIQWaveAutoPaymentPermit() && isESBAutoPaymentPermit())
		{
			return new String[]{CatalogActionBase.ESB_AUTOPAYMENTS_FUNCTIONALITY, CatalogActionBase.IQW_AUTOPAYMENTS_FUNCTIONALITY};
		}
		if (isIQWaveAutoPaymentPermit())
		{
			return new String[]{CatalogActionBase.IQ_WAVE_UUID_PARAMETER_NAME};
		}
		if (isESBAutoPaymentPermit())
		{
			return new String[]{CatalogActionBase.ESB_AUTOPAYMENTS_FUNCTIONALITY};
		}
		return new String[]{};
	}

	protected String getTopLevelServicesQueryName(IndexForm frm)
	{
		return "toplist.autopayments";
	}

	protected String getServicesQueryName(IndexForm frm)
	{
		return "services.autopayments";
	}

	protected void fillSearchQuery(Query query, IndexForm frm) throws BusinessException
	{
		super.fillSearchQuery(query, frm);
		query.setParameter("orderType", frm.getSearchType());
		query.setParameter("loginId", LoginHelper.getCurrentUserLoginId().getId());
	}

	protected boolean needFillInvoices()
	{
		return false;
	}
}
