import javax.xml.crypto.dsig.DigestMethod;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.*;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    // System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage

     final String command = args[0];

     switch (command) {
       case "init" -> {
         final File root = new File(".git");
         new File(root, "objects").mkdirs();
         new File(root, "refs").mkdirs();
         final File head = new File(root, "HEAD");

         try {
           head.createNewFile();
           Files.write(head.toPath(), "ref: refs/heads/main\n".getBytes());
           System.out.print("Initialized git directory");
         } catch (IOException e) {
           throw new RuntimeException(e);
         }
       }
       case "cat-file" -> Git.catFile(args[2],args[1],"blob");
       case "hash-object" -> {
         byte[] hash = Git.hashObjectCreate(args);
         System.out.println(Git.bytesToHex(hash));
       }
       case "ls-tree" ->  {
         if(args[1].equals("--name-only")){
           Git.catFile(args[2],args[1],"tree");
         }else{
           Git.catFile(args[1],"","tree");
         }
       }
       case "write-tree" ->{
         // Ensure the current directory is the root of the repository
         Path path = Paths.get("").toAbsolutePath();
         File gitDir = new File(path.toString(), ".git");
         if (!gitDir.exists()) {
           System.out.println("Error: .git directory not found. Please run 'init' command first.");
           return;
         }

         try {
           byte[] shaTree = Git.itereateDirectory(new File(path.toString()));
           System.out.print(Git.bytesToHex(shaTree));
         } catch (Exception e) {
           throw new RuntimeException(e);
         }
       }
       default -> System.out.println("Unknown command: " + command);
     }
  }

}
