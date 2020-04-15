package com.project.SDProject;

import com.project.model.Drug;
import com.project.model.User;
import com.project.repository.DrugRepository;
import com.project.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static java.lang.Boolean.FALSE;
import static org.mockito.Mockito.when;

@SpringBootTest
class SdProjectApplicationTests extends TestCase {

	@Mock
	UserRepository userRepository;

	@Mock
	DrugRepository drugRepository;

	@Test
	void testFindUser() {
		MockitoAnnotations.initMocks(this);
		User user = new User();
		user.setId(6);
		user.setName("Rusu Daniel");
		user.setMail("mail@rusu");
		user.setMoney("23");
		user.setPassword("rusu");
		user.setRole(FALSE);

		when(userRepository.findByName( "Rusu Daniel" )).thenReturn(user);
		User userFound = userRepository.findByName("Rusu Daniel");
		assertNotNull(userFound);
		assertEquals(user.getName(), userFound.getName());
	}

	@Test
	void testFindDrug() {
		MockitoAnnotations.initMocks(this);
		Drug drug = new Drug();
		drug.setId(8);
		drug.setName("Strepsils");
		drug.setType("durere");
		drug.setPrice("41");
		drug.setDescription("Pt durere in gat");

		when(drugRepository.findByName( "Strepsils" )).thenReturn(drug);
		Drug drugFound = drugRepository.findByName("Strepsils");
		assertNotNull(drugFound);
		assertEquals(drug.getName(), drugFound.getName());
	}

}
