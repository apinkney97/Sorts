set datafile separator ","
set term pdf size 30cm, 21cm
set output "allplots.pdf"
set log x 2
set log y
set xra [1:]
set yra [1:1e10]
set key inside left
set title "Runtimes for various sorts (averaged over 1000 runs for each input size)"
set yla "runtime (ns)"
set xla "input size"
plot "0.csv" title "Bubble Sort", "1.csv" title "Better Bubble Sort", "2.csv" title "Best Bubble Sort", "3.csv" title "Cocktail Sort", "4.csv" title "Selection Sort", "5.csv" title "Merge Sort", "6.csv" title "Quicksort", "7.csv" title "Heapsort", "8.csv" title "Java 7 default (Dual-Pivot Quicksort)", "9.csv" title "Bogosort", "10.csv" title "Bozosort", 50 * x title "50 n", x*x title "n^2", x * x * x title "n^3", 10 * x * log(x) title "10 n log n", gamma(x + 1) title "n!"

