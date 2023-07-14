package com.rssl.phizic.business.news;

import com.rssl.phizic.auth.CommonLogin;

import java.util.Calendar;

/**
 * Оповещения о новостях банка
 * @author lukina
 * @ created 31.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class NewsDistribution
{
	private Long id;
	private CommonLogin login;              //логин сотрудника, выполнившего рассылку
	private Calendar date;                  //дата создания
	private NewsDistributionType type;      // событие системы или событие на странице входа
	private Long newsId;                    // Id события
	private NewsDistributionsState state;   //статус оповещения
	private int mailCount;                  //количество писем в пачке
	private Long    timeout;                //интервал отправки пачек в секундах
	private String title;                   //заголовок
	private String text;                    //текст
	private String terbanks;                //номера тербанков, для которых создана рассылка

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public NewsDistributionType getType()
	{
		return type;
	}

	public void setType(NewsDistributionType type)
	{
		this.type = type;
	}

	public Long getNewsId()
	{
		return newsId;
	}

	public void setNewsId(Long newsId)
	{
		this.newsId = newsId;
	}

	public int getMailCount()
	{
		return mailCount;
	}

	public void setMailCount(int mailCount)
	{
		this.mailCount = mailCount;
	}

	public Long getTimeout()
	{
		return timeout;
	}

	public void setTimeout(Long timeout)
	{
		this.timeout = timeout;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public NewsDistributionsState getState()
	{
		return state;
	}

	public void setState(NewsDistributionsState state)
	{
		this.state = state;
	}

	public String getTerbanks()
	{
		return terbanks;
	}

	public void setTerbanks(String terbanks)
	{
		this.terbanks = terbanks;
	}
}
