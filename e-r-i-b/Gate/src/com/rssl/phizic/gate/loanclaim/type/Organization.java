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
 * Информация об организации
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
	 * @param fullName - полное имя организации (never null)
	 * @param formOfIncorporation - организационна-правовая форма организации (never null)
	 * @param taxID - ИНН организации (never null)
	 * @param kindOfActivity - вид деятельности компании (never null)
	 * @param kindOfActivityComment - комментарий к виду деятельности компании (can be null)
	 * @param numberOfEmployees - количество сотрудников в компании работодателе (never null)
	 */
	public Organization(String fullName, FormOfIncorporation formOfIncorporation, String taxID, KindOfActivity kindOfActivity, String kindOfActivityComment, NumberOfEmployees numberOfEmployees)
	{
		if (StringHelper.isEmpty(fullName))
			throw new IllegalArgumentException("Не указано полное наименование организации");
		if (formOfIncorporation == null)
		    throw new IllegalArgumentException("Не указана организационна-правовая форма");
		if (taxID == null)
			throw new IllegalArgumentException("Не указан ИНН организации");
		if (kindOfActivity == null)
			throw new IllegalArgumentException("Не указан вид деятельности");

		this.formOfIncorporation = formOfIncorporation;
		this.fullName = fullName;
		this.taxID = taxID;
		this.kindOfActivity = kindOfActivity;
		this.kindOfActivityComment = kindOfActivityComment;
		this.numberOfEmployees = numberOfEmployees;
	}

	/**
	 * @return полное имя организации (never null)
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @return организационна-правовая форма организации (never null)
	 */
	public FormOfIncorporation getFormOfIncorporation()
	{
		return formOfIncorporation;
	}

	/**
	 * @return ИНН организации (never null)
	 */
	public String getTaxID()
	{
		return taxID;
	}

	/**
	 * @return вид деятельности компании (never null)
	 */
	public KindOfActivity getKindOfActivity()
	{
		return kindOfActivity;
	}

	/**
	 * @return комментарий к виду деятельности компании (can be null)
	 */
	public String getKindOfActivityComment()
	{
		return kindOfActivityComment;
	}

	/**
	 * @return количество сотрудников в компании работодателе (never null)
	 */
	public NumberOfEmployees getNumberOfEmployees()
	{
		return numberOfEmployees;
	}
}
