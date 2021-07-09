package com.cursos.andemar.cursos;

import com.cursos.andemar.cursos.bean.MyBean;
import com.cursos.andemar.cursos.bean.MyBeanWithDependency;
import com.cursos.andemar.cursos.bean.MyBeanWithProperties;
import com.cursos.andemar.cursos.bean.MyBeanWithPropertiesImplement;
import com.cursos.andemar.cursos.component.ComponentDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursosApplication implements CommandLineRunner {

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;

	public CursosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
							 MyBean myBean,
							 MyBeanWithDependency myBeanWithDependency,
							 MyBeanWithProperties myBeanWithProperties) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
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
	}
}
