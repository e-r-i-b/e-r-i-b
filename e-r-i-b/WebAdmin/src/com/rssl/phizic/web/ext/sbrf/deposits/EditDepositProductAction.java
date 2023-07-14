package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.deposits.EditDepositDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

/**
 * @author filimonova
 * @ created 15.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditDepositProductAction extends EditActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessException
	{
		EditDepositDetailsOperation op  = (EditDepositDetailsOperation) editOperation;
		EditDepositProductForm form = (EditDepositProductForm) editForm;
		DepositProduct depositProduct = op.getEntity();
		depositProduct.setAvailableOnline(form.isAvailable());
		depositProduct.setDescription(op.updateDepositSubTypeParams(form.getDepositSubTypeIds()));
		depositProduct.setAllowedDepartments(op.getProductAllowedTBList(form.getTerbankIds()));
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		//Do nothing
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		//Do nothing
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws TransformerConfigurationException, BusinessException
	{
		EditDepositProductForm frm = (EditDepositProductForm)form;
		EditDepositDetailsOperation op  = (EditDepositDetailsOperation) operation;
		DepositProduct depositProduct = op.getEntity();
		frm.setAvailable(depositProduct.isAvailableOnline());
		frm.setDepartments(depositProduct.getAllowedDepartments());
		frm.setDepositName(depositProduct.getName());

		try
		{
			Source templateSource = op.getTemplateSource();
			TransformerFactory fact = TransformerFactory.newInstance();
			fact.setURIResolver(new StaticResolver());
			Templates templates = fact.newTemplates(templateSource);
			XmlConverter converter = new DepositHtmlConverter(templates);
			frm.setHtml(getHtml(op, converter, frm.getTariff(), frm.getGroup()));

			Source visibilityTemplateSource = op.getVisibilityTemplateSource();
			TransformerFactory visibilityFactory = TransformerFactory.newInstance();
			visibilityFactory.setURIResolver(new StaticResolver());
			Templates visibilityTemplates = visibilityFactory.newTemplates(visibilityTemplateSource);
			XmlConverter visibilityConverter = new DepositHtmlConverter(visibilityTemplates);
			frm.setVisibilityHtml(getHtml(op, visibilityConverter, null, null));
		}
		catch (TransformerConfigurationException ex)
		{
			throw new BusinessException(ex);
		}
	}

	protected String getHtml(EditDepositDetailsOperation operation, XmlConverter converter, String tariff, String group) throws BusinessException
	{
		HttpServletRequest request = currentRequest();

		try
		{
			converter.setData(new DOMSource( XmlHelper.parse( operation.getEntity().getDescription() ) ) );
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
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		converter.setParameter("curDate", df.format((new GregorianCalendar()).getTime()));
		converter.setParameter("isPension", false);
		converter.setParameter("tariff", StringHelper.getEmptyIfNull(tariff));
		converter.setParameter("group", StringHelper.getEmptyIfNull(group));
		converter.setParameter("admin", true);
		return converter.convert().toString();
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditDepositDetailsOperation operation = createOperation(EditDepositDetailsOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}
