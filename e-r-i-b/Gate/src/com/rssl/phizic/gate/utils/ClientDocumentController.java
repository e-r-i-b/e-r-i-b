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
	 * �������� ����� � ������ ��������� �� ������������ ����������.
	 *
	 * @param docSeries ����� ���������
	 * @param docNumber ����� ���������
	 * @param documentType ��� ���������
	 * @param isForCEDBO �������, ������������, ��� ������ ���������� ������������ �������� (CEDBO ��� ������).
	 * � ����������� �� ���� ����� �������� ��������� ������ ������� �������� �������� Way �������������� ��-�������.
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
			throw new GateException("����� ������������ �������� �� ������ �������� �� 2 ����, � ����� �� 7.");
		}
	}

	private static void validateImmigrantRegistration(String docSeries, String docNumber) throws GateException
	{
		if ( (docSeries + docNumber).length() == 25 )
		{
			return;
		}
		throw new GateException("����� ������������� � ����������� ����������� ���������� � ��������� ��� �������� � ����� � ������� ������ ��������� 25 ������.");
	}

	private static void validateRefugeeIdentity(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("[�-�A-z]{1}")&& (docNumber.matches("\\d{6}")))
		{
			return;
		}
		throw new GateException("����� ������������� ������� ������ �������� �� 1 �����, � ����� �� 6 ����.");
	}

	private static void validateResidentalPermit(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("\\d{2}")&& docNumber.matches("\\d{7}"))
		{
			return;
		}
		throw new GateException("����� ���� �� ���������� �� ������ �������� �� 2 ����, � ����� �� 7.");
	}

	private static void validateMilitaryIDCard(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("[�-�]{2}") && (docNumber.matches("\\d{7}")))
		{
			return;
		}
		throw new GateException("����� ������������� �������� ��������������� ������ �������� �� 2 ����, � ����� -  �� 7 ����.");
	}

	private static void validateDisplacedPersonDocument(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("[�-�]{2}") && (docNumber.matches("\\d{6,7}")))
		{
			return;
		}
		throw new GateException("����� ������������� ������������ ����������� ������ �������� �� 2 ����, � ����� �� 6 ��� 7 ����.");
	}

	private static void validateTemporaryPermit(String docSeries, String docNumber) throws GateException
	{
		if (docSeries.matches("\\d{2}") && (docNumber.matches("\\d{6}")))
		{
			return;
		}
		throw new GateException("����� ���������� �� ��������� ���������� (��� ��� ��� �����������) ������ �������� �� 2, � ����� �� 6 ����.");
	}

	private static void validateSeamenPassport(String docSeries, String docNumber) throws GateException
	{
		if ( ( docSeries.matches("��") || docSeries.matches("��") || docSeries.matches("��")) && docNumber.matches("\\d{7}") )
		{
			return;
		}
		throw new GateException("����� �������� ������ ������ �������� �� 2 ���� (��, ��, ��), � ����� �� 7 ����.");
	}

	private static void validatePassportWay(String docSeries, String docNumber, boolean isForCEDBO) throws GateException
	{
		/*
		 * ���� �������� ���������� ��� ���������� CEDBO �� ����� � ����� �������� � ����� ���������,
		 * � ��������� ������� � ������.
		 */
		String value = isForCEDBO ? docSeries : docNumber;
		boolean lenghtIncorrect = (StringHelper.isEmpty(value) || value.length() > 16);

		/*
		 * ���� �������� ���������� ��� CEDBO ����� � ����� ����������� ����� ������ ��������� �� ����
		 * � �� ���������� ������� �������� ��� ���� �������� � �������� ������������,
		 * ������ �� ����� 16 ��������.
		 */
		if (isForCEDBO)
		{
			if (lenghtIncorrect)
			{
				throw new GateException("����� ��������� ��� �������� way �� ������ ��������� 16 ������� ��� ���� ������");
			}

			if (!value.matches("\\d*"))
			{
				throw new GateException("C���� ��������� ��� �������� way �������� ������������ �������");
			}
		}
		/*
		 * ���� �������� ���������� �� ��� CEDBO, �� � ������ �������� ����� � ����� ����� ��������������
		 * �������, ������ ������ �� ������������ ������� ����������� ������ �����.
		 */
		if (lenghtIncorrect)
		{
			throw new GateException("����� ��������� ��� �������� way �� ������ ��������� 16 �������� ��� ���� ������");
		}
	}

	private static void validateRegularPassportRF(String docSeries, String docNumber) throws GateException
	{
		/*
		 * ����������� ��� ����� � ����� �� ������ � ��������� ���������.
		 */

		if (StringHelper.isEmpty(docNumber)) throw new GateException("����� ��������� �� ����� ���� ������");
		if(StringHelper.isNotEmpty(docSeries))
		{
			if (!docNumber.matches("\\d*")) throw new GateException("����� ��������� �������� ������������ �������");
		}
		else
		{
			if(!docNumber.matches("\\d{2} \\d{8}")) throw new GateException("����� ��������� �������� ������������ �������");
		}
	}
}
