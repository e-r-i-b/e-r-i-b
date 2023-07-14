package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedAttributeFactory;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedAttributeFactoryImpl;
import com.rssl.phizic.business.documents.templates.impl.TransferTemplateBase;
import com.rssl.phizic.business.documents.templates.impl.activity.DefaultTransferTemplateInformer;
import com.rssl.phizic.business.documents.templates.impl.activity.FailTransferTemplateInformer;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.wsgate.services.template.CorrelationHelper;
import com.rssl.phizicgate.wsgate.services.template.generated.ExtendedAttribute;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ������ �������� ���������� (����� ����)
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
abstract class TemplateBuilderBase<T extends TransferTemplateBase> implements TemplateBuilder
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final ExtendedAttributeFactory attributeFactory = new ExtendedAttributeFactoryImpl();

	public TemplateDocument build(GateTemplate generated) throws GateException, GateLogicException
	{
		T template = newInstance(FormType.valueOf(generated.getFormType()).getName());
		doBuild(template, generated);
		return template;
	}

	protected abstract void doBuild(T template, GateTemplate generated) throws GateException, GateLogicException;

	protected void setBaseData(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		//����������� �������������� ��������� ������� �������
		Map attributes = generated.getExtendedAttributes();
		if (MapUtils.isNotEmpty(attributes))
		{
			setExtendedAttributes(template, attributes);
		}

		//id
		template.setId(generated.getId());

		//operationUID
		template.setOperationUID(generated.getOperationUID());

		//����� ���������
		template.setDocumentNumber(generated.getDocumentNumber());

		//���� �������� ��������
		template.setClientCreationDate(generated.getClientCreationDate());

		//���� ������������� ��������
		template.setClientOperationDate(generated.getClientOperationDate());

		//���� ��������������� ������������� (�����������)
		template.setAdditionalOperationDate(generated.getAdditionalOperationDate());

		//���������� �� ����������
		template.setCreatedEmployeeInfo(CorrelationHelper.toGate(generated.getCreatedEmployeeInfo()));

		//���������� �� ����������
		template.setConfirmedEmployeeInfo(CorrelationHelper.toGate(generated.getConfirmedEmployeeInfo()));

		//����� �������� ��������
		template.setClientCreationChannel(generated.getClientCreationChannel());

		//����� ������������� ��������
		String clientOperationChannel = generated.getClientOperationChannel();
		if (StringHelper.isNotEmpty(clientOperationChannel))
		{
			template.setClientOperationChannel(clientOperationChannel);
		}

		//����� ������������� �����������
		String additionalOperationChannel = generated.getAdditionalOperationChannel();
		if (StringHelper.isNotEmpty(additionalOperationChannel))
		{
			template.setAdditionalOperationChannel(additionalOperationChannel);
		}

		//�������������� ���������� �� �������
		template.setTemplateInfo(CorrelationHelper.toGate(generated.getTemplateInfo()));

		//externalId
		template.setExternalId(generated.getExternalId());

		//������
		template.setState(new State(generated.getState().getCode(), generated.getState().getDescription()));

		//��������
		template.setClientOwner(CorrelationHelper.toGate(generated.getClientOwner()));

		//���������� � �����������
		template.setReminderInfo(CorrelationHelper.toGate(generated.getReminderInfo()));
	}

	protected void setActivityInfo(T template)
	{
		template.setActivityInfo(getActivityInfo(template));
	}

	protected ActivityInfo getActivityInfo(T template)
	{
		try
		{
			return new DefaultTransferTemplateInformer(template).inform();
		}
		catch (Exception e)
		{
			log.error("��� ������� ���������� ������� id = " + template.getId() + " ��������� ������.", e);
			return new FailTransferTemplateInformer().inform();
		}
	}

	protected T newInstance(String formName)
	{
		//noinspection unchecked
		return (T) MetadataCache.getBasicMetadata(formName).createTemplate();
	}

	private void setExtendedAttributes(T template, Map attributes)
	{
		Map<String, com.rssl.phizic.gate.documents.attribute.ExtendedAttribute> result = new HashMap<String, com.rssl.phizic.gate.documents.attribute.ExtendedAttribute>(attributes.size());
		for (Object object : attributes.values())
		{
			ExtendedAttribute attribute = (ExtendedAttribute) object;
			result.put(attribute.getName(), attributeFactory.create(attribute.getId(), attribute.getName(), Type.valueOf(attribute.getType()), attribute.getValue()));
		}
		template.setExtendedAttributes(result);
	}
}
