package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author krenev
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 * ������ ���������� ����� ���������.
 * �� �������� com.rssl.phizicgate.cpfl.messaging.CPFLRequisiteProcessor#getHint()
 */
class CPFLRequisitesFieldGroup
{
	private static final int MAX_GROUP_TEMPLATE_SIZE = 15;

	private final List<CPFLRequisiteField> fields = new ArrayList<CPFLRequisiteField>();
	private Set<String> singularSet;

	CPFLRequisitesFieldGroup(CPFLRequisiteField... fields)
	{
		if (this.fields != null)
		{
			for (CPFLRequisiteField field : fields)
			{
				add(field);
			}
		}
	}

	//������ ���������� �������� �����.
	private static final List<Set<String>> similarTemplates = new ArrayList<Set<String>>();

	static
	{
		Set<String> singularSet = new HashSet<String>();
		singularSet.add("D");
		singularSet.add("M");
		singularSet.add("G");
		singularSet.add("9");
		singularSet.add("R");
		singularSet.add("K");
		singularSet.add("P");
		similarTemplates.add(singularSet);
		singularSet = new HashSet<String>();
		singularSet.add("X");
		singularSet.add("S");
		similarTemplates.add(singularSet);
		singularSet = new HashSet<String>();
		singularSet.add("A");
		similarTemplates.add(singularSet);
		singularSet = new HashSet<String>();
		singularSet.add("N");
		similarTemplates.add(singularSet);
	}

	/**
	 * ��������� �� ���� ������
	 * @param field ����
	 * @return ��/���
	 */

	boolean isSimilar(CPFLRequisiteField field)
	{
		if (singularSet == null)
		{
			//������ ������� ������������� ��������� ���������� �������� ��� ������ ������
			for (Set<String> set : similarTemplates)
			{
				if (set.contains(field.getTemplate()))
				{
					singularSet = set;
				}
			}
		}
		if (singularSet == null)
		{
			throw new IllegalArgumentException("�� ������� ��������� ���������� �������� ��� template=" + field.getTemplate());
		}
		return singularSet.contains(field.getTemplate());
	}

	void add(CPFLRequisiteField field)
	{
		if (!isSimilar(field))
		{
			throw new IllegalArgumentException("���� �����������");
		}
		fields.add(field);
	}

	/**
	 * @return ������� �� ���������� �����������(��������/���������) � ������
	 */
	boolean hasDelimiters()
	{
		int count = getFieldsCount();
		if (count < 2)
		{
			return false;
		}
		CPFLRequisiteField field = fields.get(0);
		if (!StringHelper.isEmpty(field.getPostfix()))
		{
			return true;
		}
		for (int i = 1; i < count - 1; i++)
		{
			field = fields.get(i);
			if (!StringHelper.isEmpty(field.getPrefix()) || !StringHelper.isEmpty(field.getPostfix()))
			{
				return true;
			}
		}
		field = fields.get(count - 1);
		if (!StringHelper.isEmpty(field.getPrefix()))
		{
			return true;
		}
		return false;
	}

	int getFieldsCount()
	{
		return fields.size();
	}

	/**
	 * �������� ������ �����
	 * @param templateChar - ������ ��� �������
	 * @return ������ �����. �������� XXX-XXX-X-X
	 */
	String getTemplate(char templateChar)
	{
		int count = getFieldsCount();
		if (count == 0)
		{
			throw new IllegalStateException("������ �����");
		}
		StringBuilder result = new StringBuilder();
		for (CPFLRequisiteField field : fields)
		{
			if (!StringHelper.isEmpty(field.getPrefix()))
			{
				result.append(field.getPrefix());
			}
			result.append(makeString(templateChar, Math.min(field.getMaxLength(), MAX_GROUP_TEMPLATE_SIZE)));
			if (!StringHelper.isEmpty(field.getPostfix()))
			{
				result.append(field.getPostfix());
			}
		}
		return result.toString();
	}

	//�������� ��� ��������� ���������� �������� � ������������ ����� ������������ ������
	private static final Map<String, String> templatesDescriptionSingularNominative = new HashMap<String, String>();
	//�������� ��� ��������� ���������� �������� � ������������ ����� ����������� ������
	private static final Map<String, String> templatesDescriptionSingularGenitive = new HashMap<String, String>();
	//�������� ��� ��������� ���������� �������� �� ������������� ����� ����������� ������
	private static final Map<String, String> templatesDescriptionMultipleGenitive = new HashMap<String, String>();

