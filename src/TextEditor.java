package Util;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import javax.swing.KeyStroke;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AWTKeyStroke;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextEditor extends JFrame implements ActionListener {
	private static JTextArea area;
	private static JFrame frame;

	public TextEditor() { run(); }

	public void run() {
		frame = new JFrame("Edit Text");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	      Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    area = new JTextArea();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(area);
	    frame.setSize(800,800);
	    JScrollPane scroll = new JScrollPane(area);

	    JMenuBar menuBar = new JMenuBar();	    

	    menuBar.add(getFileMenu());
	    menuBar.add(getEditMenu());

	    frame.add(scroll);
	    frame.setJMenuBar(menuBar);
	    frame.setVisible(true);
	}

	private JMenu getFileMenu() {
		JMenu menu = new JMenu("File");

	    JMenuItem menuItem_new = new JMenuItem("New");
	    KeyStroke keyStrokeToNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		menuItem_new.setAccelerator(keyStrokeToNew);
	    menuItem_new.addActionListener(this);

	    JMenuItem menuItem_save = new JMenuItem("Save");
	    KeyStroke keyStrokeToSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		menuItem_save.setAccelerator(keyStrokeToSave);
	    menuItem_save.addActionListener(this);

	    JMenuItem menuItem_open = new JMenuItem("Open");
	    KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		menuItem_open.setAccelerator(keyStrokeToOpen);
	    menuItem_open.addActionListener(this);

	    menu.add(menuItem_new);
	    menu.add(menuItem_save);
	    menu.add(menuItem_open);

	    return menu;
	}

	private JMenu getEditMenu() {
		JMenu menu = new JMenu("Edit");

	    JMenuItem menuItem_undo = new JMenuItem("Undo");
	    KeyStroke keyStrokeToUndo = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);
		menuItem_undo.setAccelerator(keyStrokeToUndo);
	    menuItem_undo.addActionListener(this);

	    JMenuItem menuItem_redo = new JMenuItem("Redo");
	    KeyStroke keyStrokeToRedo = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
		menuItem_redo.setAccelerator(keyStrokeToRedo);
	    menuItem_redo.addActionListener(this);

	    JMenuItem menuItem_wordWrap = new JMenuItem("Word Wrap");
	    KeyStroke keyStrokeToWordWrap = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);
		menuItem_wordWrap.setAccelerator(keyStrokeToWordWrap);
	    menuItem_wordWrap.addActionListener(this);

	    menu.add(menuItem_undo);
	    menu.add(menuItem_redo);
	    menu.add(menuItem_wordWrap);

	    return menu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ingest = "";

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose destination folder");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("New")) {
			int result = JOptionPane.showConfirmDialog((Component) null, "Open in a new Window",
        				"alert", JOptionPane.OK_CANCEL_OPTION);

			TextEditor runner = null;

			if(result==0)
				runner = new TextEditor();
			else
				area.setText("");
		}

		else if(actionCommand.equals("Save")) {
			int returnValue = jfc.showSaveDialog(null);
			if(returnValue==1)
				return;

			try {
				File file = new File(jfc.getSelectedFile().getAbsolutePath());
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(area.getText());
				fileWriter.close();
			}

			catch(FileNotFoundException ex){
				Component f = null;
	            JOptionPane.showMessageDialog(f,"File Not Found.");
			}

			catch (IOException ex) {
	            Component f = null;
	            JOptionPane.showMessageDialog(f,"Error.");
	        }
		}

		else if(actionCommand.equals("Open")) {
			int returnValue = jfc.showOpenDialog(null);
			if(returnValue==1)
				return;

			File file = new File(jfc.getSelectedFile().getAbsolutePath());
			try{
				FileReader fileReader = new FileReader(file);
				Scanner scanner = new Scanner(fileReader);
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine() + "\n";
					ingest += line;
				}
				area.setText(ingest);
			}

			catch(FileNotFoundException ex){
				ex.printStackTrace();
			}
		}

		else if(actionCommand.equals("Undo")) {

		}

		else if(actionCommand.equals("Redo")) {

		}

		else if(actionCommand.equals("Word Wrap")) {
			area.setLineWrap(true);
			area.setWrapStyleWord(true);
		}
	}
}
