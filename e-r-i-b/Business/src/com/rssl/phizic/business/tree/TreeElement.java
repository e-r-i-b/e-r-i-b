package com.rssl.phizic.business.tree;

import java.util.List;

/**
 * ������� ������: ������� ��� ����
 * @author lepihina
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TreeElement
{
	/**
	 * ���������� ���������
	 * @return ���������
	 */
	String getName();

	/**
	 * @param name - ���������
	 */
	void setName(String name);

	/**
	 * �������� �� ������� ������ ������
	 * @return true - ����
	 */
	boolean isLeaf();

	/**
	 * ������ �� ������� ������� ������
	 * @return true - ������
	 */
	boolean isSelected();

	/**
	 * ����� ������� ������ �� selectedId � ��������, ��� �� ������
	 * @param selectedId - ������������� ��������
	 */
	void setSelected(String selectedId);

	/**
	 * ���������� ������ �� ��������������� ������� �������� ������, ������� �������� ��� ���������.
	 * ���� � ������ ������� �������, �� ������������ �������������� ���� ������� ���� �������.
	 * @return ������ �� ��������������� ������� ������
	 */
	List<Long> getSelectedIds();

	/**
	 * ���������� ������ ���� ��������������� ������� �������� ������.
	 * @return ������ ��������������� �������
	 */
	List<Long> getAllIdentifiers();
}
