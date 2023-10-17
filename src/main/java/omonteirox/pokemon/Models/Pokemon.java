package omonteirox.pokemon.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 @JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private Integer id;
    private String num;
    private String name;
    private List<String> type = new ArrayList<String>();
    private List<Evolution>  prev_evolution;
    private List<Evolution> next_evolution;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getType() {
        return type;
    }
    public void setType(List<String> type) {
        this.type = type;
    }
   
    
    @Override
    public String toString() {
        return "Pokemon [id=" + id + ", num=" + num + ", name=" + name + ", type=" + type + ", prev_evolution="
                + prev_evolution + ", next_evolution=" + next_evolution + "]";
    }
    public List<Evolution> getPrev_evolution() {
        return prev_evolution;
    }
    public void setPrev_evolution(List<Evolution> prev_evolution) {
        this.prev_evolution = prev_evolution;
    }
    public List<Evolution> getNext_evolution() {
        return next_evolution;
    }
    public void setNext_evolution(List<Evolution> next_evolution) {
        this.next_evolution = next_evolution;
    }
   
   
   
    
    
    

    
   
    
}
