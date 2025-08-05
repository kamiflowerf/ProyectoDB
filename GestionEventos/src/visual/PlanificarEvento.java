package visual;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DAO.ComisionDAO;
import DAO.TipoEventoDAO;
import logico.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlanificarEvento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] headersComision = {"Código", "Nombre", "Área"};
	private String[] headersRecurso = {"Código", "Nombre", "Tipo"};
	private static DefaultTableModel modeloComision;
	private static Object[] rowComision;
	private static DefaultTableModel modeloRecurso;
	private static Object[] rowRecurso;
	private static DefaultTableModel modeloComisionSelected;
	private static Object[] rowComisionSelected;
	private static DefaultTableModel modeloRecursoSelected;
	private static Object[] rowRecursoSelected;
	private int indexC = -1;
	private int indexR = -1;
	private int indexCS = -1;
	private int indexRS = -1;
	private Comision selectedComision = null;
	private Recurso selectedRecurso = null;
	private JTextField txtCodigo;
	private JTextField txtTitulo;
	private JComboBox<TipoEvento> cmbTipo;
	private JTable tableComision;
	private JTable tableComisionS;
	private JButton btnQuitRecurso;
	private JButton btnAddRecurso;
	private JButton btnAddComision;
	private JButton btnQuitComision;
	private JTable tableRecurso;
	private JTable tableRecursoS;
	private JSpinner spnFecha;
	private Boolean tieneLocal = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PlanificarEvento dialog = new PlanificarEvento();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PlanificarEvento() {
		getContentPane().setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		setTitle("Planificador de Eventos");
		setBounds(100, 100, 1104, 506);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos generales:", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
			panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_1.setBounds(10, 11, 1063, 142);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Código:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(23, 33, 63, 14);
			panel_1.add(lblNewLabel);
			
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setText("E-"+ GeneradorCodigos.generarCodigoUnico(5));
			txtCodigo.setBounds(96, 30, 151, 20);
			panel_1.add(txtCodigo);
			txtCodigo.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Título:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setBounds(23, 76, 46, 14);
			panel_1.add(lblNewLabel_1);
			
			txtTitulo = new JTextField();
			txtTitulo.setBounds(96, 73, 281, 20);
			panel_1.add(txtTitulo);
			txtTitulo.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Tipo:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_2.setBounds(433, 33, 46, 14);
			panel_1.add(lblNewLabel_2);
			
			TipoEventoDAO teDAO = new TipoEventoDAO();
			try{
				ArrayList<TipoEvento> tipos = teDAO.getAll();
				DefaultComboBoxModel<TipoEvento> model = new DefaultComboBoxModel<>();
				model.addElement(new TipoEvento("0","<Seleccione>"));

				for(TipoEvento tipo : tipos){
					model.addElement(tipo);
				}
				cmbTipo = new JComboBox<>(model);
				cmbTipo.setRenderer(new DefaultListCellRenderer() {
						@Override
						public Component getListCellRendererComponent(JList<?> list, Object value, int index,
																	  boolean isSelected, boolean cellHasFocus) {
							super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
							if (value instanceof TipoEvento) {
								TipoEvento tipo = (TipoEvento) value;
								setText(tipo.getNombre());
							}
							return this;
						}
					});
				cmbTipo.setSelectedIndex(0);
			} catch (SQLException e){
				e.printStackTrace();
			}
			cmbTipo.setBounds(489, 30, 139, 20);
			panel_1.add(cmbTipo);
			
			JLabel lblNewLabel_3 = new JLabel("Fecha:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_3.setBounds(433, 76, 46, 14);
			panel_1.add(lblNewLabel_3);
			
			spnFecha = new JSpinner();
			Date fechaActual = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActual);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date limiteInferior = calendar.getTime();
			spnFecha.setModel(new SpinnerDateModel(fechaActual, limiteInferior, null, Calendar.DAY_OF_YEAR));
			spnFecha.setBounds(489, 73, 139, 20);
			panel_1.add(spnFecha);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "Comisiones", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
			panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_2.setBounds(10, 151, 209, 266);
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_2.add(scrollPane, BorderLayout.CENTER);
			
			tableComision = new JTable();
			tableComision.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableComision.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexC = tableComision.getSelectedRow();
					if(indexC >=  0) {
						String cod = (String) tableComision.getValueAt(indexC, 0);
                        try {
                            selectedComision = GestionEvento.getInstance().buscarComisionID(cod);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(selectedComision != null) {
							btnAddComision.setEnabled(true);
							btnQuitComision.setEnabled(false);
						}
					}
				}
			});
			modeloComision = new DefaultTableModel();
			modeloComision.setColumnIdentifiers(headersComision);
			tableComision.setModel(modeloComision);
			scrollPane.setViewportView(tableComision);
			
			btnAddComision = new JButton("Agregar");
			btnAddComision.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnAddComision.setEnabled(false);
			btnAddComision.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tableComision.getSelectedRow();

					if (selectedRow >= 0) {
						String idComision = (String) modeloComision.getValueAt(selectedRow, 0);
						ComisionDAO comDao = new ComisionDAO();
						try {
							Comision selectedComision = comDao.get(idComision);

							if (existeJurados(selectedComision)) {
								JOptionPane.showMessageDialog(null,
										"Esta comisión tiene jurados en común con otra ya seleccionada.",
										"Error", JOptionPane.ERROR_MESSAGE);
							} else {
								// IMPORTANTE: Marcar la comisión como seleccionada
								selectedComision.setSelected(true);

								// Agregar a la tabla de comisiones seleccionadas
								Object[] rowData = new Object[modeloComision.getColumnCount()];
								for (int i = 0; i < rowData.length; i++) {
									rowData[i] = modeloComision.getValueAt(selectedRow, i);
								}

								modeloComisionSelected.addRow(rowData);
								modeloComision.removeRow(selectedRow);
							}

						} catch (SQLException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Error al cargar la comisión.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}

					btnAddComision.setEnabled(false);
					btnQuitComision.setEnabled(false);
				}
			});
			btnAddComision.setBounds(229, 211, 87, 23);
			panel.add(btnAddComision);
			
			btnQuitComision = new JButton("Quitar");
			btnQuitComision.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnQuitComision.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tableComisionS.getSelectedRow();

					if (selectedRow >= 0) {
						String idComision = (String) modeloComisionSelected.getValueAt(selectedRow, 0);

						try {
							// IMPORTANTE: Desmarcar la comisión como no seleccionada
							Comision comision = GestionEvento.getInstance().buscarComisionID(idComision);
							if (comision != null) {
								comision.setSelected(false);
							}

							// Obtener los datos de la fila seleccionada
							Object[] rowData = new Object[modeloComisionSelected.getColumnCount()];
							for (int i = 0; i < rowData.length; i++) {
								rowData[i] = modeloComisionSelected.getValueAt(selectedRow, i);
							}

							// Mover la fila de tabla seleccionada a tabla disponible
							modeloComision.addRow(rowData);
							modeloComisionSelected.removeRow(selectedRow);

						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
					btnAddComision.setEnabled(false);
					btnQuitComision.setEnabled(false);
				}
			});
			btnQuitComision.setEnabled(false);
			btnQuitComision.setBounds(229, 294, 82, 23);
			panel.add(btnQuitComision);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Comisiones seleccionadas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_3.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_3.setBounds(321, 151, 209, 266);
			panel.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_3.add(scrollPane_1, BorderLayout.CENTER);
			
			tableComisionS = new JTable();
			tableComisionS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableComisionS.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexCS = tableComisionS.getSelectedRow();
					if(indexCS >=  0) {
						String cod = (String) tableComisionS.getValueAt(indexCS, 0);
                        try {
                            selectedComision = GestionEvento.getInstance().buscarComisionID(cod);
							if(selectedComision != null) {
								btnAddComision.setEnabled(false);
								btnQuitComision.setEnabled(true);
							}
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
					}
				}
			});
			modeloComisionSelected = new DefaultTableModel();
			modeloComisionSelected.setColumnIdentifiers(headersComision);
			tableComisionS.setModel(modeloComisionSelected);
			scrollPane_1.setViewportView(tableComisionS);

			JPanel panel_4 = new JPanel();
			panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Recursos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_4.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_4.setBounds(553, 151, 209, 266);
			panel.add(panel_4);
			panel_4.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_2 = new JScrollPane();
			scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_4.add(scrollPane_2, BorderLayout.CENTER);
			
			tableRecurso = new JTable();
			tableRecurso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableRecurso.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexR = tableRecurso.getSelectedRow();
					if(indexR >=  0) {
						String cod = (String) tableRecurso.getValueAt(indexR, 0);
                        try {
                            selectedRecurso = GestionEvento.getInstance().buscarRecursoID(cod);
							if(selectedRecurso != null) {
								btnAddRecurso.setEnabled(true);
								btnQuitRecurso.setEnabled(false);
							}
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
					}
				}
			});
			modeloRecurso = new DefaultTableModel();
			modeloRecurso.setColumnIdentifiers(headersRecurso);
			tableRecurso.setModel(modeloRecurso);
			scrollPane_2.setViewportView(tableRecurso);
			
			btnAddRecurso = new JButton("Agregar");
			btnAddRecurso.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnAddRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tableRecurso.getSelectedRow();

					if (selectedRow >= 0) {
						String idRecurso = (String) modeloRecurso.getValueAt(selectedRow, 0);

						try {
							// Obtener el recurso completo por su ID
							Recurso recurso = GestionEvento.getInstance().buscarRecursoID(idRecurso);

							if (recurso != null) {
								// Verificar si el recurso tiene un local y ya hay uno seleccionado
								if (recurso.getLocal() != null && tieneLocal) {
									JOptionPane.showMessageDialog(null,
											"Solo se puede seleccionar un local.",
											"Error", JOptionPane.ERROR_MESSAGE);
									return;
								}

								// Marcar el recurso como seleccionado
								recurso.setSelected(true);

								// Si tiene local, marcar que ya hay uno seleccionado
								if (recurso.getLocal() != null) {
									tieneLocal = true;
								}
							}

							Object[] rowData = new Object[modeloRecurso.getColumnCount()];
							for (int i = 0; i < rowData.length; i++) {
								rowData[i] = modeloRecurso.getValueAt(selectedRow, i);
							}
							modeloRecursoSelected.addRow(rowData);
							modeloRecurso.removeRow(selectedRow);

						} catch (SQLException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Error al procesar el recurso: " + ex.getMessage(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					btnAddRecurso.setEnabled(false);
					btnQuitRecurso.setEnabled(false);
				}
			});
			btnAddRecurso.setEnabled(false);
			btnAddRecurso.setBounds(772, 211, 87, 23);
			panel.add(btnAddRecurso);
			
			btnQuitRecurso = new JButton("Quitar");
			btnQuitRecurso.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnQuitRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tableRecursoS.getSelectedRow();

					if (selectedRow >= 0) {
						String idRecurso = (String) modeloRecursoSelected.getValueAt(selectedRow, 0);

						try {
							// Obtener el recurso completo por su ID
							Recurso recurso = GestionEvento.getInstance().buscarRecursoID(idRecurso);

							if (recurso != null) {
								// Desmarcar el recurso como no seleccionado
								recurso.setSelected(false);

								// Verificar si el recurso tiene un local (no es null)
								if (recurso.getLocal() != null) {
									tieneLocal = false;
								}
							}

							// Obtener los datos de la fila seleccionada
							Object[] rowData = new Object[modeloRecursoSelected.getColumnCount()];
							for (int i = 0; i < rowData.length; i++) {
								rowData[i] = modeloRecursoSelected.getValueAt(selectedRow, i);
							}

							// Mover la fila de tabla seleccionada a tabla disponible
							modeloRecurso.addRow(rowData);
							modeloRecursoSelected.removeRow(selectedRow);

						} catch (SQLException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Error al procesar el recurso: " + ex.getMessage(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}

					btnAddRecurso.setEnabled(false);
					btnQuitRecurso.setEnabled(false);
				}
			});
			btnQuitRecurso.setEnabled(false);
			btnQuitRecurso.setBounds(772, 294, 82, 23);
			panel.add(btnQuitRecurso);
			
			JPanel panel_5 = new JPanel();
			panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Recursos seleccionados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_5.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_5.setBounds(864, 151, 209, 266);
			panel.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_3 = new JScrollPane();
			scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_5.add(scrollPane_3, BorderLayout.CENTER);

			tableRecursoS = new JTable();
			tableRecursoS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableRecursoS.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexRS = tableRecursoS.getSelectedRow();
					if(indexRS >=  0) {
						String cod = (String) tableRecursoS.getValueAt(indexRS, 0);
                        try {
                            selectedRecurso = GestionEvento.getInstance().buscarRecursoID(cod);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(selectedRecurso != null) {
							btnAddRecurso.setEnabled(false);
							btnQuitRecurso.setEnabled(true);
						}
					}
				}
			});
			modeloRecursoSelected = new DefaultTableModel();
			modeloRecursoSelected.setColumnIdentifiers(headersRecurso);
			tableRecursoS.setModel(modeloRecursoSelected);
			scrollPane_3.setViewportView(tableRecursoS);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Planificar");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtTitulo.getText().isEmpty() || cmbTipo.getSelectedIndex() == 0) {
							JOptionPane.showMessageDialog(null,
									"Debe llenar todos los datos generales.",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							// NUEVA VALIDACIÓN: Verificar directamente las tablas de seleccionados
							int cantRecurSel = modeloRecursoSelected.getRowCount();
							int cantComiSel = modeloComisionSelected.getRowCount();

							if(cantRecurSel == 0 || cantComiSel == 0) {
								JOptionPane.showMessageDialog(null,
										"Debe seleccionar al menos una comisión y un recurso.",
										"Error", JOptionPane.ERROR_MESSAGE);
							} else {
								if(tieneLocal) {
									Date fecha = (Date) spnFecha.getValue();
									Evento evento = new Evento(txtCodigo.getText(), txtTitulo.getText(),
											(TipoEvento) cmbTipo.getSelectedItem(), fecha);

									try {
										// Agregar recursos seleccionados
										for (int i = 0; i < modeloRecursoSelected.getRowCount(); i++) {
											String idRecurso = (String) modeloRecursoSelected.getValueAt(i, 0);
											Recurso recurso = GestionEvento.getInstance().buscarRecursoID(idRecurso);
											if (recurso != null) {
												evento.getRecursos().add(recurso);
												recurso.setSelected(false); // Resetear después de agregar
											}
										}

										// Agregar comisiones seleccionadas
										for (int i = 0; i < modeloComisionSelected.getRowCount(); i++) {
											String idComision = (String) modeloComisionSelected.getValueAt(i, 0);
											Comision comision = GestionEvento.getInstance().buscarComisionID(idComision);
											if (comision != null) {
												evento.getComisiones().add(comision);
												comision.setSelected(false); // Resetear después de agregar
											}
										}

										GestionEvento.getInstance().insertarEvento(evento);
										JOptionPane.showMessageDialog(null,
												"Planificación exitosa.",
												"Aviso", JOptionPane.INFORMATION_MESSAGE);
										clean();

									} catch (SQLException ex) {
										ex.printStackTrace();
										JOptionPane.showMessageDialog(null,
												"Error al crear el evento: " + ex.getMessage(),
												"Error", JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null,
											"Su evento debe tener un local.",
											"Error", JOptionPane.ERROR_MESSAGE);
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
		loadComisiones();
		loadComisionesSelect();
		loadRecursos();
		loadRecursosSelect();
	}

	private void loadRecursosSelect() {
		modeloRecursoSelected.setRowCount(0);
        ArrayList<Recurso> aux = null;
        try {
            aux = GestionEvento.getInstance().getMisRecursos();
			for (Recurso obj : aux) {
				if(obj.getDisponibilidad()) {
					if(obj.getSelected()) {
						rowRecursoSelected = new Object[tableRecursoS.getColumnCount()];
						rowRecursoSelected[0] = obj.getId();
						rowRecursoSelected[1] = obj.getNombre();
						rowRecursoSelected[2] = obj.getTipo().getNombre();
						modeloRecursoSelected.addRow(rowRecursoSelected);
					}
				}
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	private void loadRecursos() {
		modeloRecurso.setRowCount(0);
        ArrayList<Recurso> aux = new ArrayList<>();
        try {
            aux = GestionEvento.getInstance().getMisRecursos();
			rowRecurso = new Object[tableRecurso.getColumnCount()];
			for (Recurso obj : aux) {
				if(obj.getDisponibilidad()) {
					if(!(obj.getSelected())) {
						rowRecurso[0] = obj.getId();
						rowRecurso[1] = obj.getNombre();
						if(obj.getTipo() != null)
							rowRecurso[2] = obj.getTipo().getNombre();
						else if(obj.getLocal() != null)
							rowRecurso[2] = obj.getLocal().getCiudad();
						modeloRecurso.addRow(rowRecurso);
					}
				}
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	private void loadComisionesSelect() {
		modeloComisionSelected.setRowCount(0);
        ArrayList<Comision> aux = null;
        try {
            aux = GestionEvento.getInstance().getMisComisiones();

			for (Comision obj : aux) {
				if(obj.getSelected()){
					rowComisionSelected = new Object[tableComisionS.getColumnCount()];
					rowComisionSelected[0] = obj.getCodComision();
					rowComisionSelected[1] = obj.getNombre();
					rowComisionSelected[2] = obj.getArea().getNombre();
					modeloComisionSelected.addRow(rowComisionSelected);
				}
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	private void loadComisiones() {
		modeloComision.setRowCount(0);
        ArrayList<Comision> aux = null;
        try {
            aux = GestionEvento.getInstance().getMisComisiones();
			rowComision = new Object[tableComision.getColumnCount()];
			for (Comision obj : aux) {
				if(!(obj.getSelected())){
					rowComision[0] = obj.getCodComision();
					rowComision[1] = obj.getNombre();
					rowComision[2] = obj.getArea().getNombre();
					modeloComision.addRow(rowComision);
				}
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	private Boolean existeJurados(Comision obj) {
        try {
            for (Comision comision : GestionEvento.getInstance().getMisComisiones()) {
                if(comision.getSelected()) {
                    for (Jurado jurado : comision.getJurado()) {
                        for (Jurado jurado2 : obj.getJurado()) {
                            if(jurado2.getIdJurado().equals(jurado.getIdJurado())) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
	}
	
	private void clean() {
		txtCodigo.setText("E-"+GeneradorCodigos.generarCodigoUnico(5));
		txtTitulo.setText("");
		cmbTipo.setSelectedIndex(0);
		Date fechaActual = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date limiteInferior = calendar.getTime();
		spnFecha.setModel(new SpinnerDateModel(fechaActual, limiteInferior, null, Calendar.DAY_OF_YEAR));
		btnAddComision.setEnabled(false);
		btnQuitComision.setEnabled(false);
		btnAddRecurso.setEnabled(false);
		btnQuitRecurso.setEnabled(false);
		tieneLocal = false;
		loadComisiones();
		loadComisionesSelect();
		loadRecursos();
		loadRecursosSelect();
	}
}
