package com.antsirs.core.spring.daosupport;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.orm.jpa.JpaTemplate;

import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
import com.google.appengine.api.datastore.Key;

public class DaoTemplate extends JpaTemplate {

	protected static EntityManagerFactory entityManagerFactory;
	
	protected static EntityManager entityManager;

	public EntityManager getEntityManager() {		
		entityManager = getDaoTemplate().getEntityManagerFactory().createEntityManager();
		return entityManager;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		DaoTemplate.entityManagerFactory = entityManagerFactory;
	}

	public static JpaTemplate getDaoTemplate() {
		return new JpaTemplate(DaoTemplate.entityManagerFactory);
	}

	public long count(Class entityClass) {
		return Long.parseLong(getDaoTemplate()
				.getEntityManager()
				.createQuery(
						"select count(e) from " + entityClass.getName() + " e")
				.getSingleResult().toString());
	}

	public long count(Class entityClazz, String query, Object... params) {
		return Long.parseLong(bindParameters(
				entityManagerFactory.createEntityManager().createQuery(
						createCountQuery(entityClazz, query, params)), params)
				.getSingleResult().toString());
	}

	public List findAll(Class entityClazz) {
		return entityManagerFactory.createEntityManager()
				.createQuery("select e from " + entityClazz.getName() + " e")
				.getResultList();
	}

	public <T> T findById(Class entityClass, Object id) {
		return (T) entityManagerFactory.createEntityManager().find(entityClass, id);
	}

	public List findByParam(Class entityClass, String query, Object... params) {
		Query q = entityManagerFactory.createEntityManager().createQuery(
				createFindByQuery(entityClass, query, params));
		return bindParameters(q, params).getResultList();

	}
	
	/**
	 * 分页查询
	 * @param entityClass
	 * @param query
	 * @param rows
	 * @param page
	 * @param max
	 * @param params
	 * @return
	 */
	public List findWithPagination(Class entityClass, Pagination pagination) {
		Query q = entityManagerFactory.createEntityManager().createQuery(
				createFindByQuery(entityClass, pagination.getQueryString(), pagination.getParamValues())).setMaxResults(pagination.getMaxResults()).setFirstResult(pagination.getFirstResult());
		return bindParameters(q, pagination.getParamValues()).getResultList();

	}

	public <T> T findOne(Class entityClass, String query, Object[] params) {
		Query q = entityManagerFactory.createEntityManager().createQuery(
				createFindByQuery(entityClass, query, params));
		List results = bindParameters(q, params).getResultList();
		if (results.size() == 0) {
			return null;
		}
		return (T)results.get(0);
	}

	public String createFindByQuery(Class entityClass, String query, Object... params) {
		if (query == null || query.trim().length() == 0) {
			return "select e from " + entityClass.getName() + " e";
		}
		if (query.matches("^by[A-Z].*$")) {
			return "select e from " + entityClass.getName() + " e where "	+ findByToJPQL(query);
		}
		if (query.trim().toLowerCase().startsWith("select ")) {
			return query;
		}
		if (query.trim().toLowerCase().startsWith("from ")) {
			return query;
		}
		if (query.trim().toLowerCase().startsWith("order by ")) {
			return "select e from " + entityClass.getName() + " e " + query;
		}
		if (query.trim().indexOf(" ") == -1 && query.trim().indexOf("=") == -1 && params != null && params.length == 1) {
			query += " = ?1";
		}

		if (query.trim().indexOf(" ") == -1 && query.trim().indexOf("=") == -1 && params == null) {
			query += " = null";
		}

		return "select e from " + entityClass.getName() + " e where " + query;
	}

	public String createDeleteQuery(Class entityClass, String query,
			Object... params) {
		if (query == null) {
			return "delete from " + entityClass.getName();
		}
		if (query.trim().toLowerCase().startsWith("delete ")) {
			return query;
		}
		if (query.trim().toLowerCase().startsWith("from ")) {
			return "delete " + query;
		}

		if (query.trim().indexOf(" ") == -1 && query.trim().indexOf("=") == -1
				&& params != null && params.length == 1) {
			query += " = ?1";
		}
		if (query.trim().indexOf(" ") == -1 && query.trim().indexOf("=") == -1
		&& params == null) {
			query += " = null";
		}
		return "delete from " + entityClass.getName() + " where " + query;

	}

