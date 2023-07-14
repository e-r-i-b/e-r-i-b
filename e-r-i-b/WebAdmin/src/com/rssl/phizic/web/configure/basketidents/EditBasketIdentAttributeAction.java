package com.rssl.phizic.web.configure.basketidents;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.basketident.AttributeDataType;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.AttributeSystemId;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.operations.basket.EditBasketIdentifierOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.SessionStore;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн редакатировани€ атрибутов идентификаторов корзины.
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketIdentAttributeAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketIdentifierOperation op = createOperation(EditBasketIdentifierOperation.class);
		EditBasketIdentAttributeForm frm = (EditBasketIdentAttributeForm) form;

		if (frm.isRemove())
		{
			op.deleteAttributeById(frm.getId());
			return mapping.findForward("Success");
		}
		else if (frm.isAdd())
		{
			BasketIndetifierType tp = op.findIdentById(frm.getIdentId());
			frm.setInn(tp.getSystemId() == DocumentType.INN);
			frm.setRc(tp.getSystemId() == DocumentType.RC);
		}
		else
		{
			AttributeForBasketIdentType attr = op.findAttrById(frm.getId());
			BasketIndetifierType tp = op.findIdentById(frm.getIdentId());
			frm.setName(attr.getName());
			frm.setMandatory(attr.isMandatory());
			frm.setRegexp(attr.getRegexp());
			frm.setSystemId(attr.getSystemId().getNameFor(tp.getSystemId()));
			frm.setType(attr.getDataType().name());
			frm.setInn(tp.getSystemId() == DocumentType.INN);
			frm.setRc(tp.getSystemId() == DocumentType.RC);
		}

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketIdentifierOperation op = createOperation(EditBasketIdentifierOperation.class);
		EditBasketIdentAttributeForm frm = (EditBasketIdentAttributeForm) form;

		try
		{
			//если измен€емый атрибут - номер, то делаем его об€зательным (по требованию BUG091226)
			if(AttributeSystemId.getByName(frm.getSystemId()).equals(AttributeSystemId.NUMBER))
				frm.setMandatory(true);

			if (frm.getId() == null || frm.getId().equals(0L))
			{
				BasketIndetifierType tp = op.findIdentById(frm.getIdentId());
				AttributeForBasketIdentType attr = new AttributeForBasketIdentType();
				attr.setName(frm.getName());
				attr.setRegexp(frm.getRegexp());
				attr.setMandatory(frm.isMandatory());
				attr.setDataType(AttributeDataType.valueOf(frm.getType()));
				attr.setSystemId(AttributeSystemId.getByName(frm.getSystemId()));
				attr.setIdentId(tp.getId());
				if (tp.getAttributes().containsKey(attr.getSystemId().name()))
					throw new BusinessLogicException("јтрибут \"" + attr.getName() + "\" с типом \"" + attr.getSystemId().getNameFor(tp.getSystemId()) + "\" уже добавлен к документу \"" + tp.getName() + "\"");
				tp.getAttributes().put(attr.getSystemId().name(), attr);
				op.addIdentifier(tp);
			}
			else
			{
				BasketIndetifierType tp = op.findIdentById(frm.getIdentId());
				AttributeForBasketIdentType attr = op.findAttrById(frm.getId());
				attr.setName(frm.getName());
				attr.setRegexp(frm.getRegexp());
				attr.setMandatory(frm.isMandatory());
				attr.setDataType(AttributeDataType.valueOf(frm.getType()));
				if (tp.getAttributes().containsKey(attr.getSystemId().name()))
					throw new BusinessLogicException("јтрибут с типом \"" + frm.getSystemId() + "\" уже добавлен к документу \"" + tp.getName() + "\"");
				if (attr.getSystemId() != AttributeSystemId.getByName(frm.getSystemId()))
					tp.getAttributes().remove(attr);
				attr.setSystemId(AttributeSystemId.getByName(frm.getSystemId()));
				attr.setIdentId(tp.getId());
				tp.getAttributes().put(attr.getSystemId().name(), attr);
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
