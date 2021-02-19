package main;

public interface UserServiceInterface {
    default void addUser(String name, int age) {

    }

    default void deleteUser(String name) {

    }
}
