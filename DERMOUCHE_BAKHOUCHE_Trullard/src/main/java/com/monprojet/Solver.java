package com.monprojet;
import java.util.*;

import com.monprojet.Colon;
import com.monprojet.Colonie;
public class Solver {
    private static final double COOLING_RATE = 0.95;
    private static final double INITIAL_TEMPERATURE = 1000.0;
    private static final int ITERATIONS_PER_TEMP = 100;

    private Random random = new Random();

    public List<String> solve(Colonie colonie) {
        List<Colon> colons = colonie.getListeColons();
        List<String> ressources = colonie.getRessourceList();

        if (colons.size() != ressources.size()) {
            throw new IllegalArgumentException("Le nombre de colons doit être égal au nombre de ressources.");
        }
        List<String> currentSolution = new ArrayList<>(ressources);
        Collections.shuffle(currentSolution);
        assignerSolution(currentSolution, colons, colonie);
        int currentCost = colonie.getJalousie();

        List<String> bestSolution = new ArrayList<>(currentSolution);
        int bestCost = currentCost;

        double temperature = INITIAL_TEMPERATURE;
        while (temperature > 1e-3) {
            for (int i = 0; i < ITERATIONS_PER_TEMP; i++) {
                List<String> newSolution = new ArrayList<>(currentSolution);
                int index1 = random.nextInt(ressources.size());
                int index2 = random.nextInt(ressources.size());
                Collections.swap(newSolution, index1, index2);
                assignerSolution(newSolution, colons, colonie);
                int newCost = colonie.getJalousie();
                if (newCost < currentCost || Math.exp((currentCost - newCost) / temperature) > random.nextDouble()) {
                    currentSolution = new ArrayList<>(newSolution);
                    currentCost = newCost;

                    if (currentCost < bestCost) {
                        bestSolution = new ArrayList<>(currentSolution);
                        bestCost = currentCost;
                    }
                }
            }
            temperature *= COOLING_RATE;
        }
        assignerSolution(bestSolution, colons, colonie);
        return bestSolution;
    }

    private void assignerSolution(List<String> solution, List<Colon> colons, Colonie colonie) {
        for (int i = 0; i < colons.size(); i++) {
            colonie.assignerRessource(colons.get(i), solution.get(i));
        }
    }
}



