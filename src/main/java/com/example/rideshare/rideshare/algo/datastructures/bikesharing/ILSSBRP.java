package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.graph.flow.FordFulkerson;
import com.example.rideshare.rideshare.algo.datastructures.graph.flow.MaxFlow;
import com.example.rideshare.rideshare.algo.datastructures.model.Copyable;
import com.example.rideshare.rideshare.algo.datastructures.model.FlowNode;
import com.example.rideshare.rideshare.algo.datastructures.model.Station;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ILSSBRP implements SBRP{
    private List<PerturbationFunc> perturbationFuncs;
    private SolutionGenerator initialSolutionGenerator;
    private FitnessFunction fitnessFunction;
    private Graph<Station> graph;
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
    public SBRPSolution getRepositioningPath(Graph<Station> graph){
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

    private boolean isFeasible(SBRPSolution solution)
    {
        Graph<FlowNode> flowNetwork = createFlowGraph(solution);
        FordFulkerson<FlowNode> maxFlowAlg = new FordFulkerson<>();
        MaxFlow<FlowNode> maxFlow = maxFlowAlg.getMaximumFlow(flowNetwork, 0, -1);
        var stations = graph.getVerticesMap();
        boolean feasible = true;
        for (var edgeFlowEntry:
             maxFlow.getFlow_map().entrySet()) {
            if(edgeFlowEntry.getKey().getSource() == 0){
                int indexInStationGraph = flowNetwork.getVerticesMap().get(edgeFlowEntry.getKey().getTarget()).getNodeInGraph();
                if(stations.get(indexInStationGraph).getStationed() != edgeFlowEntry.getValue()){
                    feasible = false;
                }
            }
        }
        return true;
    }

    private Graph<FlowNode> createFlowGraph(SBRPSolution solution){
        var route = solution.getRoutes();
        Graph<FlowNode> flowNetwork = new Graph<>(route.size() + 2);
        int source = 0;
        int sink = -1;

        flowNetwork.getVerticesMap().put(source, new FlowNode(0, 1));
        flowNetwork.getVerticesMap().put(sink, new FlowNode(0, 2));

        var stations = graph.getVerticesMap();
        int truckCapacity = solution.getVehicleLoads().get(0);
        for (int index = 1; index < route.size()-1; index++) {
            int stationId = route.get(index);
            int occurrence = (int) route.stream().limit(index + 1).filter(id -> id == stationId).count();
            flowNetwork.getVerticesMap().put(index, new FlowNode(stationId, occurrence));

            int edgeCapacity = stations.get(stationId).getStationed();

            if(occurrence == 1){
                flowNetwork.setDirectedEdge(source, index, stations.get(stationId).getStationed());
            }
            else if(occurrence == (int) route.stream().filter(id -> id == stationId).count()){
                flowNetwork.setDirectedEdge(index, sink, stations.get(stationId).getStationedAfterServicing());
            }
            else{
                var optPreviousStationOccurrence = flowNetwork.getVerticesMap().entrySet().stream()
                        .filter(entry -> entry.getValue().getNodeInGraph().equals(stationId) && entry.getValue().getNrOfOccurrence().equals(occurrence - 1))
                        .map(Map.Entry::getKey).findFirst();
                int finalIndex = index;
                optPreviousStationOccurrence.ifPresent(previous -> flowNetwork.setDirectedEdge(previous, finalIndex, stations.get(stationId).getCapacity()));
            }

            if(index > 1){
                flowNetwork.setDirectedEdge(index-1, index, truckCapacity);
            }
        }
        return flowNetwork;
    }

    private long getStationOccurrence(SBRPSolution solution, int index){
        var route = solution.getRoutes();
        if(index >= route.size()) throw  new ArrayIndexOutOfBoundsException();
        int stationId = route.get(index);
        return solution.getRoutes().stream().filter(id -> id == stationId).count();
    }

    private void addUnbalanced(SBRPSolution solution){
        var stations = graph.getVerticesMap();
        int excess = graph.getVerticesMap().entrySet().stream().max(new DemandComparator()).get().getKey();
        int lack = graph.getVerticesMap().entrySet().stream().min(new DemandComparator()).get().getKey();

        if(solution.getRoutes().contains(excess)){
            int i = solution.getRoutes().indexOf(excess);
            solution.getRoutes().add(++i, lack);
            solution.getRoutes().add(i+1, excess);
        }
        else if(solution.getRoutes().contains(lack)){
            int i = solution.getRoutes().indexOf(lack);
            solution.getRoutes().add(++i, excess);
            solution.getRoutes().add(i+1, lack);
        }
        else {
            solution.getRoutes().add(solution.getRoutes().size()-2, excess);
            solution.getRoutes().add(solution.getRoutes().size()-2, lack);
        }
    }

    private SBRPSolution neighbouringSolution(SBRPSolution solution){
        return null;
    }

    private SBRPSolution perturb(SBRPSolution solution){
        return null;
    }

    private class DemandComparator implements Comparator<Map.Entry<Integer, Station>>{

        @Override
        public int compare(Map.Entry<Integer, Station> o1, Map.Entry<Integer, Station> o2) {
            return Integer.compare(o1.getValue().getDemand(), o2.getValue().getDemand());
        }
    }
}
