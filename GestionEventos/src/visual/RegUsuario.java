package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import logico.GestionEvento;
import logico.User;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class RegUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JComboBox comboBox;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegUsuario dialog = new RegUsuario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegUsuario() {
		setTitle("Registrar Usuario");
		setBounds(100, 100, 457, 368);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		{
			JPanel panel = new JPanel();
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos personales", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			panel_1.setBounds(10, 11, 404, 261);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblImagen = new JLabel("");
			
			lblImagen.setBounds(316, 11, 78, 83);
			
			ImageIcon ico = new ImageIcon(getClass().getResource("Logo PUCMM (Color).png"));
			ImageIcon img = new ImageIcon(ico.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
	        
	        
	        lblImagen.setIcon(img);
	        panel_1.add(lblImagen);
	   
			JLabel lblNewLabel = new JLabel("Nombre:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 29, 113, 14);
			panel_1.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setBounds(10, 54, 113, 20);
			panel_1.add(textField);
			textField.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setBounds(172, 29, 85, 14);
			panel_1.add(lblNewLabel_1);
			
			textField_1 = new JTextField();
			textField_1.setBounds(172, 54, 113, 20);
			panel_1.add(textField_1);
			textField_1.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Confirmar contrase\u00F1a:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_2.setBounds(172, 85, 150, 14);
			panel_1.add(lblNewLabel_2);
			
			textField_2 = new JTextField();
			textField_2.setBounds(172, 110, 113, 20);
			panel_1.add(textField_2);
			textField_2.setColumns(10);
			
			JLabel lblNewLabel_3 = new JLabel("Apellido:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_3.setBounds(10, 85, 113, 14);
			panel_1.add(lblNewLabel_3);
			
			textField_3 = new JTextField();
			textField_3.setBounds(10, 110, 113, 20);
			panel_1.add(textField_3);
			textField_3.setColumns(10);
			
			JLabel lblNewLabel_4 = new JLabel("Nombre de Usuario:");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_4.setBounds(10, 141, 130, 14);
			panel_1.add(lblNewLabel_4);
			
			textField_4 = new JTextField();
			textField_4.setBounds(10, 166, 113, 20);
			panel_1.add(textField_4);
			textField_4.setColumns(10);
			
			JLabel lblNewLabel_5 = new JLabel("Tipo:");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_5.setBounds(172, 141, 46, 14);
			panel_1.add(lblNewLabel_5);
			
			comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Administrador", "Comercial"}));
			comboBox.setBounds(172, 166, 113, 20);
			panel_1.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String userName = textField_4.getText();
				        String password = textField_1.getText();
				        String confirmPass = textField_2.getText();
				        String nombre = textField.getText();
				        String apellido = textField_3.getText();
				        String tipo = comboBox.getSelectedItem().toString();
				        
				        if(userName.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || 
				           nombre.isEmpty() || apellido.isEmpty() || tipo.equals("<Seleccione>")) {
				            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos", 
				                "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				
				        
				        if(GestionEvento.getInstance().existeUserName(userName)) {
				            JOptionPane.showMessageDialog(null, 
				                "El nombre de usuario ya está en uso. Por favor, elija otro.", 
				                "Error", JOptionPane.ERROR_MESSAGE);
				            textField_4.setText("");
				            return;
				        }
				        
				        if(!password.equals(confirmPass)) {
				            JOptionPane.showMessageDialog(null, 
				                "Las contraseñas no coinciden", 
				                "Error", JOptionPane.ERROR_MESSAGE);
				            textField_1.setText("");
				            textField_2.setText("");
				            return;
				        }
				        
				        User nuevoUsuario = new User(nombre, apellido, userName, password, tipo);
				        GestionEvento.getInstance().getMisUsuarios().add(nuevoUsuario);
				        
				        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente", 
				            "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
				        clean();
					}
				});
				okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	private void clean() {
	    textField.setText("");
	    textField_1.setText("");
	    textField_2.setText("");
	    textField_3.setText("");
	    textField_4.setText("");
	    comboBox.setSelectedIndex(0);
	}
}
