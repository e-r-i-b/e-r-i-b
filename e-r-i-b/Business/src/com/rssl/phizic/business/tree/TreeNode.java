package com.rssl.phizic.business.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * ������� ������
 * @author lepihina
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class TreeNode implements TreeElement
{
	private String name; // ���������
	private List<TreeElement> list = new ArrayList<TreeElement>(); // ������ �� �����������
	private String id;
	private boolean selected;

	/**
	 * @param name - ��������� �������
	 * @param id - �������������
	 */
	public TreeNode(String name, String id)
	{
		this.name = name;
		this.id = id;
	}

	/**
	 * ��������� ��������� � ������
	 * @param element - ���������
	 */
	public void add(TreeElement element)
	{
		list.add(element);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������ �����������
	 */
	public List<TreeElement> getList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return list;
	}

	/**
	 * @param list - ������ �����������
	 */
	public void setList(List<TreeElement> list)
	{
		this.list = new ArrayList<TreeElement>(list);
	}

	/**
	 * @return ������������� ��������
	 */
	public String getId()
	{
		return id;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(String selectedId)
	{
		selected = selectedId.equals(id);

		if (!selected)
			for (TreeElement element : list)
				element.setSelected(selectedId);
	}

	public List<Long> getSelectedIds()
	{
		if (selected)
			return getAllIdentifiers();

		List<Long> selectedIds = new ArrayList<Long>();
		for (TreeElement element : list)
			selectedIds.addAll(element.getSelectedIds());

		return selectedIds;
	}

	public List<Long> getAllIdentifiers()
	{
		List<Long> ids = new ArrayList<Long>();

		for (TreeElement element : list)
			ids.addAll(element.getAllIdentifiers());

		return ids;
	}

	public boolean isLeaf()
	{
		return false;
	}
}
