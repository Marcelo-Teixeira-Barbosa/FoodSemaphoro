package pratica2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Kitchen {
    private Semaphore semaphore;

    public Kitchen(int capacity) {
        semaphore = new Semaphore(capacity);
    }

    public void prepareFood(String customerName) throws InterruptedException {
        semaphore.acquire();
        System.out.println("Cozinha cozinhando para: " + customerName);
        // aguarde 2 segundos
        Thread.sleep(2000);
        System.out.println("Cozinha preparou comida para: " + customerName);
        semaphore.release();
    }
}

class Customer extends Thread {
    private String name;
    private Kitchen kitchen;

    public Customer(String name, Kitchen kitchen) {
        this.name = name;
        this.kitchen = kitchen;
    }

    public void run() {
        try {
            System.out.println(name + " aguardando fazer um pedido");
            kitchen.prepareFood(name);
            if (ThreadLocalRandom.current().nextDouble() < 0.5) {
                System.out.println(name + ": Achei delicioso!");
            } else {
                System.out.println(name + ": Você é a vergonha da profisson!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class FoodOrderingSystem {
    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen(2); // Cozinha que atende até 2 pessoas por vez

        Customer customer1 = new Customer("Paola", kitchen);
        Customer customer2 = new Customer("Fogaça", kitchen);
        Customer customer3 = new Customer("Jacquin", kitchen);

        customer1.start();
        customer2.start();
        customer3.start();
    }
}
