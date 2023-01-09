package service;

import model.Account;

import java.util.ArrayList;
import java.util.Scanner;

public interface ICrudManager<E> {
    E create(Scanner scanner);

    void deleteById(Scanner scanner);

    void title();

    void displayAll();
}
