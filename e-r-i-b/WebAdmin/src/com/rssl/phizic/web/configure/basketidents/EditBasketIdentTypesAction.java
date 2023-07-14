package com.rssl.phizic.web.configure.basketidents;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.basket.EditBasketIdentifierOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн редактирования идентфикаторов корзины
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketIdentTypesAction extends OperationalActionBase
{
	private StaticMessage messageWith;
	private StaticMessage messageWithout;
	private static final StaticMessagesService service = new StaticMessagesService();
	private static final String BASKET_MESSAGE_WITH_KEY_PREFIX = "basket_message_with_";
	private static final String BASKET_MESSAGE_WITHOUT_KEY_PREFIX = "basket_message_without_";

	private static final String BASKET_MESSAGE_FOR_INN = "Документ успешно сохранен. Мы будем автоматически проверять наличие просроченной задолженности по ПУ «Поиск и оплата налогов ФНС» и показывать счета для оплаты в разделе переводы и платежи.";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.removeFormula", "removeFormula");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketIdentifierOperation op = createOperation(EditBasketIdentifierOperation.class);
		EditBasketIdentTypesForm frm = (EditBasketIdentTypesForm) form;

		if (frm.isRemove())
		{
			op.deleteIdentById(frm.getId());
			return mapping.findForward("Success");
		}
		else if (frm.isAdd())
		{
		}
		else
		{
			BasketIndetifierType tp = op.findIdentById(frm.getId());

			frm.setBasketIndetifierType(tp);
			frm.setName(tp.getName());
			frm.setFormulas(op.getFormulasWithName(frm.getId()));
			frm.setField(EditBasketIdentTypesForm.SYSTEM_ID_FIELD, tp.getSystemId().name());
			frm.setAttributes(new LinkedList<Pair<AttributeForBasketIdentType, String>>());
			frm.setServiceProviders(op.getServiceProviders(frm.getId()));

			for (AttributeForBasketIdentType tps : tp.getAttributes().values())
			{
				frm.getAttributes().add(new Pair<AttributeForBasketIdentType, String>(tps, tps.getSystemId().getNameFor(tp.getSystemId())));
			}
		}

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward removeFormula(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketIdentifierOperation op = createOperation(EditBasketIdentifierOperation.class);
		EditBasketIdentTypesForm frm = (EditBasketIdentTypesForm) form;

		op.removeFormula(frm.getId(), frm.getProviderId());

		return start(mapping, form, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketIdentifierOperation op = createOperation(EditBasketIdentifierOperation.class);
		EditBasketIdentTypesForm frm = (EditBasketIdentTypesForm) form;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), frm.createForm());
		try
		{
			if (frm.getId() == null || frm.getId().equals(0L))
			{
				BasketIndetifierType tp = new BasketIndetifierType();
				tp.setName(frm.getName());
				if(!processor.process())
				{
					frm.setId(null);
					saveErrors(request, processor.getErrors());
					return mapping.findForward(FORWARD_START);
				}
				tp.setSystemId(DocumentType.valueOf(frm.getField(EditBasketIdentTypesForm.SYSTEM_ID_FIELD).toString()));
				for (BasketIndetifierType type :  op.getAllIdentifiers())
					if (type.getSystemId() == tp.getSystemId())
	                    throw new BusinessLogicException("Системный ID \"" + type.getSystemId().name() + "\" уже добавлен в системе");
				op.addIdentifier(tp);
			}
			else
			{
				BasketIndetifierType tp = op.findIdentById(frm.getId());
				tp.setName(frm.getName());
				if(!processor.process())
				{
					frm.setAttributes(new LinkedList<Pair<AttributeForBasketIdentType, String>>());
					for (AttributeForBasketIdentType tps : tp.getAttributes().values())
					{
						frm.getAttributes().add(new Pair<AttributeForBasketIdentType, String>(tps, tps.getSystemId().getNameFor(tp.getSystemId())));
					}
					saveErrors(request, processor.getErrors());
					return mapping.findForward(FORWARD_START);
				}
				tp.setSystemId(DocumentType.valueOf(frm.getField(EditBasketIdentTypesForm.SYSTEM_ID_FIELD).toString()));
				op.addIdentifier(tp);
			}
		}
		catch (BusinessLogicException e)
		{
			saveSessionError(e.getMessage(), null);
		}

		return mapping.findForward("Success");
	}
}
