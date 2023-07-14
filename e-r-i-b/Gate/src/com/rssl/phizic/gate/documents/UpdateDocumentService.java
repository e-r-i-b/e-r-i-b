package com.rssl.phizic.gate.documents;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ���������� ����������.
 *
 */
public interface UpdateDocumentService extends Service
{
   /**
    * ����� �������� �� ��� ExternalId. ���� �������� �� ������ ������������ null.
    *
    * @param externalId (Domain: ExternalID)
    * @retun ��������
    */
   SynchronizableDocument find(String externalId) throws GateException, GateLogicException;
   /**
    * �������� SynchronizableDocument.
    * ������������ � ������, ����� ������������ ������ �������� ������ ���������.
    *
    * @param document ��������, ������� ���� ��������
    */
   void update(SynchronizableDocument document) throws GateException, GateLogicException;

	/**
	 * ����� ������������� ��������� �� ��������
	 * @return ������ ����������
	 * @param state ������ ��������
	 */
	List<GateDocument> findUnRegisteredPayments(State state) throws GateException, GateLogicException;

	/**
	 * ���������� ������� ���������
	 * @param document ��������, ������� ���� ��������
	 * @param command �������� ��������, ������� ���������� ���������� � ����������
	 * @throws GateException
	 */
	void update(SynchronizableDocument document, DocumentCommand command) throws GateException, GateLogicException;

	/**
	 * ����� �������� ���������� ���������� �� ������� ��� �������� �������
	 * @param state ������ ���������
	 * @param uid ������� �������
	 * @param firstResult ����� ������� ��������
	 * @param listLimit ���������� ������������ ����������
	 * @return ������ ����������
	 * @throws GateException
	 * @throws GateLogicException
	 * @deprecated ����� � �������� ��������� � ����.
	 */
	@Deprecated
	List<GateDocument> findUnRegisteredPayments(State state, String uid, Integer firstResult, Integer listLimit) throws GateException, GateLogicException;

	void createDocumentOrder(String externalId, Long id, String orderUuid) throws GateException, GateLogicException;
}