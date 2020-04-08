package org.geogebra.common.euclidian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoLocusStroke;
import org.geogebra.common.kernel.geos.groups.Group;
import org.geogebra.common.util.CopyPaste;

public class LayerManager {

	private List<GeoElement> drawingOrder = new ArrayList<>();
	private boolean renaming = false;
	private LayersForGroup groups = new LayersForGroup(drawingOrder);

	private int getNextOrder() {
		return drawingOrder.size();
	}

	/**
	 * Add geo on the last position and set its ordering
	 */
	public void addGeo(GeoElement geo) {
		if (renaming) {
			return;
		}
		if (geo instanceof GeoLocusStroke) {
			GeoLocusStroke stroke = (GeoLocusStroke) geo;
			if (stroke.getSplitParentLabel() != null) {
				int order = stroke.getConstruction()
						.lookupLabel(stroke.getSplitParentLabel()).getOrdering();
				drawingOrder.add(order, geo);
				updateOrdering();
				return;
			}
		}

		if (!geo.isMask()) {
			geo.setOrdering(getNextOrder());
			drawingOrder.add(geo);
		}
	}

	/**
	 * Remove the geo and update the ordering of all other elements
	 */
	public void removeGeo(GeoElement geo) {
		if (renaming) {
			return;
		}
		drawingOrder.remove(geo);
		updateOrdering();
	}

	public void clear() {
		drawingOrder.clear();
	}

	/**
	 * Move the geos in the selection exactly one step in front of the
	 * one with the highest priority in the selection
	 */
	public void moveForward(List<GeoElement> selection) {
		if (isGroupMember(selection)) {
			groups.moveForward(selection.get(0));
		} else {
			moveForwardSelection(selection);
		}
		updateOrdering();
	}

	private boolean isGroupMember(List<GeoElement> selection) {
		return selection.size() == 1 && selection.get(0).hasGroup();
	}

	public void moveForwardSelection(List<GeoElement> selection) {
		ArrayList<GeoElement> resultingOrder = new ArrayList<>(drawingOrder.size());
		int i = 0;
		int found = 0;

		while (i < drawingOrder.size() && found < selection.size()) {
			if (selection.contains(drawingOrder.get(i))) {
				found++;
			} else {
				resultingOrder.add(drawingOrder.get(i));
			}
			i++;
		}

		// Add one more element to _move forward_
		if (i < drawingOrder.size()) {
			resultingOrder.add(drawingOrder.get(i));
			i++;
		}

		addSorted(resultingOrder, selection);

		while (i < drawingOrder.size()) {
			resultingOrder.add(drawingOrder.get(i));
			i++;
		}

		drawingOrder = resultingOrder;
		updateOrdering();
	}



	/**
	 * Move the selection exactly one step behind the one with the
	 * lowest priority in the selection
	 */
	public void moveBackward(List<GeoElement> selection) {
		if (isGroupMember(selection)) {
			moveBackwardWithinGroup(selection.get(0));
		} else {
			moveBackwardSelection(selection);
		}
	}

	public void moveBackwardSelection(List<GeoElement> selection) {
		ArrayList<GeoElement> resultingOrder = new ArrayList<>(drawingOrder.size());
		int i = 0;

		if (!selection.contains(drawingOrder.get(0))) {
			while (i < drawingOrder.size() && !selection.contains(drawingOrder.get(i + 1))) {
				resultingOrder.add(drawingOrder.get(i));
				i++;
			}
		}

		addSorted(resultingOrder, selection);

		while (i < drawingOrder.size()) {
			if (!selection.contains(drawingOrder.get(i))) {
				resultingOrder.add(drawingOrder.get(i));
			}
			i++;
		}

		drawingOrder = resultingOrder;
		updateOrdering();
	}

	private void moveBackwardWithinGroup(GeoElement geo) {
		Group group = geo.getParentGroup();
		ArrayList<GeoElement> geos = group.getGroupedGeos();
		int index = geos.indexOf(geo);
		if (index > 0) {
			Collections.swap(geos, index, index - 1);
		}

		updateOrdering(geos, group.getMinOrder());
	}

	/**
	 * Move the selected geos to the top of the drawing priority list
	 * while respecting their relative ordering
	 */
	public void moveToFront(List<GeoElement> selection) {
		if (isGroupMember(selection)) {
			groups.moveToFront(selection.get(0));
		} else {
			moveSelectionToFront(selection);
		}
		updateOrdering();
	}

	public void moveSelectionToFront(List<GeoElement> selection) {
		ArrayList<GeoElement> resultingOrder = new ArrayList<>(drawingOrder.size());

		for (GeoElement geo : drawingOrder) {
			if (!selection.contains(geo)) {
				resultingOrder.add(geo);
			}
		}

		addSorted(resultingOrder, selection);

		drawingOrder = resultingOrder;
	}

	/**
	 * Move the selected geos to the botom of the drawing priority list
	 * while respecting their relative ordering
	 */
	public void moveToBack(List<GeoElement> selection) {
		if (isGroupMember(selection)) {
			groups.moveToBack(selection.get(0));
		} else {
			moveSelectionToBack(selection);
		}
		updateOrdering();
	}

	public void moveSelectionToBack(List<GeoElement> selection) {
		ArrayList<GeoElement> resultingOrder = new ArrayList<>(drawingOrder.size());
		addSorted(resultingOrder, selection);

		for (GeoElement geo : drawingOrder) {
			if (!selection.contains(geo)) {
				resultingOrder.add(geo);
			}
		}

		drawingOrder = resultingOrder;
	}

	private void moveToBackWithinGroup(GeoElement geo) {
		Group group = geo.getParentGroup();

		ArrayList<GeoElement> geos = group.getGroupedGeos();
		geos.remove(geo);
		geos.add(0, geo);
		updateOrdering(geos, group.getMinOrder());
	}

	private void updateOrdering() {
		updateOrdering(drawingOrder, 0);
	}

	private void updateOrdering(List<GeoElement> geos, int minOrder) {
		for (int i = 0; i < geos.size(); i++) {
			geos.get(i).setOrdering(minOrder + i);
		}
	}

	private void addSorted(List<GeoElement> to, List<GeoElement> from) {
		List<GeoElement> copy = new ArrayList<>(from);
		sortByOrder(copy);
		to.addAll(copy);
	}

	private void sortByOrder(List<GeoElement> copy) {
		Collections.sort(copy, new Comparator<GeoElement>() {
			@Override
			public int compare(GeoElement a, GeoElement b) {
				if (isPasted(a) && !isPasted(b)) {
					return 1;
				}
				if (isPasted(b) && !isPasted(a)) {
					return -1;
				}
				return a.getOrdering() - b.getOrdering();
			}
		});
	}

	private boolean isPasted(GeoElement a) {
		return a.getLabelSimple() != null && a.getLabelSimple().startsWith(CopyPaste.labelPrefix);
	}

	/**
	 * Update the list from geos
	 */
	public void updateList() {
		sortByOrder(drawingOrder);
		updateOrdering(); // remove potential gaps
	}

	public void setRenameRunning(boolean renaming) {
		this.renaming = renaming;
	}
}
