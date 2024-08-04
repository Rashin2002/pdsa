import java.util.Date;
import java.util.Scanner;

class FileNode 
{
    String fileName;
    String fileType;
    String fileLocation;
    Date dateCreated;
    double fileSize;
    FileNode next;
    FileNode prev;

    public FileNode(String fileName, String fileType, String fileLocation, Date dateCreated, double fileSize) 
    {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileLocation = fileLocation;
        this.dateCreated = dateCreated;
        this.fileSize = fileSize;
        this.next = null;
        this.prev = null;
    }
}

class FileManager 
{
    private FileNode head;

    public FileManager() 
    {
        head = null;
    }

    // Add a new file
    public void addFile(String fileName, String fileType, String fileLocation, double fileSize)
    {
        Date dateCreated = new Date();
        FileNode newNode = new FileNode(fileName, fileType, fileLocation, dateCreated, fileSize);
        if (head == null) 
        {
            head = newNode;
        } 
        else
        {
            FileNode current = head;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
        System.out.println("File added successfully!");
    }

    // Update an existing file
    public void updateFile(String fileName, String newFileType, String newFileLocation, double newFileSize)
    {
        FileNode current = head;
        while (current != null)
        {
            if (current.fileName.equals(fileName))
            {
                current.fileType = newFileType;
                current.fileLocation = newFileLocation;
                current.fileSize = newFileSize;
                System.out.println("File updated successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("File not found!");
    }

    // Rename a file
    public void renameFile(String oldFileName, String newFileName) 
    {
        FileNode current = head;
        while (current != null) 
        {
            if (current.fileName.equals(oldFileName)) 
            {
                current.fileName = newFileName;
                System.out.println("File renamed successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("File not found!");
    }

    // Delete a file
    public void deleteFile(String fileName) 
    {
        FileNode current = head;
        while (current != null) 
        {
            if (current.fileName.equals(fileName)) 
            {
                if (current.prev != null) 
                {
                    current.prev.next = current.next;
                } 
                else 
                {
                    head = current.next;
                }
                if (current.next != null)
                {
                    current.next.prev = current.prev;
                }
                System.out.println("File deleted successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("File not found!");
    }

    // Display all files
    public void displayFiles() 
    {
        if (head == null) 
        {
            System.out.println("No files to display.");
            return;
        }
        FileNode current = head;
        System.out.println("Files in the system:");
        while (current != null) 
        {
            displayFileInfo(current);
            current = current.next;
        }
    }

    private void displayFileInfo(FileNode file) 
    {
        System.out.println("File Name: " + file.fileName);
        System.out.println("File Type: " + file.fileType);
        System.out.println("File Location: " + file.fileLocation);
        System.out.println("Date Created: " + file.dateCreated);
        System.out.println("File Size: " + file.fileSize + " MB");
        System.out.println("----------------------------");
    }

    // Sort files by name
    public void sortFilesByName() 
    {
        if (head == null || head.next == null) 
        {
            return;
        }
        head = mergeSortByName(head);
        System.out.println("Files sorted by name.");
    }

    private FileNode mergeSortByName(FileNode head) 
    {
        if (head == null || head.next == null) 
        {
            return head;
        }

        FileNode middle = getMiddle(head);
        FileNode nextOfMiddle = middle.next;
        middle.next = null;

        FileNode left = mergeSortByName(head);
        FileNode right = mergeSortByName(nextOfMiddle);

        return sortedMergeByName(left, right);
    }

    private FileNode sortedMergeByName(FileNode left, FileNode right) 
    {
        if (left == null) 
        {
            return right;
        }
        if (right == null) 
        {
            return left;
        }

        if (left.fileName.compareTo(right.fileName) <= 0)
        {
            left.next = sortedMergeByName(left.next, right);
            left.next.prev = left;
            left.prev = null;
            return left;
        } 
        else
        {
            right.next = sortedMergeByName(left, right.next);
            right.next.prev = right;
            right.prev = null;
            return right;
        }
    }

    private FileNode getMiddle(FileNode head) 
    {
        if (head == null) 
        {
            return head;
        }
        FileNode slow = head;
        FileNode fast = head.next;

        while (fast != null && fast.next != null) 
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Sort files by date
    public void sortFilesByDate() 
    {
        if (head == null || head.next == null) 
        {
            return;
        }
        head = mergeSortByDate(head);
        System.out.println("Files sorted by date.");
    }

    private FileNode mergeSortByDate(FileNode head) {
        if (head == null || head.next == null) 
        {
            return head;
        }

        FileNode middle = getMiddle(head);
        FileNode nextOfMiddle = middle.next;
        middle.next = null;

        FileNode left = mergeSortByDate(head);
        FileNode right = mergeSortByDate(nextOfMiddle);

        return sortedMergeByDate(left, right);
    }

    private FileNode sortedMergeByDate(FileNode left, FileNode right) 
    {
        if (left == null)
        {
            return right;
        }
        if (right == null)
        {
            return left;
        }

        if (left.dateCreated.compareTo(right.dateCreated) <= 0)
        {
            left.next = sortedMergeByDate(left.next, right);
            left.next.prev = left;
            left.prev = null;
            return left;
        }
        else
        {
            right.next = sortedMergeByDate(left, right.next);
            right.next.prev = right;
            right.prev = null;
            return right;
        }
    }

    // Sort files by size
    public void sortFilesBySize(boolean ascending)
    {
        if (head == null || head.next == null)
        {
            return;
        }
        head = mergeSortBySize(head, ascending);
        System.out.println("Files sorted by size " + (ascending ? "(Smallest to Largest)." : "(Largest to Smallest)."));
    }

    private FileNode mergeSortBySize(FileNode head, boolean ascending)
    {
        if (head == null || head.next == null)
        {
            return head;
        }

        FileNode middle = getMiddle(head);
        FileNode nextOfMiddle = middle.next;
        middle.next = null;

        FileNode left = mergeSortBySize(head, ascending);
        FileNode right = mergeSortBySize(nextOfMiddle, ascending);

        return sortedMergeBySize(left, right, ascending);
    }

    private FileNode sortedMergeBySize(FileNode left, FileNode right, boolean ascending)
    {
        if (left == null)
        {
            return right;
        }
        if (right == null)
        {
            return left;
        }

        if ((ascending && left.fileSize <= right.fileSize) || (!ascending && left.fileSize > right.fileSize))
        {
            left.next = sortedMergeBySize(left.next, right, ascending);
            left.next.prev = left;
            left.prev = null;
            return left;
        }
        else
        {
            right.next = sortedMergeBySize(left, right.next, ascending);
            right.next.prev = right;
            right.prev = null;
            return right;
        }
    }

    // Categorize files by type
    public void categorizeFilesByType(int fileTypeOption)
    {
        if (head == null)
        {
            System.out.println("No files to categorize.");
            return;
        }

        String selectedType = "";
        switch (fileTypeOption) 
        {
            case 1:
                selectedType = "photos";
                break;
            case 2:
                selectedType = "videos";
                break;
            case 3:
                selectedType = "docs";
                break;
            case 4:
                selectedType = "music";
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        FileNode current = head;
        boolean found = false;
        System.out.println("Files categorized by type (" + selectedType + "):");
        while (current != null)
        {
            if (current.fileType.equalsIgnoreCase(selectedType)) 
            {
                displayFileInfo(current);
                found = true;
            }
            current = current.next;
        }
        if (!found)
        {
            System.out.println("No files found for the selected type.");
        }
    }
    
    public void categorizeFilesByLocation(int locationOption)
    {
        if (head == null)
        {
            System.out.println("No files to categorize.");
            return;
        }

        String selectedLocation = "";
        switch (locationOption)
        {
            case 1:
                selectedLocation = "downloads";
                break;
            case 2:
                selectedLocation = "desktop";
                break;
            case 3:
                selectedLocation = "documents";
                break;
            case 4:
                selectedLocation = "local disk D";
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        FileNode current = head;
        boolean found = false;
        System.out.println("Files categorized by location (" + selectedLocation + "):");
        while (current != null) 
        {
            if (current.fileLocation.equalsIgnoreCase(selectedLocation))
            {
                displayFileInfo(current);
                found = true;
            }
            current = current.next;
        }
        if (!found)
        {
            System.out.println("No files found for the selected location.");
        }
    }
    
    public void searchFilesByName(String searchName)
    {
        if (head == null)
        {
            System.out.println("No files to search.");
            return;
        }

        FileNode current = head;
        boolean found = false;
        System.out.println("Search results for file name containing '" + searchName + "':");
        while (current != null)
        {
            if (current.fileName.toLowerCase().contains(searchName.toLowerCase())) 
            {
                displayFileInfo(current);
                found = true;
            }
            current = current.next;
        }
        if (!found)
        {
            System.out.println("No files found");
        }
    }
}




public class FileManagementTool 
{
    public static void main(String[] args) 
    {
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(System.in);

        // Display welcome message
        System.out.println("Welcome to the File Manager Tool!");
        System.out.println("=================================");

        while (true) 
        {
            System.out.println("File Management Tool");
            System.out.println("1. Add New File");
            System.out.println("2. Update File");
            System.out.println("3. Delete File");
            System.out.println("4. Display All Files");
            System.out.println("5. Rename File");
            System.out.println("6. Sort Files by Name");
            System.out.println("7. Sort Files by Date");
            System.out.println("8. Categorize Files by Type");
            System.out.println("9. Sort Files by Size");
            System.out.println("10. Categorize Files by Location");
            System.out.println("11. Search Files by Name");
            System.out.println("12. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter file name: ");
                    String fileName = scanner.nextLine();
                    System.out.print("Enter file type (e.g., photos, videos, docs, music): ");
                    String fileType = scanner.nextLine();
                    System.out.print("Enter file location: ");
                    String fileLocation = scanner.nextLine();
                    System.out.print("Enter file size in MB: ");
                    double fileSize = scanner.nextDouble();
                    scanner.nextLine();
                    fileManager.addFile(fileName, fileType, fileLocation, fileSize);
                    break;
                case 2:
                    System.out.print("Enter file name to update: ");
                    String updateFileName = scanner.nextLine();
                    System.out.print("Enter new file type: ");
                    String newFileType = scanner.nextLine();
                    System.out.print("Enter new file location: ");
                    String newFileLocation = scanner.nextLine();
                    System.out.print("Enter new file size in MB: ");
                    double newFileSize = scanner.nextDouble();
                    scanner.nextLine();
                    fileManager.updateFile(updateFileName, newFileType, newFileLocation, newFileSize);
                    break;
                case 3:
                    System.out.print("Enter file name to delete: ");
                    String deleteFileName = scanner.nextLine();
                    fileManager.deleteFile(deleteFileName);
                    break;
                case 4:
                    fileManager.displayFiles();
                    break;
                case 5:
                    System.out.print("Enter current file name to rename: ");
                    String oldFileName = scanner.nextLine();
                    System.out.print("Enter new file name: ");
                    String newFileName = scanner.nextLine();
                    fileManager.renameFile(oldFileName, newFileName);
                    break;
                case 6:
                    fileManager.sortFilesByName();
                    fileManager.displayFiles();
                    break;
                case 7:
                    fileManager.sortFilesByDate();
                    fileManager.displayFiles();
                    break;
                case 8:
                    System.out.println("Select file type to categorize:");
                    System.out.println("1. Photos");
                    System.out.println("2. Videos");
                    System.out.println("3. Docs");
                    System.out.println("4. Music");
                    System.out.print("Choose a type: ");
                    int fileTypeOption = scanner.nextInt();
                    scanner.nextLine();
                    fileManager.categorizeFilesByType(fileTypeOption);
                    break;
                case 9:
                    System.out.println("Sort files by size:");
                    System.out.println("1. Largest to Smallest");
                    System.out.println("2. Smallest to Largest");
                    System.out.print("Choose an option: ");
                    int sortSizeOption = scanner.nextInt();
                    scanner.nextLine();
                    if (sortSizeOption == 1)
                    {
                        fileManager.sortFilesBySize(false);
                    } 
                    else if (sortSizeOption == 2) 
                    {
                        fileManager.sortFilesBySize(true);
                    } 
                    else
                    {
                        System.out.println("Invalid choice.");
                    }
                    fileManager.displayFiles();
                    break;
                case 10:
                    System.out.println("Select file location to categorize:");
                    System.out.println("1. Downloads");
                    System.out.println("2. Desktop");
                    System.out.println("3. Documents");
                    System.out.println("4. Local Disk D");
                    System.out.print("Choose a location: ");
                    int locationOption = scanner.nextInt();
                    scanner.nextLine(); 
                    fileManager.categorizeFilesByLocation(locationOption);
                    break;
                case 11:
                    System.out.print("Enter file name to search: ");
                    String searchName = scanner.nextLine();
                    fileManager.searchFilesByName(searchName);
                    break;
                case 12:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}


