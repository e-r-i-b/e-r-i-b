package com.rssl.phizic.web.loans.products;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.*;
import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.loans.products.EditLoanProductOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 * @author gladishev
 * @ created 21.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanProductAction extends EditActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		if(frm instanceof EditModifiableLoanProductForm)
			return EditModifiableLoanProductForm.FORM;
		else
			return EditLoanProductForm.FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		if(frm instanceof EditModifiableLoanProductForm)
		{
			EditModifiableLoanProductForm form = (EditModifiableLoanProductForm) frm;
			ModifiableLoanProduct product = (ModifiableLoanProduct) entity;
			if(product.getId() == null)
				return;

			form.setField("loanKind", product.getLoanKind().getId());
			form.setField("name", product.getName());

			form.setField("minDurationYears", NumericUtil.getEmptyIfNull(product.getMinDuration().getYear()));
			form.setField("minDurationMonths", NumericUtil.getEmptyIfNull(product.getMinDuration().getMonth()));
			form.setField("maxDurationYears", NumericUtil.getEmptyIfNull(product.getMaxDuration().getYear()));
			form.setField("maxDurationMonths", NumericUtil.getEmptyIfNull(product.getMaxDuration().getMonth()));
			form.setField("isMaxDurationInclude", product.isMaxDurationInclude());
			form.setField("needInitialInstalment", product.isNeedInitialInstalment());
			form.setField("minInitialInstalment", product.getMinInitialInstalment());
			form.setField("maxInitialInstalment", product.getMaxInitialInstalment());
			form.setField("isMaxInitialInstalmentInclude", product.isMaxInitialInstalmentInclude());
			
			form.setField("needSurety", product.isNeedSurety());
			form.setField("additionalTerms", product.getAdditionalTerms());

			form.setNeedInitialInstalment(product.isNeedInitialInstalment());

			String[] terbankIds = new String[product.getTerbanks().size()];
			int i = 0;
			for(Department terbank : product.getTerbanks())
				terbankIds[i++] = terbank.getId().toString();
			form.setTerbankIds(terbankIds);
		}
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, TransformerConfigurationException
	{
		EditLoanProductOperation op = (EditLoanProductOperation) operation;

		if(form instanceof EditModifiableLoanProductForm)
		{
			EditModifiableLoanProductForm frm = (EditModifiableLoanProductForm) form;
			ModifiableLoanProduct product = (ModifiableLoanProduct) op.getEntity();
			frm.setLoanKinds(op.getLoanKinds());
			frm.setAllTerbanks(op.getAllTerbanks());
			frm.setCurrencies(op.getCurrencies());
			frm.setConditions(product.getConditions());
		}
		else if(form instanceof EditLoanProductForm)
		{
			EditLoanProductForm frm = (EditLoanProductForm) form;
			XmlConverter converter = new CommonXmlConverter(getTemplate());
			converter.setData(op.getProductSource());
			converter.setParameter("webRoot", currentRequest().getContextPath());
			converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));

			String html = converter.convert().toString();
			frm.setHtml(html);
		}
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws BusinessException
	{
		LoanKindService loanKindService = new LoanKindService();
		
		LoanProductBase productBase = (LoanProductBase) entity;
		LoanKind loanKind = loanKindService.findById(Long.parseLong((String) data.get("loanKind")));

		productBase.setName((String) data.get("name"));
		productBase.setLoanKind(loanKind);

		if(entity instanceof ModifiableLoanProduct)
		{
			ModifiableLoanProduct product = (ModifiableLoanProduct) productBase;
			
			product.getMinDuration().setYear((Integer) data.get("minDurationYears"));
			product.getMinDuration().setMonth((Integer) data.get("minDurationMonths"));
			product.getMaxDuration().setYear((Integer) data.get("maxDurationYears"));
			product.getMaxDuration().setMonth((Integer) data.get("maxDurationMonths"));
			product.getMaxDuration().setInclude((Boolean) data.get("isMaxDurationInclude"));
			product.setMaxDurationInclude((Boolean) data.get("isMaxDurationInclude"));
			product.setNeedInitialInstalment((Boolean) data.get("needInitialInstalment"));
			product.setMinInitialInstalment((BigDecimal) data.get("minInitialInstalment"));
			product.setMaxInitialInstalment((BigDecimal) data.get("maxInitialInstalment"));
			product.setMaxInitialInstalmentInclude((Boolean) data.get("isMaxInitialInstalmentInclude"));
			product.setNeedSurety((Boolean) data.get("needSurety"));
			product.setAdditionalTerms((String) data.get("additionalTerms"));
			if(product.getPublicity() == null)
				product.setPublicity(Publicity.UNPUBLISHED);
		}
		else if(entity instanceof LoanProduct)
		{
			LoanProduct product = (LoanProduct) productBase;
			product.setPackageTemplateId(Long.parseLong((String) data.get("packageTemplate")));
			product.setAnonymousClaim((Boolean) data.get("anonymousClaim"));
			product.setBriefDescription((String) data.get("description"));
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		EditLoanProductOperation op = (EditLoanProductOperation) operation;

		if(frm instanceof EditModifiableLoanProductForm)
		{
			EditModifiableLoanProductForm form = (EditModifiableLoanProductForm) frm;
			Form editConditionForm = EditModifiableLoanProductForm.CONDITION_FORM;

			//условия в разрезе валют
			List<LoanCondition> conditions = new ArrayList<LoanCondition>();
			for(int i = 0; i < form.getConditionId().length; ++i)
			{
				Map<String, Object> fields = new HashMap<String, Object>();

				if(StringHelper.isEmpty(form.getCurrencyNumber()[i]))
					continue;

				fields.put("conditionId",              form.getConditionId()[i]);
				fields.put("currencyNumber",           form.getCurrencyNumber()[i]);
				fields.put("minAmount",                form.getMinAmount()[i]);
				fields.put("maxAmount",                form.getMaxAmount()[i]);
				fields.put("maxAmountPercent",         form.getMaxAmountPercent()[i]);
				fields.put("amountType",               form.getAmountType()[i]);
				fields.put("isMaxAmountInclude",       form.getMaxAmountInclude()[i]);
				fields.put("minInterestRate",          form.getMinInterestRate()[i]);
				fields.put("maxInterestRate",          form.getMaxInterestRate()[i]);
				fields.put("isMaxInterestRateInclude", form.getMaxInterestRateInclude()[i]);

				FieldValuesSource valuesSource = new MapValuesSource(fields);
				//Фиксируем данные, введенные пользователе
				addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", editConditionForm, fields));

				FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editConditionForm);
				if (processor.process())
					conditions.add(op.createCondition(processor.getResult()));
				else
				{
					actionMessages.add(processor.getErrors());
					return actionMessages;
				}
			}
			ModifiableLoanProduct product = (ModifiableLoanProduct) op.getEntity();
			product.setConditions(conditions);
		}

		return actionMessages;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException, BusinessLogicException, TransformerConfigurationException
	{
		EditLoanProductOperation op = (EditLoanProductOperation) editOperation;
		updateFormAdditionalData(form, op);

		if(form instanceof EditModifiableLoanProductForm)
		{
			EditModifiableLoanProductForm frm = (EditModifiableLoanProductForm) form;
			op.setTerbanks(frm.getTerbankIds());
		}
		else if(form instanceof EditLoanProductForm)
		{
			EditLoanProductForm frm = (EditLoanProductForm) form;
			op.setSelectedLoanTypes(frm.getSelectedLoanTypes());
		}
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditLoanProductOperation operation = createOperation(EditLoanProductOperation.class);
		boolean modifiable = frm instanceof EditModifiableLoanProductForm;
		Long id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(id, modifiable);
		else
			operation.initializeNew(modifiable);
		
		return operation;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm)
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (ConstraintViolationException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.cannot-add-or-update-loanproduct"));
		}
		catch (Exception e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveErrors(currentRequest(), msgs);
		return mapping.findForward(FORWARD_START);
	}

	private Templates getTemplate() throws TransformerConfigurationException
	{
		Source templateSource = new StreamSource(ResourceHelper.loadResourceAsStream("com/rssl/phizic/web/loans/products/edit.xslt"));
		TransformerFactory fact = TransformerFactory.newInstance();
		fact.setURIResolver(new StaticResolver());
		return fact.newTemplates(templateSource);
	}
}