package com.rssl.phizic.gate.clients;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Danilov
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Документ клиента
 */
public interface ClientDocument extends Serializable
{
   /**
    * Тип документа
    *
    * @return тип документа
    */
   public ClientDocumentType getDocumentType();

	/**
	 * Установить тип документа
	 * @param clientDocumentType - тип документа
	 */
   public void setDocumentType(ClientDocumentType clientDocumentType);

   /**
    * Название документа для отображения клиенту.
    *
    * @return Название документа
    */
   public String getDocTypeName();

   /**
    * Номер документа
    *
    * @return Номер документа
    */
   public String getDocNumber();

   /**
    * Серия документа
    */
   public String getDocSeries();

   /**
    * Дата выдачи документа
    *
    * @return Дата выдачи документа
    */
   public Calendar getDocIssueDate();

   /**
    * Кем выдан.
    *
    * @return кем выдан
    */
   public String getDocIssueBy();

   /**
    * Код подразделения, выдавшего документ
    *
    * @return Код подразделения
    */
   public String getDocIssueByCode();

	/**
	 * Признак, того, что документ удостоверяет личность
	 *
	 * @return является ли документ, удостоверяющим личность
	 */
	public boolean isDocIdentify();

	/**
    * Дата окончания срока действия/пребывания на территории РФ(для миграционной карты)
    *
    * @return Дата окончания действия
    */
   public Calendar getDocTimeUpDate();
}