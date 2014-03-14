/*
 *  Document : GridModelTester.java
 *  Created on Mo, Feb 20 2012, 21:27:37
 *  Copyright (C) 2012 Robert Diawara
 * 
 *  This file is part of OpenCms Theme Engine.
 * 
 *  OpenCms Theme Engine is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  OpenCms Theme Engine is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.componio.opencms.modules.eight.skinndriva.rd.engine.model;

/**
 *
 * @author robert
 */
public class GridModelTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GridModel grid = new GridModel();
        
        try{
            //grid.setColumnCount(16);
            //grid.setColumnWidth(40);
            grid.setOrientation(GridModel.ORIENTATION_RIGHT);
            System.out.println(grid.generateCSSCode());
        }catch(Exception ex){
            ex.printStackTrace(System.out);
        }
    }
}
