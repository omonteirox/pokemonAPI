package omonteirox.pokemon.Controllers;

import java.util.List;

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
import javax.ws.rs.core.Response.Status;

import omonteirox.pokemon.Models.Pokemon;
import omonteirox.pokemon.Services.PokemonService;
import omonteirox.pokemon.Services.PokemonTypeService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonController {
    PokemonService pokemonService;
    PokemonTypeService pokemonTypeService;

    public PokemonController() {
        pokemonService = new PokemonService();
        pokemonTypeService = new PokemonTypeService();
    }

    @GET
    @Path("/pokemon/{num}")
    public Response getByNum(@PathParam("num") String num) {
        try {
            Pokemon pokemon = pokemonService.getByNum(num);
            if (pokemon == null || pokemon.getId() == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
            return Response.ok(pokemon).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getCause()).build();
        }
    }

    @POST
    @Path("/pokemon")
    public Response create(Pokemon pokemon) {
        try {
            Pokemon newPokemon = pokemonService.insert(pokemon);
            if (newPokemon == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            for (String type : pokemon.getType()) {
                pokemonTypeService.create(type, pokemon.getNum());
            }
            return Response.ok(newPokemon).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    @DELETE
    @Path("/pokemon/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            boolean deleted = pokemonService.delete(id);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }
    }

    @PUT
    @Path("/pokemon")
    public Response update(Pokemon pokemon) {
        try {
            Pokemon updatePokemon = pokemonService.update(pokemon);
            if (updatePokemon == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            pokemonTypeService.delete(pokemon.getNum());
            for (String type : pokemon.getType()) {
                pokemonTypeService.create(type, pokemon.getNum());
            }
            return Response.ok(updatePokemon).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }

    }

    @GET
    @Path("/pokemons")
    public Response getAll() {
        try {
            List<Pokemon> pokemons = pokemonService.getAll();
            if (pokemons == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            for (Pokemon pokemon : pokemons) {
                pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
            }
            return Response.ok(pokemons).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }

    }

    @GET
    @Path("pokemons/{type}")
    public Response getByType(@PathParam("type") String type) {
        try {
            List<Pokemon> pokemons = pokemonService.getByType(type);
            if (pokemons == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            for (Pokemon pokemon : pokemons) {
                pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
            }
            return Response.ok(pokemons).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/pokemons/{page}/{qtd}")
    public Response getByPage(@PathParam("page") int page, @PathParam("qtd") int qtd) {
        try {
            List<Pokemon> pokemons = pokemonService.getByPage(page, qtd);
            if (pokemons == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            for (Pokemon pokemon : pokemons) {
                pokemon.setType(pokemonTypeService.getByNum(pokemon.getNum()));
            }
            return Response.ok(pokemons).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }

    }

    

}
