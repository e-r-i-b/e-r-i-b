package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityService;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Сорс для получения объектов учета
 * @author saharnova
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */

public class AccountingEntityListSource implements EntityListSource
{
	private AccountingEntityService accountingEntityService = new AccountingEntityService();

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityList entityList = getEntityList();
		return new StreamSource(new StringReader(entityList.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			EntityList entityList = getEntityList();
			return XmlHelper.parse(entityList.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityList getEntityList() throws BusinessException
	{
		EntityList entityList = new EntityList();

		List<AccountingEntity> list = getAccountingEntities();
		for (AccountingEntity accountingEntity: list)
		{
			Entity entity = new Entity("group:" + String.valueOf(accountingEntity.getId()), null);
			entity.addField(new Field("id", String.valueOf(accountingEntity.getId())));
			entity.addField(new Field("name", accountingEntity.getName()));
			entity.addField(new Field("type", accountingEntity.getType().getCode()));
			entityList.addEntity(entity);
		}

		return entityList;
	}

	private List <AccountingEntity> getAccountingEntities() throws BusinessException
	{
		long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		return accountingEntityService.findByLogin(loginId);
	}
}
