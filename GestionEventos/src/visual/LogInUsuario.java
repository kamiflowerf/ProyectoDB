package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import logico.GestionEvento;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LogInUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtPassword;
	private JTextField txtUserName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
                try {
                    GestionEvento.getInstance();
					LogInUsuario frame = new LogInUsuario();
					frame.setVisible(true);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogInUsuario() {
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 339);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblImagen = new JLabel("");
		
		lblImagen.setBounds(132, 11, 78, 83);
		
		ImageIcon ico = new ImageIcon(getClass().getResource("Logo PUCMM (Color).png"));
		ImageIcon img = new ImageIcon(ico.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
        
        
        lblImagen.setIcon(img);
        panel.add(lblImagen);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(148, 103, 59, 14);
		panel.add(lblNewLabel);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(111, 130, 120, 20);
		panel.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Contraseña:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(132, 163, 90, 14);
		panel.add(lblNewLabel_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(111, 186, 120, 20);
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    if(GestionEvento.getInstance().confirmUser(txtUserName.getText(), txtPassword.getText())) {
                        PrincipalGestion frame = new PrincipalGestion();
                        dispose();
                        frame.setVisible(true);
                    }else {
                        JOptionPane.showMessageDialog(null,
					"Usuario no existente.",
						"Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(122, 233, 98, 23);
		panel.add(btnNewButton);

		try {
			if (GestionEvento.getInstance().getMisUsuarios().size() == 1 &&
					GestionEvento.getInstance().existeUserName("admin")) {
				JOptionPane.showMessageDialog(null,
						"Se creó un usuario por defecto:\nUsuario: admin\nContraseña: admin123",
						"Usuario creado", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
