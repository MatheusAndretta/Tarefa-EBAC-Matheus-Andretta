package Mod30.src.java.main.dao.services.generico;

import java.io.Serializable;
import java.util.Collection;

import Mod30.src.java.main.dao.Persistente;
import Mod30.src.java.main.dao.exceptions.DAOException;
import Mod30.src.java.main.dao.exceptions.MaisDeUmRegistroException;
import Mod30.src.java.main.dao.exceptions.TableException;
import Mod30.src.java.main.dao.exceptions.TipoChaveNaoEncontradaException;
import Mod30.src.java.main.dao.generico.IGenericDAO;

public abstract class GenericService<T extends Persistente, E extends Serializable> 
	implements IGenericService<T, E> {
	
	protected IGenericDAO<T,E> dao;
	
	public GenericService(IGenericDAO<T,E> dao) {
		this.dao = dao;
	}

	@Override
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
		return this.dao.cadastrar(entity);
	}

	@Override
	public void excluir(E valor) throws DAOException {
		this.dao.excluir(valor);
	}

	@Override
	public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
		this.dao.alterar(entity);
	}

	@Override
	public T consultar(E valor) throws DAOException {
		try {
			return this.dao.consultar(valor);
		} catch (MaisDeUmRegistroException | TableException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<T> buscarTodos() throws DAOException {
		return this.dao.buscarTodos();
	}

}
