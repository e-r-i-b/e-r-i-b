package com.rssl.phizic.business.dictionaries.pages.messages;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.pages.Page;

import java.util.Calendar;
import java.util.Set;

/**
 * �������� "�������������� ���������"
 * @author komarov
 * @ created 15.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class InformMessage
{
	private Long id; // ������������� ��������.
	private String name; //��� �������.
	private Set<Page> pages; // ������ �������, �� ������� ������������ ���������.
	private Set<String> departments; //������ ������������� ��� ������� ������������� ���������.
	private Calendar startPublishDate; //���� ������ ����������.
	private Calendar   cancelPublishDate; //���� ��������� ����������.
	private ViewType vievType = ViewType.STATIC_MESSAGE;//��� ����������� ��������������� ���������
	private InformMessageState state = InformMessageState.PUBLISED; //������ ��������������� ���������.
	private String   text; //����� ���������.
	private Importance importance = Importance.MEDIUM; //�������� ��������������� ���������.

	public Long getId()
	{
		return id;
	}

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

	public Set<Page> getPages()
	{
		return pages;
	}

	public void setPages(Set<Page> pages)
	{
		this.pages = pages;
	}

	public Set<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(Set<String> departments)
	{
		this.departments = departments;
	}

	public Calendar getStartPublishDate()
	{
		return startPublishDate;
	}

	public void setStartPublishDate(Calendar startPublishDate)
	{
		this.startPublishDate = startPublishDate;
	}

	public Calendar getCancelPublishDate()
	{
		return cancelPublishDate;
	}

	public void setCancelPublishDate(Calendar cancelPublishDate)
	{
		this.cancelPublishDate = cancelPublishDate;
	}

	public ViewType getVievType()
	{
		return vievType;
	}

	public void setVievType(ViewType vievType)
	{
		this.vievType = vievType;
	}

	public InformMessageState getState()
	{
		return state;
	}

	public void setState(InformMessageState state)
	{
		this.state = state;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Importance getImportance()
	{
		return importance;
	}

	public void setImportance(Importance importance)
	{
		this.importance = importance;
	}
}
