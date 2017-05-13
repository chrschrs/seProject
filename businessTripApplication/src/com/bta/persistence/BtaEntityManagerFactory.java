package com.bta.persistence;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.config.PersistenceUnitProperties;

public class BtaEntityManagerFactory {
	
	private static final Logger LOGGER = LogManager.getLogger(BtaEntityManagerFactory.class);
	private static BtaEntityManagerFactory factory;
	private EntityManagerFactory emf;
	
	private BtaEntityManagerFactory() throws Exception {
		
		LOGGER.info("Starting application...");
		Connection connection = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds;
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/testdb");
			connection = ds.getConnection();
			
			LOGGER.info("datasource connection successful...");
			
			Map<Object, Object> properties = new HashMap<Object, Object>();
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			emf = Persistence.createEntityManagerFactory("bta", properties);
			
			connection.close();
			LOGGER.info("closing datasource connection...");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

	public static BtaEntityManagerFactory getInstance() throws Exception{
		if (factory == null){
			factory = new BtaEntityManagerFactory();
		}
		return factory;
	}
	
	public EntityManager createManager(){
		return emf.createEntityManager();
	}
}
