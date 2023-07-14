/***********************************************************************
 * Module:  EditDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface EditDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

/**
 * »зменение ранее отосланного документа
 */
public interface EditDocument extends SynchronizableDocument
{
   /**
    * ƒанные редактируемого документа
    *
    * @return документ
    */
   GateDocument getEditedDocument();

}