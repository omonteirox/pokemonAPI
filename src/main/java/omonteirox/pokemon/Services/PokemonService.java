package omonteirox.pokemon.Services;

import java.io.StringReader;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import omonteirox.pokemon.DAO.PokemonDAO;
import omonteirox.pokemon.Database.DbContext;
import omonteirox.pokemon.Exceptions.DatabaseException;
import omonteirox.pokemon.Models.Evolution;
import omonteirox.pokemon.Models.Pokemon;

public class PokemonService implements PokemonDAO{
    private Connection connection = null;
    @Override
    public List<Pokemon> getAll() {
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM pokemon order by num";
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Pokemon p = new Pokemon();
                p.setId(resultSet.getInt("id"));
                p.setNum(resultSet.getString("num"));
                p.setName(resultSet.getString("name"));
                p.setNext_evolution(getEvolution(resultSet.getArray("next_evolution")));
                p.setPre_evolution(getEvolution(resultSet.getArray("pre_evolution")));
                pokemonList.add(p);
            }
            return pokemonList;
        } 
        catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }
         finally {
            DbContext.CloseResultSet(resultSet);
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
        }
    }

    @Override
    public Pokemon getByNum(String num) {
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM pokemon where num = ?";
        Pokemon pokemon = new Pokemon();
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                pokemon.setId(resultSet.getInt("id"));
                pokemon.setNum(resultSet.getString("num"));
                pokemon.setName(resultSet.getString("name"));
                pokemon.setNext_evolution(getEvolution(resultSet.getArray("next_evolution")));
                pokemon.setPre_evolution(getEvolution(resultSet.getArray("pre_evolution")));   
            }
            return pokemon;           
        } 
        catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }
         finally {
            DbContext.CloseResultSet(resultSet);
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
        }

        
    }

    @Override
    public Pokemon insert(Pokemon pokemon) {
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO pokemon ( num, name, pre_evolution, next_evolution) VALUES (?, ?, ?::json, ?::json)";
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pokemon.getNum());
            preparedStatement.setString(2, pokemon.getName());
            preparedStatement.setObject(3, converToJson(pokemon.getPre_evolution()));
            preparedStatement.setObject(4, converToJson(pokemon.getNext_evolution()));
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                pokemon.setId(resultSet.getInt("id"));
            }
            return pokemon;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally{
            if(resultSet != null){
                DbContext.CloseResultSet(resultSet);
            }
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
        }
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "UPDATE pokemon set name = ?, pre_evolution = ?::json, next_evolution = ?::json where num = ?";
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pokemon.getName());
            preparedStatement.setObject(2, converToJson(pokemon.getPre_evolution()));
            preparedStatement.setObject(3, converToJson(pokemon.getNext_evolution()));
            preparedStatement.setString(4, pokemon.getNum().toString());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
               return null;
            }
            resultSet = preparedStatement.getGeneratedKeys();
            Pokemon newPokemon = new Pokemon();
            if(resultSet.next()){
                newPokemon.setId(resultSet.getInt("id"));
                newPokemon.setNum(resultSet.getString("num"));
                newPokemon.setName(resultSet.getString("name"));
                newPokemon.setNext_evolution(getEvolution(resultSet.getArray("next_evolution")));
                newPokemon.setPre_evolution(getEvolution(resultSet.getArray("pre_evolution")));
            }
            return newPokemon;

        } catch (Exception e) {
               throw new DatabaseException(e.getMessage());
        }
        finally{
            DbContext.CloseResultSet(resultSet);
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
        }
    }

    @Override
    public boolean delete(String id) {
        connection = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM pokemon WHERE num = ?";
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
            return true;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

     private List<Evolution> getEvolution(Array array) {
        if (array == null)
            return new ArrayList<Evolution>();
        String jsonString = array.toString();
        List<Evolution> evList = new ArrayList<Evolution>();
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonArray arrayjson = reader.readArray();
        for (int i = 0; i < arrayjson.size(); i++) {
            Evolution v = new Evolution();
            JsonObject jsonObject = arrayjson.getJsonObject(i);
            v.setName(jsonObject.getString("name"));
            v.setNum(jsonObject.getString("num"));
            evList.add(v);
        }
        return evList;
    }

    private String converToJson(List<Evolution> evList) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Evolution v : evList) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
            .add("num", v.getNum())
            .add("name", v.getName());
            arrayBuilder.add(objBuilder);
        }
        JsonArray array = arrayBuilder.build();
        return array.toString();
    }

    public List<Pokemon> getByType(String type) {
        String query = "select pokemon.* from pokemon inner join pokemon_type on pokemon_type.pokemon_num = pokemon.num where pokemon_type.name = ? order by pokemon.num";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;        
        List<Pokemon> pokemons = new ArrayList<Pokemon>();
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	Pokemon pokemon = new Pokemon();
            	pokemon.setId(resultSet.getInt(1));
            	pokemon.setNum(resultSet.getString(2));
            	pokemon.setName(resultSet.getString(3));
            	pokemon.setPre_evolution(getEvolution(resultSet.getArray("pre_evolution")));
            	pokemon.setNext_evolution(getEvolution(resultSet.getArray("next_evolution")));             	
            	pokemons.add(pokemon);
            }
            return pokemons;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        } finally {
            DbContext.Disconnect(connection);
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.CloseResultSet(resultSet);
        }
    }

    public List<Pokemon> getByPage(int page, int qtd) {
        int offset = (page - 1) * qtd;
        String query = "SELECT * FROM pokemon LIMIT ? OFFSET ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, qtd);
            preparedStatement.setInt(2, offset);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pokemon p = new Pokemon();
                p.setId(resultSet.getInt("id"));
                p.setNum(resultSet.getString("num"));
                p.setName(resultSet.getString("name"));
                p.setNext_evolution(getEvolution(resultSet.getArray("next_evolution")));
                p.setPre_evolution(getEvolution(resultSet.getArray("pre_evolution")));
                pokemonList.add(p);
            }
            return pokemonList;
        } 
        catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }
         finally {
            if(resultSet != null){
                DbContext.CloseResultSet(resultSet);
            }
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
        }


    }
}
