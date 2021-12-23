package ua.com.alevel.task3;

import java.io.*;
import java.util.*;

public class Task3 {
    private final int[] dist;
    private final Set<Integer> settled;
    private final PriorityQueue<Node> pq;
    private final int V;
    List<List<Node>> adj;

    public Task3(int V) {
        this.V = V;
        dist = new int[V];
        pq = new PriorityQueue<>(V, new Node());
        settled = new HashSet<>();
    }

    public static void run() throws IOException {
        BufferedReader reader = getFileReader();

        int cityCount = Integer.parseInt(reader.readLine());
        List<List<Node>> point = new ArrayList<>();
        for (int i = 0; i < cityCount; i++) {
            List<Node> item = new ArrayList<>();
            point.add(item);
        }

        Map<String, Integer> pointNames = new HashMap<>();
        for (int i = 0; i < cityCount; i++) {
            String cityName = reader.readLine();
            pointNames.put(cityName, i);

            int nCount = Integer.parseInt(reader.readLine());
            for (int j = 0; j < nCount; j++) {
                String[] nCost = reader.readLine().split(" ");
                point.get(i).add(new Node(Integer.parseInt(nCost[0]) - 1, Integer.parseInt(nCost[1])));
            }
        }

        int wayCount = Integer.parseInt(reader.readLine());
        FileWriter fileWriter = new FileWriter("Output.txt", true);
        try {
            for (int i = 0; i < wayCount; i++) {
                Task3 task3 = new Task3(cityCount);
                String[] way = reader.readLine().split(" ");
                int[] dijkstra = task3.dijkstra(point, pointNames.get(way[0]));
                int dist = dijkstra[pointNames.get(way[1])];
                fileWriter.write("\n");
                String result = String.valueOf(dist);
                fileWriter.write(result);
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedReader getFileReader() throws IOException {
        String pathToFile = "Input.txt";
        File file = new File(pathToFile);
        return new BufferedReader(new FileReader(file));
    }

    public int[] dijkstra(List<List<Node>> adj, int src) {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        pq.add(new Node(src, 0));

        dist[src] = 0;
        while (settled.size() != V) {
            if (pq.isEmpty())
                return null;

            int u = pq.remove().node;

            settled.add(u);

            neighbours(u);
        }

        return dist;
    }

    private void neighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;


        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;

                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }

    static class Node implements Comparator<Node> {
        public int node;
        public int cost;

        public Node() {
        }

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2) {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }
    }
}