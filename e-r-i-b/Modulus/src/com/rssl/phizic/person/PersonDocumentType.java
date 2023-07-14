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
	 * Общегражданский паспорт РФ
	 */
	REGULAR_PASSPORT_RF(ClientDocumentType.REGULAR_PASSPORT_RF),
	/**
	 * Удостоверение личности военнослужащего
	 */
	MILITARY_IDCARD(ClientDocumentType.MILITARY_IDCARD),
	/**
	 * Паспорт моряка
	 */
	SEAMEN_PASSPORT(ClientDocumentType.SEAMEN_PASSPORT),
	/**
	 * Вид на жительство РФ
	 */
	RESIDENTIAL_PERMIT_RF(ClientDocumentType.RESIDENTIAL_PERMIT_RF),
	/**
	 * Свидетельство о регистрации ходатайства иммигранта о признании его беженцем
	 */
	IMMIGRANT_REGISTRATION(ClientDocumentType.IMMIGRANT_REGISTRATION),
	/**
	 * Удостоверение беженца в РФ
	 */
	REFUGEE_IDENTITY(ClientDocumentType.REFUGEE_IDENTITY),
	/**
	 * Заграничный паспорт РФ
	 */
	FOREIGN_PASSPORT_RF(ClientDocumentType.FOREIGN_PASSPORT_RF),
	/**
	 * Миграционная карта
	 */
	MIGRATORY_CARD(ClientDocumentType.MIGRATORY_CARD),
	/**
	 * паспорт гражданина СССР
	 */
	REGULAR_PASSPORT_USSR(ClientDocumentType.REGULAR_PASSPORT_USSR),
	/**
	 * Заграничный паспорт гражданина СССР
	 */
	FOREIGN_PASSPORT_USSR(ClientDocumentType.FOREIGN_PASSPORT_USSR),
	/**
	 * свидетельство о рождении
	 */
	BIRTH_CERTIFICATE(ClientDocumentType.BIRTH_CERTIFICATE),
	/**
	 * удостоверение личности офицера
	 */
	OFFICER_IDCARD(ClientDocumentType.OFFICER_IDCARD),
	/**
	 * паспорт Минморфлота
	 */
	PASSPORT_MINMORFLOT(ClientDocumentType.PASSPORT_MINMORFLOT),
	/**
	 * дипломатичский паспорт гражданина РФ
	 */
	DIPLOMATIC_PASSPORT_RF(ClientDocumentType.DIPLOMATIC_PASSPORT_RF),
	/**
	 * иностранный паспорт
	 */
	FOREIGN_PASSPORT(ClientDocumentType.FOREIGN_PASSPORT),
	/**
	 * военный билет офицера запаса
	 */
	RESERVE_OFFICER_IDCARD(ClientDocumentType.RESERVE_OFFICER_IDCARD),
	/**
	 * временное удостоверение личности гражданина РФ
	 */
	TIME_IDCARD_RF(ClientDocumentType.TIME_IDCARD_RF),
	/**
	 * Иные документы, выдаваемые органами МВД
	 */
	OTHER_DOCUMENT_MVD(ClientDocumentType.OTHER_DOCUMENT_MVD),
	/**
	 * справка об освобождении из места лишения свободы
	 */
	INQUIRY_ON_CLEARING(ClientDocumentType.INQUIRY_ON_CLEARING),
	/**
	 * Паспорт way(реквизиты документа, удостоверяющего личность, представляют собой строку,в которой указаны последовательно серия и номер документа. Не допускается использование пробелов в пле данного типа)
	 */
	PASSPORT_WAY(ClientDocumentType.PASSPORT_WAY),
	/**
	 * Иные документы,подтверждающие личность ин.граждан, их право на пребывание в РФ
	 */
	FOREIGN_PASSPORT_OTHER(ClientDocumentType.FOREIGN_PASSPORT_OTHER),
	/**
	 * Удостоверение вынужденного переселенца
	 */
	DISPLACED_PERSON_DOCUMENT(ClientDocumentType.DISPLACED_PERSON_DOCUMENT),
	/**
	 * Иные документы, призн.в соответствии с межд.договорами РФ (для лиц без гражд-ва)
	 */
	OTHER_NOT_RESIDENT(ClientDocumentType.OTHER_NOT_RESIDENT),
	/**
	 * Разрешение на временное проживание (для лиц без гражданства)
	 */
	TEMPORARY_PERMIT(ClientDocumentType.TEMPORARY_PERMIT),
	/**
	 * Документ ин. гос-ва, признаваемый в соответствии с международным договором РФ
	 */
	FOREIGN_PASSPORT_LEGAL(ClientDocumentType.FOREIGN_PASSPORT_LEGAL),
	/**
	 * пенсионное удостоверение
	 */
	PENSION_CERTIFICATE(ClientDocumentType.PENSION_CERTIFICATE),
	/**
	 * Иной документ
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
