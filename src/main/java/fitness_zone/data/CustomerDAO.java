package fitness_zone.data;

import fitness_zone.domain.Customer;

import java.sql.*;
import java.util.*;

import static fitness_zone.connection.ConnectionDatabase.connect;

public class CustomerDAO implements ICustomerDAO {

  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private Connection connection = connect();

  @Override
  public List<Customer> customerList() {
    List<Customer> customers = new ArrayList<>();

    var sql = "SELECT * FROM customer ORDER BY id DESC";

    try {
      preparedStatement = connection.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Customer customer = Customer.fromResultSet(resultSet);

        customers.add(customer);
      }
    } catch (SQLException | NullPointerException e) {
      exceptionHandler("Error when listing customers: ", e);
    }
    return customers;
  }

  @Override
  public boolean searchCustomerByID(Customer customer) {
    String sql = "SELECT * FROM customer WHERE id = ?";

    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, customer.getId());
      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        customer.setName(resultSet.getString("name"));
        customer.setLastname(resultSet.getString("lastname"));
        customer.setMembership(resultSet.getInt("membership"));
        return true;
      }
    } catch (SQLException | NullPointerException e) {
      exceptionHandler("Error retrieving a customer by ID: ", e);
    }
    return false;
  }

  @Override
  public boolean createCustomer(Customer customer) {
    String sql = "INSERT INTO customer(name, lastname, membership) "
        + "VALUES(?, ?, ?)";

    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, customer.getName());
      preparedStatement.setString(2, customer.getLastname());
      preparedStatement.setInt(3, customer.getMembership());

      preparedStatement.execute();

      return true;
    } catch (SQLException | NullPointerException e) {
      exceptionHandler("An error occurred when trying to add the customer: ", e);
    }
    return false;
  }

  @Override
  public boolean updateCustomer(Customer customer) {
    String sql = "UPDATE customer SET name=?, lastname=?, membership=? "
        + "WHERE id = ?";

    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, customer.getName());
      preparedStatement.setString(2, customer.getLastname());
      preparedStatement.setInt(3, customer.getMembership());
      preparedStatement.setInt(4, customer.getId());

      preparedStatement.execute();

      return true;
    } catch (SQLException | NullPointerException e) {
      exceptionHandler("An error occurred when trying to update the customer: ", e);
    }
    return false;
  }

  @Override
  public boolean deleteCustomer(Customer customer) {
    String sql = "DELETE FROM customer WHERE id = ?";

    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, customer.getId());

      preparedStatement.execute();

      return true;
    } catch (SQLException | NullPointerException e) {
      exceptionHandler("An error occurred when trying to delete the customer: ", e);
    }
    return false;
  }

  private void exceptionHandler(String message, Exception e) {
    System.out.println(message + e.getMessage());
    System.out.println("Exception type: " + e.getClass().getName());
    System.out.println("Exception parent class: " + e.getClass().getSuperclass().getName());
    try {
      connection.close();
    } catch (SQLException exception) {
      exceptionHandler("An error occurred while trying to close the connection: ", exception);
    }
  }
}
