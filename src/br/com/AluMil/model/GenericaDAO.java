/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author OCTI01
 * @param <Entity>
 */
public class GenericaDAO<Entity> {

    //Gerenciador de conexoes
    private static class Banco {

        private static final SessionFactory sessionFactory;

        static {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

    }
    //Para acessar base de dados
    protected Session session;
    protected Class<Entity> classe;
    protected Criteria criteria;
    //Retornos
    protected Entity entity;
    protected List<Entity> entitys;

    public GenericaDAO() {
        session = Banco.getSessionFactory().openSession();
        classe = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        criteria = session.createCriteria(classe);
        entitys = new ArrayList<>();
    }

    public void cadastrar(Entity entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        closeSession();
    }

    public void editar(Entity entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        closeSession();
    }

    public void excluir(Entity entity) {
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        closeSession();
    }

    public Entity pegarPorId(Serializable id) {
        entity = (Entity) session.get(classe, id);
        closeSession();
        return entity;
    }

    public List<Entity> pegarTodos() {
        entitys = criteria.list();
        closeSession();
        return entitys;
    }

    protected void closeSession() {
        session.flush();
        session.close();
    }
}
