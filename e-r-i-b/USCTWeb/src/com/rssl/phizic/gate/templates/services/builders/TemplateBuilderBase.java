package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.owners.person.Profile;
import com.rssl.phizic.gate.owners.person.ProfileService;
import com.rssl.phizic.gate.templates.attributable.ExtendedAttributeFactory;
import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.impl.TransferTemplateBase;
import com.rssl.phizic.gate.templates.metadata.MetadataService;
import com.rssl.phizic.gate.templates.services.CorrelationHelper;
import com.rssl.phizic.gate.templates.services.generated.Client;
import com.rssl.phizic.gate.templates.services.generated.ExtendedAttribute;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ������� ����� �������� �������� ����������
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
abstract class TemplateBuilderBase<T extends TransferTemplateBase> implements TemplateBuilder<T>
{
	public TemplateDocument build(GateTemplate generated) throws Exception
	{
		T template = newInstance(FormType.valueOf(generated.getFormType()).getName());
		doBuild(template, generated);
		return template;
	}

	public TemplateDocument build(T template, GateTemplate generated) throws Exception
	{
		doBuild(template, generated);
		return template;
	}

	protected abstract void doBuild(T template, GateTemplate generated) throws Exception;

	void setBaseData(T template, GateTemplate generated) throws Exception
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
		template.setClientOperationChannel(generated.getClientOperationChannel());

		//����� ������������� �����������
		template.setAdditionalOperationChannel(generated.getAdditionalOperationChannel());

		//�������������� ���������� �� �������
		template.setTemplateInfo(CorrelationHelper.toGate(generated.getTemplateInfo()));

		//externalId
		template.setExternalId(generated.getExternalId());

		//������
		template.setState(new State(generated.getState().getCode(), generated.getState().getDescription()));

		//��������
		template.setProfile(getProfile(generated.getClientOwner()));

		//����/����� ��������
		template.setChargeOffResource(getChargeOffResource(generated));

		//���� ����� ����������
		template.setDestinationResource(getDestinationResource(generated));

		//����� � ������ ����� ��������
		template.setChargeOffAmount(CorrelationHelper.toGate(generated.getChargeOffAmount()));

		//����� � ������ ����� ����������
		template.setDestinationAmount(CorrelationHelper.toGate(generated.getDestinationAmount()));

		//��� �����
		String inputSumType = generated.getInputSumType();

		template.setInputSumType(inputSumType);

		template.setOffice(CorrelationHelper.toGate(generated.getOffice()));

		//���������� �������
		template.setGround(generated.getGround());

		//���������� � �����������
		template.setReminderInfo(CorrelationHelper.toGate(generated.getReminderInfo()));
	}

	private T newInstance(String formName) throws Exception
	{
		//noinspection unchecked
		return (T) MetadataService.getInstance().findByFormName(formName).createTemplate();
	}

	private void setExtendedAttributes(T template, Map attributes) throws Exception
	{
		Map<String, com.rssl.phizic.gate.documents.attribute.ExtendedAttribute> result = new HashMap<String, com.rssl.phizic.gate.documents.attribute.ExtendedAttribute>(attributes.size());
		for (Object object : attributes.values())
		{
			ExtendedAttribute attribute = (ExtendedAttribute) object;

			result.put(attribute.getName(), ExtendedAttributeFactory.getInstance().create(attribute));
		}
		template.setExtendedAttributes(result);
	}

	private String getChargeOffResource(com.rssl.phizic.gate.templates.services.generated.GateTemplate generated)
	{
		if (StringHelper.isNotEmpty(generated.getChargeOffAccount()))
		{
			return generated.getChargeOffAccount();
		}
		return generated.getChargeOffCard();
	}

	private String getDestinationResource(com.rssl.phizic.gate.templates.services.generated.GateTemplate generated)
	{
		if (StringHelper.isNotEmpty(generated.getReceiverAccount()))
		{
			return generated.getReceiverAccount();
		}
		return generated.getReceiverCard();
	}

	private Profile getProfile(Client generated) throws Exception
	{
		return ProfileService.getInstance().getOriginalPerson(CorrelationHelper.toGate(generated));
	}
}
