import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
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

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;


public class GUI extends JFrame {

	private JPanel contentPane;
	static int drawState = Constants.MOVE;
	static int drawThick = 1 ;
	//private int selectedButton[] = new int[10];
	ButtonGroup buttonGroup = new ButtonGroup();

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.textHighlight);
		setJMenuBar(menuBar);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setBackground(Color.WHITE);
		menuBar.add(mntmSave);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		menuBar.add(mntmLoad);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		menuBar.add(mntmUndo);
		
		JMenuItem mntmRedo = new JMenuItem("Redo");
		menuBar.add(mntmRedo);
		
		JMenuItem mntmLoadClass = new JMenuItem("Load Class");
		menuBar.add(mntmLoadClass);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
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
		
		JToggleButton tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.LINE;
			}
		});
		toolBar.add(tglbtnLine);
		
		JToggleButton tglbtnEllipse = new JToggleButton("Ellipse");
		tglbtnEllipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.ELLIPSE;
			}
		});
		toolBar.add(tglbtnEllipse);
		
		JToggleButton tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.CIRCLE;
			}
		});
		toolBar.add(tglbtnCircle);
		
		JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.RECTANGLE;
			}
		});
		toolBar.add(tglbtnRectangle);
		
		JToggleButton tglbtnSquare = new JToggleButton("Square");
		tglbtnSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.SQUARE;
			}
		});
		toolBar.add(tglbtnSquare);
		
		JToggleButton tglbtnTriangle = new JToggleButton("Triangle");
		tglbtnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.TRIANGLE;
			}
		});
		toolBar.add(tglbtnTriangle);
		
		JToggleButton tglbtnPolygon = new JToggleButton("Polygon");
		tglbtnPolygon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				drawState = Constants.POLYGON;
			}
		});
		toolBar.add(tglbtnPolygon);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 20));
		toolBar.add(separator);
		
		JToggleButton tglbtnColor = new JToggleButton("Color");
		toolBar.add(tglbtnColor);
		
		JToggleButton tglbtnFill = new JToggleButton("Fill");
		toolBar.add(tglbtnFill);
		
		buttonGroup.add(tglbtnMove);
		buttonGroup.add(tglbtnLine);
		buttonGroup.add(tglbtnEllipse);
		buttonGroup.add(tglbtnCircle);
		buttonGroup.add(tglbtnSquare);
		buttonGroup.add(tglbtnRectangle);
		buttonGroup.add(tglbtnTriangle);
		buttonGroup.add(tglbtnPolygon);
		
		
		JLabel lblThickness = new JLabel("Thickness");
		lblThickness.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		toolBar.add(lblThickness);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawThick = Integer.parseInt((String) comboBox.getSelectedItem());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox.setMaximumSize(new Dimension(70, 30));
		toolBar.add(comboBox);
		
		JPanel panel = new GUIPanel();
		
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		
	}

}
