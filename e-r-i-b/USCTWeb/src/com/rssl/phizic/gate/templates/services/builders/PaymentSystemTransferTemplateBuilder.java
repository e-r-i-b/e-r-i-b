package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.gate.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.gate.templates.services.CorrelationHelper;
import com.rssl.phizic.gate.templates.services.generated.Field;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ������ ������� ��������� � ������������ ����������� �����
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemTransferTemplateBuilder extends TemplateBuilderBase<PaymentSystemTransferTemplate>
{
	@Override
	protected void doBuild(PaymentSystemTransferTemplate template, GateTemplate generated) throws Exception
	{
		//���������� ����� ������
		setBaseData(template, generated);

		//���������� �������������� ���� ������� �������
		List fields = generated.getExtendedFields();
		if (CollectionUtils.isNotEmpty(fields))
		{
			setExtendedFields(template, fields);
		}

		//���������� ���������� �����
		String guid = generated.getMultiBlockReceiverPointCode();
		if (StringHelper.isNotEmpty(guid))
		{
			template.setMultiBlockReceiverPointCode(guid);

		}

		//������� ������������� ���������� �����
		template.setReceiverPointCode(generated.getReceiverPointCode());

		//������������ ���������� �������
		template.setReceiverName(generated.getReceiverName());

		//������� ���������� ������������ ������� �������
		template.setIdFromPaymentSystem(generated.getIdFromPaymentSystem());
	}

	private void setExtendedFields(PaymentSystemTransferTemplate template, List generated) throws Exception
	{
		List<com.rssl.phizic.gate.payments.systems.recipients.Field> result = new ArrayList<com.rssl.phizic.gate.payments.systems.recipients.Field>(generated.size());
		for (Object object : generated)
		{
			Field field = (Field) object;
			result.add(CorrelationHelper.toGate(field));
		}
		template.setExtendedFields(result);
	}
}