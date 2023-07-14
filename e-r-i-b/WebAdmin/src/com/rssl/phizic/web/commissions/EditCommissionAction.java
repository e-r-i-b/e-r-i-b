package com.rssl.phizic.web.commissions;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.commission.CommissionRule;
import com.rssl.phizic.business.commission.FixedRule;
import com.rssl.phizic.business.commission.GateRule;
import com.rssl.phizic.business.commission.RateRule;
import com.rssl.phizic.operations.commission.EditCommissionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 12.09.2007
 * @ $Author: basharin $
 * @ $Revision: 52085 $
 */

@SuppressWarnings({"JavaDoc"}) public class EditCommissionAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String[] currencies = {"EUR", "RUB", "USD"};
	private static final Object lock = new Object();
	private static Map<String, Form> forms = null;

	private static Map<String, Form> getForms()
	{
		if (forms != null)
		{
			return forms;
		}
		synchronized (lock)
		{
			if (forms == null)
			{
				forms = createForms();
			}
			return forms;
		}
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditCommissionForm frm = (EditCommissionForm) form;
		EditCommissionOperation operation = createOperation("ViewCommissionOperation");
		operation.initialize(frm.getId());

		List<? extends CommissionRule> rules = operation.getRules();
		frm.setRules(rules);
		frm.setType(operation.getType());
		if(!rules.isEmpty() && frm.getField("rulesClass") == null)
		{
			frm.setField("rulesClass", rules.get(0).getClass().getName());

			for (CommissionRule rule : rules)
			{
				if (rule instanceof FixedRule)
				{
					FixedRule fixedRule = (FixedRule) rule;
					frm.setField(fixedRule.getCurrencyCode() + "_amount", fixedRule.getAmount());
				}
				else if (rule instanceof RateRule)
				{
					RateRule rateRule = (RateRule) rule;
					frm.setField(rateRule.getCurrencyCode() + "_rate", rateRule.getRate());
					frm.setField(rateRule.getCurrencyCode() + "_minAmount", rateRule.getMinAmount());
					frm.setField(rateRule.getCurrencyCode() + "_maxAmount", rateRule.getMaxAmount());
				}
			}
		}

		addLogParameters(new CollectionLogParametersReader("Обрабатываемая сущность", rules));

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditCommissionForm frm = (EditCommissionForm) form;

		String ruleClass = (String) frm.getField("rulesClass");

		Form metaForm = getForms().get(ruleClass);
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), metaForm);

		if(processor.process())
		{
			frm.setResult(processor.getResult());
			dispatchMethod(mapping, form, request, response, "save" + metaForm.getName());
			return sendRedirectToSelf(request);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}
	}

	public ActionForward saveGateRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditCommissionForm frm = (EditCommissionForm) form;
		EditCommissionOperation operation = createOperation("EditCommissionOperation");
		operation.initialize(frm.getId());

		for (String currency : currencies)
		{
			operation.addGateRule(currency);
		}

		operation.updateRules();

		addLogParameters(new CollectionLogParametersReader("Обрабатываемая сущность", operation.getRules()));

		return null;
	}

	public ActionForward saveFixedRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditCommissionForm frm = (EditCommissionForm) form;
		EditCommissionOperation operation = createOperation("EditCommissionOperation");
		operation.initialize(frm.getId());
		Map<String, Object> result = frm.getResult();

		for (String currency : currencies)
		{
			operation.addFixedRule(currency, (BigDecimal) result.get(currency + "_amount"));
		}

		operation.updateRules();

		addLogParameters(new CollectionLogParametersReader("Обрабатываемая сущность", operation.getRules()));

		return null;
	}

	public ActionForward saveRateRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditCommissionForm frm = (EditCommissionForm) form;
		EditCommissionOperation operation = createOperation("EditCommissionOperation");
		operation.initialize(frm.getId());
		Map<String, Object> result = frm.getResult();

		for (String currency : currencies)
		{
			BigDecimal rate = (BigDecimal) result.get(currency + "_rate");
			BigDecimal min = (BigDecimal) result.get(currency + "_minAmount");
			BigDecimal max = (BigDecimal) result.get(currency + "_maxAmount");
			operation.addRateRule(currency, rate, min, max);
		}

		operation.updateRules();

		addLogParameters(new CollectionLogParametersReader("Обрабатываемая сущность", operation.getRules()));

		return null;
	}

	private static Map<String, Form> createForms()
	{
		Map<String, Form> res = new HashMap<String, Form>();

		res.put(GateRule.class.getName(), buildGateForm());
		res.put(FixedRule.class.getName(), buildFixedForm());
		res.put(RateRule.class.getName(), buildRateForm());

		return res;
	}

	private static Form buildGateForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName("GateRule");
		return formBuilder.build();
	}

	private static Form buildFixedForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName("FixedRule");

		for (String currency : currencies)
		{
			addFixedFields(currency, formBuilder);
		}

		return formBuilder.build();
	}

	private static Form buildRateForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName("RateRule");

		for (String currency : currencies)
		{
			addRateFields(currency, formBuilder);
		}

		return formBuilder.build();	}

	private static void addFixedFields(String curr, FormBuilder formBuilder)
	{
		FieldBuilder fb;
		fb = new FieldBuilder();
		fb.setName(curr + "_amount");
		fb.setDescription("Сумма (" + curr + ")");
		fb.setType("money");
		fb.setValidators(
				new RequiredFieldValidator(),
				new MoneyFieldValidator()

		);
		formBuilder.addField(fb.build());
	}

	private static void addRateFields(String curr, FormBuilder formBuilder)
	{
		FieldBuilder fb;
		fb = new FieldBuilder();
		fb.setName(curr + "_rate");
		fb.setDescription("Процент (" + curr + ")");
		fb.setType("string");
		fb.setParser(new BigDecimalParser());
		fb.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d+((\\.|,)\\d{0,4})?$")

		);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(curr + "_minAmount");
		fb.setDescription("Минимальная сумма (" + curr + ")");
		fb.setType("money");
		fb.setValidators(
				new RequiredFieldValidator(),
				new MoneyFieldValidator()

		);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(curr + "_maxAmount");
		fb.setDescription("Максимальная сумма (" + curr + ")");
		fb.setType("money");
		fb.setValidators(
				new RequiredFieldValidator(),
				new MoneyFieldValidator()

		);
		formBuilder.addField(fb.build());
	}


}