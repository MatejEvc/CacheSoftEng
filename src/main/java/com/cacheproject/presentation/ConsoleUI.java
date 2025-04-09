package com.cacheproject.presentation;

import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.model.CacheSystem;
import com.cacheproject.util.CacheStateRenderer;
import com.cacheproject.util.SimulationStepPrinter;

import java.util.Scanner;

public class ConsoleUI {
    private final CacheSystem cacheSystem;
    private final CacheStateRenderer cacheStateRenderer;
    private final SimulationStepPrinter simulationStepPrinter;
    private final Scanner scanner;

    public ConsoleUI(CacheSystem cacheSystem) {
        this.cacheSystem = cacheSystem;
        this.cacheStateRenderer = new CacheStateRenderer();
        this.simulationStepPrinter = new SimulationStepPrinter();
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println("Welcome to my Cache Sim");
        while (true){
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Access memory address");
            System.out.println("2. Display cache state");
            System.out.println("3. Run simulation");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> accessMemoryAddress();
                case 2 -> displayCacheState();
                case 3 -> runSimulation();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void accessMemoryAddress(){
        System.out.print("Enter memory address: ");
        int addressValue = scanner.nextInt();
        Address address = new Address(addressValue);
        CacheAccessResult result = cacheSystem.access(address);
        simulationStepPrinter.printAccessResult(address, result);
    }

    private void displayCacheState(){
        System.out.println(getCacheState());
    }

    private String getCacheState(){
        return cacheStateRenderer.renderAsAscii(cacheSystem);
    }
    private String runSimulation(){
        System.out.println("Running simulation with addresses: 13, 42, 8, 15, 73");
        int[] addresses = {13, 42, 8, 15, 73};
        for (int addr : addresses) {
            Address address = new Address(addr);
            CacheAccessResult result = cacheSystem.access(address);
            simulationStepPrinter.printAccessResult(address, result);
        }
            displayCacheState();
        return getCacheState();
        }

}
