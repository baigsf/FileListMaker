import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<String> currentList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String filename = "list.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("A - Add an item to the list");
            System.out.println("D - Delete an item from the list");
            System.out.println("V - View the list");
            System.out.println("C - Clear the list");
            System.out.println("O - Open a list file from disk");
            System.out.println("S - Save the current list file to disk");
            System.out.println("Q - Quit the program");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "V":
                    viewList();
                    break;
                case "C":
                    clearList();
                    break;
                case "O":
                    openList(scanner);
                    break;
                case "S":
                    saveList();
                    break;
                case "Q":
                    quitProgram(scanner);
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        currentList.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem(Scanner scanner) {
        System.out.print("Enter the item to delete: ");
        String item = scanner.nextLine();
        if (currentList.remove(item)) {
            needsToBeSaved = true;
            System.out.println("Item deleted.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void viewList() {
        System.out.println("Current List:");
        for (String item : currentList) {
            System.out.println(item);
        }
    }

    private static void clearList() {
        currentList.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }

    private static void openList(Scanner scanner) {
        System.out.print("Enter the filename to load: ");
        String newFilename = scanner.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader(newFilename))) {
            currentList.clear();
            String line;
            while ((line = br.readLine()) != null) {
                currentList.add(line);
            }
            needsToBeSaved = false;
            filename = newFilename;
            System.out.println("List loaded successfully.");
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    private static void saveList() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String item : currentList) {
                bw.write(item);
                bw.newLine();
            }
            System.out.println("List saved successfully.");
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error saving the list.");
        }
    }

    private static void quitProgram(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("Do you want to save the list before quitting? (Y/N): ");
            String save = scanner.nextLine().toUpperCase();
            if (save.equals("Y")) {
                saveList();
            }
        }
    }
}
