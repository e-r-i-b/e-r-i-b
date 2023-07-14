package com.rssl.phizic.web.common.client.deposits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperation;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: egorovaav $
 * @ $Revision: 82874 $
 */

public class ListDepositProductsAction extends ListActionBase
{
	private static final String FORWARD_DETAILS = "Details";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDepositProductsListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws BusinessException, TransformerConfigurationException
	{
		GetDepositProductsListOperation op = (GetDepositProductsListOperation) operation;
		ListDepositProductsForm frm = (ListDepositProductsForm) form;
        frm.setPromoDivMaxLength(ConfigFactory.getConfig(PromoCodesDepositConfig.class).getMaxCountSymbols());

		if (isUseCasNsiDictionaries())
		{
			frm.setDepositProductEntities(filterDepositProductEntities(getDepositProductEntities(op, frm)));
		}
		else
		{
			Source templateSource = getTemplateSource(op);
			TransformerFactory fact = TransformerFactory.newInstance();
			fact.setURIResolver(new StaticResolver());
			Templates templates = fact.newTemplates(templateSource);
			XmlConverter converter = new DepositHtmlConverter(templates);
			frm.setListHtml(getHtml(op, converter, frm));
		}
	}

	protected List<DepositProductEntity> filterDepositProductEntities(List<DepositProductEntity> entities)
	{
		return entities;
	}

	protected boolean isUseCasNsiDictionaries()
	{
		DepositConfig depositConfig = ConfigFactory.getConfig(DepositConfig.class);
		return depositConfig.isUseCasNsiDictionaries();
	}

	protected List<DepositProductEntity> getDepositProductEntities(ListEntitiesOperation operation, ListDepositProductsForm form) throws BusinessException
	{
		return null;
	}

	protected String getHtml(ListEntitiesOperation operation, XmlConverter converter, ListDepositProductsForm form) throws BusinessException
	{
		GetDepositProductsListOperation op = (GetDepositProductsListOperation) operation;
		HttpServletRequest request = currentRequest();
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		converter.setData(op.getListSource(person.getDepartmentId().toString()));
		converter.setParameter("webRoot", request.getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		converter.setParameter("detailsUrl", request.getContextPath() + getCurrentMapping().findForward(FORWARD_DETAILS).getPath());
		return converter.convert().toString();
	}

	protected Source getTemplateSource(GetDepositProductsListOperation op) throws BusinessException
	{
		return op.getTemplateSource();
	}
}
