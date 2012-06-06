package geogebra.common.cas.view;

import geogebra.common.kernel.geos.GeoCasCell;
import geogebra.common.main.AbstractApplication;

public interface CASTable {

	int getRowCount();

	int getRowHeight(int i);

	void setLabels();

	GeoCasCell getGeoCasCell(int n);

	AbstractApplication getApplication();

	void deleteAllRows();

	void insertRow(int rows, GeoCasCell casCell, boolean b);

	int[] getSelectedRows();

	int getSelectedRow();

	void stopEditing();

	void startEditingRow(int selectedRow);

	CASTableCellEditor getEditor();

	boolean isRowEmpty(int i);

	void insertRow(GeoCasCell newRowValue, boolean b);

	void deleteRow(int rowNumber);

	void setRow(int rowNumber, GeoCasCell casCell);

}
