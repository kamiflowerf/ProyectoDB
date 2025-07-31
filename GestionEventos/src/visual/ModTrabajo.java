package visual;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import logico.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModTrabajo extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txtId;
    private Boolean existe = false;
    private Persona obj = null;
    private JTextField txtNombre;
    private JComboBox cmbArea;
    private JPanel panelAutor;
    private JTextField txtCedulaAutor;
    private JTextField txtNombreAutor;
    private JTextField txtApellidosAutor;
    private JTextField txtTelefonoAutor;
    private TrabajoCientifico trabajo;

    public static void main(String[] args) {
        try {
            ModTrabajo dialog = new ModTrabajo(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ModTrabajo(TrabajoCientifico trabajo) {
        this.trabajo = trabajo;
        if (trabajo == null) {
            dispose();
            return;
        }

        setTitle("Modificar Trabajo Científico");
        
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

        txtId = new JTextField(trabajo.getId());
        txtId.setEditable(false);
        txtId.setBounds(104, 30, 116, 22);
        panel_1.add(txtId);

        // Nombre Trabajo
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNombre.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblNombre.setBounds(23, 74, 56, 16);
        panel_1.add(lblNombre);

        txtNombre = new JTextField(trabajo.getNombre());
        txtNombre.setBounds(104, 72, 230, 22);
        panel_1.add(txtNombre);

        // Área
        JLabel lblArea = new JLabel("Área:");
        lblArea.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblArea.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblArea.setBounds(23, 116, 56, 16);
        panel_1.add(lblArea);

        cmbArea = new JComboBox();
        cmbArea.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Tecnología en informática", 
            "Ciencias de la salud", "Ciencias Sociales", "Investigación y Desarrollo"}));
        cmbArea.setSelectedItem(trabajo.getArea());
        cmbArea.setEnabled(false);
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

        // Datos del autor
        Participante autor = trabajo.getAutor();

        // Cédula Autor
        JLabel lblCedulaAutor = new JLabel("Cédula:");
        lblCedulaAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCedulaAutor.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblCedulaAutor.setBounds(27, 39, 70, 16);
        panelAutor.add(lblCedulaAutor);

        txtCedulaAutor = new JTextField(autor.getCedula());
        txtCedulaAutor.setBounds(107, 37, 200, 22);
        panelAutor.add(txtCedulaAutor);

        // Nombre Autor
        JLabel lblNombreAutor = new JLabel("Nombre:");
        lblNombreAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNombreAutor.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblNombreAutor.setBounds(28, 96, 56, 16);
        panelAutor.add(lblNombreAutor);

        txtNombreAutor = new JTextField(autor.getNombre());
        txtNombreAutor.setEditable(false);
        txtNombreAutor.setBounds(107, 94, 200, 22);
        panelAutor.add(txtNombreAutor);

        // Apellidos Autor
        JLabel lblApellidosAutor = new JLabel("Apellidos:");
        lblApellidosAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblApellidosAutor.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblApellidosAutor.setBounds(27, 155, 70, 16);
        panelAutor.add(lblApellidosAutor);

        txtApellidosAutor = new JTextField(autor.getApellidos());
        txtApellidosAutor.setEditable(false);
        txtApellidosAutor.setBounds(107, 153, 200, 22);
        panelAutor.add(txtApellidosAutor);

        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTelefono.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblTelefono.setBounds(345, 96, 70, 16);
        panelAutor.add(lblTelefono);

        txtTelefonoAutor = new JTextField(autor.getTelefono());
        txtTelefonoAutor.setEditable(false);
        txtTelefonoAutor.setBounds(425, 94, 200, 22);
        panelAutor.add(txtTelefonoAutor);
        
        JButton btnNewButton = new JButton("Buscar");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		obj = GestionEvento.getInstance().buscarPersonasCedula(txtCedulaAutor.getText().toString());
        		if(obj != null) {
        			if(obj instanceof Participante) {
        				existe = true;
        				txtNombreAutor.setText(obj.getNombre());
        				txtApellidosAutor.setText(obj.getApellidos());
        				txtTelefonoAutor.setText(obj.getTelefono());
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
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(existe) {
        			if(!(cmbArea.getSelectedItem().toString().equals(""))) {
        				trabajo.setNombre(txtNombre.getText().toString());
        				obj = (Participante)GestionEvento.getInstance().buscarPersonasCedula(txtCedulaAutor.getText().toString());
        				trabajo.setAutor((Participante) obj);
        				JOptionPane.showMessageDialog(null, "Modificación completada.", 
                                "Modificación", JOptionPane.WARNING_MESSAGE);
        				dispose();
        			}else {
        				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos generales.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
        			}
        		}else {
        			trabajo.setNombre(txtNombre.getText().toString());
        			Participante participante = new Participante(("P-"+GestionEvento.getInstance().codPersona),txtCedulaAutor.getText().toString(), txtNombreAutor.getText().toString(), txtApellidosAutor.getText().toString(), txtTelefonoAutor.getText().toString());
        			GestionEvento.getInstance().insertarPersonas(participante);
    				trabajo.setAutor(participante);
    				JOptionPane.showMessageDialog(null, "Modificación completada.", 
                            "Modificación", JOptionPane.WARNING_MESSAGE);
    				dispose();
        		}
        	}
        });
        btnModificar.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonPane.add(btnModificar);
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonPane.add(btnCancelar);
    }
}