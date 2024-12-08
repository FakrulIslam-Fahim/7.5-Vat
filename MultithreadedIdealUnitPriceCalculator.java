package com.example.idealunitpricecalculator;

import java.util.*;
import java.util.concurrent.*;

public class MultithreadedIdealUnitPriceCalculator {
public static List<DeductionResult<Double, Integer>> findIdealUnitPricesMultithreaded(
            double actualUnitPrice, int quantity, int vatRate, int maxResults, int threadCount) throws InterruptedException {

        double searchStart = actualUnitPrice - 800.0;
        double searchEnd = actualUnitPrice;
        double rangePerThread = (searchEnd - searchStart) / threadCount;

        List<DeductionResult<Double, Integer>> synchronizedResults = Collections.synchronizedList(new ArrayList<>());

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            double start = searchStart + i * rangePerThread;
            double end = start + rangePerThread;

            Thread thread = new Thread(() -> {
                List<DeductionResult<Double, Integer>> partialResults = findIdealUnitPricesInRange(start, end, quantity, vatRate, actualUnitPrice);
                synchronizedResults.addAll(partialResults);
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        synchronizedResults.sort(Comparator.comparingInt(a -> a.differenceFromOriginal));
        return synchronizedResults.size() > maxResults ? synchronizedResults.subList(0, maxResults) : synchronizedResults;
    }

    public static List<DeductionResult<Double, Integer>> findIdealUnitPricesInRange(double start, double end, int quantity, int vatRate, double actualUnitPrice) {
        List<DeductionResult<Double, Integer>> results = new ArrayList<>();

        for (double unitPrice = start; unitPrice < end; unitPrice += 0.01) {
            double roundedUnitPrice = Math.round(unitPrice * 100.0) / 100.0;
            double deductedTotalRaw = roundedUnitPrice * quantity;

            if (deductedTotalRaw != Math.floor(deductedTotalRaw)) continue;

            int deductedTotal = (int) deductedTotalRaw;
            int vatAmount = (deductedTotal * vatRate) / 1000;
            if ((deductedTotal * vatRate) % 1000 != 0) continue;

            int totalWithVAT = deductedTotal + vatAmount;
            int originalTotal = (int) Math.round(actualUnitPrice * quantity);

            if (totalWithVAT <= originalTotal) {
                int difference = originalTotal - totalWithVAT;
                results.add(new DeductionResult<>(roundedUnitPrice, deductedTotal, vatAmount, totalWithVAT, difference));
            }
        }

        return results;
    }
}
