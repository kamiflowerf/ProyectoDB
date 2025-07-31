package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import logico.GestionEvento;
import logico.Jurado;
import logico.Participante;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class RegPersona extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JRadioButton rdJurd;
	private JRadioButton rdPart;
	private JComboBox cmbArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegPersona dialog = new RegPersona();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegPersona() {
		setTitle("Registrar Persona");
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
		
		setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		getContentPane().setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		setBounds(100, 100, 583, 369);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, UIManager.getColor("InternalFrame.activeTitleGradient"), UIManager.getColor("InternalFrame.activeTitleGradient"), UIManager.getColor("InternalFrame.activeTitleGradient"), UIManager.getColor("InternalFrame.activeTitleGradient")));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		{
			JPanel panel = new JPanel();
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos generales:", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
				panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
				panel_1.setBounds(10, 0, 541, 150);
				panel.add(panel_1);
				panel_1.setLayout(null);
				
				JLabel lblNewLabel = new JLabel("C\u00E9dula:");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
				lblNewLabel.setBounds(10, 31, 50, 14);
				panel_1.add(lblNewLabel);
				
				txtCedula = new JTextField();
				txtCedula.setBounds(76, 28, 172, 20);
				panel_1.add(txtCedula);
				txtCedula.setColumns(10);
				
				JLabel lblNewLabel_1 = new JLabel("Nombre:");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
				lblNewLabel_1.setBounds(10, 76, 63, 14);
				panel_1.add(lblNewLabel_1);
				
				txtNombre = new JTextField();
				txtNombre.setBounds(77, 73, 150, 20);
				panel_1.add(txtNombre);
				txtNombre.setColumns(10);
				
				JLabel lblNewLabel_2 = new JLabel("Apellido:");
				lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
				lblNewLabel_2.setBounds(10, 113, 63, 14);
				panel_1.add(lblNewLabel_2);
				
				txtApellido = new JTextField();
				txtApellido.setBounds(76, 110, 150, 20);
				panel_1.add(txtApellido);
				txtApellido.setColumns(10);
				
				JLabel lblNewLabel_3 = new JLabel("Tel\u00E9fono:");
				lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
				lblNewLabel_3.setBounds(284, 76, 70, 14);
				panel_1.add(lblNewLabel_3);
				
				txtTelefono = new JTextField();
				txtTelefono.setBounds(367, 74, 135, 20);
				panel_1.add(txtTelefono);
				txtTelefono.setColumns(10);
			}
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_1.setBorder(new TitledBorder(null, "Tipo:", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
			panel_1.setBounds(10, 152, 541, 54);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			rdPart = new JRadioButton("Participante");
			rdPart.setFont(new Font("Tahoma", Font.BOLD, 13));
			rdPart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdPart.setSelected(true);
					rdJurd.setSelected(false);
					cmbArea.setEnabled(false);
				}
			});
			rdPart.setSelected(true);
			rdPart.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			rdPart.setBounds(76, 15, 109, 23);
			panel_1.add(rdPart);
			
			rdJurd = new JRadioButton("Jurado");
			rdJurd.setFont(new Font("Tahoma", Font.BOLD, 13));
			rdJurd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdJurd.setSelected(true);
					rdPart.setSelected(false);
					cmbArea.setEnabled(true);
				}
			});
			rdJurd.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			rdJurd.setBounds(287, 15, 109, 23);
			panel_1.add(rdJurd);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_2.setBounds(10, 216, 541, 64);
			panel.add(panel_2);
			panel_2.setLayout(null);
			
			JLabel lblNewLabel_4 = new JLabel("Area:");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_4.setBounds(10, 24, 46, 14);
			panel_2.add(lblNewLabel_4);
			
			cmbArea = new JComboBox();
			cmbArea.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Tecnolog\u00EDa en inform\u00E1tica ", "Ciencias de la salud", "Ciencias Sociales", "Investigaci\u00F3n\u00A0y\u00A0Desarrollo"}));
			cmbArea.setEnabled(false);
			cmbArea.setBounds(76, 21, 220, 20);
			panel_2.add(cmbArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtCedula.getText().equals("") || txtNombre.getText().equals("") || txtApellido.getText().equals("") || txtTelefono.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Debe llenar todos los campos generales.", 
                                    "Error", JOptionPane.ERROR_MESSAGE);
						}else {
							if(rdPart.isSelected()) {
								Participante participante = new Participante(("P-"+GestionEvento.getInstance().codPersona), txtCedula.getText().toString(), txtNombre.getText().toString(), txtApellido.getText().toString(), txtTelefono.getText().toString());
								GestionEvento.getInstance().insertarPersonas(participante);
								JOptionPane.showMessageDialog(null, "Operación Exitosa.", 
	                                    "Aviso", JOptionPane.WARNING_MESSAGE);
								clean();
							}else {
								if(cmbArea.getSelectedIndex() == 0) {
									JOptionPane.showMessageDialog(null, "Debe seleccionar un área.", 
		                                    "Error", JOptionPane.ERROR_MESSAGE);
								}else {
									Jurado jurado = new Jurado(("P-"+GestionEvento.getInstance().codPersona), txtCedula.getText().toString(), txtNombre.getText().toString(), txtApellido.getText().toString(), txtTelefono.getText().toString(), cmbArea.getSelectedItem().toString());
									GestionEvento.getInstance().insertarPersonas(jurado);
									JOptionPane.showMessageDialog(null, "Operación Exitosa.", 
		                                    "Aviso", JOptionPane.WARNING_MESSAGE);
									clean();
								}
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void clean() {
		txtCedula.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtTelefono.setText("");
		cmbArea.setSelectedIndex(0);
		rdPart.setSelected(true);
		rdJurd.setSelected(false);
		cmbArea.setEnabled(false);
	}
}
