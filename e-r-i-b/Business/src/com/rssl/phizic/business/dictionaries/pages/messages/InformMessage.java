package com.rssl.phizic.business.dictionaries.pages.messages;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.pages.Page;

import java.util.Calendar;
import java.util.Set;

/**
 * Сущность "Информационное сообщение"
 * @author komarov
 * @ created 15.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class InformMessage
{
	private Long id; // Идентификатор сущности.
	private String name; //Имя шаблона.
	private Set<Page> pages; // Список страниц, на которых отображается сообщение.
	private Set<String> departments; //Список департаментов для которых предназначено сообщение.
	private Calendar startPublishDate; //Дата начала публикации.
	private Calendar   cancelPublishDate; //Дата окончания публикации.
	private ViewType vievType = ViewType.STATIC_MESSAGE;//Тип отображения информационного сообщения
	private InformMessageState state = InformMessageState.PUBLISED; //Статус информационного сообщения.
	private String   text; //Текст сообщения.
	private Importance importance = Importance.MEDIUM; //Важность информационного сообщения.

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
