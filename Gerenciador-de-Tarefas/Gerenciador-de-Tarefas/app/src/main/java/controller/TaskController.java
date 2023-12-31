package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Davi
 */
public class TaskController {

    public void save(Task task) {
        String sql = "INSERT INTO tasks (idProject, name, description, notes, completed, deadline, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Task task) {
        String sql = "UPDATE tasks SET idProject = ?, name = ?, description = ?, notes = ?, completed = ?, deadline = ?, createdAt = ?, updatedAt = ? WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com banco de dados
            connection = ConnectionFactory.getConnection();

            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Definindo os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());

            // Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com banco de dados
            connection = ConnectionFactory.getConnection();

            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Definindo os valores do statement
            statement.setInt(1, taskId);

            // Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {
        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<Task>();

        try {
            // Estabelecendo a conexão com banco de dados
            connection = ConnectionFactory.getConnection();

            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Definindo o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);

            // Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            // Enquanto houverem valores a serem percorridos no resultSet
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar tarefas " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        // Lista de tarefas que foram criadas e carregadas do banco de dados
        return tasks;
    }

    public Task findById(int idTask) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        Task task = new Task();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idTask);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar tarefa", e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        return task;
    }
}
