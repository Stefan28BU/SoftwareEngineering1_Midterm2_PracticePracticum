package mid2prac;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Utilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main implements ActionListener{
	private ConcreteTableModel c2;
	private JTable t1;
	
	public void defaultWindow() {
		JFrame frame = new JFrame("CSI 3471 - Homework 7");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		t1 = new JTable();

		//make menu
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File Chooser");
		JLabel lb1 = new JLabel("Choose a File", SwingConstants.CENTER);

		//put menu bar on frame
		frame.setJMenuBar(menubar);
		frame.add(lb1 , BorderLayout.CENTER);

		JMenuItem menuItem4 = new JMenuItem("Open File...");
		menu.add(menuItem4);
		menuItem4.addActionListener(this);

		menubar.add(menu);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(300, 100);
		frame.setVisible(true);
	}

	public void createAndShowGUI(ConcreteTableModel a) {
		//window with title
		JFrame frame = new JFrame("CSI 3471 - Homework 7");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		c2 = a;
		t1 = new JTable(a);

		//make menu
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");

		//put menu bar on frame
		frame.setJMenuBar(menubar);

		JLabel lb1 = new JLabel("Sort Rows by Clicking Each Column", SwingConstants.CENTER);
		frame.add(lb1, BorderLayout.AFTER_LINE_ENDS);
		JMenu menu2 = new JMenu("File");
		JMenuItem menuItem4 = new JMenuItem("Open File...");
		JMenu menu3 = new JMenu("Save as...");
		menu2.add(menuItem4);
		menu2.add(menu3);

		JMenuItem menuItem5 = new JMenuItem("CSV File");
		JMenuItem menuItem7 = new JMenuItem("XML File");
		
		menuItem5.addActionListener(this);
		menuItem7.addActionListener(this);
		
		menu3.add(menuItem5);
		menu3.add(menuItem7);
		
		menu.add(menu2);

		JMenuItem menuItem6 = new JMenuItem("Edit Selected Row");
		menuItem6.addActionListener(this);
		menu.add(menuItem6);

		JMenuItem menuItem2 = new JMenuItem("Remove Row");
		menuItem2.addActionListener(this);
		menu.add(menuItem2);

		JMenuItem menuItem3 = new JMenuItem("Add Row");
		menuItem3.addActionListener(this);
		menu.add(menuItem3);

		menubar.add(menu);

		//add a table
		final TableRowSorter <TableModel> sorter = new TableRowSorter 
				<TableModel> (t1.getModel());

		t1.setRowSorter(sorter);

		JScrollPane pane = new JScrollPane(t1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		t1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		frame.add(pane, BorderLayout.CENTER);

		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Filter Text: ");
		panel.add(label, BorderLayout.WEST);
		final JTextField filterText = new JTextField("");
		panel.add(filterText, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		JButton button = new JButton("Apply Filter");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = filterText.getText();
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
		});
		frame.add(button, BorderLayout.SOUTH);

		//Display frame
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
	}

	public static void main(String[]args) throws FileNotFoundException {
		final Main guiMaker = new Main();
		JOptionPane.showMessageDialog(null, "Choose a File Using File Chooser","Message",
				JOptionPane.WARNING_MESSAGE);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				guiMaker.defaultWindow();
			}
		});
	}

	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
		if (arg0.getActionCommand() == "Remove Row") {
			if (t1.isRowSelected(t1.getSelectedRow())) {
				c2.removeRow(t1.getSelectedRow());
			}
			else {
				JOptionPane.showMessageDialog(null, "Plese Select a Row to Delete","Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Please Select a Row");
			}
		}
		if (arg0.getActionCommand() == "Add Row") {
			c2.addRow();

			JTextField field1 = new JTextField();
			JTextField field2 = new JTextField();
			JTextField field3 = new JTextField();
			JTextField field4 = new JTextField();
			JTextField field5 = new JTextField();

			Object[] message = {
					"Name", field1, 
					"Age", field2,
					"Gender", field3,
					"Salary", field4,
					"Email", field5,
			
			};
			int option = JOptionPane.showConfirmDialog(null, message, "Add New Row", JOptionPane.OK_CANCEL_OPTION);

			if (option == JOptionPane.OK_OPTION)
			{
				c2.setValueAt(field1.getText(), c2.getRowCount()-1, 0);
				c2.setValueAt(field2.getText(), c2.getRowCount()-1, 1);
				c2.setValueAt(field3.getText(), c2.getRowCount()-1, 2);
				c2.setValueAt(field4.getText(), c2.getRowCount()-1, 3);
				c2.setValueAt(field5.getText(), c2.getRowCount()-1, 4);
			}
			t1.repaint();
		}

		if (arg0.getActionCommand() == "Edit Selected Row") {

			JTextField field1 = new JTextField();
			JTextField field2 = new JTextField();
			JTextField field3 = new JTextField();
			JTextField field4 = new JTextField();
			JTextField field5 = new JTextField();
		
			Object[] message = {
					"Name", field1, 
					"Age", field2,
					"Gender", field3,
					"Salary", field4,
					"Email", field5,
			
			};
			if (t1.isRowSelected(t1.getSelectedRow())) {
				field1.setText((String) c2.getValueAt(t1.getSelectedRow(), 0));
				field2.setText((String) c2.getValueAt(t1.getSelectedRow(), 1));
				field3.setText((String) c2.getValueAt(t1.getSelectedRow(), 2));
				field4.setText((String) c2.getValueAt(t1.getSelectedRow(), 3));
				field5.setText((String) c2.getValueAt(t1.getSelectedRow(), 4));
			

				int option = JOptionPane.showConfirmDialog(null, message, "Edit Selected Row", JOptionPane.OK_CANCEL_OPTION);

				if (option == JOptionPane.OK_OPTION)
				{
					c2.setValueAt(field1.getText(), t1.getSelectedRow(), 0);
					c2.setValueAt(field2.getText(), t1.getSelectedRow(), 1);
					c2.setValueAt(field3.getText(), t1.getSelectedRow(), 2);
					c2.setValueAt(field4.getText(), t1.getSelectedRow(), 3);
					c2.setValueAt(field5.getText(), t1.getSelectedRow(), 4);
				}
				t1.repaint();
			}
			else {
				JOptionPane.showMessageDialog(null, "Plese Select a Row to Edit","Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (arg0.getActionCommand() == "Open File...") {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());

				final Main guiMaker = new Main();
				try {
					c2 = new ConcreteTableModel(selectedFile.getAbsolutePath());
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						public void run () {
							guiMaker.createAndShowGUI(c2);
						}
					});
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (arg0.getActionCommand() == "CSV File") {
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fileChooser.setDialogTitle("Sava as CSV File (DO NOT ADD FILE NAME EXTENSION)");   

			int userSelection = fileChooser.showSaveDialog(null);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				System.out.println(fileToSave.getName());

				try {
					c2.saveFile(fileToSave);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (arg0.getActionCommand() == "XML File") {
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fileChooser.setDialogTitle("Sava as XML File (DO NOT ADD FILE NAME EXTENSION)");   
			
			int userSelection = fileChooser.showSaveDialog(null);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				System.out.println(fileToSave.getName());

				c2.saveAsXML(fileToSave);
			}
		}
	}
}