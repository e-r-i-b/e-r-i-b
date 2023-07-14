package com.rssl.phizicgate.rsretailV6r4.stopList;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.stoplist.ClientStopListState;
import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 26.01.2009
 * @ $Author$
 * @ $Revision$
 */

public class StopListServiceImpl extends AbstractService implements StopListService
{
	private static Map<ClientDocumentType, Integer> docTypesMap = new HashMap<ClientDocumentType, Integer>();
	static{
		docTypesMap.put(ClientDocumentType.REGULAR_PASSPORT_RF,0);//Паспорт гражданина РФ
		docTypesMap.put(ClientDocumentType.FOREIGN_PASSPORT_RF,6);//Загранпаспорт гражданина РФ
		docTypesMap.put(ClientDocumentType.IMMIGRANT_REGISTRATION,12);//Свидетельство о регистрации ходатайства иммигранта о признании его беженцем
		//TODO docTypesMap.put(ClientDocumentType.MIGRATORY_CARD,);
		docTypesMap.put(ClientDocumentType.MILITARY_IDCARD,2);//Военный билет солдата (матроса, сержанта, старшины)
		//TODO docTypesMap.put(ClientDocumentType.PENSION_CERTIFICATE,1);//Пенсионное удостоверение
		docTypesMap.put(ClientDocumentType.REFUGEE_IDENTITY,13);//Удостоверение беженца в РФ
		docTypesMap.put(ClientDocumentType.RESIDENTIAL_PERMIT_RF,5);//Вид на жительство
		docTypesMap.put(ClientDocumentType.SEAMEN_PASSPORT,15);//Паспорт моряка
		docTypesMap.put(ClientDocumentType.OTHER, 18); //Иной документ
	}
	public StopListServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	private int getDocType(ClientDocumentType documentType) throws GateLogicException
	{
		Integer id = docTypesMap.get(documentType);
		if (id == null){
			throw new GateLogicException("неподдерживаемый тип документа "+documentType);
		}
		return id;
	}

	private ClientStopListState getResult(final DetachedCriteria criteria) throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<ClientStopListState>()
			{
				public ClientStopListState run(Session session) throws Exception
				{
					Criteria executableCriteria = criteria.getExecutableCriteria(session);
					return getClientStopListState(executableCriteria.list());
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public ClientStopListState check(String INN, String firstName, String middleName, String lastName, Office office) throws GateLogicException, GateException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(StopListImpl.class);
		if (INN == null){
			throw new GateLogicException("Не задан ИНН");
		}
		if (lastName == null || firstName == null){
			throw new GateLogicException("Не заданы ФИО");
		}
		criteria.add(Expression.eq("inn", INN.toUpperCase()));
		criteria.add(Expression.eq("family", lastName.toUpperCase()));
		criteria.add(Expression.eq("name", firstName.toUpperCase()));

		if (middleName!= null){
			criteria.add(Expression.eq("patrName", middleName.toUpperCase()));
		}

		return getResult(criteria);
	}

	public ClientStopListState check(String firstName, String middleName, String lastName, Office office) throws GateLogicException, GateException
	{
		if (lastName == null || firstName == null){
			throw new GateLogicException("Не заданы ФИО");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(StopListImpl.class);
		criteria.add(Expression.eq("family", lastName.toUpperCase()));
		criteria.add(Expression.eq("name", firstName.toUpperCase()));
		if (middleName!= null){
			criteria.add(Expression.eq("patrName", middleName.toUpperCase()));
		}
		criteria.add(Expression.gt("isTer", 0));

		return getResult(criteria);
	}

	public ClientStopListState check(final String documentSeries, final String documentNumber,
	                                 final ClientDocumentType documentType, Office office) throws GateLogicException, GateException
	{
		if (documentSeries == null || documentNumber == null ||documentType == null){
			throw new GateLogicException("Не заданы данные документа");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(StopListImpl.class);
		criteria.add(Expression.eq("docType", getDocType(documentType)));

		criteria.add(Expression.eq("series", documentSeries.toUpperCase()));
		criteria.add(Expression.eq("number", documentNumber.toUpperCase()));

		return getResult(criteria);
	}

	public ClientStopListState check(final String firstName, final String middleName, final String lastName, String documentSeries, String documentNumber,
	                                 final Calendar dateOfBirth, Office office) throws GateLogicException, GateException
	{
		if (lastName == null || firstName == null){
			throw new GateLogicException("Не заданы ФИО");
		}
		if (dateOfBirth== null){
			throw new GateLogicException("Не задана дата рождения");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(StopListImpl.class);
		criteria.add(Expression.eq("family", lastName.toUpperCase()));
		criteria.add(Expression.eq("name", firstName.toUpperCase()));

		if (middleName!= null){
			criteria.add(Expression.eq("patrName", middleName.toUpperCase()));
		}
		criteria.add(Expression.eq("birthDate", dateOfBirth));

		return getResult(criteria);
	}

	private ClientStopListState getClientStopListState(List<StopListImpl> clients) throws GateException
	{
		if (clients.size()==1)
		{
			JonImpl jonClient = new JonImpl();
			updateJonClient(jonClient, clients.get(0));
			add(jonClient);
			return getState(clients.get(0));
		}
		if (clients.size()>1)
		{
			JonImpl jonClient = new JonImpl();
			StopListImpl client = getStopListClient(clients);
			updateJonClient(jonClient, client);
			add(jonClient);
			return getState(client);
		}
		return ClientStopListState.trusted;
	}

	private StopListImpl getStopListClient(List<StopListImpl> clients)
	{
		for (StopListImpl client: clients)
		{
			if (client.getIsTer()>0)
			{
				return client;
			}
		}
		return clients.get(0);
	}

	private ClientStopListState getState(StopListImpl client)
	{
		if (client.getSlType()==1)
		{
			return ClientStopListState.blocked;
		}
		return ClientStopListState.shady;
	}

	private void updateJonClient(JonImpl jonClient, StopListImpl client)  //TODO добавить заполнение остальных полей
	{
		jonClient.setInn(client.getInn());
		jonClient.setOperName(client.getName() + " " + client.getPatrName() + " " + client.getFamily());
		jonClient.setRecordId(client.getId());
		jonClient.setSlCliId(client.getCliId());
	}

	private void add(final JonImpl jonClient) throws GateException
	{
		try
		{
		    RSRetailV6r4Executor.getInstance().execute(new HibernateAction<JonImpl>()
		    {
		        public JonImpl run(Session session) throws Exception
		        {
		            session.save(jonClient);
			        return jonClient;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}
}
