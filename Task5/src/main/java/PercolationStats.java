import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class PercolationStats {
    private int testsCount;
    private int N;
    private int nSquared;
    private double[] results;


    public PercolationStats(int n, int tests){
        this.N = n;
        this.nSquared = n * n;
        this.testsCount = tests;
        this.results = new double[tests];
    }

    public void run(){
        for(int test = 0; test < testsCount; test++) {
//        int testIdx = 0;
            Random rng = new Random();
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()) {
                int i = rng.nextInt(N) + 1;
                int j = rng.nextInt(N) + 1;
                percolation.open(i, j);
            }
//            System.out.println("results = " + (double) percolation.getOpenCellsCount() / (nSquared));
            results[test] = ((double) percolation.getOpenCellsCount()) / (nSquared);
        }

        System.out.println("Mean: " + mean());
        System.out.println("Variance: " + variance());
    }

    public void printToFile(String fileName) {

        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int maxOpenCells = 0; maxOpenCells < nSquared; maxOpenCells += 10) {
                if (maxOpenCells % 100 == 0) System.out.println(maxOpenCells);
                int percolateCount = 0;
                for (int test = 0; test < testsCount; test++) {
                    Random rng = new Random();
                    Percolation percolation = new Percolation(N);
                    while (percolation.getOpenCellsCount() < maxOpenCells) {
                        int i = rng.nextInt(N) + 1;
                        int j = rng.nextInt(N) + 1;
                        percolation.open(i, j);
                    }
                    if (percolation.percolates())
                        percolateCount++;
                }
//                System.out.println(((double) maxOpenCells) / (N * N) + "   " + ((double) percolateCount) / testsCount);
                writer.printf(Locale.ROOT, "%.3f, %.3f\n", ((double) maxOpenCells) / (nSquared), ((double) percolateCount) / testsCount);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Can not create result file");
        }
    }

    public double mean() {
        double sum = 0;
        for (var result: results) {
            sum += result;
        }
        return (double) sum/ testsCount;
    }

    public double variance() {
        double mean = mean();
        double sum = 0;

        for (double result : results) {
            sum += (result - mean) * (result - mean);
        }
        return sum / testsCount;
    }
}
