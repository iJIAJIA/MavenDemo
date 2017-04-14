package train.hard.jpa;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;




import org.junit.*;

public class JPAConfigTest {
	
	private static EntityManagerFactory entityManagerFactory ;
    private static EntityManager entityManager ;
    private EntityTransaction transaction ;
	
	@BeforeClass
	public static void init(){
		entityManagerFactory = Persistence.createEntityManagerFactory("ule.beta.mysql") ;
        entityManager = entityManagerFactory.createEntityManager() ;
	}
	
	@Test
	public void test(){
		assertNotNull(entityManager);
		System.out.println(entityManager);
	}
}
