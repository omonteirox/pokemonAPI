package omonteirox.pokemon.Controllers;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import omonteirox.pokemon.Models.Pokemon;
import omonteirox.pokemon.Services.PokemonService;
import omonteirox.pokemon.Services.PokemonTypeService;

@Path("/pokemon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonController {
    PokemonService pokemonService;
    PokemonTypeService pokemonTypeService;
    public PokemonController(){
        pokemonService = new PokemonService();
        pokemonTypeService = new PokemonTypeService();    
    } 
    @GET
    @Path("/{num}")
    public Response getByNum(@PathParam("num") String num){
        Pokemon pokemon = pokemonService.getByNum(num);
        if(pokemon == null || pokemon.getId() == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
        return Response.ok(pokemon).build();
    }
    @POST
    public Response create(Pokemon pokemon) {
        Pokemon newPokemon = pokemonService.insert(pokemon);
        if(newPokemon == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        for(String type : pokemon.getType()){
            pokemonTypeService.create(type, pokemon.getNum());
        }
        return Response.ok(newPokemon).build();
        
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id){
         boolean deleted = pokemonService.delete(id);
            if(!deleted){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().build();
    }
    @PUT
    public Response update(Pokemon pokemon){
       Pokemon updatePokemon = pokemonService.update(pokemon);
         if(updatePokemon == null){
              return Response.status(Response.Status.NOT_FOUND).build();
         }
         pokemonTypeService.delete(pokemon.getNum());
         for(String type : pokemon.getType()){
            pokemonTypeService.create(type, pokemon.getNum());
         }
        return Response.ok(updatePokemon).build();
    }

}
