package com.rssl.phizic.gate.payments.template;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ������ ��� ������ � ��������� ��������
 *
 * @author khudyakov
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateGateService extends Service
{
	/**
	 * ����� ������� ��������� �� �������� ��������������
	 * ����������: ���� �� ��������� �������� �� �������������� ������� �������
	 *
	 * @param id �������������
	 * @return ������ ��������
	 */
	GateTemplate findById(Long id) throws GateException;

	/**
	 * ����� �������� ����������
	 * ����������:
	 * 1. ���� �� ��������� �������� �� �������������� ������� �������
	 * 2. �� ������ ����� �� ������� ������� ������������ ��� ������� ��������� ������� �������, ��� ���� � ���� ������� �� ����������������� �� ������ ������� � �������
	 *
	 * @param client ������
	 * @return ������ ��������
	 */
	List<? extends GateTemplate> getAll(Client client) throws GateException, GateLogicException;

	/**
	 * �������� ������(�)
	 * ����������: ���� �� ��������� �������� �� �������������� ������� �������
	 *
	 * @param templates ������ ��������
	 */
	void addOrUpdate(List<? extends GateTemplate> templates) throws GateException;

	/**
	 * ������� ������
	 * ����������: ���� �� ��������� �������� �� �������������� ������� �������
	 *
	 * @param templates ������
	 */
	void remove(List<? extends GateTemplate> templates) throws GateException;
}
