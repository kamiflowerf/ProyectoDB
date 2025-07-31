package visual;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import logico.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegTrabajo extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private Boolean existe = false;
    private JTextField txtId;
    private Persona participante = null;
    private JTextField txtNombre;
    private JPanel panelAutor;
    private JTextField txtCedulaAutor;
    private JTextField txtNombreAutor;
    private JTextField txtApellidosAutor;
    private JTextField txtTelefonoAutor;
    private JComboBox cmbArea;

    public static void main(String[] args) {
        try {
            RegTrabajo dialog = new RegTrabajo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegTrabajo() {
        setTitle("Registrar Trabajo Científico");
        
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        
        setBounds(100, 100, 685, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient")));
        contentPanel.add(panel);
        panel.setLayout(null);

        // Panel Datos del Trabajo
        JPanel panel_1 = new JPanel();
        panel_1.setForeground(Color.WHITE);
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
            "Datos del Trabajo", TitledBorder.LEADING, TitledBorder.TOP, null, 
            UIManager.getColor("FormattedTextField.foreground")));
        panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_1.setBounds(12, 13, 643, 157);
        panel.add(panel_1);
        panel_1.setLayout(null);

        // ID Trabajo
        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblId.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblId.setBounds(24, 32, 26, 16);
        panel_1.add(lblId);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setText("T-"+GestionEvento.getInstance().codTrabajos);
        txtId.setBounds(104, 30, 116, 22);
        panel_1.add(txtId);

        // Nombre Trabajo
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNombre.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblNombre.setBounds(23, 74, 56, 16);
        panel_1.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(104, 72, 230, 22);
        panel_1.add(txtNombre);

        // Área
        JLabel lblArea = new JLabel("Área:");
        lblArea.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblArea.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblArea.setBounds(23, 116, 56, 16);
        panel_1.add(lblArea);
        
        cmbArea = new JComboBox();
        cmbArea.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Tecnolog\u00EDa en inform\u00E1tica ", "Ciencias de la salud", "Ciencias Sociales", "Investigaci\u00F3n\u00A0y\u00A0Desarrollo"}));
        cmbArea.setBounds(104, 115, 179, 20);
        panel_1.add(cmbArea);

        // Panel Datos del Autor
        panelAutor = new JPanel();
        panelAutor.setForeground(Color.WHITE);
        panelAutor.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
            "Datos del Autor", TitledBorder.LEADING, TitledBorder.TOP, null, 
            UIManager.getColor("FormattedTextField.foreground")));
        panelAutor.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panelAutor.setBounds(12, 183, 643, 200);
        panel.add(panelAutor);
        panelAutor.setLayout(null);

        // Cédula Autor
        JLabel lblCedulaAutor = new JLabel("Cédula:");
        lblCedulaAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCedulaAutor.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblCedulaAutor.setBounds(27, 39, 70, 16);
        panelAutor.add(lblCedulaAutor);

        txtCedulaAutor = new JTextField();
        txtCedulaAutor.setBounds(107, 37, 200, 22);
        panelAutor.add(txtCedulaAutor);

        // Nombre Autor
        JLabel lblNombreAutor = new JLabel("Nombre:");
        lblNombreAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNombreAutor.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblNombreAutor.setBounds(28, 96, 56, 16);
        panelAutor.add(lblNombreAutor);

        txtNombreAutor = new JTextField();
        txtNombreAutor.setEditable(false);
        txtNombreAutor.setBounds(107, 94, 200, 22);
        panelAutor.add(txtNombreAutor);

        // Apellidos Autor
        JLabel lblApellidosAutor = new JLabel("Apellidos:");
        lblApellidosAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblApellidosAutor.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblApellidosAutor.setBounds(27, 155, 70, 16);
        panelAutor.add(lblApellidosAutor);

        txtApellidosAutor = new JTextField();
        txtApellidosAutor.setEditable(false);
        txtApellidosAutor.setBounds(107, 153, 200, 22);
        panelAutor.add(txtApellidosAutor);

        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTelefono.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblTelefono.setBounds(345, 96, 70, 16);
        panelAutor.add(lblTelefono);

        txtTelefonoAutor = new JTextField();
        txtTelefonoAutor.setEditable(false);
        txtTelefonoAutor.setBounds(425, 94, 200, 22);
        panelAutor.add(txtTelefonoAutor);
        
        JButton btnNewButton = new JButton("Buscar");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		participante = GestionEvento.getInstance().buscarPersonasCedula(txtCedulaAutor.getText().toString());
        		if(participante != null) {
        			if(participante instanceof Participante) {
        				existe = true;
        				txtNombreAutor.setText(participante.getNombre());
        				txtApellidosAutor.setText(participante.getApellidos());
        				txtTelefonoAutor.setText(participante.getTelefono());
        				txtNombreAutor.setEditable(false);
        				txtApellidosAutor.setEditable(false);
        				txtTelefonoAutor.setEditable(false);
        			}
        		}else {
        			existe = false;
        			txtNombreAutor.setEditable(true);
        			txtApellidosAutor.setEditable(true);
        			txtTelefonoAutor.setEditable(true);
        		}
        	}
        });
        btnNewButton.setBounds(345, 37, 89, 23);
        panelAutor.add(btnNewButton);

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(existe) {
        			if(txtNombre.getText().toString().equals("") || cmbArea.getSelectedIndex() == 0) {
        				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos generales.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
        			}else {
        				TrabajoCientifico trabajo = new TrabajoCientifico(txtId.getText().toString(), txtNombre.getText().toString(), cmbArea.getSelectedItem().toString(), (Participante) participante);
        				GestionEvento.getInstance().insertarTrabajo(trabajo);
        				JOptionPane.showMessageDialog(null, "Registro exitoso.", 
                                "Aviso", JOptionPane.WARNING_MESSAGE);
        				clean();
        			}
        		}else {
        			if(txtNombre.getText().toString().equals("") || cmbArea.getSelectedIndex() == 0) {
        				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos generales.", 
        						"Error", JOptionPane.WARNING_MESSAGE);
        			}else {
        				if(txtCedulaAutor.getText().toString().equals("") || txtNombreAutor.getText().toString().equals("") || txtApellidosAutor.getText().toString().equals("") || txtTelefonoAutor.getText().toString().equals("")) {
        					JOptionPane.showMessageDialog(null, "Debe llenar todos los datos del autor.", 
                                    "Error", JOptionPane.ERROR_MESSAGE);
        				}else {
        					Participante participante = new Participante(("P-"+GestionEvento.getInstance().codPersona), txtCedulaAutor.getText().toString(), txtNombreAutor.getText().toString(), txtApellidosAutor.getText().toString(), txtTelefonoAutor.getText().toString());
        					TrabajoCientifico trabajo = new TrabajoCientifico(txtId.getText().toString(), txtNombre.getText().toString(), cmbArea.getSelectedItem().toString(), participante);
        					GestionEvento.getInstance().insertarPersonas(participante);
        					GestionEvento.getInstance().insertarTrabajo(trabajo);
        					JOptionPane.showMessageDialog(null, "Registro exitoso.", 
        							"Aviso", JOptionPane.WARNING_MESSAGE);
        					clean();
        				}
        			}
        		}
        	}
        });
        okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonPane.add(btnCancelar);
    }

    private void clean() {
        txtId.setText("T-" + GestionEvento.getInstance().codTrabajos);
        txtNombre.setText("");
        cmbArea.setSelectedIndex(0);
        txtCedulaAutor.setText("");
        txtNombreAutor.setText("");
        txtNombreAutor.setEditable(false);
        txtApellidosAutor.setText("");
        txtApellidosAutor.setEditable(false);
        txtTelefonoAutor.setText("");
        txtTelefonoAutor.setEditable(false);
    }
}