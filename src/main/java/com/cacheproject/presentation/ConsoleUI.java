package com.cacheproject.presentation;

import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.service.CacheService;
import com.cacheproject.util.CacheStateRenderer;
import com.cacheproject.util.SimulationStepPrinter;
import com.cacheproject.util.StatisticsPrinter;

import java.util.Scanner;

public class ConsoleUI {

    private final CacheService cacheService;
    private final CacheStateRenderer cacheStateRenderer;
    private final SimulationStepPrinter simulationStepPrinter;
    private final StatisticsPrinter statisticsPrinter;
    private final Scanner scanner;
    private final String cacheId;

    public ConsoleUI(CacheService cacheService, String cacheId) {
        this.cacheService = cacheService;
        this.cacheStateRenderer = new CacheStateRenderer();
        this.simulationStepPrinter = new SimulationStepPrinter();
        this.statisticsPrinter = new StatisticsPrinter();
        this.scanner = new Scanner(System.in);
        this.cacheId = cacheId;
    }

    public void start(){
        System.out.println("Welcome to my Cache Simulator");
        while (true){
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Access memory address");
            System.out.println("2. Display cache state");
            System.out.println("3. Run simulation");
            System.out.println("4. Show statistics");
            System.out.println("5. Reset statistics");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> accessMemoryAddress();
                case 2 -> displayCacheState();
                case 3 -> runSimulation();
                case 4 -> showStatistics();
                case 5 -> resetStatistics();
                case 6 -> {
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
        scanner.nextLine(); // Consume newline
        Address address = new Address(addressValue);
        CacheAccessResult result = cacheService.accessCache(cacheId, address.getValue());
        simulationStepPrinter.printAccessResult(address, result);
    }

    private void displayCacheState(){
        System.out.println(cacheStateRenderer.renderAsAscii(cacheService.getCache(cacheId)));
    }

    private void runSimulation(){
        System.out.println("Running simulation with addresses: 13, 42, 8, 15, 73");
        int[] addresses = {13, 42, 8, 15, 73};
        for (int addr : addresses) {
            Address address = new Address(addr);
            CacheAccessResult result = cacheService.accessCache(cacheId, address.getValue());
            simulationStepPrinter.printAccessResult(address, result);
        }
        displayCacheState();
    }

    private void showStatistics() {
        statisticsPrinter.printStatistics(cacheService.getStatistics(cacheId));
    }

    private void resetStatistics() {
        cacheService.getStatistics(cacheId).reset();
        System.out.println("Statistics have been reset.");
    }

}
