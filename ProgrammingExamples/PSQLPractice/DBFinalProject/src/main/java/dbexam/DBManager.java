package dbexam;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        HousingPricesLoader hpl = new HousingPricesLoader();
        //Get Data
        List<Year> Data = hpl.loadPrices();


    }


    public Year createYear(int year, int bedFlats1, int bedFlats2, int bedHouses2, int bedHouses3, int bedHouses4){
        entityManager.getTransaction().begin();
        //create year object
        Year yr = new Year(year, bedFlats1, bedFlats2, bedHouses2, bedHouses3, bedHouses4);
        entityManager.persist(yr);
        entityManager.getTransaction().commit();
        return yr
    }

    public Year createYear(Year yr){
        entityManager.getTransaction().begin();
        //create year object
        entityManager.persist(yr);
        entityManager.getTransaction().commit();
        return yr
    }

    public void deleteAllYears(){
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Year y");
        entityManager.getTransaction().commit();
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
