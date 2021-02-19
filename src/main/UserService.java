package main;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserService implements UserServiceInterface {

    Set<User> userList = new HashSet<>();
    File file = new File("src/res/users.txt");

    public UserService() {
        initFile();
        run();
    }

    private void initFile() {
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        System.out.println("type \"add\" to add user or \"delete\" to remove user or \"q\" to end");
        Scanner sc = new Scanner(System.in);
        String next = sc.next();
        if (!next.equals("q")) {
            if (next.contains("add")) {
                System.out.println("type name");
                String name = sc.next();
                System.out.println("type age");
                int age = sc.nextInt();
                addUser(name, age);
            } else if (next.contains("delete")) {
                System.out.println("type name");
                String name = sc.next();
                deleteUser(name);
            } else {
                System.out.println("wrong input, try again");
            }
            run();
        }
    }

    @Override
    public void addUser(String name, int age) {

        try {
            User user = new User(name, age);

            if (file.length() != 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                userList = (Set<User>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            }
            userList.add(user);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userList);
            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("user " + name + " added");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("exception in addUser");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(String name) {
        try {
            boolean marker = false;

            if (file.length() != 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                userList = (Set<User>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();

                for (User user : userList) {
                    if (user.getName().equals(name)) {
                        userList.remove(user);
                        marker = true;
                        System.out.println("user " + name + " deleted");
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(userList);
                objectOutputStream.close();
                fileOutputStream.close();
            }
            if (!marker) {
                System.out.println("no such user");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("exception in deleteUser");
            e.printStackTrace();
        }
    }
}
