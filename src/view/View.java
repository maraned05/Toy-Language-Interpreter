package view;

import java.util.Scanner;

import controller.*;;

public class View {
    private IController controller;

    public View (Controller c) {
        this.controller = c;
    }
    
    public void executeProgram() {
        System.out.println("Please select one of the programs: \n1. int v; v = 2; print(v);" +
        "\n2. int a; int b; a = 2+3*5; b = a+1; print(b);\n3. bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)\n");
        System.out.println("Your option: ");
        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        scanner.close();
        
    }

}
