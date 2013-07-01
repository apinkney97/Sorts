Sorts
=====

Timing tests for some sorting algorithms

Sorry for the interface not being very intuitive... hopefully if you can read
the code, you can work out how to run it.

I suggest that you redirect the output to a file (with a .csv extension) and
open with a spreadsheet. Or plot in GnuPlot! 

e.g. run with `java Sorts 6 > quicksort.csv`

If you're using bash, try `for i in {0..11}; do java Sorts $i > $i.csv; done`

You will probably have to kill the process (with ctrl-c) unless you fancy
waiting forever. Even if you decrease the max value of inputSize, you'll
still be waiting a long time, especially for bogosort and bozosort.

A gnuplot .plt file is included, that attempts to plot files called 0.csv
to 10.csv. This uses a log-log graph as otherwise it would be difficult to
show all the data on one plot. Run with `gnuplot plotall.plt` -- a file called
allplots.pdf will be created.

Here's one I made earlier: http://eprg.org/allplots.pdf

