package dbexam;

import javax.persistence.*;
import java.io.IOException;
import java.util.Properties;

public class DBManager {

    private EntityManager entityManager;

    private static DBManager instance;
	
    private DBManager() {
		//Initialize your entityManager here.
        Properties props = new Properties();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "jpa_exam", props);
        entityManager = emf.createEntityManager();

        //Get Data
        try {
            String Data = HousingPricesLoader.getJSONDataUsingURL(HousingPricesLoader.URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static DBManager getInstance() {
        if(null == instance) {
            instance = new DBManager();
        }
        return instance;
    }

    public void destroy() {
        //Clean up your entityManager here.
        entityManager.close();
    }
}
