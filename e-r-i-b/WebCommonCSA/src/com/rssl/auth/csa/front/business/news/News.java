package com.rssl.auth.csa.front.business.news;

import com.rssl.phizic.business.news.Important;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.io.Serializable;

/**
 * ������� ������������ �� �������� ����� � �������
 * @author basharin
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class News implements Serializable
{
	private Long     id;
	private String   title;                           //��������� �������
	private Calendar newsDate;                        //���� � ����� �������
	private String   text;                            //����� �������
	private String   shortText;                       //������� �����
	private Important important;                      //������� ��������

	/**
	 * @return ������� ��������
	 */
	public Important getImportant()
	{
		return important;
	}

	/**
	 * @param important ������� ��������
	 */
	public void setImportant(Important important)
	{
		this.important = important;
	}

	/**
	 * @return �������������
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

	/**
	 * @return ��������� �������
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title ��������� �������
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return ���� � ����� �������
	 */
	public Calendar getNewsDate()
	{
		if (newsDate == null)
			newsDate = DateHelper.getCurrentDate();
		return newsDate;
	}

	/**
	 * @return ������� �����
	 */
	public String getShortText()
	{
		return shortText;
	}

	/**
	 * @param shortText ������� �����
	 */
	public void setShortText(String shortText)
	{
		this.shortText = shortText;
	}

	/**
	 * @param newDate ���� � ����� �������
	 */
	public void setNewsDate(Calendar newDate)
	{
		this.newsDate = newDate;
	}

	/**
	 * @return ����� �������
	 */
	public String getText ()
	{
		return text;
	}

	/**
	 * @param text ����� �������
	 */
	public void setText (String text)
	{
		this.text = text;
	}

	/**
	 * @return ��������������� ����
	 */
	public String getNewsDate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", newsDate);
	}
}
