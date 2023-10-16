package omonteirox.pokemon.DAO;

import java.util.List;

public interface PokemonTypeDAO {
    List<String> getByNum(String num);
	String getByTypeAndNum(String type, String num);
	Boolean create(String type, String num);
	Boolean delete(String num);
}
