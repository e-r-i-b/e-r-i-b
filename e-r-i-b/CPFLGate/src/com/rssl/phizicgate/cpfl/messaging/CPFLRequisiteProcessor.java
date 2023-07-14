package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 22.02.2011
 * @ $Author$
 * @ $Revision$
 * ���������� ��������� ����.
 * �������� ����� �������� �� ���������� �����.
 * �� ��� ����(� ����� ������ �������� ����������) - ��� ��� ����� ����� 1 ����, ��� ����� ��������
 * � ������ ���������� ��������� ������� ������ ����� ������� ��� �����������:
 * ������ ���������:
 * <requisites>
 *   <name>N ��������:</name>
 *   <field>
 *     <template>P</template>
 *     <length>3</length>
 *     <postfix>-</postfix>
 *     <enteredData>499</enteredData>
 *   </field>
 *   <field>
 *     <template>9</template>
 *     <length>3</length>
 *     <postfix>-</postfix>
 *     <enteredData>722</enteredData>
 *   </field>
 *   <field>
 *     <template>9</template>
 *     <length>2</length>
 *     <postfix>-</postfix>
 *     <enteredData>33</enteredData>
 *   </field>
 *   <field>
 *     <template>9</template>
 *     <length>2</length>
 *     <enteredData>30</enteredData>
 *   </field>
 * </requisites>
 * ��� ������ ��������� ������ �������������� 1 ���� ��� ����� �������� � ������� XXX-XXX-XX-XX
 *
 * ������ ����� ������������� ������ ������������ ����������, ��������� ��� ����, � ����� ������ ��������� ��������
 * ����(��� ���� ���������) �� ��������� � ��������� ������ ������. 
 */
class CPFLRequisiteProcessor
{
	private GateFactory factory;
	private int number;
	private int vvodNumber;
	private List<CPFLRequisiteField> fields;
	private String name;

	/**
	 * ����������� �����������
	 * @param factory ������� ��� ������� � �������� ��������.
	 * @param requisites ���� requisites
	 * @param number ����� ���������� ������ specialClient
	 * @param vvodNumber �������� ��������� vvodNumber ������ specialClient
	 * @throws GateException
	 */
	CPFLRequisiteProcessor(GateFactory factory, Element requisites, int number, int vvodNumber) throws GateException
	{
		this.factory = factory;
		this.number = number;
		this.vvodNumber = vvodNumber;
		//������ ���� � ��������� �������������
		this.fields = parseFields(requisites);
		this.name = XmlHelper.getSimpleElementValue(requisites, "name");
		if (name.endsWith(":"))
		{
			name = name.substring(0, name.length()-1);//�������� �������� ":", ���� ����.
		}
	}

