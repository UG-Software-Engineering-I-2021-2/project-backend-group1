package config.endpointClasses;

import config.enums.State;
import data.entities.Carrera;

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
        this.nState.put(State.SinAsignar.toString(), Integer.parseInt(courseInterface.getCount1()));
        this.nState.put(State.AprobacionPendiente.toString(), Integer.parseInt(courseInterface.getCount2()));
        this.nState.put(State.DisponibleParaCalificar.toString(), Integer.parseInt(courseInterface.getCount3()));
        this.nState.put(State.FueraDeFecha.toString(), Integer.parseInt(courseInterface.getCount4()));
        this.nState.put(State.Cumplidos.toString(), Integer.parseInt(courseInterface.getCount5()));
        if(this.nState.get(State.SinAsignar.toString()) > 0)
            state = State.SinAsignar.toString();
        else if (this.nState.get(State.AprobacionPendiente.toString()) > 0)
            state = State.AprobacionPendiente.toString();
        else if (this.nState.get(State.DisponibleParaCalificar.toString()) > 0)
            state = State.DisponibleParaCalificar.toString();
        else if (this.nState.get(State.FueraDeFecha.toString()) > 0)
            state = State.FueraDeFecha.toString();
        else
            state = State.Cumplidos.toString();
    }
    public void setCaeers(List<String> carreras){
        this.careers = carreras;
    }


}
