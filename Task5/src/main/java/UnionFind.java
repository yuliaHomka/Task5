public class UnionFind {
    private int[] parent;


    public UnionFind(int n) {
        parent = new int[n];
        for (var i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public void union(int x, int y)  {
        var px = find(x);
        var py = find(y);
        if (px != py) {
            parent[px] = py;
        }
    }
}
