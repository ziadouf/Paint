import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Component;

import javax.swing.JMenuItem;

import com.thoughtworks.xstream.XStream;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GUI extends JFrame {

	private JPanel contentPane;
	static int drawState = Constants.MOVE;
	static int drawThick = 1 ;
	static Color drawColor = Color.BLACK;
	static boolean isChanged = false;
	//private int selectedButton[] = new int[10];
	static ButtonGroup buttonGroup = new ButtonGroup();
	static ArrayList <JToggleButton> toggleButtons = new ArrayList <JToggleButton>(); 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GUI() {
		XStream xstream = new XStream();
		
		Shape.addShapeVector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new GUIPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.textHighlight);
		setJMenuBar(menuBar);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String xml = "";
				xml = xstream.toXML(Shape.shapes);
				JFileChooser saveDialog = new JFileChooser();				
				int userSelection = saveDialog.showSaveDialog(null);
				
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					String filePath = saveDialog.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".xml")) filePath += ".xml";
					System.out.println(filePath);
					PrintWriter out = null;
					try {
						out = new PrintWriter(filePath);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					out.println(xml);
					out.close();
				}
			}
		});
		mntmSave.setBackground(Color.WHITE);
		menuBar.add(mntmSave);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openDialog = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML *.xml", "xml");
				openDialog.setFileFilter(filter);
				int userSelection = openDialog.showOpenDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					String filePath = openDialog.getSelectedFile().getAbsolutePath();
					String xml = "";
					BufferedReader in = null;
					try {
						in = new BufferedReader(new FileReader(filePath));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String line ;
					try {
						while (( line = in.readLine()) != null) 
						{
							xml += line ;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Shape.allShapes.clear();
					Shape.setIndex(-1);
					Shape.shapes = (ArrayList<Shape>) xstream.fromXML(xml);
					Shape.addShapeVector();
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GUIPanel.selectedIndices.clear();
					panel.repaint();
				}
			}
		});
		menuBar.add(mntmLoad);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Shape.undo();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "No previous history.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				GUIPanel.selectedIndices.clear();
				panel.repaint();
			}
		});
		menuBar.add(mntmUndo);
		
		JMenuItem mntmRedo = new JMenuItem("Redo");
		mntmRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Shape.redo();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "End of history.", "Warning", JOptionPane.WARNING_MESSAGE);				}
				GUIPanel.selectedIndices.clear();
				panel.repaint();
			}
		});
		menuBar.add(mntmRedo);
		
		JMenuItem mntmLoadClass = new JMenuItem("Load Class");
		menuBar.add(mntmLoadClass);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		contentPane.add(toolBar, BorderLayout.WEST);
		
		JToggleButton tglbtnMove = new JToggleButton("Move");
		tglbtnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.MOVE;
			}
		});
		toolBar.add(tglbtnMove);
		toggleButtons.add(tglbtnMove);
		
		JToggleButton tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.LINE;
			}
		});
		toolBar.add(tglbtnLine);
		toggleButtons.add(tglbtnLine);
		
		JToggleButton tglbtnEllipse = new JToggleButton("Ellipse");
		tglbtnEllipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.ELLIPSE;
			}
		});
		toolBar.add(tglbtnEllipse);
		toggleButtons.add(tglbtnEllipse);
		
		JToggleButton tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.CIRCLE;
			}
		});
		toolBar.add(tglbtnCircle);
		toggleButtons.add(tglbtnCircle);
		
		JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.RECTANGLE;
			}
		});
		toolBar.add(tglbtnRectangle);
		toggleButtons.add(tglbtnRectangle);
		
		JToggleButton tglbtnSquare = new JToggleButton("Square");
		tglbtnSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.SQUARE;
			}
		});
		toolBar.add(tglbtnSquare);
		toggleButtons.add(tglbtnSquare);
		
		JToggleButton tglbtnTriangle = new JToggleButton("Triangle");
		tglbtnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.TRIANGLE;
			}
		});
		toolBar.add(tglbtnTriangle);
		toggleButtons.add(tglbtnTriangle);
		
		JToggleButton tglbtnPolygon = new JToggleButton("Polygon");
		tglbtnPolygon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.POLYGON;
			}
		});
		toolBar.add(tglbtnPolygon);
		toggleButtons.add(tglbtnPolygon);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 20));
		toolBar.add(separator);
		
		JToggleButton tglbtnColor = new JToggleButton("Color");
		tglbtnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawColor = JColorChooser.showDialog(null, "Change Foreground Color",
			            drawColor);
			}
		});
		toolBar.add(tglbtnColor);
		toggleButtons.add(tglbtnColor);
		
		JToggleButton tglbtnFill = new JToggleButton("Fill");
		tglbtnFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.FILL ;
			}
		});
		toolBar.add(tglbtnFill);
		toggleButtons.add(tglbtnFill);
		
		JToggleButton tglbtnDelete = new JToggleButton("Delete");
		tglbtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(GUIPanel.selectedIndices);
				Collections.reverse(GUIPanel.selectedIndices);
				for (int i=0 ; i<GUIPanel.selectedIndices.size() ; i++) {
					System.out.println(Shape.shapes.get(GUIPanel.selectedIndices.get(i)));
					Shape.delete(GUIPanel.selectedIndices.get(i));
				}
				GUIPanel.selectedIndices.clear();
				tglbtnDelete.setSelected(false);
				panel.repaint();
			}
		});
		toolBar.add(tglbtnDelete);
		
		buttonGroup.add(tglbtnMove);
		buttonGroup.add(tglbtnLine);
		buttonGroup.add(tglbtnEllipse);
		buttonGroup.add(tglbtnCircle);
		buttonGroup.add(tglbtnSquare);
		buttonGroup.add(tglbtnRectangle);
		buttonGroup.add(tglbtnTriangle);
		buttonGroup.add(tglbtnPolygon);
		buttonGroup.add(tglbtnColor);
		buttonGroup.add(tglbtnFill);
		
		JLabel lblThickness = new JLabel("Thickness");
		lblThickness.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		toolBar.add(lblThickness);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawThick = Integer.parseInt((String) comboBox.getSelectedItem());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7"}));
		comboBox.setMaximumSize(new Dimension(70, 30));
		toolBar.add(comboBox);

	}

}
