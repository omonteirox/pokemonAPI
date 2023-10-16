package omonteirox.pokemon.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import omonteirox.pokemon.DAO.PokemonTypeDAO;
import omonteirox.pokemon.Database.DbContext;
import omonteirox.pokemon.Exceptions.DatabaseException;

public class PokemonTypeService implements PokemonTypeDAO {
    Connection connection = null;
    @Override
    public List<String> getByNum(String num) {
        String query = "select name from pokemon_type where pokemon_num = ?";	
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> types = new ArrayList<String>();
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	types.add(resultSet.getString(1));           	            	                          
            }  
            return types;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        } finally {
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.CloseResultSet(resultSet);
            DbContext.Disconnect(connection);
        }  
    }

    @Override
    public String getByTypeAndNum(String type, String num) {
        String query = "select name from pokemon_type where name = ? and pokemon_num = ?";	
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, num);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	return resultSet.getString(1);           	            	                          
            }  
            return "";
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        } finally {
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.CloseResultSet(resultSet);
            DbContext.Disconnect(connection);
        }      
    }

    @Override
    public Boolean create(String type, String num) {
        String query = "insert into pokemon_type (name,pokemon_num) values (?, ?)";
        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, num);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;            
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        } finally {
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
            
        }      
    }

    @Override
    public Boolean delete(String num) {
        String query = "delete from pokemon_type where pokemon_num = ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbContext.Connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;           
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        } finally {
            DbContext.ClosePreparedStatement(preparedStatement);
            DbContext.Disconnect(connection);
        }   
    }
    
}
