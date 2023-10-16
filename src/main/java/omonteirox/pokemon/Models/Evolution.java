package omonteirox.pokemon.Models;


public class Evolution {
    private String num;
    private String name;
    public Evolution(){
        this.num = "";
        this.name = "";
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
    @Override
    public String toString() {
        return "Evolution [num=" + num + ", name=" + name + "]";
    }
    
}
