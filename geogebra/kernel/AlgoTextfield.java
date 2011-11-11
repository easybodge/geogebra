/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

*/

package geogebra.kernel;

import geogebra.euclidian.EuclidianConstants;


/**
 * Creates textfield linked with geo
 * @author Zbynek Konecny
 */

public class AlgoTextfield extends AlgoElement {

	private GeoElement inputGeo; //input
    private GeoTextField textfield; //output	

    AlgoTextfield(Construction cons, String label, GeoElement inputGeo) {
        super(cons);
        this.inputGeo = inputGeo;
               
        textfield = new GeoTextField(cons);
        if(inputGeo != null)
        	textfield.setLinkedGeo(inputGeo);
        textfield.setAbsoluteScreenLoc(30, 30);
        setInputOutput();
        compute();
        textfield.setLabel(label);
        textfield.setLabelVisible(true);
		textfield.setEuclidianVisible(true);
		textfield.update();
    }

    @Override
	public String getClassName() {
        return "AlgoTextfield";
    }

    @Override
	protected void setInputOutput(){
    	if(inputGeo == null)
    		input = new GeoElement[0];
    	else{
    		input = new GeoElement[1];
        	input[0] = inputGeo;
    	}
    	
        super.setOutputLength(1);
        super.setOutput(0, textfield);
        setDependencies(); // done by AlgoElement
    }

    GeoTextField getResult() {
        return textfield;
    }

    @Override
	protected final void compute() {
    	
    }
    
    @Override
	public int getRelatedModeID() {
    	return EuclidianConstants.MODE_TEXTFIELD_ACTION;
    }
  
}
