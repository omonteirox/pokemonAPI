package omonteirox.pokemon.Models;

import java.util.ArrayList;
import java.util.List;

public class ResponseModel<T> {
    private List<String> errors = new ArrayList<String>();
    private T data;
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public ResponseModel(List<String> errors, T data) {
        this.errors = errors;
        this.data = data;
    }
    public ResponseModel(T data) {
        this.data = data;
    }
    public ResponseModel(String error) {
        this.errors.add(error);
    }
    
}
