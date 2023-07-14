package com.rssl.phizic.business.persons;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * Удалить все сущности, которые ссылаются на CommonLogin.
 * Сам CommonLogin не удаляется!
 * @author Kidyaev
 * @ created 02.10.2006
 * @ $Author$
 * @ $Revision$
 */
public class DeleteCommonLoginQueryBuilder
{
    private static final String COMMON_LOGIN_ENTITY_NAME = "com.rssl.phizic.auth.CommonLogin";

    private DeleteEntityQueryBuilder deleteEntityQueryBuilder;
    private Long                     loginId;

    public DeleteCommonLoginQueryBuilder() throws ClassNotFoundException
    {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class       commonLoginClass   = contextClassLoader.loadClass(COMMON_LOGIN_ENTITY_NAME);
        this.deleteEntityQueryBuilder  = new DeleteEntityQueryBuilder(commonLoginClass, false);
    }

    public void setLoginId(Long loginId)
    {
        this.loginId = loginId;
        deleteEntityQueryBuilder.setId(loginId);
    }

    public List<Query> build() throws Exception
    {
        return HibernateExecutor.getInstance().execute(new HibernateAction<List<Query>>()
        {
            public List<org.hibernate.Query> run(Session session) throws Exception
            {
                List<Query> queries = createSpecificQueriesBefore(session);
                queries.addAll(deleteEntityQueryBuilder.build());
                queries.addAll(createSpecificQueriesAfter(session));

                return queries;
            }
        });
    }

    private List<Query> createSpecificQueriesBefore(Session session)
    {
        List<Query> queries = new ArrayList<Query>();

        // Удалить ограничения
        Query query =  session.createQuery("delete com.rssl.phizic.business.operations.restrictions.RestrictionData restriction\n" +
                                           "where  restriction.loginId = :loginId");
        query.setParameter("loginId", loginId);
        queries.add(query);

        return queries;
    }


    private Collection<? extends Query> createSpecificQueriesAfter(Session session)
    {
        List<Query> queries = new ArrayList<Query>();

        // Удалить персональные схемы для этого логина
        Query query = session.createQuery("delete com.rssl.phizic.business.schemes.PersonalAccessScheme personalAccessScheme\n" +
                                          "where  personalAccessScheme in \n" +
                                          "(\n" +
                                          "  select schemeOwn.accessScheme\n" +
                                          "  from com.rssl.phizic.business.resources.own.SchemeOwn schemeOwn\n" +
                                          "  where schemeOwn.loginId = :loginId\n" +
                                          ")");
        query.setParameter("loginId", loginId);
        queries.add(query);

        return queries;
    }
}
