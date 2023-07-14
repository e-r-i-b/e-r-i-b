package com.rssl.phizic.web.common.client.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

/**
 * @author Kosyakov
 * @ created 12.05.2006
 * @ $Author: egorovaav $
 * @ $Revision: 73169 $
 */

@SuppressWarnings({"JavaDoc"})
public class DepositDetailsAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		DepositDetailsOperation operation = createOperation(DepositDetailsOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		DepositDetailsForm frm = (DepositDetailsForm)form;
		DepositDetailsOperation op = (DepositDetailsOperation)operation;
		DepositProduct depositProduct = (DepositProduct) operation.getEntity();

        frm.setName(DepositProductHelper.getAvailableSubKindsName(depositProduct));

		try
		{
			Source templateSource = getTemplateSource(op);
			TransformerFactory fact = TransformerFactory.newInstance();
			fact.setURIResolver(new StaticResolver());
			Templates templates = fact.newTemplates(templateSource);
			XmlConverter converter = new DepositHtmlConverter(templates);
			converter.setParameter("group", StringHelper.getEmptyIfNull(frm.getGroup()));
			converter.setParameter("segment", StringHelper.getEmptyIfNull(frm.getSegment()));
			frm.setHtml( getHtml(op, converter) );
		}
		catch (TransformerConfigurationException ex)
		{
			throw new BusinessException(ex);
		}
	}

	protected String getHtml(ViewEntityOperation operation, XmlConverter converter) throws BusinessException
	{
		DepositDetailsOperation op = (DepositDetailsOperation)operation;
		HttpServletRequest request = currentRequest();

		try
		{
		converter.setData(new DOMSource( XmlHelper.parse( op.getEntity().getDescription() ) ) );
		}
		catch (IOException ex)
		{
			throw new BusinessException(ex);
		}
		catch (SAXException ex)
		{
			throw new BusinessException(ex);
		}
		catch (ParserConfigurationException ex)
		{
			throw new BusinessException(ex);
		}

		converter.setParameter("webRoot", request.getContextPath());
		converter.setParameter("resourceRoot",
				StringHelper.getEmptyIfNull(currentServletContext().getInitParameter("resourcesRealPath")));
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		converter.setParameter("curDate", df.format((new GregorianCalendar()).getTime()));
		converter.setParameter("isPension", PersonHelper.isPensioner());
		String tariff = PersonContext.getPersonDataProvider().getPersonData().getPerson().getTarifPlanCodeType();
		converter.setParameter("tariff", StringHelper.getZeroIfEmptyOrNull(tariff));
		return converter.convert().toString();
	}

	protected Source getTemplateSource(DepositDetailsOperation op) throws BusinessException
	{
		return op.getTemplateSource();
	}
}
