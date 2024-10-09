package fitness_zone.data;

import fitness_zone.domain.Customer;

import java.util.List;

public interface ICustomerDAO {
  List<Customer> customerList();
  boolean searchCustomerByID(Customer customer);
  boolean createCustomer(Customer customer);
  boolean updateCustomer(Customer customer);
  boolean deleteCustomer(Customer customer);
}
