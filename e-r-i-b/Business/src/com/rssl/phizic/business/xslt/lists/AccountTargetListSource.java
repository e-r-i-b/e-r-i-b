package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Соурс отвечающий за построение списка целей клиента
 * @author mihaylov
 * @ created 04.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountTargetListSource implements EntityListSource
{
	private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
	private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	private static final AccountTargetService targetService = new AccountTargetService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

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

	private EntityList getEntityList()
	{
		EntityList entityList = new EntityList();
		try
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
			List<AccountTarget> accountTargetList = targetService.findTargetsWithAccounts(login);
			for(AccountTarget target : accountTargetList)
			{
				Entity entity = new Entity(target.getId().toString(),null);
				entity.addField(new Field("name",target.getName()));
				entity.addField(new Field("comment",target.getNameComment()));
				entity.addField(new Field("plannedDate", dateFormat.format(target.getPlannedDate().getTime())));
				entity.addField(new Field("amount",target.getAmount().getDecimal().toString()));
				entity.addField(new Field("currency",target.getAmount().getCurrency().getCode()));
				entity.addField(new Field("linkId","account:" + target.getAccountLink().getId()));
				entityList.addEntity(entity);
			}
		}
		catch (Exception e)
		{
			log.error("Не удалось построить список целей клиента",e);
			return new EntityList();
		}
		return entityList;
	}
}
