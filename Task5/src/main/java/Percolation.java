public class Percolation {
    private int N;



    private boolean[][] cellOpened;
    private UnionFind unionFind;
    private int openCellsCount = 0; // count of opened cells;
    private int topCell = 0;
    private int finalCell;

    public int getOpenCellsCount() {
        return openCellsCount;
    }

    public Percolation(int size) {
        this.N = size;
        this.finalCell = size;
        this.cellOpened = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int r = 0; r < N; r++)
                this.cellOpened[i][r] = false;
        this.openCellsCount = 0;
        this.unionFind = new UnionFind(N * N + 2);
    }

    public boolean percolates() {
        return this.unionFind.find(topCell) == this.unionFind.find(this.finalCell);
    }

    public void open(int row, int col) {
        this.openCellsCount++;
        this.cellOpened[row - 1][col - 1] = true;
        unionWithAdjoiningCells(row, col);
    }


    private void unionWithAdjoiningCells(int row, int col) {
        if (row == 1) {
            unionFind.union(getIndexUnionFind(row, col), topCell);
        }
        if (row == N) {
            unionFind.union(getIndexUnionFind(row, col), finalCell);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(getIndexUnionFind(row, col), getIndexUnionFind(row, col - 1));
        }
        if (col < N && isOpen(row, col + 1)) {
            unionFind.union(getIndexUnionFind(row, col), getIndexUnionFind(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            unionFind.union(getIndexUnionFind(row, col), getIndexUnionFind(row - 1, col));
        }
        if (row < N && isOpen(row + 1, col)) {
            unionFind.union(getIndexUnionFind(row, col), getIndexUnionFind(row + 1, col));
        }
    }

    public boolean isOpen(int row, int col) {
        return cellOpened[row - 1][col - 1];
    }

    private int getIndexUnionFind(int row, int col) {
        return N * (row - 1) + col;
    }
}
