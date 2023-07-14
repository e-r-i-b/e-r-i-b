package com.rssl.phizic.gate.templates.services;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

import static com.rssl.phizic.gate.hibernate.Constants.DB_INSTANCE_NAME;

/**
 * ������ ��� ������ � ���������
 *
 * @author khudyakov
 * @ created 02.03.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateDocumentService
{
	private static final String QUERY_PREFIX_NAME       = TemplateDocumentService.class.getName() + ".";
	private static final int BATCH_SIZE                 = 1000;

	private static final TemplateDocumentService INSTANCE = new TemplateDocumentService();
	private static final DatabaseServiceBase databaseServiceBase = new DatabaseServiceBase();


	/**
	 * @return ������� �������
	 */
	public static TemplateDocumentService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * ����� ������ �� �������������� �������
	 * @param id �������������
	 * @return ������
	 * @throws Exception
	 */
	public TemplateDocument findById(Long id) throws Exception
	{
		return databaseServiceBase.findById(TemplateDocument.class, id, LockMode.NONE, DB_INSTANCE_NAME);
	}

	/**
	 * ����� ��� ������� ������� (� ������ ������ �������)
	 *
	 * @param profile ������� �������
	 * @return ������ ��������
	 */
	public List<TemplateDocument> getAll(final GUID profile) throws Exception
	{
		return HibernateExecutor.getInstance(DB_INSTANCE_NAME).execute(new HibernateAction<List<TemplateDocument>>()
		{
			public List<TemplateDocument> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(QUERY_PREFIX_NAME + "findAllBySynonyms")
						.setParameter("surName",    profile.getSurName())
						.setParameter("firstName",  profile.getFirstName())
						.setParameter("patrName",   profile.getPatrName())
						.setParameter("passport",   profile.getPassport())
						.setParameter("birthDay",   profile.getBirthDay())
						.setParameterList("tb",     ConfigFactory.getConfig(TBSynonymsDictionary.class).getTBSynonyms(profile.getTb()));

				//noinspection unchecked
				return query.list();
			}
		});
	}

	/**
	 * �������� ������ ��������
	 * @param templates ������ ��������
	 */
	public void addOrUpdate(final List<? extends GateTemplate> templates) throws Exception
	{
		HibernateExecutor.getInstance(DB_INSTANCE_NAME).execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				int count = 0;
				for (GateTemplate template : templates)
				{
					session.saveOrUpdate(template);
					count++;

					//���� � ������ ���������� �������� ���������� ����� ������,
					//���������� �� � ��
					if (count >= BATCH_SIZE)
					{
						session.flush();
						session.clear();
						count = 0;
					}
				}

				return null;
			}
		});
	}

	/**
	 * ������� ������ ��������
	 * @param templates ������ ��������
	 */
	public void remove(List<? extends GateTemplate> templates) throws Exception
	{
		databaseServiceBase.deleteAll(templates, DB_INSTANCE_NAME);
	}
}
