package br.edu.usf.poo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

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
	
	private static DlgLogin instance;
	
	private ExecutorService loginExecutor;
	private Runnable loginTask;
	private Future<?> loginFuture;

	public static DlgLogin gi() {
		
		if (instance == null) {
			instance = new DlgLogin();
		}
		
		return instance;
	}
	
	private DlgLogin() {
		initComponents();
		
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Login-Thread").build();
		loginExecutor = Executors.newSingleThreadExecutor(threadFactory);
		
		loginTask = () -> {
			// Set loading state
			setLoadingState();

			String login = txtLogin.getText();
			String password = txtPassword.getText();
			
			boolean loginSucces = LoginController.gi().login(login, password);;
			
			if (loginSucces) {
				
				AppController.gi().loadMainScreen();
			} else {
				lblMessage.setText("Login inválido");
				setLoginState();
			}
		};
		
		txtLogin.setText(System.getProperty("poo.login"));
		txtPassword.setText(System.getProperty("poo.password"));
	}
	
	private void startLoginProcess() {

		if (loginFuture != null) {
			loginFuture.cancel(true);
		}
		
		loginFuture = loginExecutor.submit(loginTask);
		
	}
	
	private void setLoadingState() {
		btnLogin.setEnabled(false);
	}
	
	private void setLoginState() {
		btnLogin.setEnabled(true);
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
		contentPaneLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		contentPaneLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPaneLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		
		panel.setLayout(contentPaneLayout);
		
		JLabel lblImage = new JLabel();
		lblImage.setIcon(IconCatalog.gi().getIcon("icon.388x388.png", 256, 256));
		
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblImage.gridx = 1;
		gbc_lblImage.gridy = 1;
		panel.add(lblImage, gbc_lblImage);
		
		lblMessage = new JLabel();
		lblMessage.setForeground(Color.RED);
		lblMessage.setFont(lblMessage.getFont().deriveFont(Font.BOLD, (float) 16));
		
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 2;
		panel.add(lblMessage, gbc_label);
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(new Color(232, 13, 69, 100));
		pnlLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
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
		txtLogin.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtPassword.requestFocus();
					
					e.consume();
				}
				super.keyPressed(e);
			}
		});
		
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
		txtPassword.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					startLoginProcess();
					
					e.consume();
				}
				super.keyPressed(e);
			}
		});
		
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 0;
		gbc_txtPassword.gridy = 1;
		pnlLogin.add(txtPassword, gbc_txtPassword);
		
		btnLogin = new JButton();
		btnLogin.setText("Login");
		btnLogin.addActionListener((e) -> startLoginProcess());
		
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 2;
		pnlLogin.add(btnLogin, gbc_btnLogin);
	}
	
	private CompoundTextField txtLogin;
	private CompoundTextField txtPassword;
	
	private JButton btnLogin;
	private JLabel lblMessage;
}
