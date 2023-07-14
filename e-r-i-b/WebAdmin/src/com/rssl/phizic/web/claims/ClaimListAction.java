package com.rssl.phizic.web.claims;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.parsers.IntegerParser;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.claims.GetClaimListOperation;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kidyaev
 * @ created 13.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class ClaimListAction extends OperationalActionBase
{
	protected static final String FORWARD_SHOW = "Show";
	private static final String FORWARD_REFUSE = "Refuse";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map=new HashMap<String, String>();
	    map.put("button.filter", "start");
		map.put("button.refuse", "refuse");
		map.put("button.accept", "accept");
	    return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClaimListForm                    frm             = (ClaimListForm) form;
		Map<String, Object>              filters         = frm.getFilters();
		MapValuesSource mapValuesSource = new MapValuesSource(filters);
		Form                             filterForm      = createForm();
		FormProcessor<ActionMessages, ?> formProcessor   = createFormProcessor(mapValuesSource, filterForm);

		if ( formProcessor.process() )
		{
			GetClaimListOperation operation  = createOperation(GetClaimListOperation.class);
			Query                 query      = operation.createQuery("list");
			Map<String, Object>   parameters = createParameters(formProcessor.getResult());

			query.setParameters(parameters);
			List<BusinessDocument> claims = query.setMaxResults(webPageConfig().getListLimit()+1).executeList();
			frm.setListLimit(webPageConfig().getListLimit());
			frm.setClaims(claims);

			Map<BusinessDocument, Office> result = new HashMap<BusinessDocument, Office>();
			for (BusinessDocument document : claims)
			{
				Office office = operation.getClaimOffice(document);
				result.put(document,office);
			}
			frm.setOfficesMap(result);
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
		}

		return mapping.findForward(FORWARD_SHOW);

	}

	@SuppressWarnings({"ReuseOfLocalVariable"})
	protected Form createForm()
	{
		List<Field>  fieldList   = new ArrayList<Field>();

		fieldList.add(createField("type", "Тип заявки", null, null));
		fieldList.add(createField("state", "Статус", null, null));
		fieldList.add(createField("receiverName", "Наименование получателя", null, null));

		FieldValidator[] fieldValidators = new FieldValidator[] {new DateFieldValidator()};
		Field            field = createField("fromDate", "Дата создания (начальная)", new DateParser(), fieldValidators);
		fieldList.add(field);

		fieldValidators = new FieldValidator[] {new DateFieldValidator()};
		field           = createField("toDate", "Дата создания (конечная)", new DateParser(), fieldValidators);
		fieldList.add(field);

		fieldValidators = new FieldValidator[] {new RegexpFieldValidator("\\d{1,10}")};
		fieldList.add(createField("number", "Номер", new IntegerParser(), fieldValidators));

		fieldValidators = new FieldValidator[] {new DateFieldValidator()};
		field = createField("fromAdmissionDate", "Дата принятия к исполнению (начальная)", new DateParser(), fieldValidators);
		fieldList.add(field);

		fieldValidators = new FieldValidator[] {new DateFieldValidator()};
		field           = createField("toAdmissionDate", "Дата принятия к исполнению  (конечная)", new DateParser(), fieldValidators);
		fieldList.add(field);

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fieldList);

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder.build();
	}

	protected Field createField(String name, String description, FieldValueParser parser, FieldValidator[] validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);

		if ( parser != null )
			fieldBuilder.setParser(parser);

		if ( validators != null )
			fieldBuilder.setValidators(validators);

		return fieldBuilder.build();
	}

	protected Map<String, Object> createParameters(Map<String, Object> filters)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("number",   filters.get("number"));
		parameters.put("type",     filters.get("type"));
		parameters.put("fromDate", filters.get("fromDate"));
		parameters.put("toDate",   filters.get("toDate"));
		parameters.put("fromAdmissionDate", filters.get("fromAdmissionDate"));
		parameters.put("toAdmissionDate",   filters.get("toAdmissionDate"));
		parameters.put("receiverName",   filters.get("receiverName"));
		parameters.put("state",   filters.get("state"));
		parameters.put("state_list", null);
		return parameters;
	}
	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClaimListForm frm = (ClaimListForm) form;
		String [] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noSelected", request);
			return start(mapping, form, request, response);
        }
		else
		{
			for (String id : selectedIds)
			{
				Long currentId = Long.parseLong(id);
				ProcessDocumentOperation operation = initOperation(currentId);
				if (!operation.checkState())
				{
					frm.clearSelection();
					saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noRefuse", request);
					return start(mapping, form, request, response);
				}
			}
			for (String id : selectedIds)
			{
				Long currentId = Long.parseLong(id);
				ProcessDocumentOperation operation = initOperation(currentId);
//				if (!operation.getMetadata().getStateMachine().getClass().equals(NullSender.class))
//				{
//					frm.clearSelection();
//					saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noSend", request);
//					return start(mapping, form, request, response);
//				}
			}
		}
		return new ActionForward(mapping.findForward(FORWARD_REFUSE).getPath()+"?id="+frm.getSelectedIds()[0],true);
	}

	public ActionForward accept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClaimListForm frm = (ClaimListForm) form;
		String [] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noSelected", request);
			return start(mapping, form, request, response);
        }
		else
		{
			for (String id : selectedIds)
			{
				Long currentId = Long.parseLong(id);
				ProcessDocumentOperation operation = initOperation(currentId);
				if (!operation.checkState())
				{
					frm.clearSelection();
					saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noAccept", request);
					return start(mapping, form, request, response);
				}
			}
			for (String id : selectedIds)
			{
				Long currentId = Long.parseLong(id);
				ProcessDocumentOperation operation = initOperation(currentId);
				//if (!operation.getMetadata().getSender().getClass().equals(NullSender.class))
				//	saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noSend", request);
				//else
				//	operation.accept(frm.getS);
			}
		}
		frm.clearSelection();
		return start(mapping, form, request, response);
	}

	protected ProcessDocumentOperation initOperation(Long id) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(id, new NullDocumentValidator());
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(source);

		return operation;
	}
}
