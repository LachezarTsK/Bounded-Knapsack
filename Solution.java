import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Application that selects products from a list, so that the total calories of 
 * the selected products is as close as possible to the entered target calories.
 *
 * The resulting total sum of calories could be lower than, or equal to, the target
 * calories but can not exceed it.
 *
 * The products comprising the results are displayed by their name, price and calories.
 *
 * The application demonstrates the implementation of the Knapsack Algorithm and 
 * a way to store the invidual components that compose tha resulting sum.
 */
public class Solution {
  /**
  * The List comprises all the prodcuts from which the selection is made.
  */
  private static List<Product> products;
  private static int targetCalories;

  public static void main(String[] args) {
    dataEntry();
    HashSet<Product> results = findProductsForTargetCalories();
    printResults(results);
  }

  private static void dataEntry() {
    Scanner scanner = new Scanner(System.in);
    products = new ArrayList<Product>();

    System.out.println("Enter target calories:");
    targetCalories = scanner.nextInt();

    while (true) {
      System.out.println("Enter product name:");
      String name = scanner.next();

      System.out.println("Enter product price:");
      int price = scanner.nextInt();

      System.out.println("Enter product calories:");
      int calories = scanner.nextInt();

      System.out.println("Press 's' to save current entry.");
      System.out.println("Press any other character to continue without saving current entry.");
      String save = scanner.next();

      if (save.equalsIgnoreCase("s")) {
        Product pr = new Product(name, price, calories);
        products.add(pr);
      }
      
      System.out.println("Press 'c' calculate list of products.");
      System.out.println("Press any other character to add another product.");
      String calculate = scanner.next();

      if (calculate.equalsIgnoreCase("c")) {
        break;
      }
    }
    scanner.close();
  }

  /**
   * Implementation of the Knapsack Algorithm for finding the products for the target calories.
   */
  private static HashSet<Product> findProductsForTargetCalories() {
    /** 
    * The Array stores the selected products. After processing all the data, 
    * the selected products are contained in selectedProducts[targetCalories].
    */
    @SuppressWarnings("unchecked")
    HashSet<Product>[] selectedProducts = new HashSet[targetCalories + 1];
    Arrays.fill(selectedProducts, new HashSet<Product>());
    
    /** 
    * Knapsack table for finding the calories closest to the target.
    * After processing all the data, the sum closest to the target
    * is contained in maxCalories[targetCalories].
    */
    int[] maxCalories = new int[targetCalories + 1];

    for (int i = 0; i < products.size(); i++) {
      for (int j = targetCalories; j >= products.get(i).calories; j--) {

        if (maxCalories[j] < products.get(i).price + maxCalories[j - products.get(i).calories]) {
          maxCalories[j] = products.get(i).price + maxCalories[j - products.get(i).calories];
          selectedProducts[j] = new HashSet<Product>();
          selectedProducts[j].add(products.get(i));
          selectedProducts[j].addAll(selectedProducts[j - products.get(i).calories]);
        }
      }
    }
    return selectedProducts[targetCalories];
  }

  private static void printResults(HashSet<Product> results) {
    int totalPrice = 0;
    int totalCalories = 0;
    for (Product p : results) {
      System.out.println(p);
      totalPrice = totalPrice + p.price;
      totalCalories = totalCalories + p.calories;
    }
    System.out.println("---------------------------");
    System.out.println("Total Price: " + totalPrice);
    System.out.println("Total Calories: " + totalCalories);
    System.out.println("Target Calories: " + targetCalories);
  }

  public static class Product {
    String name;
    int price;
    int calories;

    public Product(String name, int price, int calories) {
      this.name = name;
      this.price = price;
      this.calories = calories;
    }

    @Override
    public String toString() {
      return "Product: " + this.name + ", Price: " + this.price + ", Calories: " + this.calories;
    }
  }
}
