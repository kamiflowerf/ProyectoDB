package visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import DAO.AreaDAO;
import logico.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ModPersona extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JRadioButton rdJurd;
    private JRadioButton rdPart;
    private JComboBox<Area> cmbArea;
    private Persona miPersona;

    public static void main(String[] args) {
        try {
            ModPersona dialog = new ModPersona(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ModPersona(Persona persona) {
        this.miPersona = persona;
        if (persona == null) {
            dispose();
            return;
        }

        setTitle("Modificar Persona");
        
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        
        setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        getContentPane().setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        setBounds(100, 100, 583, 369);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient")));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        // Panel de datos generales
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
            "Datos generales:", TitledBorder.LEADING, TitledBorder.TOP, null, 
            UIManager.getColor("FormattedTextField.foreground")));
        panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_1.setBounds(10, 0, 541, 150);
        panel.add(panel_1);
        panel_1.setLayout(null);

        // Campos con los datos de la persona
        JLabel lblNewLabel = new JLabel("C�dula:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel.setBounds(10, 31, 50, 14);
        panel_1.add(lblNewLabel);

        txtCedula = new JTextField(persona.getCedula());
        txtCedula.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtCedula.setEditable(false);
        txtCedula.setBounds(76, 28, 172, 20);
        panel_1.add(txtCedula);

        JLabel lblNewLabel_1 = new JLabel("Nombre:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_1.setBounds(10, 76, 63, 14);
        panel_1.add(lblNewLabel_1);

        txtNombre = new JTextField(persona.getNombre());
        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtNombre.setBounds(77, 73, 150, 20);
        panel_1.add(txtNombre);

        JLabel lblNewLabel_2 = new JLabel("Apellido:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2.setBounds(10, 113, 63, 14);
        panel_1.add(lblNewLabel_2);

        txtApellido = new JTextField(persona.getApellidos());
        txtApellido.setBounds(76, 110, 150, 20);
        panel_1.add(txtApellido);

        JLabel lblNewLabel_3 = new JLabel("Teléfono:");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_3.setBounds(283, 76, 70, 14);
        panel_1.add(lblNewLabel_3);

        txtTelefono = new JTextField(persona.getTelefono());
        txtTelefono.setBounds(345, 73, 135, 20);
        panel_1.add(txtTelefono);

        // Panel tipo
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_2.setBorder(new TitledBorder(null, "Tipo:", TitledBorder.LEADING, TitledBorder.TOP, 
            null, UIManager.getColor("FormattedTextField.foreground")));
        panel_2.setBounds(10, 152, 541, 54);
        panel.add(panel_2);
        panel_2.setLayout(null);

        rdPart = new JRadioButton("Participante");
        rdPart.setFont(new Font("Tahoma", Font.BOLD, 13));
        rdPart.setEnabled(false);
        rdPart.setSelected(persona instanceof Participante);
        rdPart.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        rdPart.setBounds(76, 15, 109, 23);
        panel_2.add(rdPart);

        rdJurd = new JRadioButton("Jurado");
        rdJurd.setFont(new Font("Tahoma", Font.BOLD, 13));
        rdJurd.setEnabled(false);
        rdJurd.setSelected(persona instanceof Jurado);
        rdJurd.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        rdJurd.setBounds(287, 15, 109, 23);
        panel_2.add(rdJurd);

        // Panel �rea
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_3.setBounds(10, 216, 541, 64);
        panel.add(panel_3);
        panel_3.setLayout(null);

        JLabel lblNewLabel_4 = new JLabel("Area:");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_4.setBounds(10, 24, 46, 14);
        panel_3.add(lblNewLabel_4);

        AreaDAO areaDAO = new AreaDAO();
        try{
            ArrayList<Area> areas = areaDAO.getAll();
            DefaultComboBoxModel<Area> model = new DefaultComboBoxModel<>();
            model.addElement(new Area("0","<Seleccione>"));

            for(Area area : areas){
                model.addElement(area);
            }

            cmbArea = new JComboBox<>(model);
            cmbArea.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Area) {
                        Area area = (Area) value;
                        setText(area.getNombre());
                    }
                    return this;
                }
            });

            if(miPersona instanceof Jurado){
                if(((Jurado)miPersona).getArea() != null){
                    for (int i = 0; i < cmbArea.getItemCount(); i++) {
                        Area item = cmbArea.getItemAt(i);
                        if (Objects.equals(item.getIdArea(), ((Jurado)miPersona).getArea().getIdArea())) {
                            cmbArea.setSelectedIndex(i);
                            break;
                        }
                    }
                } else {
                    cmbArea.setSelectedIndex(0);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        cmbArea.setEnabled(false);
        cmbArea.setBounds(76, 21, 165, 20);
        panel_3.add(cmbArea);

        // Si es jurado, seleccionar su �rea
        if (persona instanceof Jurado) {
            Jurado jurado = (Jurado) persona;
            cmbArea.setSelectedItem(jurado.getArea());
        }

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Modificar");
        okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                        txtTelefono.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Actualizar los datos de la persona
                    miPersona.setNombre(txtNombre.getText());
                    miPersona.setApellidos(txtApellido.getText());
                    miPersona.setTelefono(txtTelefono.getText());

                    JOptionPane.showMessageDialog(null, "Persona modificada exitosamente.", 
                        "Modificación", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }
}