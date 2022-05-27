package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;

import java.util.List;

public class ILSSBRP implements SBRP{
    private List<PerturbationFunc> perturbationFuncs;
    private SolutionGenerator initialSolutionGenerator;
    private FitnessFunction fitnessFunction;
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
    public SBRPSolution getRepositioningPath(Graph graph) {
        int iteration = 0;
        SBRPSolution bestSolution = null;
        while(iteration < algorithmIterations){
            int repeat = 0;
            SBRPSolution iterationSolution = generateInitialSolution();
            SBRPSolution newSolution = new SBRPSolution(iterationSolution);
            while(repeat < localIterations){
                if(!isFeasible(newSolution)){
                    addUnbalanced(newSolution);
                }

                newSolution = neighbouringSolution(newSolution);

                if(fitnessFunction.fitness(newSolution) < fitnessFunction.fitness(iterationSolution)){
                    iterationSolution = newSolution;
                    repeat = 0;
                }
                newSolution = perturb(iterationSolution);
                repeat++;
            }

            if(bestSolution == null
                    || fitnessFunction.fitness(iterationSolution) < fitnessFunction.fitness(bestSolution)){
                bestSolution = iterationSolution;
            }
            ++iteration;
        }

        return bestSolution;
    }

    private SBRPSolution generateInitialSolution(){
        return null;
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