	static
	{
		templatesDescriptionSingularNominative.put("D", "�����");
		templatesDescriptionSingularNominative.put("M", "�����");
		templatesDescriptionSingularNominative.put("G", "�����");
		templatesDescriptionSingularNominative.put("9", "�����");
		templatesDescriptionSingularNominative.put("R", "�����");
		templatesDescriptionSingularNominative.put("K", "�����");
		templatesDescriptionSingularNominative.put("P", "�����");
		templatesDescriptionSingularNominative.put("A", "����� ��� ������");
		templatesDescriptionSingularNominative.put("N", "����� ��� �����");
		templatesDescriptionSingularNominative.put("X", "������");
		templatesDescriptionSingularNominative.put("S", "������");

		templatesDescriptionSingularGenitive.put("D", "�����");
		templatesDescriptionSingularGenitive.put("M", "�����");
		templatesDescriptionSingularGenitive.put("G", "�����");
		templatesDescriptionSingularGenitive.put("9", "�����");
		templatesDescriptionSingularGenitive.put("R", "�����");
		templatesDescriptionSingularGenitive.put("K", "�����");
		templatesDescriptionSingularGenitive.put("P", "�����");
		templatesDescriptionSingularGenitive.put("A", "����� ��� �������");
		templatesDescriptionSingularGenitive.put("N", "����� ��� �����");
		templatesDescriptionSingularGenitive.put("X", "�������");
		templatesDescriptionSingularGenitive.put("S", "�������");

		templatesDescriptionMultipleGenitive.put("D", "����");
		templatesDescriptionMultipleGenitive.put("M", "����");
		templatesDescriptionMultipleGenitive.put("G", "����");
		templatesDescriptionMultipleGenitive.put("9", "����");
		templatesDescriptionMultipleGenitive.put("R", "����");
		templatesDescriptionMultipleGenitive.put("K", "����");
		templatesDescriptionMultipleGenitive.put("P", "����");
		templatesDescriptionMultipleGenitive.put("A", "���� ��� ��������");
		templatesDescriptionMultipleGenitive.put("N", "���� ��� ����");
		templatesDescriptionMultipleGenitive.put("X", "��������");//����� �������� �������
		templatesDescriptionMultipleGenitive.put("S", "��������");//����� �������� �������
	}

	/**
	 * @return ������������ �������� ��������� ���������� �������� � �� ���� ��� ����������. 
	 */
	String getDescription()
	{
		if (getFieldsCount() == 0)
		{
			throw new IllegalStateException("������ �����");
		}
		//������� ������������ � ����������� ����� �������� ��� ����� ������.
		int max = 0;
		int min = 0;
		for (CPFLRequisiteField field : fields)
		{
			max += field.getMaxLength();
			min += field.getMinLength();
		}
		String template = fields.get(0).getTemplate();
		StringBuilder formatDestription = new StringBuilder();
		if (min == 0)
		{
			//��� ����� ���-> ���� ������ �� ����� max ��������.
			formatDestription.append(" �� ����� ").append(max);
			if (max % 10 == 1 && max % 100 != 11)
			{
				//������� � ������������ ����� ��� ������: �� ����� 101 �������, �� ����� 31 �������....
				formatDestription.append(" ").append(templatesDescriptionSingularGenitive.get(template));
			}
			else
			{
				//������� �� ������������� ����� ��� ������: �� ����� 11 ��������, �� ����� 303 ��������....
				formatDestription.append(" ").append(templatesDescriptionMultipleGenitive.get(template));
			}
		}
		else if (max == min)
		{
			//��� ��� � ���� ����� -> ���� ������ ������ ���������� ��������.
			formatDestription.append(min);

			int lastDigit = max % 10;
			int last2digits = max % 100;
			if (lastDigit == 1 && last2digits != 11)
			{
				//������� � ������������ ����� �� ������: 101 ������, 31 ������....
				formatDestription.append(" ").append(templatesDescriptionSingularNominative.get(template));
			}
			else if (2 <= lastDigit && lastDigit <= 4 && last2digits >20)
			{
				//������� � ������������ ����� ��� ������: 102 �������, 33 �������....
				formatDestription.append(" ").append(templatesDescriptionSingularGenitive.get(template));
			}
			else
			{
				//������� �� ������������� ����� ��� ������: 11 ��������, 306 ��������....
				formatDestription.append(" ").append(templatesDescriptionMultipleGenitive.get(template));
			}
		}
		else
		{
			//�� �����. ����� �� ��� �� ���
			formatDestription.append(" o� ").append(min).append(" �� ").append(max);
			if (max % 10 == 1 && max % 100 != 11)
			{
				//������� � ������������ ����� ��� ������: �� 1 �� 101 �������, �� 4 �� 31 �������....
				formatDestription.append(" ").append(templatesDescriptionSingularGenitive.get(template));
			}
			else
			{
				//������� �� ������������� ����� ��� ������: �� 1 �� 11 ��������, �� 1 �� 303 ��������....
				formatDestription.append(" ").append(templatesDescriptionMultipleGenitive.get(template));
			}
		}
		return formatDestription.toString();
	}

