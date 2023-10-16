package omonteirox.pokemon.DAO;

import java.util.List;

import omonteirox.pokemon.Models.Pokemon;

public interface PokemonDAO {
    public List<Pokemon> getAll();
    public Pokemon getByNum(String num);
    public Pokemon insert(Pokemon pokemon);
    public Pokemon update(Pokemon pokemon);
    public boolean delete(String id);

}
