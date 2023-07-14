package com.rssl.phizic.business.persons;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.*;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.AssociationType;
import org.hibernate.type.Type;

import java.util.*;

/**
 * Создать набор запросов для удаления сущности (опционально) и всех связанных сущностей (рекурсивно),
 * т.е. которые имеют ссылку на удаляемую.
 * @author Kidyaev
 * @ created 27.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class DeleteEntityQueryBuilder
{
    private List<Query>                queries;
    private Map<String, ClassMetadata> entities;
    private String                     rootEntityName;
    private boolean                    deleteMainEntity;
    private Long                       id;
	private static final Set<String>   SKIPPED_ENTITIES = new HashSet<String>();     //BUG048316: Исключение при удалении клиента

	static
	{
		SKIPPED_ENTITIES.add("com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCode");
	}

    public DeleteEntityQueryBuilder(Class entityClass)
    {
        this(entityClass, true);
    }

    /**
     *
     * @param entityClass      класс сущности
     * @param deleteMainEntity флаг, удалять (true) или нет (false) сущность, в случае false будут
     * удалены только те сущности, которые ссылаются (не только явно) на entityClass
     */
    public DeleteEntityQueryBuilder(Class entityClass, boolean deleteMainEntity)
    {
        this.rootEntityName   = entityClass.getName();
        this.deleteMainEntity = deleteMainEntity;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<Query> build() throws Exception
    {
        return HibernateExecutor.getInstance().execute(new HibernateAction<List<org.hibernate.Query>>()
        {
            public List<org.hibernate.Query> run(Session session) throws Exception
            {
                //noinspection unchecked
                entities = new HashMap<String, ClassMetadata>( session.getSessionFactory().getAllClassMetadata() );
                List<Path> paths = findAllPaths(session);
                sortPath(paths);

                if ( queries == null )
                    queries = buildQueries(session, paths);

                // парвметр id передаем в запросы
                for ( Query query : queries )
                {
                    query.setParameter("id", id);
                }

                return queries;
            }
        });
    }

    private List<Path> findAllPaths(Session session) throws Exception
    {
        List<Path> allPaths4AllEntities = new ArrayList<Path>();

        for ( String entityName : entities.keySet() )
        {
            List<Path> paths4OneEntity = new ArrayList<Path>();

            // System.out.println(entityName + ": ");

            if (SKIPPED_ENTITIES.contains(entityName) || memberOfRootEntityHierarchy(entityName))
            {
                continue;
            }

            if ( findPaths4Entity(session, entityName, paths4OneEntity) )
            {
                for ( Path path : paths4OneEntity )
                {
                    path.setFirstEntityName(entityName);
                    allPaths4AllEntities.add(path);
                }
            }
        }

        if (deleteMainEntity)
        {
            Path path = new Path();
            path.setFirstEntityName(rootEntityName);
            allPaths4AllEntities.add(path);
        }

        return allPaths4AllEntities;
    }

    private boolean findPaths4Entity(Session session, String entityName, List<Path> paths)
            throws Exception
    {
        ClassMetadata classMetadata = entities.get(entityName);
        boolean       result        = false;

        if ( classMetadata == null )
        {
            throw new Exception( "Сушность [" + entityName + "] не найдена в Hibernate-mapping" );
        }
        else if ( memberOfRootEntityHierarchy(entityName) )
        {
            // Рассматриваемая сущность входит в иерархию корневой удаляемой сущности
            paths.add(new Path());
            result = true;
        }
        else
        {
            // Рассматриваемая сущность не входит в иерархию корневой удаляемой сущности
            String[] propertyNames = classMetadata.getPropertyNames();

            for ( String propertyName : propertyNames )
            {
                Type type = classMetadata.getPropertyType(propertyName);

                if ( type.isAssociationType() && !type.isCollectionType() )
                {
                    AssociationType           associationType        = (AssociationType) type;
                    SessionFactoryImplementor factoryImplementor     = (SessionFactoryImplementor) session.getSessionFactory();
                    String                    associatedEntityName   = associationType.getAssociatedEntityName(factoryImplementor);
                    List<Path>                pathsFromCurrentEntity = new LinkedList<Path>();

                   // проверка на разные имена вставлена, чтобы не допустить зацикливания, как в случае с Department, где есть поле parent
	                // того же типа
                    if (!associatedEntityName.equals(entityName) && findPaths4Entity(session, associatedEntityName, pathsFromCurrentEntity) )
                    {
                        addEntityToPaths(propertyName, associatedEntityName, pathsFromCurrentEntity);
                        paths.addAll(pathsFromCurrentEntity);
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    private boolean memberOfRootEntityHierarchy(String entityName)
    {
        AbstractEntityPersister rootEntityClassMetadata = (AbstractEntityPersister) entities.get(rootEntityName);
        AbstractEntityPersister entityClassMetadata     = (AbstractEntityPersister) entities.get(entityName);
        boolean                 result                  = false;

        if (rootEntityName.equals(entityName))
        {
            result = true;
        }

        // является ли entityName подклассом сущности rootEntityName
        if (!result && rootEntityClassMetadata.hasSubclasses())
        {
            result = rootEntityClassMetadata.isSubclassEntityName(entityName);
        }

        // является ли rootEntityName подклассом сущности entityName
        if (!result && entityClassMetadata.isInherited())
        {
            result = entityClassMetadata.isSubclassEntityName(rootEntityName);
        }

        return result;
    }

    private void addEntityToPaths(String propertyName, String entityName, List<Path> paths)
    {
        for ( Path path : paths )
        {
            path.addEntry(new PathEntry(propertyName, entityName));
        }
    }

    private void sortPath(List<Path> paths)
    {
        Collections.sort(paths, new Comparator<Path>()
        {
            public int compare(Path o1, Path o2)
            {
                // сортируем в порядке убывания
                return o2.getSize() - o1.getSize();
            }
        });
    }

    private List<org.hibernate.Query> buildQueries(Session session, List<Path> paths) throws Exception
    {
        List<Query> queries = new ArrayList<org.hibernate.Query>();

        for ( Path path : paths )
        {
            String entityName  = path.getFirstEntityName();
            String whereClause = "";

            if ( path.getEntries().size() == 0 )
            {
                whereClause += "id = :id";
            }
            else
            {
                List<PathEntry> entries     = path.getEntries();
                String          entityAlias = "_this";
                PathEntry       firstEntry  = entries.get(0);

                whereClause += firstEntry.getPropertyName() + ".id " + " in (";
                whereClause += " select " + entityAlias + ".id" +
                               " from "   + firstEntry.getEntityName() + " " + entityAlias +
                               " where "  + entityAlias;

                for ( int i = 1; i < path.getEntries().size(); i++ )
                {
                    PathEntry entry = path.getEntries().get(i);
                    whereClause += "." + entry.getPropertyName();
                }

                whereClause += ".id = :id)";
            }

            String deleteHql = " delete " + entityName +
                               " where "  + whereClause;

            Query query = session.createQuery(deleteHql);
            queries.add(query);

            // System.out.println(deleteHql);
        }

        return queries;
    }

    private class PathEntry
    {
        private String propertyName;
        private String entityName;

        public PathEntry(String propertyName, String entityName)
        {
            this.propertyName = propertyName;
            this.entityName   = entityName;
        }

        public String getPropertyName()
        {
            return propertyName;
        }

        public String getEntityName()
        {
            return entityName;
        }
    }

    private class Path
    {
        private String                firstEntityName;
        private LinkedList<PathEntry> entries = new LinkedList<PathEntry>();

        public String getFirstEntityName()
        {
            return firstEntityName;
        }

        public void setFirstEntityName(String firstEntityName)
        {
            this.firstEntityName = firstEntityName;
        }

        public void addEntry(PathEntry entry)
        {
            entries.addFirst(entry);
        }

        public List<PathEntry> getEntries()
        {
            return entries;
        }

        public int getSize()
        {
            return entries.size();
        }
    }
}
