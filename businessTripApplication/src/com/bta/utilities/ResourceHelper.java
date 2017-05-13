package com.bta.utilities;

import javax.persistence.EntityManager;

public class ResourceHelper {
	
	/**
	 * 
	 * methods to surpress exceptions for closeable resources.
	 */
	
	public static void close(EntityManager em){
		try {
			em.close();
		} catch (Exception e) {
		}
		
	}
}
