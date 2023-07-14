package com.rssl.phizic.person;

import com.rssl.phizic.common.types.client.ClientDocumentType;

/**
 * @author hudyakov
 * @ created 08.05.2008
 * @ $Author$
 * @ $Revision$
 */
public enum PersonDocumentType
{
	/**
	 * ��������������� ������� ��
	 */
	REGULAR_PASSPORT_RF(ClientDocumentType.REGULAR_PASSPORT_RF),
	/**
	 * ������������� �������� ���������������
	 */
	MILITARY_IDCARD(ClientDocumentType.MILITARY_IDCARD),
	/**
	 * ������� ������
	 */
	SEAMEN_PASSPORT(ClientDocumentType.SEAMEN_PASSPORT),
	/**
	 * ��� �� ���������� ��
	 */
	RESIDENTIAL_PERMIT_RF(ClientDocumentType.RESIDENTIAL_PERMIT_RF),
	/**
	 * ������������� � ����������� ����������� ���������� � ��������� ��� ��������
	 */
	IMMIGRANT_REGISTRATION(ClientDocumentType.IMMIGRANT_REGISTRATION),
	/**
	 * ������������� ������� � ��
	 */
	REFUGEE_IDENTITY(ClientDocumentType.REFUGEE_IDENTITY),
	/**
	 * ����������� ������� ��
	 */
	FOREIGN_PASSPORT_RF(ClientDocumentType.FOREIGN_PASSPORT_RF),
	/**
	 * ������������ �����
	 */
	MIGRATORY_CARD(ClientDocumentType.MIGRATORY_CARD),
	/**
	 * ������� ���������� ����
	 */
	REGULAR_PASSPORT_USSR(ClientDocumentType.REGULAR_PASSPORT_USSR),
	/**
	 * ����������� ������� ���������� ����
	 */
	FOREIGN_PASSPORT_USSR(ClientDocumentType.FOREIGN_PASSPORT_USSR),
	/**
	 * ������������� � ��������
	 */
	BIRTH_CERTIFICATE(ClientDocumentType.BIRTH_CERTIFICATE),
	/**
	 * ������������� �������� �������
	 */
	OFFICER_IDCARD(ClientDocumentType.OFFICER_IDCARD),
	/**
	 * ������� �����������
	 */
	PASSPORT_MINMORFLOT(ClientDocumentType.PASSPORT_MINMORFLOT),
	/**
	 * �������������� ������� ���������� ��
	 */
	DIPLOMATIC_PASSPORT_RF(ClientDocumentType.DIPLOMATIC_PASSPORT_RF),
	/**
	 * ����������� �������
	 */
	FOREIGN_PASSPORT(ClientDocumentType.FOREIGN_PASSPORT),
	/**
	 * ������� ����� ������� ������
	 */
	RESERVE_OFFICER_IDCARD(ClientDocumentType.RESERVE_OFFICER_IDCARD),
	/**
	 * ��������� ������������� �������� ���������� ��
	 */
	TIME_IDCARD_RF(ClientDocumentType.TIME_IDCARD_RF),
	/**
	 * ���� ���������, ���������� �������� ���
	 */
	OTHER_DOCUMENT_MVD(ClientDocumentType.OTHER_DOCUMENT_MVD),
	/**
	 * ������� �� ������������ �� ����� ������� �������
	 */
	INQUIRY_ON_CLEARING(ClientDocumentType.INQUIRY_ON_CLEARING),
	/**
	 * ������� way(��������� ���������, ��������������� ��������, ������������ ����� ������,� ������� ������� ��������������� ����� � ����� ���������. �� ����������� ������������� �������� � ��� ������� ����)
	 */
	PASSPORT_WAY(ClientDocumentType.PASSPORT_WAY),
	/**
	 * ���� ���������,�������������� �������� ��.�������, �� ����� �� ���������� � ��
	 */
	FOREIGN_PASSPORT_OTHER(ClientDocumentType.FOREIGN_PASSPORT_OTHER),
	/**
	 * ������������� ������������ �����������
	 */
	DISPLACED_PERSON_DOCUMENT(ClientDocumentType.DISPLACED_PERSON_DOCUMENT),
	/**
	 * ���� ���������, �����.� ������������ � ����.���������� �� (��� ��� ��� �����-��)
	 */
	OTHER_NOT_RESIDENT(ClientDocumentType.OTHER_NOT_RESIDENT),
	/**
	 * ���������� �� ��������� ���������� (��� ��� ��� �����������)
	 */
	TEMPORARY_PERMIT(ClientDocumentType.TEMPORARY_PERMIT),
	/**
	 * �������� ��. ���-��, ������������ � ������������ � ������������� ��������� ��
	 */
	FOREIGN_PASSPORT_LEGAL(ClientDocumentType.FOREIGN_PASSPORT_LEGAL),
	/**
	 * ���������� �������������
	 */
	PENSION_CERTIFICATE(ClientDocumentType.PENSION_CERTIFICATE),
	/**
	 * ���� ��������
	 */
	OTHER(ClientDocumentType.OTHER);

	private ClientDocumentType documentType;

	PersonDocumentType(ClientDocumentType documentType){
		 this.documentType = documentType;
	}

	public String toValue() { return this.name(); }

	public static PersonDocumentType valueOf (ClientDocumentType documentType){
		for (PersonDocumentType dt: values()){
			if (dt.equals(documentType)){
				return dt;
			}
		}
		throw new IllegalArgumentException();
	}

	public static ClientDocumentType valueFrom (PersonDocumentType documentType){
		for (ClientDocumentType dt: ClientDocumentType.values()){
			if (documentType.equals(dt)){
				return dt;
			}
		}
		throw new IllegalArgumentException();
	}

	private boolean equals (ClientDocumentType documentType)
    {
        return documentType.equals(this.documentType);
	}
}
