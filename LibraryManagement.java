import java.util.Scanner;

public class COSC112FinalProjectLibraryManagementSystem {
    // Arrays to store library data
    static String[] books = {"Bleach", "One Piece", "Naruto", "Dragon Ball", "Blue Exorcist", "Jujustu Kaisen", "Windbreaker", "Magi", "Full Metal Alchemist", "Soul Eater", "Black Butler", "Kiss Him, Not Me", "Jojo's Biazzar Adventure"};
    static boolean[] isBookAvailable = {true, true, true, true, true, true, true, true, true, true, true, true, true}; // Track book availability
    static String[] members = new String[10]; // Member database. The max is 10 members.
    static int memberCount = 0; // Track the number of registered members
    static String[] borrowedBooks = new String[10]; // Track borrowed books per member

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        // Main menu loop
        do {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Register Member");
            System.out.println("2. Search for a Book");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Check Overdue Notifications");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            // Call appropriate method based on user choice
            switch (choice) {
                case 1:
                    registerMember(scanner);
                    break;
                case 2:
                    searchBook(scanner);
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    checkOverdueNotifications();
                    break;
                case 6:
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);
        scanner.close();
    }

    // Register a new member
    public static void registerMember(Scanner scanner) {
        System.out.println();
        System.out.println("=== Library Member Registration ===");
        if (memberCount < members.length) {
            System.out.print("Enter member name: ");
            String memberName = scanner.nextLine();
            members[memberCount] = memberName;
            borrowedBooks[memberCount] = null; // Initialize borrowed books for the member
            memberCount++;
            System.out.println("Member registered successfully!");
        } else {
            System.out.println("Member registration is full.");
        }
    }

    // Search for a book in the catalog
    public static void searchBook(Scanner scanner) {
        System.out.println();
        System.out.println("=== Library Book Search ===");
        System.out.print("Enter book title to search: ");
        String bookTitle = scanner.nextLine();
        boolean found = false;

        // Check the books array for the requested book
        for (int i = 0; i < books.length; i++) {
            if (books[i].equalsIgnoreCase(bookTitle)) {
                System.out.println("Book found: " + books[i] + " - " + (isBookAvailable[i] ? "Available" : "Not Available"));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book not found in the catalog.");
        }
    }

    // Borrow a book from the library
    public static void borrowBook(Scanner scanner) {
        System.out.println();
        System.out.println("=== Library Book Borrowing ===");
        System.out.print("Enter your member name: ");
        String memberName = scanner.nextLine();
        int memberIndex = findMemberIndex(memberName);

        if (memberIndex != -1) {
            if (borrowedBooks[memberIndex] == null) { // Check if member has no borrowed book
                System.out.print("Enter the title of the book to borrow: ");
                String bookTitle = scanner.nextLine();
                int bookIndex = findBookIndex(bookTitle);

                if (bookIndex != -1 && isBookAvailable[bookIndex]) {
                    isBookAvailable[bookIndex] = false; // Mark the book as borrowed
                    borrowedBooks[memberIndex] = books[bookIndex]; // Assign the book to the member
                    System.out.println("Book borrowed successfully!");
                } else {
                    System.out.println("Book is either not available or already borrowed.");
                }
            } else {
                System.out.println("You already have a borrowed book. Return it before borrowing another.");
            }
        } else {
            System.out.println("Member not found. Please register first.");
        }
    }

    // Return a borrowed book
    public static void returnBook(Scanner scanner) {
        System.out.println();
        System.out.println("=== Library Book Return ===");
        System.out.print("Enter your member name: ");
        String memberName = scanner.nextLine();
        int memberIndex = findMemberIndex(memberName);

        if (memberIndex != -1) {
            if (borrowedBooks[memberIndex] != null) {
                String returnedBook = borrowedBooks[memberIndex];
                int bookIndex = findBookIndex(returnedBook);
                isBookAvailable[bookIndex] = true; // Mark the book as available
                borrowedBooks[memberIndex] = null; // Clear the member's borrowed book
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("You have no borrowed books to return.");
            }
        } else {
            System.out.println("Member not found. Please register first.");
        }
    }

    // Check overdue notifications (basic simulation)
    public static void checkOverdueNotifications() {
        System.out.println();
        System.out.println("\n=== Overdue Notifications ===");
        for (int i = 0; i < memberCount; i++) {
            if (borrowedBooks[i] != null) {
                System.out.println("Member: " + members[i] + " - Overdue Book: " + borrowedBooks[i]);
            }
        }
        System.out.println("No overdue notifications for members without borrowed books.");
    }

    // Helper method to find a member's index
    public static int findMemberIndex(String memberName) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equalsIgnoreCase(memberName)) {
                return i;
            }
        }
        return -1; // Member not found
    }

    // Helper method to find a book's index
    public static int findBookIndex(String bookTitle) {
        for (int i = 0; i < books.length; i++) {
            if (books[i].equalsIgnoreCase(bookTitle)) {
                return i;
            }
        }
        return -1; // Book not found
    }

}

