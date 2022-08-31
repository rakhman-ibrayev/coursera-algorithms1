## [Programming Assignment: Collinear Points](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

### The problem

Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of the points.

### Brute force

Write a program BruteCollinearPoints.java that examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments. To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p and r, and between p and s are all equal.

### A faster, sorting-based solution

Remarkably, it is possible to solve the problem much faster than the brute-force solution described above. Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.

	- Think of p as the origin.
 	- For each other point q, determine the slope it makes with p.
	- Sort the points according to the slopes they makes with p.
	- Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.


### Grade: 96/100