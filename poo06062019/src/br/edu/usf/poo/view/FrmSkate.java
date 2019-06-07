package br.edu.usf.poo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.usf.poo.client.LixaClient;
import br.edu.usf.poo.client.RodaClient;
import br.edu.usf.poo.client.RolamentoClient;
import br.edu.usf.poo.client.ShapeClient;
import br.edu.usf.poo.client.SkateClient;
import br.edu.usf.poo.client.TruckClient;
import br.edu.usf.poo.models.Lixa;
import br.edu.usf.poo.models.Roda;
import br.edu.usf.poo.models.Rolamento;
import br.edu.usf.poo.models.Shape;
import br.edu.usf.poo.models.Skate;
import br.edu.usf.poo.models.Truck;
import br.edu.usf.poo.utils.IconCatalog;

public class FrmSkate extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Skate skate;
	
	private JComboBox<Lixa>		 accLixa;
	private JComboBox<Roda>		 accRoda;
	private JComboBox<Rolamento> accRolamento;
	private JComboBox<Shape>	 accShape;
	private JComboBox<Truck>	 accTruck;

	private JLabel lblValorfinal;
	private JLabel lblPrecoLixa;
	private JLabel lblPrecoRoda;
	private JLabel lblPrecoRolamento;
	private JLabel lblPrecoShape;
	private JLabel lblPrecoTruck;

	public FrmSkate() {
		initComponents();
	}
	
	public void fillImpl(Skate sk8) {
		this.skate = sk8;
		
		Lixa lixa			 = null;
		Roda roda			 = null;
		Rolamento rolamento	 = null;
		Shape shape			 = null;
		Truck truck			 = null;

		if (skate != null) {
			lixa		 = LixaClient.gi().getByID		(skate.getCodLixa());
			roda		 = RodaClient.gi().getByID		(skate.getCodRoda());
			rolamento	 = RolamentoClient.gi().getByID	(skate.getCodRolamento());
			shape		 = ShapeClient.gi().getByID		(skate.getCodShape());
			truck		 = TruckClient.gi().getByID		(skate.getCodTruck());
		}

		accLixa.setSelectedItem(lixa);
		accRoda.setSelectedItem(roda);
		accRolamento.setSelectedItem(rolamento);
		accShape.setSelectedItem(shape);
		accTruck.setSelectedItem(truck);
		
		String precoLixa = formatMoney(lixa.getPreco());
		String precoRoda = formatMoney(roda.getPreco());
		String precoRolamento = formatMoney(rolamento.getPreco());
		String precoShape = formatMoney(shape.getPreco());
		String precoTruck = formatMoney(truck.getPreco());
		
		lblPrecoLixa.setText("R$ " + precoLixa);
		lblPrecoRoda.setText("R$ " + precoRoda);
		lblPrecoRolamento.setText("R$ " + precoRolamento);
		lblPrecoShape.setText("R$ " + precoShape);
		lblPrecoTruck.setText("R$ " + precoTruck);
	}
	
	private String formatMoney(float number) {
		float epsilon = 0.004f;
		if (Math.abs(Math.round(number) - number) < epsilon) {
			return String.format("%10.0f", number);
		} else {
			return String.format("%10.2f", number);
		}
	}
	
	public Skate getModelImpl() {
		
		if (skate == null) {
			skate = new Skate();
		}
		
		Lixa lixa = (Lixa) accLixa.getSelectedItem();
		Roda roda = (Roda) accRoda.getSelectedItem();
		Rolamento rolamento = (Rolamento) accRolamento.getSelectedItem();
		Shape shape = (Shape) accShape.getSelectedItem();
		Truck truck = (Truck) accTruck.getSelectedItem();
		
		skate.setCodLixa(lixa.getCod());
		skate.setCodRoda(roda.getCod());
		skate.setCodRolamento(rolamento.getCod());
		skate.setCodShape(shape.getCod());
		skate.setCodTruck(truck.getCod());
		
		return skate;
	}
	
	private void save() {
		Object message;
		String title = "SK8";
		int informationMessage;

		boolean success = SkateClient.gi().save(getModelImpl());
		
		if (success) {
			message = "Skate salvo com sucesso";
			informationMessage = JOptionPane.INFORMATION_MESSAGE;
		} else {
			message = "Erro ao salvar Skate";
			informationMessage = JOptionPane.ERROR_MESSAGE;
		}
		
		JOptionPane.showMessageDialog(this, message, title, informationMessage);
	}
	
	private void cancel() {
		// TODO
	}
	
	private void initComponents() {
		setMinimumSize(new Dimension(800, 600));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(panel, BorderLayout.CENTER);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 500, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel.add(panel_3, gbc_panel_3);
		
		JLabel label = new JLabel();
		label.setIcon(IconCatalog.gi().getIcon("skate.gif"));
		
		panel_3.add(label);
		
		JLabel lblLixa = new JLabel("Lixa");
		
		GridBagConstraints gbc_lblLixa = new GridBagConstraints();
		gbc_lblLixa.fill = GridBagConstraints.VERTICAL;
		gbc_lblLixa.insets = new Insets(0, 0, 5, 5);
		gbc_lblLixa.gridx = 0;
		gbc_lblLixa.gridy = 1;
		panel.add(lblLixa, gbc_lblLixa);
		
		accLixa = new JComboBox<>(new Vector<>(LixaClient.gi().getAll()));
		accLixa.setSelectedItem(null);
		
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		panel.add(accLixa, gbc_panel_1);
		
		lblPrecoLixa = new JLabel();
		
		GridBagConstraints gbc_lblLblprecolixa = new GridBagConstraints();
		gbc_lblLblprecolixa.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblprecolixa.gridx = 2;
		gbc_lblLblprecolixa.gridy = 1;
		panel.add(lblPrecoLixa, gbc_lblLblprecolixa);
		
		JLabel lblRoda = new JLabel("Roda");
		GridBagConstraints gbc_lblRoda = new GridBagConstraints();
		gbc_lblRoda.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoda.gridx = 0;
		gbc_lblRoda.gridy = 2;
		panel.add(lblRoda, gbc_lblRoda);
		
		accRoda = new JComboBox<>(new Vector<>(RodaClient.gi().getAll()));
		accRoda.setSelectedItem(null);
		
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 1;
		gbc_lblA.gridy = 2;
		panel.add(accRoda, gbc_lblA);
		
		lblPrecoRoda = new JLabel();
		
		GridBagConstraints gbc_lblLblprecoroda = new GridBagConstraints();
		gbc_lblLblprecoroda.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblprecoroda.gridx = 2;
		gbc_lblLblprecoroda.gridy = 2;
		panel.add(lblPrecoRoda, gbc_lblLblprecoroda);
		
		JLabel lblRolamento = new JLabel("Rolamento");
		GridBagConstraints gbc_lblRolamento = new GridBagConstraints();
		gbc_lblRolamento.insets = new Insets(0, 0, 5, 5);
		gbc_lblRolamento.gridx = 0;
		gbc_lblRolamento.gridy = 3;
		panel.add(lblRolamento, gbc_lblRolamento);
		
		accRolamento = new JComboBox<>(new Vector<>(RolamentoClient.gi().getAll()));
		accRolamento.setSelectedItem(null);
		
		GridBagConstraints gbc_lblB = new GridBagConstraints();
		gbc_lblB.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblB.insets = new Insets(0, 0, 5, 5);
		gbc_lblB.gridx = 1;
		gbc_lblB.gridy = 3;
		panel.add(accRolamento, gbc_lblB);
		
		lblPrecoRolamento = new JLabel();
		
		GridBagConstraints gbc_lblLblprecorolamento = new GridBagConstraints();
		gbc_lblLblprecorolamento.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblprecorolamento.gridx = 2;
		gbc_lblLblprecorolamento.gridy = 3;
		panel.add(lblPrecoRolamento, gbc_lblLblprecorolamento);
		
		JLabel lblShape = new JLabel("Shape");
		GridBagConstraints gbc_lblShape = new GridBagConstraints();
		gbc_lblShape.insets = new Insets(0, 0, 5, 5);
		gbc_lblShape.gridx = 0;
		gbc_lblShape.gridy = 4;
		panel.add(lblShape, gbc_lblShape);
		
		accShape = new JComboBox<>(new Vector<>(ShapeClient.gi().getAll()));
		accShape.setSelectedItem(null);
		
		GridBagConstraints gbc_lblV = new GridBagConstraints();
		gbc_lblV.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblV.insets = new Insets(0, 0, 5, 5);
		gbc_lblV.gridx = 1;
		gbc_lblV.gridy = 4;
		panel.add(accShape, gbc_lblV);
		
		lblPrecoShape = new JLabel();
		
		GridBagConstraints gbc_lblLblprecoshape = new GridBagConstraints();
		gbc_lblLblprecoshape.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblprecoshape.gridx = 2;
		gbc_lblLblprecoshape.gridy = 4;
		panel.add(lblPrecoShape, gbc_lblLblprecoshape);
		
		JLabel lblTruck = new JLabel("Truck");
		GridBagConstraints gbc_lblTruck = new GridBagConstraints();
		gbc_lblTruck.insets = new Insets(0, 0, 5, 5);
		gbc_lblTruck.gridx = 0;
		gbc_lblTruck.gridy = 5;
		panel.add(lblTruck, gbc_lblTruck);
		
		accTruck = new JComboBox<>(new Vector<>(TruckClient.gi().getAll()));
		accTruck.setSelectedItem(null);
		
		GridBagConstraints gbc_lblD = new GridBagConstraints();
		gbc_lblD.insets = new Insets(0, 0, 5, 5);
		gbc_lblD.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblD.gridx = 1;
		gbc_lblD.gridy = 5;
		panel.add(accTruck, gbc_lblD);
		
		lblPrecoTruck = new JLabel();
		
		GridBagConstraints gbc_lblLblprecotruck = new GridBagConstraints();
		gbc_lblLblprecotruck.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblprecotruck.gridx = 2;
		gbc_lblLblprecotruck.gridy = 5;
		panel.add(lblPrecoTruck, gbc_lblLblprecotruck);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 6;
		panel.add(panel_2, gbc_panel_2);
		
		lblValorfinal = new JLabel();
		lblValorfinal.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblValorfinal);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Salvar");
		btnSave.setBackground(new Color(47, 128, 38));
		btnSave.setForeground(Color.WHITE);
		btnSave.setPreferredSize(new Dimension(100, 25));
		btnSave.addActionListener((e) -> save());
		panel_1.add(btnSave);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(new Color(255, 6, 1));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setPreferredSize(new Dimension(100, 25));
		btnCancel.addActionListener((e) -> cancel());
		panel_1.add(btnCancel);
	}
	
}
