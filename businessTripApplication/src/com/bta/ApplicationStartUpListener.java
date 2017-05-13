package com.bta;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bta.persistence.BtaEntityManagerFactory;
import com.bta.utilities.ResourceHelper;

@WebListener
public class ApplicationStartUpListener implements ServletContextListener {
	
	private static final Logger LOGGER = LogManager.getLogger(ApplicationStartUpListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		EntityManager entityManager = null;
		
		try {
			BtaEntityManagerFactory factory = BtaEntityManagerFactory.getInstance();
			entityManager = factory.createManager();
			LOGGER.info("Creating entityManager successful...");
			testInsertIntoDB(entityManager);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			LOGGER.error("Error...");
		} finally {
			ResourceHelper.close(entityManager);
			LOGGER.info("Closing entityManager successful...");
		}
	}
	
	private void testInsertIntoDB(EntityManager entityManager) throws Exception{
		LOGGER.info("Testing insert into DB successful...");
	}

}
