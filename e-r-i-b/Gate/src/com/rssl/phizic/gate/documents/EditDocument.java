/***********************************************************************
 * Module:  EditDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface EditDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

/**
 * ��������� ����� ����������� ���������
 */
public interface EditDocument extends SynchronizableDocument
{
   /**
    * ������ �������������� ���������
    *
    * @return ��������
    */
   GateDocument getEditedDocument();

}