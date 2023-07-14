package com.rssl.phizic.business.tree;

import java.util.List;
import java.util.ArrayList;

/**
 * ���� ������
 * @author lepihina
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class TreeLeaf implements TreeElement
{
	private Long id; // ������������� ��������
	private String name;
	private boolean selected;

	/**
	 * ����������� ����� ������
	 * @param id - �������������
	 * @param name - ��������� �����
	 */
	public TreeLeaf(Long id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/**
	 * @return ������������� ��������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isLeaf()
	{
		return true;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(String selectedId)
	{
		selected = selectedId.equals(id.toString());
	}

	public List<Long> getSelectedIds()
	{
		List<Long> selectedIds = new ArrayList<Long>();

		if (selected)
			selectedIds.add(id);

		return selectedIds;
	}

	public List<Long> getAllIdentifiers()
	{
		List<Long> selectedIds = new ArrayList<Long>();
		selectedIds.add(id);

		return selectedIds;
	}
}
