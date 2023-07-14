package com.rssl.phizic.web.actions.payments.catalog;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.CatalogActionBase;
import com.rssl.phizic.web.actions.payments.IndexForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 * Базовый класс каталога услуг для каналов, отличных от веба
 */
public abstract class ApiCatalogActionBase extends CatalogActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ApiCatalogFormBase frm = (ApiCatalogFormBase) form;
		String idS = frm.getId();

		// если пусто => возвращаем список категорий верхнего уровня
		if (StringHelper.isEmpty(idS) || idS.trim().length() == 0)
		{
			frm.setType(ApiCatalogFormBase.CATEGORY_TYPE);
			frm.setList(getTopLevelServices(frm));
			return mapping.findForward(FORWARD_START);
		}

		frm.setServiceId(Long.valueOf(idS));
		if (getFinalDescendants(frm))
		{
			//получаем список поставщиков, привязанных к данной услуге
			frm.setType(ApiCatalogFormBase.PROVIDER_TYPE);
			frm.setList(getProviders(frm));
			return mapping.findForward(FORWARD_START);
		}

		List<Object[]> services = getServices(frm);
		if (!services.isEmpty())
		{
			//если есть дочерние услуги - возвращаем их
			frm.setType(ApiCatalogFormBase.SERVICE_TYPE);
			frm.setList(services);
			return mapping.findForward(FORWARD_START);
		}
		//возвращаем поставщиков
		frm.setType(ApiCatalogFormBase.PROVIDER_TYPE);
		frm.setList(getProviders(frm));
		return mapping.findForward(FORWARD_START);

	}

	protected abstract boolean getFinalDescendants(IndexForm frm);

	protected String getTopLevelServicesQueryName(IndexForm form)
	{
		ApiCatalogFormBase frm = (ApiCatalogFormBase) form;
		if (frm.isAutoPaymentOnly())
		{
			return "toplist.autopayments.api";
		}
		return "toplist.payments.api";
	}

	protected String getServicesQueryName(IndexForm form)
	{
		ApiCatalogFormBase frm = (ApiCatalogFormBase) form;
		if (frm.isAutoPaymentOnly())
		{
			return "services.autopayments";
		}
		return super.getServicesQueryName(frm);
	}

	protected String getProvidersQueryName(IndexForm form)
	{
		ApiCatalogFormBase frm = (ApiCatalogFormBase) form;
		if (getFinalDescendants(frm))
		{
			return "providers.available.descendants";
		}
		return "providers.available";
	}

	public String[] getFunctionality(IndexForm form) throws BusinessException
	{
		ApiCatalogFormBase frm = (ApiCatalogFormBase) form;
		if (!frm.isAutoPaymentOnly())
		{
			return new String[]{CatalogActionBase.PAYMENTS_FUNCTIONALITY};
		}
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

	protected void fillPagination(Query query, IndexForm form)
	{
		ApiCatalogFormBase frm = (ApiCatalogFormBase) form;
		if (frm.getPaginationSize() > 0)
		{
			query.setMaxResults(frm.getPaginationSize() + 1);
		}
		if (frm.getPaginationOffset() > 0)
		{
			query.setFirstResult(frm.getPaginationOffset());
		}
	}

	protected void fillSearchPagination(Query query, IndexForm frm)
	{
		fillPagination(query, frm);
	}
}