package config.endpoint_classes.course;

import config.enums.State;

import java.util.*;

public class Course{
    String name;
    String code;
    String state;
    List<String> careers;
    Map<String, Integer> nState;

    public Course(CourseInterface courseInterface){
        this.name = courseInterface.getName();
        this.code = courseInterface.getCode();
        this.nState = new HashMap<>();
        this.nState.put(State.SIN_ASIGNAR.toString(), Integer.parseInt(courseInterface.getCount1()));
        this.nState.put(State.APROBACION_PENDIENTE.toString(), Integer.parseInt(courseInterface.getCount2()));
        this.nState.put(State.DISPONIBLE_PARA_CALIFICAR.toString(), Integer.parseInt(courseInterface.getCount3()));
        this.nState.put(State.FUERA_DE_FECHA.toString(), Integer.parseInt(courseInterface.getCount4()));
        this.nState.put(State.CUMPLIDOS.toString(), Integer.parseInt(courseInterface.getCount5()));
        if(this.nState.get(State.SIN_ASIGNAR.toString()) > 0)
            state = State.SIN_ASIGNAR.toString();
        else if (this.nState.get(State.APROBACION_PENDIENTE.toString()) > 0)
            state = State.APROBACION_PENDIENTE.toString();
        else if (this.nState.get(State.DISPONIBLE_PARA_CALIFICAR.toString()) > 0)
            state = State.DISPONIBLE_PARA_CALIFICAR.toString();
        else if (this.nState.get(State.FUERA_DE_FECHA.toString()) > 0)
            state = State.FUERA_DE_FECHA.toString();
        else
            state = State.CUMPLIDOS.toString();
        this.careers = new ArrayList<>();
        this.careers.addAll(Arrays.asList(courseInterface.getCareers().split("\\|")));
    }
}
