package com.kk.test.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class UITest extends JFrame implements DropTargetListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UITest() {
		// TODO Auto-generated constructor stub
		
		JPanel panel = new JPanel();
		panel.setBounds(50, 50, 400, 600);
		panel.setDropTarget(new DropTarget(this,DnDConstants.ACTION_COPY_OR_MOVE, this, true));
		
		
		setBounds(50, 50, 400, 600);
		add(panel);
		setVisible(true);
		
		
		
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("dragEnter");
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		//System.out.println("dragOver");
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("dropActionChanged");
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		System.out.println("dragExit");
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("drop");
		
		StringBuffer strbuffer = new StringBuffer();
		
        DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();
        if(dataFlavors[0].match(DataFlavor.javaFileListFlavor)){
            try {
                Transferable tr = dtde.getTransferable();
                Object obj = tr.getTransferData(DataFlavor.allHtmlFlavor);
                List<File> files = (List<File>)obj;
                for(int i = 0; i < files.size(); i++){
                	strbuffer.append(files.get(i).getAbsolutePath()+"/r/n");
                }
            } catch (UnsupportedFlavorException ex) {

            } catch (IOException ex) {

            }
        }
        
        System.out.println("drop:"+ strbuffer.toString());
		
		
		
	}

	
	

	
	
}


