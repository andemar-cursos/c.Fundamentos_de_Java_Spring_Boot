package com.cursos.andemar.cursos;

import com.cursos.andemar.cursos.bean.MyBean;
import com.cursos.andemar.cursos.bean.MyBeanWithDependency;
import com.cursos.andemar.cursos.bean.MyBeanWithProperties;
import com.cursos.andemar.cursos.bean.MyBeanWithPropertiesImplement;
import com.cursos.andemar.cursos.component.ComponentDependency;
import com.cursos.andemar.cursos.entity.User;
import com.cursos.andemar.cursos.pojo.UserPojo;
import com.cursos.andemar.cursos.repository.UserRepository;
import com.cursos.andemar.cursos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CursosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(CursosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public CursosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
							 MyBean myBean,
							 MyBeanWithDependency myBeanWithDependency,
							 MyBeanWithProperties myBeanWithProperties,
							 UserPojo userPojo,
							 UserRepository userRepository,
						     UserService userService) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;

	}

	public static void main(String[] args) {
		SpringApplication.run(CursosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		clasesAnteriores();

//		saveUserInDataBase();
//		getInformationJpqlFromUser();
		saveWithErrorTransactional();

	}

	private void saveWithErrorTransactional(){

		User test1 = new User("TestTransactional1", "TestTransactional1@test.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@test.com", LocalDate.now());
		// Este tiene la duplicidad del email (Error).
		User test3 = new User("TestTransactional3", "TestTransactional1@test.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@test.com", LocalDate.now());

		List<User> users = List.of(test1, test2, test3, test4);

		try {
			userService.saveTransactional(users);
		}catch (Exception e){
			LOGGER.error(e);
		}

		userService.getAllUsers()
			.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transaccional: " + user));
	}

	private void getInformationJpqlFromUser() {
		LOGGER.info(	"Usuario con el metodo findByEmail: " +
						userRepository.findByUserEmail("test@andemar.com")
						.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("R", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con metodo sort: " + user));
	}

	private void saveUserInDataBase() {
		User user1 = new User("Andemar", "test@andemar.com", LocalDate.of(2021, 03, 1));
		User user2 = new User("Mashiro", "test@mashiro.com", LocalDate.of(2020, 03, 23));
		User user3 = new User("Ran", "test@Ran.com", LocalDate.of(2020, 07, 20));
		User user4 = new User("Rena", "test@Rena.com", LocalDate.of(2020, 06, 12));
		User user5 = new User("Nagisa", "test@Nagisa.com", LocalDate.of(2020, 03, 30));
		User user6 = new User("Sinon", "test@Asuna.com", LocalDate.of(2020, 03, 20));
		User user7 = new User("Sinon", "test@Sinon.com", LocalDate.of(2020, 01, 20));
		User user8 = new User("Alice", "test@Alice.com", LocalDate.of(2020, 12, 20));
		User user9 = new User("Tyese", "test@Tyese.com", LocalDate.of(2020, 11, 20));

		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9);

		list.stream().forEach(userRepository::save);

		/*userRepository.findByName("Andemar")
			.forEach(user -> LOGGER.info("Usuario con query method: " + user));

		userRepository.findByEmailAndName("test@mashiro.com", "Mashiro")
				.ifPresent(LOGGER::info);

		userRepository.findByNameLike("%R%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike " + user));

		userRepository.findByNameOrEmail(null, "test@mashiro.com")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail " + user));


		userRepository.findByBirthDateBetween(LocalDate.of(2021, 03, 1), LocalDate.of(2021, 06, 1))
			.stream()
			.forEach(user -> LOGGER.info("Usuario findByBirthDateBetween " + user));

		userRepository.findByNameLikeOrderByIdDesc("%Sinon%")
				.forEach(user -> LOGGER.info("Usuario findByNameLikeOrderByIdDesc: " + user));

		userRepository.findByNameContainingOrderByIdDesc("Sinon")
				.forEach(user -> LOGGER.info("Usuario findByNameContainingOrderByIdDesc: " + user));

		userRepository.getAllByBiAndBirthDateAndEmail(LocalDate.of(2020, 12, 20), "test@Alice.com")
			.ifPresent(user -> LOGGER.info("Usuario getAllByBiAndBirthDateAndEmail: " + user));*/
	}


	public void clasesAnteriores() {
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + " - " + userPojo.getPassword());

		try {
			int value = 10/0;
			LOGGER.debug("Mi valor:" + value);
		}catch (Exception e){
			LOGGER.error("Divicion con 0: "+ e);

		}

		LOGGER.error("Esto es un err del app");
	}
}
