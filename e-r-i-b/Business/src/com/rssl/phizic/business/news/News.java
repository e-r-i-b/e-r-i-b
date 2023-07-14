package com.rssl.phizic.business.news;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Set;

/**
 * User: Zhuravleva
 * Date: 05.12.2006
 * Time: 11:10:00 
 */
public class News extends MultiBlockDictionaryRecordBase
{
	private Long     id;
	private String   title;
	private Calendar newsDate;
	private String   text;
	private String   shortText;
	private NewsState   state;
	private NewsType    type;
	private Important   important;
	private Set<String> departments;
	private Calendar   startPublishDate;
	private Calendar   endPublishDate;

	public Important getImportant()
	{
		return important;
	}

	public void setImportant(Important important)
	{
		this.important = important;
	}

	public NewsState getState()
	{
		return state;
	}

	public void setState(NewsState state)
	{
		this.state = state;
	}

	public NewsType getType()
	{
		return type;
	}

	public void setType(NewsType type)
	{
		this.type = type;
	}

	public Calendar getStartPublishDate()
	{
		return startPublishDate;
	}

	public void setStartPublishDate(Calendar startPublishDate)
	{
		this.startPublishDate = startPublishDate;
	}

	public Calendar getEndPublishDate()
	{
		return endPublishDate;
	}

	public void setEndPublishDate(Calendar endPublishDate)
	{
		this.endPublishDate = endPublishDate;
	}


	public Set<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(Set<String> departments)
	{
		this.departments = departments;
	}

	public Long getId()
    {
        return id;
    }

	public void setId(Long id)
    {
        this.id = id;
    }

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Calendar getNewsDate()
	{
		if (newsDate == null)
			newsDate = DateHelper.getCurrentDate();
		return newsDate;
	}

	public String getShortText()
	{
		return shortText;
	}

	public void setShortText(String shortText)
	{
		this.shortText = shortText;
	}

	public void setNewsDate(Calendar newDate)
	{
		this.newsDate = newDate;
	}

	public String getText ()
	{
		return text;
	}

	public void setText (String text)
	{
		this.text = text;
	}

	public String getNewsDate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", newsDate);
	}
}
