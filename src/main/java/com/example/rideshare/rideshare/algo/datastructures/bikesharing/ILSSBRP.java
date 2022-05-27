package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Copyable;
import com.example.rideshare.rideshare.algo.datastructures.model.Station;

import java.util.List;

public class ILSSBRP implements SBRP{
    private List<PerturbationFunc> perturbationFuncs;
    private SolutionGenerator initialSolutionGenerator;
    private FitnessFunction fitnessFunction;
    private Graph<?> graph;
    private int algorithmIterations;
    private int localIterations;

    public ILSSBRP(List<PerturbationFunc> perturbationFuncs, SolutionGenerator solutionGenerator,
                   FitnessFunction function, int iterations, int repetitions){
        this.perturbationFuncs = perturbationFuncs;
        this.initialSolutionGenerator = solutionGenerator;
        this.fitnessFunction = function;
        this.algorithmIterations = iterations;
        this.localIterations = repetitions;
    }
    @Override
    public<T extends Copyable<T>> SBRPSolution getRepositioningPath(Graph<T> graph) {
        this.graph  = graph;
        int iteration = 0;
        SBRPSolution bestSolution = null;
        while(iteration < algorithmIterations){
            int repeat = 0;
            SBRPSolution iterationSolution = initialSolutionGenerator.getSolution();
            SBRPSolution newSolution = new SBRPSolution(iterationSolution);
            while(repeat < localIterations){
                if(!isFeasible(newSolution)){
                    addUnbalanced(newSolution);
                }

                newSolution = neighbouringSolution(newSolution);

                if(fitnessFunction.fitness(newSolution, graph) < fitnessFunction.fitness(iterationSolution, graph)){
                    iterationSolution = newSolution;
                    repeat = 0;
                }
                newSolution = perturb(iterationSolution);
                repeat++;
            }

            if(bestSolution == null
                    || fitnessFunction.fitness(iterationSolution, graph) < fitnessFunction.fitness(bestSolution, graph)){
                bestSolution = iterationSolution;
            }
            ++iteration;
        }

        return bestSolution;
    }

    private boolean isFeasible(SBRPSolution solution){
        return false;
    }

    private void addUnbalanced(SBRPSolution solution){

    }

    private SBRPSolution neighbouringSolution(SBRPSolution solution){
        return null;
    }

    private SBRPSolution perturb(SBRPSolution solution){
        return null;
    }
}
