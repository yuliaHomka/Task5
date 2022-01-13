public class Main {

    public static void main (String args []) {

        int N = 1000;
        int testsCount = 1000;

        var percolationStats = new PercolationStats(N, testsCount);
//        percolationStats.printToFile("result.txt");
        percolationStats.run();
   }

}
