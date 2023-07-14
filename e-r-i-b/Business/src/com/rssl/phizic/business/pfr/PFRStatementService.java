package com.rssl.phizic.business.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.payments.forms.meta.XSLTBean;
import com.rssl.phizic.business.payments.forms.meta.XSLTService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.io.IOUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

/**
 * @author Dorzhinov
 * @ created 03.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class PFRStatementService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final XSLTService xsltService = new XSLTService();

	/**
	 * Добавление выписки
	 * @param statement выписка
	 * @throws BusinessException
	 */
	public void add(final PFRStatement statement) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(null).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					statement.setValid(false);
					try
					{
						XSLTBean xsltBean = xsltService.getBeanByName(XSLTService.PFR_STATEMENT_NAME);
						if (xsltBean != null)
							messageValidate(xsltBean.getXsd(), statement);
						else
							log.error("В таблице нет xslt шаблона для преобразования выписки из ПФР");
					}
					catch (UnsupportedEncodingException e)
					{
						log.error("Не удалось сконвертировать xslt-шаблон." + e.getCause().getMessage());
					}
					catch (ValidateException e)
					{
						log.error("Ошибка валидации выписки из ПФР: claimId=" + statement.getClaimId() +
							" id=" + statement.getId() + ". " + e.getCause().getMessage());
					}
					simpleService.addOrUpdate(statement, null);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void messageValidate(String xsd, PFRStatement statement) throws UnsupportedEncodingException, SAXException, ValidateException
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
			Source source = new StreamSource(inputStream);
			Schema schema = XmlHelper.newSchema(source);
			XSDSchemeValidator.validate(schema, statement.getStatementXml());
			statement.setValid(true);
		}
		finally
		{
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * Удаление выписки
	 * @param statement выписка
	 * @throws BusinessException
	 */
	public void remove(final PFRStatement statement) throws BusinessException
	{
		simpleService.remove(statement, null);
	}

	/**
	 * Поиск выписки по id
	 * @param statementId идентификатор выписки
	 * @return Statement или null
	 * @throws BusinessException
	 */
	public PFRStatement findById(Long statementId) throws BusinessException
	{
		return simpleService.findById(PFRStatement.class, statementId, null);
	}

	/**
	 * Поиск выписки по id платежа
	 * @param claimId идентификатор платежа
	 * @return Statement или null
	 * @throws BusinessException
	 */
	public PFRStatement findByClaimId(final Long claimId) throws BusinessException
	{
		if(claimId == null)
			throw new IllegalArgumentException("Параметр 'claimId' не может быть null");

		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<PFRStatement>()
			{
				public PFRStatement run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pfr.PFRStatementService.findByClaimId");
					query.setParameter("claimId", claimId);
					return (PFRStatement)query.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
