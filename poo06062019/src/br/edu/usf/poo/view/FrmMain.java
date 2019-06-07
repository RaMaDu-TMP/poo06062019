package br.edu.usf.poo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.edu.usf.poo.client.SkateClient;
import br.edu.usf.poo.controller.SkateController;
import br.edu.usf.poo.models.Skate;

public class FrmMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FrmMain instance;
	
	private JTable table;

	private List<Skate> skates;

	public static FrmMain gi() {
		
		if (instance == null) {
			instance = new FrmMain();
		}
		
		return instance;
	}
	
	private FrmMain() {
		initComponents();
		
		search();
	}
	
	private void search() {
		List<String> lstIdentifiers = new ArrayList<>();
		lstIdentifiers.add("Lixa");
		lstIdentifiers.add("Roda");
		lstIdentifiers.add("Rolamento");
		lstIdentifiers.add("Shape");
		lstIdentifiers.add("Truck");
		
		skates = SkateClient.gi().getAll();
		Object[][] matrix = SkateClient.gi().getAllAsMatrix();
		
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setDataVector(matrix, lstIdentifiers.toArray());
		tableModel.setColumnIdentifiers(lstIdentifiers.toArray());

		TableColumnModel columnModel = new DefaultTableColumnModel();
		
		for (String columnName : lstIdentifiers) {
			TableColumn column = new TableColumn();
			column.setIdentifier(columnName);
			
			columnModel.addColumn(column );
		}
		table.setColumnModel(columnModel);
		table.setModel(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
		        
				Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	Skate selectedSkate = skates.get(row);
		        	SkateController.gi().openSkateForm(selectedSkate);
		        }
		    }
		});
		
		if (matrix.length == 0) {
			JOptionPane.showMessageDialog(this, "Não foram encontrados dados", "Erro ao pesquisar",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void createSkate() {
		SkateController.gi().openCreateSkateForm();
	}

	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DlgLogin.class.getResource("/br/edu/usf/poo/icon/icon.388x388.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1280, 720));
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel pnlSkates = new JPanel();
		pnlSkates.setOpaque(false);
		panel.add(pnlSkates, BorderLayout.CENTER);
		pnlSkates.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnlSkates.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		
		scrollPane.setViewportView(table);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setPreferredSize(new Dimension(150, 0));
		pnlButtons.setBackground(Color.GRAY);
		
		panel.add(pnlButtons, BorderLayout.EAST);
		pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Dimension buttonSize = new Dimension(125, 25);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setPreferredSize(buttonSize);
		btnPesquisar.addActionListener((e) -> search());
		pnlButtons.add(btnPesquisar);

		JButton btnCreate = new JButton("Criar Skate");
		btnCreate.setPreferredSize(buttonSize);
		btnCreate.addActionListener((e) -> createSkate());
		
		pnlButtons.add(btnCreate);
		
		pack();
	}
}