	public String createCountQuery(Class entityClass, String query, Object... params) {
		if (query.trim().toLowerCase().startsWith("select ")) {
			return query;
		}
		if (query.matches("^by[A-Z].*$")) {
			return "select count(*) from " + entityClass.getName() + " where "
			+ findByToJPQL(query);
		}
		if (query.trim().toLowerCase().startsWith("from ")) {
			return "select count(*) " + query;
		}
		if (query.trim().toLowerCase().startsWith("order by ")) {
			return "select count(*) from " + entityClass.getName();
		}
		if (query.trim().indexOf(" ") == -1 && query.trim().indexOf("=") == -1
		&& params != null && params.length == 1) {
			query += " = ?1";
		}

		if (query.trim().indexOf(" ") == -1 && query.trim().indexOf("=") == -1
		&& params == null) {
			query += " = null";
		}

		if (query.trim().length() == 0) {
			return "select count(*) from " + entityClass.getName();
		}
		return "select count(e) from " + entityClass.getName() + " e where " + query;
	}

	@SuppressWarnings("unchecked")
	public Query bindParameters(Query q, Object... params) {
		if (params == null) {
			return q;
		}

		if (params.length == 1 && params[0] instanceof Map) {
			return bindParameters(q, (Map<String, Object>) params[0]);
		}

		for (int i = 0; i < params.length; i++) {
			q.setParameter(i + 1, params[i]);
		}
		return q;

	}
	
	public int delete(Class entityClass, String query, Object[] params) {
		Query q = entityManagerFactory.createEntityManager().createQuery(
				createDeleteQuery(entityClass, query, params));
		return bindParameters(q, params).executeUpdate();

	}

	public int deleteAll(Class entityClass) {
		Query q = entityManagerFactory.createEntityManager().createQuery(
				createDeleteQuery(entityClass, null));
		return bindParameters(q).executeUpdate();

	}

	public Query bindParameters(Query q, Map<String, Object> params) {
		if (params == null) {
			return q;
		}
		for (String key : params.keySet()) {
			q.setParameter(key, params.get(key));
		}
		return q;
	}

	public String findByToJPQL(String findBy) {
		findBy = findBy.substring(2);
		StringBuffer jpql = new StringBuffer();
		String[] parts = findBy.split("And");
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			if (part.endsWith("NotEqual")) {
				String prop = extractProp(part, "NotEqual");
				jpql.append(prop + " <> ?");
			} else if (part.endsWith("Equal")) {
				String prop = extractProp(part, "Equal");
				jpql.append(prop + " = ?");
			} else if (part.endsWith("IsNotNull")) {
				String prop = extractProp(part, "IsNotNull");
				jpql.append(prop + " is not null");
			} else if (part.endsWith("IsNull")) {
				String prop = extractProp(part, "IsNull");
				jpql.append(prop + " is null");
			} else if (part.endsWith("LessThan")) {
				String prop = extractProp(part, "LessThan");
				jpql.append(prop + " < ?");
			} else if (part.endsWith("LessThanEquals")) {
				String prop = extractProp(part, "LessThanEquals");
				jpql.append(prop + " <= ?");
			} else if (part.endsWith("GreaterThan")) {
				String prop = extractProp(part, "GreaterThan");
				jpql.append(prop + " > ?");
			} else if (part.endsWith("GreaterThanEquals")) {
				String prop = extractProp(part, "GreaterThanEquals");
				jpql.append(prop + " >= ?");
			} else if (part.endsWith("Between")) {
				String prop = extractProp(part, "Between");
				jpql.append(prop + " < ? AND " + prop + " > ?");
			} else if (part.endsWith("Like")) {
				String prop = extractProp(part, "Like");
				jpql.append("LOWER(" + prop + ") like ?");
			} else if (part.endsWith("Ilike")) {
				String prop = extractProp(part, "Ilike");
				jpql.append("LOWER(" + prop + ") like LOWER(?)");
			} else if (part.endsWith("Elike")) {
				String prop = extractProp(part, "Ilike");
				jpql.append(prop + " like LOWER(?)");
			} else {
				String prop = extractProp(part, "");
				jpql.append(prop + " = ?");
			}

			if (i < parts.length - 1) {
				jpql.append(" AND ");
			}
		}
		return jpql.toString();
	}

	protected static String extractProp(String part, String end) {
		String prop = part.substring(0, part.length() - end.length());
		prop = (prop.charAt(0) + "").toLowerCase() + prop.substring(1);
		return prop;
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param key
	 * @return
	 */
	public <T> Object findOne(Class<?> entityClass, T key){
		return getDaoTemplate().find(entityClass, key);
	}
	
	/**
	 * delete by class
	 * @param entityClass
	 */
	public void deleteOne(Class<?> entityClass){
		getDaoTemplate().remove(entityClass);
	}
	
	/**
	 * 
	 * @param entityClass
	 */
	public void createOne(Class<?> entityClass){
		getDaoTemplate().persist(entityClass);
	}
	
	public void updateOne(Class<?> entityClass) {
		getDaoTemplate().merge(entityClass);
	}
}
