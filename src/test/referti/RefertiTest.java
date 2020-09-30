package referti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.gestionepazienti.gestionepazientiweb.batch.referti.RefertiBatch;

@RunWith(Suite.class)
@SuiteClasses({})
public class RefertiTest {
	@Autowired
	RefertiBatch refertiBatch;
	
	@Test
	public void voidTest() {
		System.out.println("Void test");
	}
	
	
	
}
