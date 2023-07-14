package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 26.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ������.
 * ����� �� ������� ������� �������������� ���� ������� � ������ �������������� �����������.
 */
@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
class OfferLogin implements Serializable
{
	/**
	 * ������������� ����� [PK]
	 */
	Long id;

	/**
	 * �������
	 */
	String surName;

	/**
	 * ���
	 */
	String firstName;

	/**
	 * ��������
	 */
	String patrName;

	/**
	 * ���� ��������
	 */
	Calendar birthDay;

	/**
	 * ����� ���������
	 */
	String docSeries;

	/**
	 * ����� ���������
	 */
	String docNumber;

	/**
	 * ��� ��������
 	 */
	String tb;

	/**
	 * UID ���������� ������� � CRM [1]
	 */
	String lastRqUID;

	/**
	 * ����+����� ���������� ������� � CRM [1]
	 */
	Calendar lastRqTime;

	/**
	 * ������ ���������� ���������� ������� � CRM [1]
	 */
	OfferRequestStatus lastRqStatus;
}
