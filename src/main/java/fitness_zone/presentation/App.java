package fitness_zone.presentation;

import fitness_zone.data.CustomerDAO;
import fitness_zone.data.ICustomerDAO;
import fitness_zone.domain.Customer;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    menu();
  }

  private static void menu() {
    boolean exit = false;
    Scanner sc = new Scanner(System.in);
    ICustomerDAO customerDAO = new CustomerDAO();

    while (!exit) {
      try {
        var option = showMenu(sc);
        exit = executeOptions(sc, option, customerDAO);
      } catch (Exception e) {
        System.out.println("An error occurred while executing the selected option: " + e.getMessage());
        System.out.println("Exception type: " + e.getClass().getName());
        System.out.println("Exception parent class: " + e.getClass().getSuperclass().getName());
      }
      System.out.println();
    }
  }

  private static int showMenu(Scanner sc) {
    System.out.print("""
        *** Fitness Zone Gym ***
        1. Customer list
        2. Find a customer
        3. Add a customer
        4. Update a customer
        5. Delete a customer
        6. Exit
        Select an option:\s""");
    return Integer.parseInt(sc.nextLine());
  }

  private static boolean executeOptions(Scanner sc, int option, ICustomerDAO customerDAO) {
    return switch (option) {
      case 1 -> {
        System.out.println("\n--- Customer list ---");

        var customers = customerDAO.customerList();
        customers.forEach(System.out::println);

        yield false;
      }
      case 2 -> {
        System.out.println("\n--- Find a customer ---");

        System.out.print("Type the ID of the user you want to search for: ");
        var id = Integer.parseInt(sc.nextLine());

        var customer = new Customer(id);
        var customerFound = customerDAO.searchCustomerByID(customer);

        if (customerFound)
          System.out.println("\nCustomer found: \n" + customer);
        else
          System.out.println("\nCustomer with ID " + id + " was not found.");

        yield false;
      }
      case 3 -> {
        System.out.println("\n--- Add a customer ---");

        System.out.print("Type the user's name: ");
        var name = sc.nextLine();

        System.out.print("Type the user's lastname: ");
        var lastname = sc.nextLine();

        System.out.print("Type the user's membership: ");
        var membership = Integer.parseInt(sc.nextLine());

        Customer newCustomer = new Customer(name, lastname, membership);
        var aggregatedCustomer = customerDAO.createCustomer(newCustomer);

        if (aggregatedCustomer)
          System.out.println("\nCustomer added: " + newCustomer);
        else
          System.out.println("An error occurred while trying to add the customer.");

        yield false;
      }
      case 4 -> {
        System.out.println("\n--- Update a customer ---");

        System.out.print("Type the user's ID: ");
        var id = Integer.parseInt(sc.nextLine());

        System.out.print("Type the user's name: ");
        var name = sc.nextLine();

        System.out.print("Type the user's lastname: ");
        var lastname = sc.nextLine();

        System.out.print("Type the user's membership: ");
        var membership = Integer.parseInt(sc.nextLine());

        Customer customer = new Customer(id, name, lastname, membership);
        var updatedCustomer = customerDAO.updateCustomer(customer);

        if (updatedCustomer)
          System.out.println("Updated customer: " + customer);
        else
          System.out.println("Customer information could not be updated.");

        yield false;
      }
      case 5 -> {
        System.out.println("\n--- Delete a customer ---");

        System.out.print("Enter the ID of the customer you wish to delete: ");
        var id = Integer.parseInt(sc.nextLine());

        Customer customer = new Customer(id);
        var customerDeleted = customerDAO.deleteCustomer(customer);

        if (customerDeleted)
          System.out.print("Customer deleted.\n");
        else
          System.out.println("An error occurred while trying to delete the client.\n");

        yield false;
      }
      case 6 -> {
        System.out.println("Leaving...");
        yield true;
      }
      default -> throw new IllegalStateException("Unexpected value: " + option);
    };
  }
}