	//������� "�����"(template) ����� �� ��������� ���������� ������� ��� ��������� �������.
	private static final Map<String, String> exampleAllowedCharsMap = new HashMap<String, String>();

	static
	{
		exampleAllowedCharsMap.put("D", "0123456789");
		exampleAllowedCharsMap.put("M", "0123456789");
		exampleAllowedCharsMap.put("G", "0123456789");
		exampleAllowedCharsMap.put("9", "0123456789");
		exampleAllowedCharsMap.put("R", "0123456789");
		exampleAllowedCharsMap.put("K", "0123456789");
		exampleAllowedCharsMap.put("P", "0123456789");//����� � ������
		exampleAllowedCharsMap.put("A", "ABC123 DEFJHIGKLMNOPQRSTUVWXYZ");//����� � ������
		exampleAllowedCharsMap.put("N", "ABC123DEFJHIGKLMNOPQRSTUVWXYZ0123456789");//����� � �����
		exampleAllowedCharsMap.put("X", "ABC123 DEFJHIGKLMNOPQRSTUVWXYZ0123456789");//����� �������� �������
		exampleAllowedCharsMap.put("S", "ABC123 DEFJHIGKLMNOPQRSTUVWXYZ0123456789");//����� �������� �������
	}

	/**
	 * @return ������������� ������ ���������� ������
	 */
	String getExample()
	{
		int count = getFieldsCount();
		if (count == 0)
		{
			throw new IllegalStateException("������ �����");
		}
		StringBuilder result = new StringBuilder();
		for (CPFLRequisiteField field : fields)
		{
			if (!StringHelper.isEmpty(field.getPrefix()))
			{
				result.append(field.getPrefix());
			}
			result.append(generateExampleString((int) Math.min(field.getMaxLength(), MAX_GROUP_TEMPLATE_SIZE)));
			if (!StringHelper.isEmpty(field.getPostfix()))
			{
				result.append(field.getPostfix());
			}
		}
		return result.toString();
	}

	private String generateExampleString(int n)
	{
		int count = getFieldsCount();
		if (count == 0)
		{
			throw new IllegalStateException("������ �����");
		}
		CPFLRequisiteField firstField = fields.get(0); // ����� ����� ����� ����, �� ��� ��� ���������
		String exampleChars = exampleAllowedCharsMap.get(firstField.getTemplate());
		if (exampleChars == null)
		{
			throw new IllegalStateException("�� ������� ��������� �������� ��� ������� template=" + firstField.getTemplate());
		}

		StringBuffer buffer = new StringBuffer(n);
		int length = exampleChars.length();

		char[] charsArray = new char[length];
		exampleChars.getChars(0, length, charsArray, 0);

		for (int i = 0; i < n; i++)
		{
			buffer.append(charsArray[i % length]);
		}
		return buffer.toString();
	}

	String makeString(char templateChar, long count)
	{
		StringBuilder format = new StringBuilder();
		for (int j = 0; j < count; j++)
		{
			format.append(templateChar);
		}
		return format.toString();
	}
}
