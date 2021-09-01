public class Dijkstra {
    //From geeks for geeks
    static final int V = 10;
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    void printSolution(int dist[], int n, int end) {
        System.out.println("Vertex " + end + " is " + dist[end] + " units of distance from the source!");
    }


    void dijkstra(int graph[][], int src, int end)
    {
        int dist[] = new int[V];


        Boolean sptSet[] = new Boolean[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)


                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        // print the constructed distance array
        printSolution(dist, V, end);
    }


    public static void main(String[] args) {
        int graph[][] = new int[][]{{0, 50, 7, 9, 0, 0, 0, 0, 0, 0},
                {50, 0, 30, 0, 2, 0, 98, 0, 0, 0},
                {7, 30, 0, 6, 27, 15, 0, 0, 0, 0},
                {9, 0, 6, 0, 0, 10, 0, 0, 3, 0},
                {0, 2, 27, 0, 0, 11, 120, 105, 0, 0},
                {0, 0, 15, 10, 11, 0, 0, 119, 4, 0},
                {0, 98, 0, 0, 120, 0, 0, 5, 0, 66},
                {0, 0, 0, 0, 105, 119, 5, 0, 122, 62},
                {0, 0, 0, 3, 0, 4, 0, 122, 0, 190},
                {0, 0, 0, 0, 0, 0, 66, 62, 190, 0}};


        Dijkstra test = new Dijkstra();
        test.dijkstra(graph, 0, 9);

    }
}
