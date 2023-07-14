package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.gate.loanclaim.dictionary.FormOfIncorporation;
import com.rssl.phizic.gate.loanclaim.dictionary.KindOfActivity;
import com.rssl.phizic.gate.loanclaim.dictionary.NumberOfEmployees;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �� �����������
 */
@Immutable
public class Organization
{
	private final String fullName;

	private final FormOfIncorporation formOfIncorporation;

	private final String taxID;

	private final KindOfActivity kindOfActivity;

	private final String kindOfActivityComment;

	private final NumberOfEmployees numberOfEmployees;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param fullName - ������ ��� ����������� (never null)
	 * @param formOfIncorporation - ��������������-�������� ����� ����������� (never null)
	 * @param taxID - ��� ����������� (never null)
	 * @param kindOfActivity - ��� ������������ �������� (never null)
	 * @param kindOfActivityComment - ����������� � ���� ������������ �������� (can be null)
	 * @param numberOfEmployees - ���������� ����������� � �������� ������������ (never null)
	 */
	public Organization(String fullName, FormOfIncorporation formOfIncorporation, String taxID, KindOfActivity kindOfActivity, String kindOfActivityComment, NumberOfEmployees numberOfEmployees)
	{
		if (StringHelper.isEmpty(fullName))
			throw new IllegalArgumentException("�� ������� ������ ������������ �����������");
		if (formOfIncorporation == null)
		    throw new IllegalArgumentException("�� ������� ��������������-�������� �����");
		if (taxID == null)
			throw new IllegalArgumentException("�� ������ ��� �����������");
		if (kindOfActivity == null)
			throw new IllegalArgumentException("�� ������ ��� ������������");

		this.formOfIncorporation = formOfIncorporation;
		this.fullName = fullName;
		this.taxID = taxID;
		this.kindOfActivity = kindOfActivity;
		this.kindOfActivityComment = kindOfActivityComment;
		this.numberOfEmployees = numberOfEmployees;
	}

	/**
	 * @return ������ ��� ����������� (never null)
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @return ��������������-�������� ����� ����������� (never null)
	 */
	public FormOfIncorporation getFormOfIncorporation()
	{
		return formOfIncorporation;
	}

	/**
	 * @return ��� ����������� (never null)
	 */
	public String getTaxID()
	{
		return taxID;
	}

	/**
	 * @return ��� ������������ �������� (never null)
	 */
	public KindOfActivity getKindOfActivity()
	{
		return kindOfActivity;
	}

	/**
	 * @return ����������� � ���� ������������ �������� (can be null)
	 */
	public String getKindOfActivityComment()
	{
		return kindOfActivityComment;
	}

	/**
	 * @return ���������� ����������� � �������� ������������ (never null)
	 */
	public NumberOfEmployees getNumberOfEmployees()
	{
		return numberOfEmployees;
	}
}
