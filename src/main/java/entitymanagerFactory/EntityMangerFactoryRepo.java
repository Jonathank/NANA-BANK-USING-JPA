package entitymanagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityMangerFactoryRepo{
	
	 private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NanaBank_PU");

	    public static EntityManager getEntityManager() {
	        return entityManagerFactory.createEntityManager();
	    }

	    public static void shutdown() {
	    	if (entityManagerFactory != null) {
	            entityManagerFactory.close();
	        }
	    }

}
