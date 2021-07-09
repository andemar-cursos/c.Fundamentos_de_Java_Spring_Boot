package com.cursos.andemar.cursos.component;

import org.springframework.stereotype.Component;

@Component
public class ComponentTwoImplement implements ComponentDependency{


    @Override
    public void saludar() {
        System.out.println("Hola mundo desde mi componente dos");
    }
}
