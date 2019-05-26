package br.edu.usf.poo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.usf.poo.controller.AppController;
import br.edu.usf.poo.controller.LoginController;
import br.edu.usf.poo.utils.IconCatalog;
import br.edu.usf.poo.view.components.JGradientPanel;
import br.edu.usf.poo.view.components.ui.CompoundTextField;

public class DlgLogin extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CompoundTextField txtLogin;
	private CompoundTextField txtPassword;

	private ExecutorService loginExecutor;
	private Callable<Boolean> loginTask;
	private Future<Boolean> loginFuture;
	
	public DlgLogin() {
		initComponents();
		
		loginExecutor = Executors.newSingleThreadExecutor();
		
		loginTask = () -> {
			String login = txtLogin.getText();
			String password = txtPassword.getText();
			
			return LoginController.gi().login(login, password);
		};
	}
	
	private void startLoginProcess() {
		// TODO
		if (loginFuture != null) {
			loginFuture.cancel(false);
		}
		
		loginFuture = loginExecutor.submit(loginTask);
	}
	
	private void initComponents() {
		setTitle("Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DlgLogin.class.getResource("/br/edu/usf/poo/icon/icon.388x388.png")));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				AppController.gi().loginDialogClossing();
			}
		});
		
		JGradientPanel panel = new JGradientPanel();
		panel.setTopLeftColor(new Color(232, 13, 69));
		panel.setBottomRightColor(new Color(182, 182, 182));
		
		getContentPane().add(panel);
		
		GridBagLayout contentPaneLayout = new GridBagLayout();
		contentPaneLayout.columnWidths = new int[]{0, 0, 0, 0};
		contentPaneLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		contentPaneLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPaneLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		
		panel.setLayout(contentPaneLayout);
		
		JLabel lblImage = new JLabel();
		lblImage.setIcon(IconCatalog.gi().getIcon("icon.388x388.png", 256, 256));
		
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblImage.gridx = 1;
		gbc_lblImage.gridy = 1;
		panel.add(lblImage, gbc_lblImage);
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(new Color(232, 13, 69, 100));
		pnlLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(pnlLogin, gbc_panel_1);
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlLogin.setLayout(gbl_panel_1);
		
		txtLogin = new CompoundTextField();
		Font userFont = txtLogin.getFont().deriveFont((float) 14);
		txtLogin.setHint("Usuário");
		txtLogin.setFont(userFont);
		txtLogin.setHintFont(userFont);
		GridBagConstraints gbc_txtUser = new GridBagConstraints();
		gbc_txtUser.insets = new Insets(0, 0, 5, 0);
		gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUser.gridx = 0;
		gbc_txtUser.gridy = 0;
		pnlLogin.add(txtLogin, gbc_txtUser);
		
		txtPassword = new CompoundTextField();
		Font passwordFont = txtPassword.getFont().deriveFont((float) 14);
		txtPassword.setHint("Senha");
		txtPassword.setFont(passwordFont);
		txtPassword.setHintFont(passwordFont);
		txtPassword.setPasswordField(true);
		
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 0;
		gbc_txtPassword.gridy = 1;
		pnlLogin.add(txtPassword, gbc_txtPassword);
		
		JButton btnLogin = new JButton();
		btnLogin.setText("Login");
		btnLogin.addActionListener((e) -> startLoginProcess());
		
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 2;
		pnlLogin.add(btnLogin, gbc_btnLogin);
	}
}
