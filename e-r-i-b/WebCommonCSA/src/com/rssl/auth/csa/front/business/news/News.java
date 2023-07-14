package com.rssl.auth.csa.front.business.news;

import com.rssl.phizic.business.news.Important;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.io.Serializable;

/**
 * новость отображаемая на странице входа в систему
 * @author basharin
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class News implements Serializable
{
	private Long     id;
	private String   title;                           //Заголовок новости
	private Calendar newsDate;                        //Дата и время новости
	private String   text;                            //Текст новости
	private String   shortText;                       //Краткий текст
	private Important important;                      //Степень важности

	/**
	 * @return Степень важности
	 */
	public Important getImportant()
	{
		return important;
	}

	/**
	 * @param important Степень важности
	 */
	public void setImportant(Important important)
	{
		this.important = important;
	}

	/**
	 * @return идентификатор
	 */
	public Long getId()
    {
        return id;
    }

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
    {
        this.id = id;
    }

	/**
	 * @return Заголовок новости
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title Заголовок новости
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return Дата и время новости
	 */
	public Calendar getNewsDate()
	{
		if (newsDate == null)
			newsDate = DateHelper.getCurrentDate();
		return newsDate;
	}

	/**
	 * @return Краткий текст
	 */
	public String getShortText()
	{
		return shortText;
	}

	/**
	 * @param shortText Краткий текст
	 */
	public void setShortText(String shortText)
	{
		this.shortText = shortText;
	}

	/**
	 * @param newDate Дата и время новости
	 */
	public void setNewsDate(Calendar newDate)
	{
		this.newsDate = newDate;
	}

	/**
	 * @return Текст новости
	 */
	public String getText ()
	{
		return text;
	}

	/**
	 * @param text Текст новости
	 */
	public void setText (String text)
	{
		this.text = text;
	}

	/**
	 * @return форматированная дата
	 */
	public String getNewsDate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", newsDate);
	}
}