	private List<CPFLRequisiteField> parseFields(Element requisites) throws GateException
	{
		final List<CPFLRequisiteField> result = new ArrayList<CPFLRequisiteField>();
		try
		{
			XmlHelper.foreach(requisites, "./field", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					result.add(new CPFLRequisiteField(element));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return result;
	}

	/**
	 * ������������� ������� ������������ ��� ���������
	 * @return ������� ������������ ���������.
	 */
	String getExternalId()
	{
		return "extended-field-" + number + "_" + vvodNumber;
	}

	/**
	 * �������� ��� ���� ����.
	 * ���������, ��������� �� 1 ����, ����� ��� �������������� �������� template
	 * ���������, ��������� �� �� ���������� �����, ����� ������.
	 * @return ��� ���� ����.
	 */
	FieldDataType getType()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).getType();
		}
		return FieldDataType.string;
	}

	/**
	 * ������������ ��������� ��� ����������.
	 * �����������:
	 * 1) ���������� ���� - ����, ���������� ����������� ��������
	 * ���������� �������� ������� �������� ������ ������.
	 * 2) ������ - ��� ������������������ ���������� �����. ��� ����������� ������ ������ ������������ ���� ������ X, Y, Z, A, B, �...
	 * �������:
	 * 1) ��� ��������� ����� �� ���������� ��������� ��� ����������� .
	 * 2) ���� �������� �� �������� ����� � ����������/���������� � ��� ���� ����������
	 * �� ���������� ��������� ����:
	 * ������� <��� ���������>, <��������� �� ���������� � ��������� ���������� ��������>
	 * ������: ������� ��� ����������, 12 ����.
	 * 3) ���� �������� ������� �� ����� ���������� ��������/��������, ��� ���� ���������� � ����� ����� ��������� �� ����� 20,
	 * �� ���������� ��������� ����:
	 * ������� <��� ���������> � ������� <������� ���� 1>XXX<������� ���� 1><������� ���� 2>XXX<������� ���� 3>, <��������� �� ���������� � ��������� ���������� ��������>
	 * ��� XXX - ������ �� ���� X ������ ������ ������������ ����� ����
	 * ������: ������� ����� �������� � ������� XXX-XXX-XX-XX, �� ����� 10 ����
	 * 4)� ��������� ������� ���������� ������ ����:
	 * ������� <��� ���������> � ������� <������� ���� 1><������_1><������� ���� 1><������� ���� 2><������_1><������� ���� 3>, ��� <��������� �� ���������� � ��������� ���������� ��������>
	 * ������:
	 * ������� ����� �������� � ������� XXX-YYY-ZZ-AA, ��� XXX � �� ����� 3 ����, YYY- �� ����� 3 ����, ZZ � 2 �����, �� �  2 �����
	 * ����� ������ � �������(���������� ��������) ������ ���� ��������� �� ���� ����� ������ � 5.
	 *
	 * ��������� �� ��������� ���������� �������� � ����� ������ ����� ���:
	 * [<������ ������_1> -] [�� <��� �����> ��] [�� �����] <���� �����> <�������� ��������� ���������� �������� ����� 1 �� ������� �������>,
	 * [<������ ������_2> -] [�� <��� �����> ��] [�� �����] <���� �����> <�������� ��������� ���������� �������� ����� 2 �� ������� �������> ....
	 * ���� �������� ���������� (�����������, ��� ������ ����� 1) ������ ����� �� ��������� � ��������� ���������� �� ��������� �����������.
	 * @return �������� ��������� �� ���������� ����
	 */
	String getHint()
	{
		if (FieldDataType.list == getType())
		{
			return null; //��� ���������� ������� ��� ��������� �� ����������. 
		}
		return generateHint();
	}

	private String generateHint()
	{
		//1) ��������� ���� ���������� �� ���������������� ������ ���������� �����
		List<CPFLRequisitesFieldGroup> requisiteGroups = new ArrayList<CPFLRequisitesFieldGroup>();
		//�������� ������ ���� � ������ ������
		CPFLRequisitesFieldGroup currentGroup = new CPFLRequisitesFieldGroup(fields.get(0));
		requisiteGroups.add(currentGroup);
		//����� ��������� �� ������� ���� �� ������ ������ ��������� ������ � ������ ������
		for (int i = 1; i < fields.size(); i++)
		{
			CPFLRequisiteField curentField = fields.get(i);
			if (currentGroup.isSimilar(curentField))
			{
				//���� ��������� ������� ������
				//��������� ��� � ������� ������
				currentGroup.add(curentField);
			}
			else
			{
				//���� ����������� � ������� �������
				//������� ����� ������
				currentGroup = new CPFLRequisitesFieldGroup(curentField);
				//��������� � ������ �����.
				requisiteGroups.add(currentGroup);
			}
		}

		//2) ������ �������� ������ � ����������� ��������
		int groupsCount = requisiteGroups.size();
		CPFLRequisitesFieldGroup curentGroup = requisiteGroups.get(0);
		StringBuilder hint = new StringBuilder("������� ").append(name).append(" ");
		if (groupsCount == 1)
		{
			if (curentGroup.hasDelimiters())
			{
				//���� ������� ����������� � ������, �� ������� ������������� ������.
				hint.append(" � ������� ");
				hint.append(curentGroup.getTemplate('X'));
			}
			hint.append(", ");
			hint.append(curentGroup.getDescription());
			hint.append(".");
			return hint.toString();
		}
		//����� �������� ��� 4 ������ ��������� �������� ������ � ��������� �� �����c��� ���������� ��������
		StringBuilder format = new StringBuilder();
		StringBuilder description = new StringBuilder();
		StringBuilder example = new StringBuilder();
		for (int i = 0; i < groupsCount; i++)
		{
			curentGroup = requisiteGroups.get(i);
			char templateChar = i < 3 ? (char) ('X' + i) : (char) ('A' + i - 3);
			format.append(curentGroup.getTemplate(templateChar));
			example.append(curentGroup.getExample());

			description.append(templateChar).append(" - ");
			description.append(curentGroup.getDescription());
			if (i < groupsCount - 1)
			{
				//��� ����������� ������ ������� ","
				description.append(", ");
			}
		}
		hint.append(" � ������� ");
		hint.append(format).append(", ��� ").append(description).append(". ");
		hint.append("��������: ").append(example).append(".");
		return hint.toString();
	}

	/**
	 * ����������� �� ���������� ����
	 * �������������� ���������� ������������ ��������� �������� template �
	 * �������� ���������/��������� ��� ��������� ����������.
	 * @return �����������.�������������
	 */
	boolean isRequired()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).isRequired();
		}
		for (CPFLRequisiteField field : fields)
		{
			//���� ���� �� 1 ���� ��������� ���� ����������� ��� �������� �������� ��� ��������� - ���� ������ ��� ���������.
			if (field.isRequired() || !StringHelper.isEmpty(field.getPrefix()) || !StringHelper.isEmpty(field.getPostfix()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * �������� ������������ ����� ����.
	 * ������������ ����� ��� �������� ���������  ������������� ���� ����� ����
	 * ��� ��������� ���������� ������������ ����� ����� ����� ���� ���� ����� � ����� ����� �� ���������/����������(�� ������ ���� ������)
	 * @return ������������ ����� ����
	 */
	Long getMaxLength()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).getMaxLength();
		}
		long maxLength = 0;
		for (CPFLRequisiteField field : fields)
		{
			maxLength += field.getMaxLength();
			if (field.getPrefix() != null)
			{
				maxLength += field.getPrefix().length();
			}
			if (field.getPostfix() != null)
			{
				maxLength += field.getPostfix().length();
			}
		}
		return maxLength;
	}

	/**
	 * �������� ����������� ����� ����.
	 * ����������� ����� ��� �������� ��������� ������������ ��� ����� ����
	 * ��� ��������� ���������� ������������ ����� ����� ����� ��� ���� ����� � ����� ����� �� ���������/����������(�� ������ ���� ������)
	 * @return ����������� ����� ����
	 */
	Long getMinLength()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).getMinLength();
		}
		long maxLength = 0;
		for (CPFLRequisiteField field : fields)
		{
			maxLength += field.getMinLength();
			if (field.getPrefix() != null)
			{
				maxLength += field.getPrefix().length();
			}
			if (field.getPostfix() != null)
			{
				maxLength += field.getPostfix().length();
			}
		}
		return maxLength;
	}

	private static final String VALIDATOR_TYPE = "regexp";

	/**
	 * @return ������ �����������.
	 */
	List<FieldValidationRule> getValidationRules()
	{
		if (FieldDataType.list == getType())
		{
			return null; //��� ���������� ������� ��� �����������.
		}
		List<FieldValidationRule> result = new ArrayList<FieldValidationRule>();
		FieldValidationRuleImpl validator = new FieldValidationRuleImpl();
		String regexp = buildRegex();
		validator.setErrorMessage(getHint());
		validator.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(VALIDATOR_TYPE, regexp);
		validator.setParameters(map);
		result.add(validator);
		return result;
	}

	/**
	 * �������� ������ ��� ��������� ����.
	 * �������� ���� ����� ���� ������ � ������� ����������.
	 * ���� template = T � �������� ������ typePaymentList. ��������� ��� ������������ ���� ����������� ������ �� ���������� �� ������ typePaymentList
	 * ���� template = U � ��������� ��� ������������ ��������� ���� � ���������� �������� �� ��������� ����.
	 * @return ������ ��������� ��� null, ���� ��� ���� �� ������������� ��������
	 */
	List<ListValue> getMenu() throws GateLogicException, GateException
	{
		if (fields.size() != 1)
		{
			return null; //��� ��������� ��������� - ���������
		}
		CPFLRequisiteField simpleField = fields.get(0);
		if ("T".equals(simpleField.getTemplate()))
		{
			return getTypePaymentListMenu();
		}
		if ("U".equals(simpleField.getTemplate()))
		{
			List<ListValue> result = new ArrayList<ListValue>();
			for (String menuItem : simpleField.getMenu())
			{
				result.add(new ListValue(menuItem, menuItem));
			}
			return result;
		}
		return null;
	}

	/**
	 * �������� �� ������� �������� ���� ��� ���������
	 * @param payment ������ � �������
	 * @param number ����� ���� � ���������(����������� ������ ������ � p�� ���������)
	 * @return ��������
	 */
	String getRequisiteFieldValue(AbstractPaymentSystemPayment payment, int number) throws GateException, GateLogicException
	{
		//�������� ����
		Field field = CPFLBillingPaymentHelper.getFieldById(payment, getExternalId());
		if (field == null)
		{
			return null;
		}
		String value = (String) field.getValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		if (fields.size() == 1)
		{
			return value; //������� �������� ����������� ���������.
		}
		//�������� ������ ��� ���������
		String regexp = buildRegex();
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches())
		{
			throw new GateLogicException("���������� �������� ��������� " + name);
		}
		if (matcher.groupCount() < number + 1)
		{
			throw new GateException("��� ��������� " + getExternalId() + " ��������� �������������� ������ " + (number + 1) +
					". regexp:" + regexp + ". ��������: " + value);
		}
		return matcher.group(number + 1);//������ � ������� ���������� � 1.
	}

	/**
	 * ������������ ���������� ��������� �������������� �������� ����.
	 * ������ ��������� ������������ ��� ��������� � ���������� �������� ��� ���� ����� ���������.
	 * ��� �������� ��������� ����������� ��������� ������������ ����������� ��������� ����.
	 * ��� ���������� ��������� ������������ ��������� ����:
	 * <�������_����_1>(<regexp_����_1>)<�������_����_1 ><regexp_����_2>(<����_2>)<�������_����_2>...
	 * ������� ������ � ��� ����������� ������ ���������, ��� ��������� �������� ���� ���������� ���������.
	 * @return ���������� ��������� �������������� �������� ����.
	 */
	private String buildRegex()
	{
		if (fields.size() == 1)
		{
			return "(" + fields.get(0).getRegexp() + ")";
		}
		StringBuilder builder = new StringBuilder();
		for (CPFLRequisiteField field : fields)
		{
			if (field.getPrefix() != null)
				builder.append(Pattern.quote(field.getPrefix()));
			builder.append("(");
			builder.append(field.getRegexp());
			builder.append(")");
			if (field.getPostfix() != null)
				builder.append(Pattern.quote(field.getPostfix()));
		}
		return builder.toString();
	}

	/**
	 * @return ��������� ���������� ������� typePaymentList, �������������� � ���� List<ListValue>
	 */
	private List<ListValue> getTypePaymentListMenu() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = factory.service(WebBankServiceFacade.class);
		GateMessage gateMessage = service.createRequest("typePaymentList_q");
		Document responce = service.sendOnlineMessage(gateMessage, null);
		final List<ListValue> result = new ArrayList<ListValue>();
		try
		{
			XmlHelper.foreach(responce.getDocumentElement(), "//typePayment", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String code = XmlHelper.getSimpleElementValue(element, "typePaymentCode");
					String value = XmlHelper.getSimpleElementValue(element, "name");
					result.add(new ListValue(value, code));
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return result;
	}

	public CPFLRequisiteField getField()
	{
		if(fields == null)
			return null;
		if (fields.size() != 1)
			return null; //��� ��������� ��������� - ���������
		return fields.get(0);
	}
}