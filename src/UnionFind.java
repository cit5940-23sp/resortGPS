/**
 * A class that implements the Union-Find data structure.
 * Supports finding the root of a set and merging two sets.
 */
public class UnionFind {
    private int[] parent;
    private int[] rank;

    /**
     * Constructor that initializes parent and rank arrays
     * with values from 0 to size - 1.
     *
     * @param size The size of the UnionFind structure.
     */
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }



    /**
     * Find the root of a set given an element in the set.
     * Uses path compression to optimize performance.
     *
     * @param x The element to find the root for.
     * @return The root of the set.
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }



    /**
     * Merge two sets together.
     * Uses union-by-rank to optimize performance.
     *
     * @param x The first element to merge.
     * @param y The second element to merge.
     * @return True if the sets were merged, false otherwise.
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        return true;
    }
}