package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * ������ ��� ������ � �����������
 *
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentService extends Service
{
   /**
    * �������� �������� ��� �������.
    *
    * @param document ��������
    * @throws GateException
    * @exception GateLogicException
    */
   void calcCommission(GateDocument document) throws GateException, GateLogicException;
   /**
    * ��������� �������� � ���-����.
    *
    * @param document ��������
    */
   void send(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ��������� �������� ��������� � ���-����.
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void repeatSend(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ��������� ���������� ���������. ��������� � �����. �.�. ��� ��� �������, ����� ������ �������������� ����������.
	 *
	 * @param document ��������
	 * @return StateUpdateInfo ��������� ���������
	 */
	StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ���������� ���������.
	 * ���������������� �������� ��� ����������, ������� ���������� ���������� � ���-�����,
	 * ����������� �� ������� � ����� ������ � ������� send.
	 * ��������, ����� ��������� �������� ����� � ���-�����.
	 *
	 * @param document �������� ��� ��������
	 */
	void prepare(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ����������� ������. ������������ � ����������� �����������.
	 * 
	 * @param document ������ ��� �������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void confirm(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ��������� ������� � ���-�����.
	 *
	 * @param document  ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void validate(GateDocument document) throws GateException, GateLogicException;

	/**
	 * �������� ��������
	 *
	 * @param document �������� ��� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void recall(GateDocument document) throws GateException, GateLogicException;
}