package com.cursos.andemar.cursos;

import com.cursos.andemar.cursos.bean.MyBean;
import com.cursos.andemar.cursos.bean.MyBeanWithDependency;
import com.cursos.andemar.cursos.bean.MyBeanWithProperties;
import com.cursos.andemar.cursos.bean.MyBeanWithPropertiesImplement;
import com.cursos.andemar.cursos.component.ComponentDependency;
import com.cursos.andemar.cursos.pojo.UserPojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(CursosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;

	public CursosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
							 MyBean myBean,
							 MyBeanWithDependency myBeanWithDependency,
							 MyBeanWithProperties myBeanWithProperties,
							 UserPojo userPojo) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;

	}

	public static void main(String[] args) {
		SpringApplication.run(CursosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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
