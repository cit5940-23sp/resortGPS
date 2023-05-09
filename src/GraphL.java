import java.util.HashMap;

/**
 * @author Park_Navigation_Team
 *
 */
public class GraphL implements Graph {

    private Edge[]   nodeArray;
    private Coordinates[] cordVals;
    private int      numEdge;

    private HashMap<Edge, Boolean> isWheelChairFriendly = new HashMap<>();



    /**
     * Empty no argument constructor
     */
    GraphL()
    {
        // No real constructor needed
    }


    /**
     * Initialize the graph with n vertices.
     *
     * @param n the number of vertices in the graph
     */
    public void init(int n)
    {
        nodeArray = new Edge[n];
        // List headers;
        for (int i = 0; i < n; i++)
            nodeArray[i] = new Edge(-1, -1, null, null);
        cordVals = new Coordinates[n];
        numEdge = 0;
    }



    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph
     */
    public int nodeCount()
    {
        return nodeArray.length;
    }


    /**
     * Returns the current number of edges in the graph.
     *
     * @return the number of edges in the graph
     */
    public int edgeCount()
    {
        return numEdge;
    }



    /**
     * Returns the coordinates of the vertex with index v.
     *
     * @param v the index of the vertex
     * @return the coordinates of the vertex
     */
    public Coordinates getCord(int v)
    {
        return cordVals[v];
    }


    /**
     * Sets the coordinates of the vertex with index v.
     *
     * @param v the index of the vertex
     * @param val the coordinates of the vertex
     */
    public void setCord(int v, Coordinates val)
    {
        cordVals[v] = val;
    }


    /**
     * Returns the link in v's neighbor list that preceeds the one with w (or where it would be).
     *
     * @param v the index of the first vertex
     * @param w the index of the second vertex
     * @return the link in v's neighbor list that preceeds the one with w
     */
    private Edge find(int v, int w)
    {
        Edge curr = nodeArray[v];
        while ((curr.next != null) && (curr.next.vertex < w))
            curr = curr.next;
        return curr;
    }


    /**
     * Adds a new edge from node v to node w with weight wgt.
     *
     * @param v the index of the first vertex
     * @param w the index of the second vertex
     * @param wgt the weight of the edge
     */
    public void addEdge(int v, int w, int wgt)
    {
        if (wgt == 0)
            return; // Can't store weight of 0
        // return the one proceeds w
        Edge curr = find(v, w);
        if ((curr.next != null) && (curr.next.vertex == w))
            curr.next.weight = wgt;
        else
        {
            curr.next = new Edge(w, wgt, curr, curr.next);
            if (curr.next.next != null)
                curr.next.next.prev = curr.next;
        }
        numEdge++;
    }


    /**

     Gets the weight value of the edge between vertices v and w.
     @param v the starting vertex
     @param w the ending vertex
     @return the weight value of the edge, or 0 if the edge does not exist
     */
    public int weight(int v, int w)
    {
        Edge curr = find(v, w);
        if ((curr.next == null) || (curr.next.vertex != w))
            return 0;
        else
            return curr.next.weight;
    }



    /**
     *
     Removes the edge between vertex v and vertex w from the graph.
     @param v the index of the first vertex
     @param w the index of the second vertex
     */
    public void removeEdge(int v, int w)
    {
        Edge curr = find(v, w);
        if ((curr.next == null) || curr.next.vertex != w)
            return;
        else
        {
            curr.next = curr.next.next;
            if (curr.next != null)
                curr.next.prev = curr;
        }
        numEdge--;
    }


    /**

     Returns true if and only if the graph has the edge between vertices v and w
     @param v the vertex where the edge starts
     @param w the vertex where the edge ends
     @return true if the edge between vertices v and w exists in the graph, false otherwise
     */
    public boolean hasEdge(int v, int w)
    {
        return weight(v, w) != 0;
    }


    /**

     Returns an array containing the indices of the neighbors of the vertex with index v.
     @param v the index of the vertex
     @return an array of integers representing the indices of the neighbors of the vertex with index v
     */
    public int[] neighbors(int v)
    {
        int cnt = 0;
        Edge curr;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next)
            cnt++;
        int[] temp = new int[cnt];
        cnt = 0;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next)
            temp[cnt++] = curr.vertex;
        return temp;
    }


    /**

     Checks whether the edge from node u to node v is wheelchair-friendly.
     @param u the start vertex of the edge
     @param v the end vertex of the edge
     @return true if the edge is wheelchair-friendly, false otherwise
     */
    public boolean isWheelChairFriendly(int u, int v) {
        Edge e = find(u, v);
        // Check if the edge is present in the HashMap and return the corresponding value.
        // Return false if the edge is not present in the HashMap.
        Boolean isFriendly = this.isWheelChairFriendly.get(e);
        return isFriendly != null ? isFriendly : false;
    }


    /**

     Sets the wheelchair accessibility of the edge between nodes u and v to the specified value.
     @param u the starting node of the edge
     @param v the ending node of the edge
     @param isWheel a boolean representing whether the edge is wheelchair accessible or not
     */
    public void setWheelChairFriendly(int u, int v, boolean isWheel) {
        Edge e = find(u, v);
        isWheelChairFriendly.put(e, isWheel);

    }

    private class Edge { // Doubly linked list node
        int  vertex, weight;
        Edge prev;
        Edge next;


        Edge(int v, int w, Edge p, Edge n)
        {
            vertex = v;
            weight = w;
            prev = p;
            next = n;
        }
    }
}