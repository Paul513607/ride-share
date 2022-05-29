package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Copyable;
import com.example.rideshare.rideshare.algo.datastructures.model.Station;

public interface SBRP {
    public SBRPSolution getRepositioningPath(Graph<Station> graph);
}
