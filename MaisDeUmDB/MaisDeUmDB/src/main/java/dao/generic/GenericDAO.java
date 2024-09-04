package main.java.dao.generic;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



import main.java.dao.Persistente;
import main.java.exceptions.DAOException;
import main.java.exceptions.TipoChaveNaoEncontradaException;

public class GenericDAO<T extends Persistente,E extends Serializable> implements IGenericDAO<T,E> {

    private static final String PERSISTENCE_UNIT_NAME = "JPA";

    protected EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    private Class<T> persistenteClass;

    private String persistenteUnitName;

    public GenericDAO(Class<T> persistenteClass,String persistenteUnitName){
        this.persistenteClass = persistenteClass;
        this.persistenteUnitName = persistenteUnitName;
    }

    @Override
    public Collection<T> buscarTodos() {
       openConnection();
        List<T> lista = entityManager.createQuery(getSelectSql(), this.persistenteClass).getResultList();
        closeConnection();
        return lista;
        
    }

    @Override
    public void excluir(T entity) throws DAOException {
        openConnection();
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        closeConnection();
    }

    

    @Override
    public T alterar(T entity) {
        openConnection();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    @Override
    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        openConnection();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;

    }

    

    @Override
    public T consultar(E valor) {
        openConnection();
        T entity = entityManager.find(persistenteClass, valor);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    private String getSelectSql(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT obj FROM ");
        sb.append(this.persistenteClass.getSimpleName());
        sb.append(" obj");
        return sb.toString();
    }

    protected void openConnection() {
       entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName());
       entityManager = entityManagerFactory.createEntityManager();
       entityManager.getTransaction().begin();
    }

    private String getPersistenceUnitName() {
       if (persistenteUnitName !=null && !"".equals(persistenteUnitName))  {
        return persistenteUnitName;
       } else{
        return PERSISTENCE_UNIT_NAME;
       }
    }

    protected void closeConnection() {
		entityManager.close();
		entityManagerFactory.close();
	}
}