package com.rssl.phizic.business.payments.forms.meta.externalPayments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ext.sbrf.payments.PaymentsFormatHelper;
import com.rssl.phizic.business.fields.FieldImpl;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.ListUtil;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хендлер для подготовки внешнего платежа к оплате
 * Добавляет доп.поля в платёж из связанного заказа/платёжного поручения
 */
public class PrepareUECPaymentHandler extends BusinessDocumentHandlerBase
{
	///////////////////////////////////////////////////////////////////////////

	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		if (!(stateObject instanceof BusinessDocument))
			throw new DocumentException("BusinessDocument");
		BusinessDocument document = (BusinessDocument) stateObject;
		try
		{
			Order order = DocumentHelper.getUECOrder(document);
			if (order == null)
				return;

			if (!(stateObject instanceof AbstractPaymentSystemPayment))
				throw new DocumentException("Ожидается AbstractPaymentSystemPayment");
			AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) stateObject;

			List<Field> orderFields = buildOrderFields(order);
			if (orderFields != null)
				payment.setExtendedFields(ListUtil.combine(payment.getExtendedFields(), orderFields));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private List<Field> buildOrderFields(Order order)
	{
		String orderFieldsJson = order.getAdditionalFields();
		Map<String, String> orderFields = PaymentsFormatHelper.deserializeAdditionalFields(orderFieldsJson);
		if (MapUtils.isEmpty(orderFields))
			return null;

		List<Field> fields = new ArrayList<Field>(orderFields.size());

		int i = 1;
		for (Map.Entry<String, String> entry : orderFields.entrySet())
		{
			FieldImpl field = new FieldImpl();
			String fieldUECName = formatUECAttributeName(i);
			field.setExternalId(fieldUECName);
			field.setName(fieldUECName);
			field.setType(FieldDataType.string);
			field.setValue(formatUECAttributeValue(entry.getKey(), entry.getValue()));
			field.setVisible(false);
			fields.add(field);
			i++;
		}

		return fields;
	}

	private String formatUECAttributeValue(String fieldName, String fieldValue)
	{
		return String.format("%s=%s", fieldName, fieldValue);
	}

	private String formatUECAttributeName(int fieldIndex)
	{
		return "UECAttr" + fieldIndex;
	}
}
