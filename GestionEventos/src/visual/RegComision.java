package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DAO.AreaDAO;
import DAO.ComisionDAO;
import DAO.ComisionTrabajoDAO;
import logico.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.Objects;

public class RegComision extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private DefaultTableModel modeloNoSelectJurado;
	private DefaultTableModel modeloSelectJurado;
	private DefaultTableModel modeloNoSelectTrabajo;
	private DefaultTableModel modeloSelecTrabajo;
	private Object rowJuradoSelect[];
	private Object rowJuradoNoSelect[];
	private Object rowTrabajoSelect[];
	private Object rowTrabajoNoSelect[];
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JComboBox<Area> cbxArea;
	private Comision comisionAModificar = null;
	private JButton btnQuitJurado;
	private JButton btnAddJurado;
	private JButton btnAddTrabajo;
	private JButton btnQuitTrabajo;
	private int indexJ = -1;
	private int indexT = -1;
	private int indexJS = -1;
	private int indexTS = -1;
	private Jurado selectedJurado = null;
	private TrabajoCientifico selectedTrabajo = null;
	

	
	public RegComision(Comision comision) {
	    this();
	    
	    try {
	        this.comisionAModificar = comision;
	        
	        setTitle("Modificar Comisión");
	        
	        txtCodigo.setText(comision.getCodComision());
	        txtNombre.setText(comision.getNombre());
	        cbxArea.setSelectedItem(comision.getArea());
	        
	        for (Persona persona : GestionEvento.getInstance().getMisPersonas()) {
	            if (persona instanceof Jurado) {
	                ((Jurado) persona).setSeleccionado(false);
	            }
	        }
	        
	        for (TrabajoCientifico trabajo : GestionEvento.getInstance().getMisTrabajosCientificos()) {
	            trabajo.setSeleccionado(false);
	        }
	        
	        loadJurados();
	        loadNoSelectTrabajos();
	        
	        for (Jurado jurado : comision.getJurado()) {
	            jurado.setSeleccionado(true);
	        }
	        
	        for (TrabajoCientifico trabajo : comision.getTrabajos()) {
	            trabajo.setSeleccionado(true);
	        }
	        
	        loadJurados();
	        loadSelectJurados();
	        loadNoSelectTrabajos();
	        loadSelectTrabajos();
	        
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
	            "Error al cargar los datos de la comisión",
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegComision dialog = new RegComision();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegComision() {
		
		setTitle("Registrar Comisiones");
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
		
		setBounds(100, 100, 1115, 576);
		setLocationRelativeTo(null);
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
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos:", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			panel_1.setBounds(10, 11, 1069, 117);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("C\u00F3digo:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setForeground(Color.BLACK);
			lblNewLabel.setBounds(10, 31, 50, 15);
			panel_1.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setForeground(Color.BLACK);
			lblNewLabel_1.setBounds(10, 80, 55, 14);
			panel_1.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("\u00C1rea:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_2.setForeground(Color.BLACK);
			lblNewLabel_2.setBounds(240, 31, 40, 14);
			panel_1.add(lblNewLabel_2);
			
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setText("C-"+ GeneradorCodigos.generarCodigoUnico(5));
			txtCodigo.setBounds(67, 28, 86, 20);
			panel_1.add(txtCodigo);
			txtCodigo.setColumns(10);
			
			txtNombre = new JTextField();
			txtNombre.setBounds(67, 77, 161, 20);
			panel_1.add(txtNombre);
			txtNombre.setColumns(10);
			
			AreaDAO areaDAO = new AreaDAO();
			try{
				ArrayList<Area> areas = areaDAO.getAll();
				DefaultComboBoxModel<Area> model = new DefaultComboBoxModel<>();
				model.addElement(new Area("0","<Seleccione>"));

				for(Area area : areas){
					model.addElement(area);
				}

				cbxArea = new JComboBox<>(model);
				cbxArea.setRenderer(new DefaultListCellRenderer() {
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
					cbxArea.setSelectedIndex(0);
			} catch (SQLException e){
				e.printStackTrace();
			}
			cbxArea.setBounds(282, 28, 179, 20);
			panel_1.add(cbxArea);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Jurados:", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_2.setBounds(10, 139, 524, 333);
			panel.add(panel_2);
			panel_2.setLayout(null);
			
			JPanel panel_4 = new JPanel();
			panel_4.setBounds(10, 23, 198, 299);
			panel_2.add(panel_4);
			panel_4.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel_4.add(scrollPane, BorderLayout.CENTER);
			
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexJ = table.getSelectedRow();
					if(indexJ >=  0) {
						String cedula = (String) table.getValueAt(indexJ, 0);
                        try {
                            selectedJurado = (Jurado) GestionEvento.getInstance().buscarPersonasCedula(cedula);
							if(selectedJurado != null) {
								btnAddJurado.setEnabled(true);
								btnQuitJurado.setEnabled(false);
							}
						} catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
					}
				}
			});
			modeloNoSelectJurado = new DefaultTableModel();
			String [] identificadores2 = {"Cédula", "Apellido","Area"};
			modeloNoSelectJurado.setColumnIdentifiers(identificadores2);
			table.setModel(modeloNoSelectJurado);
			scrollPane.setViewportView(table);
			
			JPanel panel_5 = new JPanel();
			panel_5.setBounds(316, 23, 198, 299);
			panel_2.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_2 = new JScrollPane();
			panel_5.add(scrollPane_2, BorderLayout.CENTER);
			
			table_2 = new JTable(); 
			table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexJS = table_2.getSelectedRow();
					if(indexJS >=  0) {
						String cedula = (String) table_2.getValueAt(indexJS, 0);
                        try {
                            selectedJurado = (Jurado) GestionEvento.getInstance().buscarPersonasCedula(cedula);
							if(selectedJurado != null) {
								btnAddJurado.setEnabled(false);
								btnQuitJurado.setEnabled(true);
							}
						} catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
					}
				}
			});
			modeloSelectJurado = new DefaultTableModel(); 
			String [] identificadores3 = {"Cédula", "Apellido","Area"};
			table_2.setModel(modeloSelectJurado); 
			scrollPane_2.setViewportView(table_2);
			modeloSelectJurado.setColumnIdentifiers(identificadores3);
			
			btnAddJurado = new JButton("Agregar");
			btnAddJurado.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnAddJurado.setEnabled(false);
			btnAddJurado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
			        if(selectedRow >= 0) {
			        	
			        	if(cbxArea.getSelectedIndex() == 0) {
			        		JOptionPane.showMessageDialog(null,"Debe elegir un área.", "Error", JOptionPane.ERROR_MESSAGE);
			        	}else if(modeloNoSelectJurado.getValueAt(selectedRow, 2).toString().equalsIgnoreCase(cbxArea.getSelectedItem().toString())) {
			                Object[] rowData = new Object[3];
			                for(int i = 0; i < 3; i++) {
			                    rowData[i] = modeloNoSelectJurado.getValueAt(selectedRow, i);
			                }
			                
			                modeloSelectJurado.addRow(rowData);
			                modeloNoSelectJurado.removeRow(selectedRow);
			                
			                if(modeloSelectJurado.getRowCount() > 0) {
			                    cbxArea.setEnabled(false);
			                }
			               
			                String nombre = rowData[0].toString();
			                String cedula = rowData[1].toString();
                            try {
                                for (Persona persona : GestionEvento.getInstance().getMisPersonas()) {
                                    if (persona instanceof Jurado &&
                                        persona.getNombre().equals(nombre) &&
                                        persona.getCedula().equals(cedula)) {
                                        ((Jurado) persona).setSeleccionado(true);
                                        break;
                                    }
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
			                JOptionPane.showMessageDialog(null, 
			                    "El �rea del jurado debe coincidir con el �rea de la comisi�n",
			                    "Error", JOptionPane.ERROR_MESSAGE);
			            }
			        }
				}
			});
			
			btnAddJurado.setBounds(217, 172, 89, 23);
			panel_2.add(btnAddJurado);
			
			btnQuitJurado = new JButton("Quitar");
			btnQuitJurado.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnQuitJurado.setEnabled(false);
			btnQuitJurado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table_2.getSelectedRow();
			        if(selectedRow >= 0) {
			            Object[] rowData = new Object[3];
			            for(int i = 0; i < 3; i++) {
			                rowData[i] = modeloSelectJurado.getValueAt(selectedRow, i);
			            }
			            
			            modeloNoSelectJurado.addRow(rowData);
			            modeloSelectJurado.removeRow(selectedRow);
			            
			            if(modeloSelectJurado.getRowCount() == 0) {
			                cbxArea.setEnabled(true);
			            }
			            
			            String nombre = rowData[0].toString();
			            String cedula = rowData[1].toString();
                        try {
                            for (Persona persona : GestionEvento.getInstance().getMisPersonas()) {
                                if (persona instanceof Jurado &&
                                    persona.getNombre().equals(nombre) &&
                                    persona.getCedula().equals(cedula)) {
                                    ((Jurado) persona).setSeleccionado(false);
                                    break;
                                }
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
				}
			});
			
			btnQuitJurado.setBounds(217, 205, 89, 23);
			panel_2.add(btnQuitJurado);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Trabajos Cient\u00EDficos:", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			panel_3.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_3.setBounds(555, 139, 524, 333);
			panel.add(panel_3);
			panel_3.setLayout(null);
			
			JPanel panel_6 = new JPanel();
			panel_6.setBounds(10, 25, 198, 299);
			panel_3.add(panel_6);
			panel_6.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel_6.add(scrollPane_1, BorderLayout.CENTER);
			
			table_1 = new JTable(); 
			table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexT= table_1.getSelectedRow();
					if(indexT >=  0) {
						String cod = (String) table_1.getValueAt(indexT, 0);
                        try {
                            selectedTrabajo = GestionEvento.getInstance().buscarTrabajoID(cod);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(selectedTrabajo != null) {
							btnAddTrabajo.setEnabled(true);
							btnQuitTrabajo.setEnabled(false);
						}

					}
				}
			});
			modeloNoSelectTrabajo = new DefaultTableModel(); 
			String[] identificadoresTrabajos = {"Código","Título", "Área"};
			table_1.setModel(modeloNoSelectTrabajo); 
			scrollPane_1.setViewportView(table_1);
			modeloNoSelectTrabajo.setColumnIdentifiers(identificadoresTrabajos); 
			
			btnAddTrabajo = new JButton("Agregar");
			btnAddTrabajo.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnAddTrabajo.setEnabled(false);
			btnAddTrabajo.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        int selectedRow = table_1.getSelectedRow();
			        if(selectedRow >= 0) {
						if (cbxArea.getSelectedIndex() == 0) {
							JOptionPane.showMessageDialog(null, "Debe elegir un área.", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						Area areaComision = (Area) cbxArea.getSelectedItem();
						if (selectedJurado != null && selectedJurado.getArea() != null &&
								selectedJurado.getArea().getIdArea().equals(areaComision.getIdArea())) {

							Object[] rowData = new Object[3];
							for (int i = 0; i < 3; i++) {
								rowData[i] = modeloNoSelectJurado.getValueAt(selectedRow, i);
							}

							modeloSelectJurado.addRow(rowData);
							modeloNoSelectJurado.removeRow(selectedRow);

							if (modeloSelectJurado.getRowCount() > 0) {
								cbxArea.setEnabled(false);
							}

							try {
								selectedJurado.setSeleccionado(true); // no necesitás buscarlo de nuevo
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						} else {
							JOptionPane.showMessageDialog(null,
									"El área del jurado debe coincidir con el área de la comisión",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
			        }
			    }
			});
			btnAddTrabajo.setBounds(218, 169, 89, 23);
			panel_3.add(btnAddTrabajo);
			
			JPanel panel_7 = new JPanel();
			panel_7.setBounds(316, 25, 198, 299);
			panel_3.add(panel_7);
			panel_7.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_3 = new JScrollPane();
			panel_7.add(scrollPane_3, BorderLayout.CENTER);
			
			table_3 = new JTable(); 
			table_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexTS = table_3.getSelectedRow();
					if(indexTS >=  0) {
						String cod = (String) table_3.getValueAt(indexTS, 0);
                        try {
                            selectedTrabajo = GestionEvento.getInstance().buscarTrabajoID(cod);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(selectedTrabajo != null) {
							btnAddTrabajo.setEnabled(false);
							btnQuitTrabajo.setEnabled(true);
						}

					}
				}
			});
			modeloSelecTrabajo = new DefaultTableModel(); 
			table_3.setModel(modeloSelecTrabajo); 
			scrollPane_3.setViewportView(table_3);
			modeloSelecTrabajo.setColumnIdentifiers(identificadoresTrabajos); 
			
			btnQuitTrabajo = new JButton("Quitar");
			btnQuitTrabajo.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnQuitTrabajo.setEnabled(false);
			btnQuitTrabajo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table_3.getSelectedRow();
			        if(selectedRow >= 0) {
			            Object[] rowData = new Object[3];
			            for(int i = 0; i < 3; i++) {
			                rowData[i] = modeloSelecTrabajo.getValueAt(selectedRow, i);
			            }
			            
			            modeloNoSelectTrabajo.addRow(rowData);
			            modeloSelecTrabajo.removeRow(selectedRow);
			            
			            if(modeloSelecTrabajo.getRowCount() == 0) {
			                cbxArea.setEnabled(true);
			            }
			            
			            String titulo = rowData[0].toString();
                        try {
                            for (TrabajoCientifico trabajo : GestionEvento.getInstance().getMisTrabajosCientificos()) {
                                if (trabajo.getNombre().equals(titulo)) {
                                    trabajo.setSeleccionado(false);
                                    break;
                                }
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
				}
			});
			
			btnQuitTrabajo.setBounds(218, 203, 89, 23);
			panel_3.add(btnQuitTrabajo);
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
						if(modeloSelectJurado.getRowCount() > 0 && modeloSelecTrabajo.getRowCount() > 0 && !txtNombre.getText().isEmpty() 
							&& cbxArea.getSelectedIndex() != 0) {
							
							Comision comision;
				            if(comisionAModificar == null) {
				                Area area = (Area) cbxArea.getSelectedItem();
								comision = new Comision(txtCodigo.getText(), txtNombre.getText(),
				                                    area);
				            } else {
				                comisionAModificar.setNombre(txtNombre.getText());
				                comisionAModificar.setArea((Area) cbxArea.getSelectedItem());
				                comisionAModificar.getJurado().clear();
				                comisionAModificar.getTrabajos().clear();
				                comision = comisionAModificar;
				            }

                            try {
                                for (Persona jurado : GestionEvento.getInstance().getMisPersonas()) {
                                    if(jurado instanceof Jurado) {
                                        if(((Jurado) jurado).isSeleccionado()) {
                                            comision.getJurado().add((Jurado) jurado);
                                            ((Jurado) jurado).setSeleccionado(false);
                                        }
                                    }
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                            try {
                                for (TrabajoCientifico trabajo : GestionEvento.getInstance().getMisTrabajosCientificos()) {
                                    if(trabajo.isSeleccionado()){
                                        comision.getTrabajos().add(trabajo);
                                        trabajo.setSeleccionado(false);
                                    }
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                            if(comisionAModificar == null) {
                                try {
                                    GestionEvento.getInstance().insertarComision(comision);
									JOptionPane.showMessageDialog(null, "Comisión registrada con éxito");
									clean();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                            } else {
								ComisionDAO cDao = new ComisionDAO();
                                try {
                                    cDao.update(comisionAModificar);
									JOptionPane.showMessageDialog(null, "Comisión modificada con éxito");
									dispose();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
				            }
					        } else {
					            if(txtNombre.getText().isEmpty() && cbxArea.getSelectedIndex() == 0) {
					                JOptionPane.showMessageDialog(null, "Debe completar todos los datos generales.");
					            } else if(modeloSelectJurado.getRowCount() == 0) {
					                JOptionPane.showMessageDialog(null, "Debe ingresar al menos un jurado.");
					            } else if(modeloSelecTrabajo.getRowCount() == 0) {
					                JOptionPane.showMessageDialog(null, "Debe ingresar al menos un trabajo cient�fico.");
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
		loadJurados();
		loadSelectJurados();
		loadNoSelectTrabajos();
		loadSelectTrabajos();
	}
	
	private void clean() throws SQLException {
	    txtCodigo.setText("C-"+GeneradorCodigos.generarCodigoUnico(5));
	    txtNombre.setText("");
	    cbxArea.setSelectedIndex(0);
	    cbxArea.setEnabled(true);
	    
	    for (Persona persona : GestionEvento.getInstance().getMisPersonas()) {
	        if (persona instanceof Jurado) {
	            ((Jurado) persona).setSeleccionado(false);
	        }
	    }
	    
	    for (TrabajoCientifico trabajo : GestionEvento.getInstance().getMisTrabajosCientificos()) {
	        trabajo.setSeleccionado(false);
	    }
	    
	    loadJurados();
	    loadSelectJurados();
	    loadNoSelectTrabajos();
	    loadSelectTrabajos();
	    
	    btnAddJurado.setEnabled(false);
	    btnQuitJurado.setEnabled(false);
	    btnAddTrabajo.setEnabled(false);
	    btnQuitTrabajo.setEnabled(false);
	}
	
	private void loadJurados() {
	    modeloNoSelectJurado.setRowCount(0);
        ArrayList<Persona> aux = new ArrayList<>();
        try {
            aux = GestionEvento.getInstance().getMisPersonas();
			rowJuradoSelect = new Object[3];
			for(Persona persona : aux) {
				if(persona instanceof Jurado && !((Jurado) persona).isSeleccionado()) {
					rowJuradoSelect[0] = persona.getCedula();
					rowJuradoSelect[1] = persona.getApellidos();
					rowJuradoSelect[2] = ((Jurado) persona).getArea();
					modeloNoSelectJurado.addRow(rowJuradoSelect);
				}
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	private void loadSelectJurados() { 
		modeloSelectJurado.setRowCount(0);
        ArrayList<Persona> aux = new ArrayList<>();
        try {
            aux = GestionEvento.getInstance().getMisPersonas();
			rowJuradoNoSelect = new Object[3];
			for (Persona persona : aux) {
				if (persona instanceof Jurado && ((Jurado) persona).isSeleccionado()) {
					rowJuradoNoSelect[0] = persona.getCedula();
					rowJuradoNoSelect[1] = persona.getApellidos();
					rowJuradoNoSelect[2] = ((Jurado) persona).getArea(); modeloSelectJurado.addRow(rowJuradoNoSelect);
				}
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	private void loadNoSelectTrabajos() {
	    modeloNoSelectTrabajo.setRowCount(0);
        ArrayList<TrabajoCientifico> aux = null;
        try {
            aux = GestionEvento.getInstance().getMisTrabajosCientificos();
			rowTrabajoNoSelect = new Object[3];
			for (TrabajoCientifico trabajo : aux) {
				if(!trabajo.isSeleccionado()) {
					rowTrabajoNoSelect[0] = trabajo.getId();
					rowTrabajoNoSelect[1] = trabajo.getNombre();
					rowTrabajoNoSelect[2] = trabajo.getArea();
					modeloNoSelectTrabajo.addRow(rowTrabajoNoSelect);
				}
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}


	private void loadSelectTrabajos() {
	    modeloSelecTrabajo.setRowCount(0);
        ArrayList<TrabajoCientifico> aux = new ArrayList<>();
        try {
            aux = GestionEvento.getInstance().getMisTrabajosCientificos();
			rowTrabajoSelect = new Object[3];
			for (TrabajoCientifico trabajo : aux) {
				if (trabajo.isSeleccionado()) {
					rowTrabajoNoSelect[0] = trabajo.getId();
					rowTrabajoNoSelect[1] = trabajo.getNombre();
					rowTrabajoNoSelect[2] = trabajo.getArea();
					modeloSelecTrabajo.addRow(rowTrabajoSelect);
				}
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	public boolean validarArea() {
		if (cbxArea.getSelectedIndex() <= 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un área",
					"Validación", JOptionPane.WARNING_MESSAGE);
			cbxArea.requestFocus();
			return false;
		}
		return true;
	}
	
	
}
