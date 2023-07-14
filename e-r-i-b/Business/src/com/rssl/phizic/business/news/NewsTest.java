package com.rssl.phizic.business.news;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.test.BusinessTestCaseBase;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * User: Zhuravleva
 * Date: 05.12.2006
 * Time: 18:08:48
 */
@SuppressWarnings({"ALL"})
public class NewsTest extends BusinessTestCaseBase
{
	public void testNews () throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
		  public Void run(Session session) throws Exception
		  {
			  News news = new News();
			  try
				{
					news.setNewsDate(Calendar.getInstance());
	                news.setText("Test000");
	                news.setTitle("Test000");
					news.setShortText("Test000");
	                news.setImportant(Important.HIGH);

					session.save(news);
					session.flush();

					session.clear();

					Query query = session.createQuery("select news from com.rssl.phizic.business.news.News news " +
                                                      "where news.title='Test000'");

					News news2 = (News) query.uniqueResult();

					assertNotNull(news2);
				}
				finally
				{
					try
					{
						if(news.getId() != null)
						{
							session.clear();
							session.delete(news);
						}

					}
					catch(Throwable t)
					{
					}
				}
				return null;
		  }	
		});
	}
}
