package edu.kit.pse.bdhkw.server.model;

import java.util.ListIterator;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.LinkedListWrapper;

/**
 * Takes a set of points and replaces all points within a cluster of certain size
 * with a single point.
 * TODO: Find appropriate algorithm in apache.commons.math.
 * TODO: K-Means algorithm does not fit our purpose.
 * @author Tarek Wilkening
 *
 */
public class Clusterer {
	/**
	 * Constant that defines a clusters size in meters
	 */
	private static final int clusterRadius = 10;
	/**
	 * Returns clustered list in O(n^2 + n)
	 * Clustering is calculated by radius
	 * @param list of GPS objects
	 * @return clustered list of GPS objects
	 */
	public static LinkedListWrapper<GpsObject> cluster(LinkedListWrapper<GpsObject> list) {
		// The cluster of the group
		LinkedListWrapper<GpsObject> cluster = new LinkedListWrapper<GpsObject>();
		GpsObject clusterPoint = new GpsObject();
		
		ListIterator<GpsObject> iterator;
		GpsObject comparator;
		
		for (GpsObject current : list) {
			iterator = list.listIterator();
			
			while (iterator.hasNext()) {
				comparator = iterator.next();
				// Skip equal objects (distance will be 0)
				if (current == comparator) {
					continue;
				}
				if (current.distanceTo(comparator) < clusterRadius) {
					cluster.add(comparator);
					iterator.remove();
				}
			}
		}
		for (GpsObject current : cluster) {
			clusterPoint.setLongitude(clusterPoint.getLongitude() + current.getLongitude());
			clusterPoint.setLatitude(clusterPoint.getLatitude() + current.getLatitude());
		}
		// Average coordinates
		clusterPoint.setLatitude(clusterPoint.getLatitude() / cluster.size());
		clusterPoint.setLongitude(clusterPoint.getLongitude() / cluster.size());
		return list;
	}
}
