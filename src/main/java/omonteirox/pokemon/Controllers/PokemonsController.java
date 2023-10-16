package omonteirox.pokemon.Controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import omonteirox.pokemon.Models.Pokemon;
import omonteirox.pokemon.Services.PokemonService;
import omonteirox.pokemon.Services.PokemonTypeService;

@Path("/pokemons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonsController {
    PokemonService pokemonService;
    PokemonTypeService pokemonTypeService;
    public PokemonsController(){
        pokemonService = new PokemonService();
        pokemonTypeService = new PokemonTypeService();    
    }
    @GET
    public Response getAll() {
        List<Pokemon> pokemons = pokemonService.getAll();
        if(pokemons == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        for(Pokemon pokemon : pokemons){
            pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
        }
        return Response.ok(pokemons).build();
    }
    @GET
    @Path("/{type}")
    public Response getByType(@PathParam("type") String type){
        List<Pokemon> pokemons = pokemonService.getByType(type);
        if(pokemons == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        for(Pokemon pokemon : pokemons){
            pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
        }
        return Response.ok(pokemons).build();
    }
    @GET
    @Path("/{page}/{qtd}")
    public Response getByPage(@PathParam("page") int page, @PathParam("qtd") int qtd){
        List<Pokemon> pokemons = pokemonService.getByPage(page, qtd);
        if(pokemons == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        for(Pokemon pokemon : pokemons){
            pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
        }
        return Response.ok(pokemons).build();
    }
}
