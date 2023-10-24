package omonteirox.pokemon.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import omonteirox.pokemon.Models.Pokemon;
import omonteirox.pokemon.Models.PokemonData;
import omonteirox.pokemon.Services.PokemonService;
import omonteirox.pokemon.Services.PokemonTypeService;

public class ImportJson {
    private static final PokemonService pokemonService = new PokemonService();
    private static final PokemonTypeService pokemonTypeService = new PokemonTypeService();
 

    private static List<Pokemon> lerJSON(String caminhoArquivo) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PokemonData pokemonData = objectMapper.readValue(new File(caminhoArquivo), PokemonData.class);
        return pokemonData.getPokemon();
    }
    public static void ImportarJSON(String caminhoArquivo){
        try {
            
            List<Pokemon> pokemons = lerJSON(caminhoArquivo);

            for (Pokemon pokemon : pokemons) {
                pokemonService.insert(pokemon);
                pokemonTypeService.create(pokemon.getType().toString(), pokemon.getNum());
            }
            System.out.println("Dados importados com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
