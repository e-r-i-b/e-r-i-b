package com.rssl.phizic.gate.utils;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

/**
 * User: Balovtsev
 * Date: 23.08.2011
 * Time: 13:38:27
 */
public class ClientDocumentController
{

	/**
	 *
	 * Проверка серии и номера документа на корректность заполнения.
	 *
	 * @param docSeries серия документа
	 * @param docNumber номер документа
	 * @param documentType тип документа
	 * @param isForCEDBO признак, определяющий, для какого интерфейса предназначен документ (CEDBO или другой).
	 * В зависимости от того какое значение принимает данный признак проверка паспорта Way осуществляется по-разному.
	 * @throws GateException
	 */
	public static void validateDocument(String docSeries, String docNumber, String documentType, boolean isForCEDBO) throws GateException
	{
		ClientDocumentType docType = ClientDocumentType.valueOf(documentType);

		switch (docType)
		{
			case REGULAR_PASSPORT_RF:
			{
				validateRegularPassportRF(docSeries, docNumber);
				break;
			}

			case PASSPORT_WAY:
			{
				validatePassportWay(docSeries, docNumber, isForCEDBO);
				break;
			}

			case MILITARY_IDCARD:
			{
				validateMilitaryIDCard(docSeries, docNumber);
				break;
			}

			case SEAMEN_PASSPORT:
			{
				validateSeamenPassport(docSeries, docNumber);
				break;
			}

			case FOREIGN_PASSPORT_RF:
			{
				validateForeignPassport(docSeries, docNumber);
				break;
			}

			case RESIDENTIAL_PERMIT_RF:
			{
				validateResidentalPermit(docSeries, docNumber);
				break;
			}

			case REFUGEE_IDENTITY:
			{
				validateRefugeeIdentity(docSeries, docNumber);
				break;
			}

			case IMMIGRANT_REGISTRATION:
			{
				validateImmigrantRegistration(docSeries, docNumber);
				break;
			}

			case DISPLACED_PERSON_DOCUMENT:
			{
				validateDisplacedPersonDocument(docSeries, docNumber);
				break;
			}

			case TEMPORARY_PERMIT:
			{
				validateTemporaryPermit(docSeries, docNumber);
				break;
			}
		}
	}

	private static void validateForeignPassport(String docSeries, String docNumber) throws GateException
	{
		try
		{
			validateResidentalPermit(docSeries, docNumber);
		}
		catch (GateException ignored)
		{
			throw new GateException("Серия заграничного паспорта РФ должна состоять из 2 цифр, а номер из 7.");
		}
	}

	private static void validateImmigrantRegistration(String docSeries, String docNumber) throws GateException
	{
		if ( (docSeries + docNumber).length() == 25 )
		{
			return;
		}
		throw new GateException("Серия свидетельства о регистрации ходатайства иммигранта о признании его беженцем в сумме с номером должна содержать 25 знаков.");
	}

	private static void validateRefugeeIdentity(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("[А-яA-z]{1}")&& (docNumber.matches("\\d{6}")))
		{
			return;
		}
		throw new GateException("Серия удостоверения беженца должна состоять из 1 буквы, а номер из 6 цифр.");
	}

	private static void validateResidentalPermit(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("\\d{2}")&& docNumber.matches("\\d{7}"))
		{
			return;
		}
		throw new GateException("Серия вида на жительство РФ должна состоять из 2 цифр, а номер из 7.");
	}

	private static void validateMilitaryIDCard(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("[А-я]{2}") && (docNumber.matches("\\d{7}")))
		{
			return;
		}
		throw new GateException("Серия удостоверения личности военнослужащего должна состоять из 2 букв, а номер -  из 7 цифр.");
	}

	private static void validateDisplacedPersonDocument(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("[А-я]{2}") && (docNumber.matches("\\d{6,7}")))
		{
			return;
		}
		throw new GateException("Серия удостоверения вынужденного переселенца должна состоять из 2 букв, а номер из 6 или 7 цифр.");
	}

	private static void validateTemporaryPermit(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("\\d{2}") && (docNumber.matches("\\d{6}")))
		{
			return;
		}
		throw new GateException("Серия разрешения на временное проживание (для лиц без гражданства) должна состоять из 2, а номер из 6 цифр.");
	}

	private static void validateSeamenPassport(String docSeries, String docNumber) throws GateException
	{
		if ( ( docSeries.matches("МФ") || docSeries.matches("РФ") || docSeries.matches("РХ")) && docNumber.matches("\\d{7}") )
		{
			return;
		}
		throw new GateException("Серия паспорта моряка должна состоять из 2 букв (МФ, РФ, РХ), а номер из 7 цифр.");
	}

	private static void validatePassportWay(String docSeries, String docNumber, boolean isForCEDBO) throws GateException
	{
		/*
		 * Если документ заполяется для интерфейса CEDBO то серия и номер хранятся в серии документа,
		 * в остальных случаях в номере.
		 */
		String value = isForCEDBO ? docSeries : docNumber;
		boolean lenghtIncorrect = (StringHelper.isEmpty(value) || value.length() > 16);

		/*
		 * Если документ заполнялся для CEDBO серия и номер преставляют собой строку состоящую из цифр
		 * и не содержащую никаких пробелов или иных символов в качестве разделителей,
		 * длиной не более 16 символов.
		 */
		if (isForCEDBO)
		{
			if (lenghtIncorrect)
			{
				throw new GateException("Серии документа для паспорта way не должна превышать 16 сиволов или быть пустой");
			}

			if (!value.matches("\\d*"))
			{
				throw new GateException("Cерия документа для паспорта way содержит недопустимые символы");
			}
		}
		/*
		 * Если документ заполнялся не для CEDBO, то в строке хранящей серию и номер могут присутствовать
		 * пробелы, формат строки не унифицирован поэтому проверяется только длина.
		 */
		if (lenghtIncorrect)
		{
			throw new GateException("Номер документа для паспорта way не должен превышать 16 символов или быть пустым");
		}
	}

	private static void validateRegularPassportRF(String docSeries, String docNumber) throws GateException
	{
		/*
		 * Проверяется что серия и номер не пустые и правильно заполнены.
		 */

		if (StringHelper.isEmpty(docNumber)) throw new GateException("Номер документа не может быть пустым");
		if(StringHelper.isNotEmpty(docSeries))
		{
			if (!docNumber.matches("\\d*")) throw new GateException("Номер документа содержит недопустимые символы");
		}
		else
		{
			if(!docNumber.matches("\\d{2} \\d{8}")) throw new GateException("Номер документа содержит недопустимые символы");
		}
	}
}
