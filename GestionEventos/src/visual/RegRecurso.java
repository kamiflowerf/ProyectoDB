package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import DAO.LocalDAO;
import DAO.RecursoDAO;
import DAO.TipoRecursoDAO;
import logico.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegRecurso extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txt_Id;
    private JTextField txt_Nombre;
    private JComboBox<TipoRecurso> cbxTipo;
    private JPanel panel_otro;
    private JPanel panel_campus;
    private JRadioButton rdLocal;
    private JRadioButton rdOtro;
    private JComboBox<Local> cmbCampus;
    private Boolean modificar = false;
    private Recurso recursoAModificar = null;
    
    public RegRecurso(Recurso recurso) {
        this(); 
        
        try {
            this.recursoAModificar = recurso;
            this.modificar = true;
            setTitle("Modificar Recurso");
            
            txt_Id.setText(recurso.getId());
            txt_Nombre.setText(recurso.getNombre());
            
            if(recurso.getTipo().equals("Local")) {
                rdLocal.setSelected(true);
                rdOtro.setSelected(false);
                rdLocal.setEnabled(false);
                rdOtro.setEnabled(false);
                panel_campus.setVisible(true);
                panel_otro.setVisible(false);
                cmbCampus.setSelectedItem(recurso.getLocal().getCiudad());
            } else {
                rdLocal.setSelected(false);
                rdOtro.setSelected(true);
                rdLocal.setEnabled(false);
                rdOtro.setEnabled(false);
                panel_campus.setVisible(false);
                panel_otro.setVisible(true);
                cbxTipo.setSelectedItem(recurso.getTipo());
            }
            
            for (Component comp : getContentPane().getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component btn : panel.getComponents()) {
                        if (btn instanceof JButton) {
                            JButton button = (JButton) btn;
                            if (button.getText().equals("Registrar")) {
                                button.setText("Modificar");
                                break;
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar los datos del recurso",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            RegRecurso dialog = new RegRecurso();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegRecurso() {
        
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        
        setFont(new Font("Dialog", Font.BOLD, 12));
        setTitle("Registrar Recursos");
        setBounds(100, 100, 630, 380);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient")));
        contentPanel.add(panel);
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setForeground(new Color(255, 255, 255));
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos Generales", 
            TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
        panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_1.setBounds(12, 13, 574, 117);
        panel.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("ID:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblNewLabel.setBounds(27, 32, 26, 16);
        panel_1.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Nombre:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_1.setForeground(UIManager.getColor("FormattedTextField.foreground"));
        lblNewLabel_1.setBounds(27, 74, 56, 16);
        panel_1.add(lblNewLabel_1);
        
        txt_Id = new JTextField();
        txt_Id.setEditable(false);
        txt_Id.setText("R-"+GeneradorCodigos.generarCodigoUnico(5));
        txt_Id.setBounds(112, 29, 56, 22);
        panel_1.add(txt_Id);
        txt_Id.setColumns(10);
        
        txt_Nombre = new JTextField();
        txt_Nombre.setBounds(112, 71, 230, 22);
        panel_1.add(txt_Nombre);
        txt_Nombre.setColumns(10);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_2.setBorder(new TitledBorder(null, "Tipo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(12, 136, 574, 68);
        panel.add(panel_2);
        panel_2.setLayout(null);
        
        rdLocal = new JRadioButton("Recurso local");
        rdLocal.setFont(new Font("Tahoma", Font.BOLD, 13));
        rdLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdLocal.setSelected(true);
                rdOtro.setSelected(false);
                panel_campus.setVisible(true);
                panel_otro.setVisible(false);
            }
        });
        rdLocal.setSelected(true);
        rdLocal.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        rdLocal.setBounds(82, 22, 127, 25);
        panel_2.add(rdLocal);
        
        rdOtro = new JRadioButton("Otro");
        rdOtro.setFont(new Font("Tahoma", Font.BOLD, 13));
        rdOtro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdLocal.setSelected(false);
                rdOtro.setSelected(true);
                panel_campus.setVisible(false);
                panel_otro.setVisible(true);
            }
        });
        rdOtro.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        rdOtro.setBounds(340, 22, 127, 25);
        panel_2.add(rdOtro);
        
        panel_campus = new JPanel();
        panel_campus.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_campus.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_campus.setBounds(12, 217, 574, 68);
        panel.add(panel_campus);
        panel_campus.setLayout(null);
        
        LocalDAO localDAO = new LocalDAO();
        try{
            ArrayList<Local> ciudades = localDAO.getAll();
            DefaultComboBoxModel<Local> model = new DefaultComboBoxModel<>();
            model.addElement(new Local("0","<Seleccione>"));

            for(Local ciudad : ciudades){
                model.addElement(ciudad);
            }

            cmbCampus = new JComboBox<>(model);
            cmbCampus.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                  boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Local) {
                            Local ciudad = (Local) value;
                            setText(ciudad.getCiudad());
                        }
                        return this;
                    }
                });
                cmbCampus.setSelectedIndex(0);
			} catch (SQLException e){
				e.printStackTrace();
			}
        cmbCampus.setBounds(116, 21, 164, 22);
        panel_campus.add(cmbCampus);
        
        JLabel lblNewLabel_3 = new JLabel("Campus:");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_3.setBounds(31, 25, 78, 14);
        panel_campus.add(lblNewLabel_3);
        
        panel_otro = new JPanel();
        panel_otro.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_otro.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel_otro.setBounds(12, 217, 574, 68);
        panel.add(panel_otro);
        panel_otro.setLayout(null);
        panel_otro.setVisible(false);
        
        JLabel lblNewLabel_2 = new JLabel("Tipo:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2.setBounds(31, 25, 56, 16);
        panel_otro.add(lblNewLabel_2);
        
        TipoRecursoDAO trDAO = new TipoRecursoDAO();
        try{
            ArrayList<TipoRecurso> tipos = trDAO.getAll();
            DefaultComboBoxModel<TipoRecurso> model = new DefaultComboBoxModel<>();
            model.addElement(new TipoRecurso("0","<Seleccione>"));

            for(TipoRecurso tipo : tipos){
                model.addElement(tipo);
            }

            cbxTipo = new JComboBox<>(model);
            cbxTipo.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                  boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof TipoRecurso) {
                            TipoRecurso tipo = (TipoRecurso) value;
                            setText(tipo.getNombre());
                        }
                        return this;
                    }
                });
            cbxTipo.setSelectedIndex(0);
            cbxTipo.setBounds(116, 21, 152, 22);
            panel_otro.add(cbxTipo);
        } catch (SQLException e){
            e.printStackTrace();
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(modificar == false) {
        			if(!(txt_Nombre.getText().isEmpty())) {
        				if(rdLocal.isSelected()) {
        					if(cmbCampus.getSelectedIndex() > 0) {
        						Local localObj = (Local)cmbCampus.getSelectedItem();
                                Recurso localRecurso = new Recurso(txt_Id.getText(),
                                        txt_Nombre.getText(),
                                        null,
                                        true,
                                        localObj);
                                try {
                                    GestionEvento.getInstance().insertarRecurso(localRecurso);
                                    JOptionPane.showMessageDialog(null, "Recurso registrado exitosamente",
                                            "Registro", JOptionPane.INFORMATION_MESSAGE);
                                    clean();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
        					} else {
        						JOptionPane.showMessageDialog(null, "Debe seleccionar un campus", 
        								"Error", JOptionPane.ERROR_MESSAGE);
        					}
        				} else {
        					if(cbxTipo.getSelectedIndex() > 0) {
        						Recurso otro = new Recurso(txt_Id.getText(),
        								txt_Nombre.getText(),
                                        (TipoRecurso) cbxTipo.getSelectedItem(),
                                        true,
                                        null);
                                try {
                                    GestionEvento.getInstance().insertarRecurso(otro);
                                    JOptionPane.showMessageDialog(null, "Recurso registrado exitosamente",
                                            "Registro", JOptionPane.INFORMATION_MESSAGE);
                                    clean();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
        					} else {
        						JOptionPane.showMessageDialog(null, "Debe especificar el tipo de recurso",
        								"Error", JOptionPane.ERROR_MESSAGE);
        					}
        				}
        			} else {
        				JOptionPane.showMessageDialog(null, "Debe completar todos los campos", 
        						"Error", JOptionPane.ERROR_MESSAGE);
        			}
        		}else {
        			String cod = txt_Id.getText();
                    Recurso recursoMod = null;
                    try {
                        recursoMod = GestionEvento.getInstance().buscarRecursoID(cod);
                        recursoMod.setNombre(txt_Nombre.getText());
                        if(recursoMod.getLocal() != null) {
                            recursoMod.setLocal((Local)cmbCampus.getSelectedItem());
                        }else {
                            recursoMod.setTipo((TipoRecurso) cbxTipo.getSelectedItem());
                        }
                        RecursoDAO recDao = new RecursoDAO();
                        recDao.update(recursoMod);
                        JOptionPane.showMessageDialog(null, "Modificación exitosa.",
                                "Modificación", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
        		}
        	}
        });
        okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCancelar.setActionCommand("OK");
        buttonPane.add(btnCancelar);
    }

    private void clean() {
        txt_Id.setText("R-"+GeneradorCodigos.generarCodigoUnico(5));
        txt_Nombre.setText("");
        cbxTipo.setSelectedIndex(0);
        rdLocal.setSelected(true);
        rdOtro.setSelected(false);
        panel_campus.setVisible(true);
        panel_otro.setVisible(false);
        cmbCampus.setSelectedIndex(0);
    }
}