package gui.Controls;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class FileController extends JPanel {
	JList list = null;
	JScrollPane listScroller = null;
	Controller myController = null;
	
	public FileController(Controller controller){
		this.myController = controller;
		
		Vector<String> data = getFileNames("midiFiles");
		list = new JList<String>(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		this.add(listScroller);
		
	}
	
	private Vector<String> getFileNames(String directory){
		Path newPath = Paths.get("midiFiles\\");
		Vector<String> data = new Vector<String>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(newPath)) {
		    for (Path file: stream) {
		    	data.add(file.getFileName().toString());
		        System.out.println(file.getFileName());
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    // IOException can never be thrown by the iteration.
		    // In this snippet, it can only be thrown by newDirectoryStream.
		    System.err.println(x);
		}
		
		return  data;
	}
}
