Eric Prouty
Mike Gheorghe

To run this program you can either execute the included jar file "java -jar CSP.jar constraint01.dat" with the selected constraint file as the first argument as shown.  Or the project can be loaded into exclipse if desired and can be run by changing the given arguments under run configurations.

To approach this problem it was first necessary to read in the appropriate constraints and build a way of representing the data.  To do this we created objects representing the bags and stored them within a constraint net.  The items were treated as the nodes of the net, while the bags simply made up the valid domain.  In order to ensure node consistency the unary constraints were applied directly to the items which maintain a list of which bags are valid for each item. In order to represent the binary constraints we created a ConstraintMatrix which are treates as the arcs of the net.  These constraint matrixs were fully configured to represent all binary constraints of two items and then were modified to ensure that they still met the unary constraints.  Once all of these arcs were created, arc consistency was then obtained using the AC-3 algorithm, this means that each individual node only has in its list of valid bags, those which can actually complete the problem.  Once all of this has been completed our constraint net represented a fully node and arc consistent version of the problem.
We then employed backtracking search in order to solve the CSP.  Selection of the variable (item) that would first be assigned was done using the MRV heuristic, to do this we selected the item that, if represented by a constraint matrix, had the fewest possible correct placements.  If it was not connected to a arc, then it simply chose the item that had the least number of possible bags in its valid bags list.  Next the domain (bag) to be assigned to was selected based on the LCV heuristic.  This selects the bag, of the possible domain available to the selected item, based on which bag has the least number of intersecting arcs.

To test out our program we ran the 10 given constraint problems, in most cases it matched the correct solution with the one that was given.  However, it did find a different solution to a couple of problems which we verified by hand as still being a valid answer. Our program ran fantastically and returned a result instantaneously in all cases.

Comparison of the algorithm running:
This was done based upon constraint04.dat

Backtracking alone: 	3582 steps
Backtracking + LCV: 	3170 steps
BT + LCV + MRV:			1608 steps
BT + LCV + MRV + AC3: 	1360 steps


Comparing the runtimes of just backtracking search using next in line dumb choosing of the variable to be assigned produces a significantly slower solution than with the introduction of the MRV and LCV heuristics.  For our implementation the change in runtime was almost a 60% increase.  On constraint04.dat it improved from ~3600 steps to only ~1400.

Please see the contained traceBT, traceBTLCV, traceBTLCVMRV, and traceBTLCVMRVAC3 files for traces of the program running.