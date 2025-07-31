package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Comision;
import logico.Evento;
import logico.GestionEvento;
import logico.Jurado;
import logico.Recurso;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ModEvento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] headersComision = {"Código", "Nombre", "Area"};
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
	private JComboBox cmbTipo;
	private JTable tableComision;
	private JTable tableComisionS;
	private JButton btnQuitRecurso;
	private JButton btnAddRecurso;
	private JButton btnAddComision;
	private JButton btnQuitComision;
	private JTable tableRecurso;
	private JTable tableRecursoS;
	private JSpinner spnFecha;
	private Boolean tieneLocal = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModEvento dialog = new ModEvento(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModEvento(Evento evento) {
		getContentPane().setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		setTitle("Modificar Evento");
		setBounds(100, 100, 1104, 506);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        
        for (Comision comision : evento.getComisiones()) {
			Comision encontrado = GestionEvento.getInstance().buscarComisionID(comision.getCodComision());
			if(encontrado != null) {
				encontrado.setSelected(true);
			}
		}
        
        for (Recurso recurso : evento.getRecursos()) {
			Recurso encontrado = GestionEvento.getInstance().buscarRecursoID(recurso.getId());
			if(encontrado != null) {
				encontrado.setSelected(true);
			}
		}
        
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
			
			JLabel lblNewLabel = new JLabel("C\u00F3digo:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(23, 32, 63, 17);
			panel_1.add(lblNewLabel);
			
			txtCodigo = new JTextField();
			txtCodigo.setText(evento.getId());
			txtCodigo.setEditable(false);
			txtCodigo.setBounds(96, 30, 151, 20);
			panel_1.add(txtCodigo);
			txtCodigo.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("T\u00EDtulo:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setBounds(23, 76, 46, 14);
			panel_1.add(lblNewLabel_1);
			
			txtTitulo = new JTextField();
			txtTitulo.setText(evento.getTitulo());
			txtTitulo.setBounds(96, 73, 281, 20);
			panel_1.add(txtTitulo);
			txtTitulo.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Tipo:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_2.setBounds(433, 33, 46, 14);
			panel_1.add(lblNewLabel_2);
			
			cmbTipo = new JComboBox();
			cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Conferencia", "Panel", "Ponencia", "Poster", "Mesa Reonda"}));
			cmbTipo.setBounds(489, 30, 139, 20);
			cmbTipo.setSelectedItem(evento.getTipo().toString());
			panel_1.add(cmbTipo);
			
			JLabel lblNewLabel_3 = new JLabel("Fecha:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_3.setBounds(433, 76, 46, 14);
			panel_1.add(lblNewLabel_3);
			
			spnFecha = new JSpinner();
			Date limiteInferior = new Date();
			spnFecha.setModel(new SpinnerDateModel(evento.getFecha(), limiteInferior, null, Calendar.DAY_OF_YEAR));
			spnFecha.setBounds(489, 73, 139, 20);
			panel_1.add(spnFecha);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "Comisiones", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
			panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_2.setBounds(10, 163, 209, 254);
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_2.add(scrollPane, BorderLayout.CENTER);
			
			tableComision = new JTable();
			tableComision.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexC = tableComision.getSelectedRow();
					if(indexC >=  0) {
						String cod = (String) tableComision.getValueAt(indexC, 0);
						selectedComision = GestionEvento.getInstance().buscarComisionID(cod);
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
					 if(existeJurados(selectedComision)) {
							JOptionPane.showMessageDialog(null, 
									"Esta comisión tiene jurados en común con otra ya seleccionada.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else {
							selectedComision.setSelected(true);
						}
						loadComisiones();
						loadComisionesSelect();
						btnAddComision.setEnabled(false);
						btnQuitComision.setEnabled(false);
				}
			});
			btnAddComision.setBounds(229, 211, 87, 23);
			panel.add(btnAddComision);
			
			btnQuitComision = new JButton("Quitar");
			btnQuitComision.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnQuitComision.setBackground(new Color(240, 240, 240));
			btnQuitComision.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedComision.setSelected(false);
					loadComisiones();
					loadComisionesSelect();
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
			panel_3.setBounds(321, 164, 209, 254);
			panel.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_3.add(scrollPane_1, BorderLayout.CENTER);
			
			tableComisionS = new JTable();
			tableComisionS.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexCS = tableComisionS.getSelectedRow();
					if(indexCS >=  0) {
						String cod = (String) tableComisionS.getValueAt(indexCS, 0);
						selectedComision = GestionEvento.getInstance().buscarComisionID(cod);
						if(selectedComision != null) {
							btnAddComision.setEnabled(false);
							btnQuitComision.setEnabled(true);
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
			panel_4.setBounds(553, 163, 209, 254);
			panel.add(panel_4);
			panel_4.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_2 = new JScrollPane();
			scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_4.add(scrollPane_2, BorderLayout.CENTER);
			
			tableRecurso = new JTable();
			tableRecurso.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexR = tableRecurso.getSelectedRow();
					if(indexR >=  0) {
						String cod = (String) tableRecurso.getValueAt(indexR, 0);
						selectedRecurso = GestionEvento.getInstance().buscarRecursoID(cod);
						if(selectedRecurso != null) {
							btnAddRecurso.setEnabled(true);
							btnQuitRecurso.setEnabled(false);
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
					if(!(tieneLocal) && (selectedRecurso.getTipo().toString().equals("Local"))) {
						selectedRecurso.setSelected(true);
						tieneLocal = true;
					}else if(tieneLocal && (selectedRecurso.getTipo().toString().equals("Local"))) {
						JOptionPane.showMessageDialog(null, 
				                "Solo se puede seleccionar un local.",
				                "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						selectedRecurso.setSelected(true);
					}
					loadRecursos();
					loadRecursosSelect();
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
					if(selectedRecurso.getTipo().toString().equals("Local")) {
						tieneLocal = false;
					}
					selectedRecurso.setSelected(false);
					loadRecursos();
					loadRecursosSelect();
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
			panel_5.setBounds(864, 164, 209, 254);
			panel.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_3 = new JScrollPane();
			scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_5.add(scrollPane_3, BorderLayout.CENTER);

			tableRecursoS = new JTable();
			tableRecursoS.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexRS = tableRecursoS.getSelectedRow();
					if(indexRS >=  0) {
						String cod = (String) tableRecursoS.getValueAt(indexRS, 0);
						selectedRecurso = GestionEvento.getInstance().buscarRecursoID(cod);
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
				JButton okButton = new JButton("Modificar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtTitulo.getText().toString().equals("") || cmbTipo.getSelectedIndex() == 0) {
							JOptionPane.showMessageDialog(null, 
					                "Debe llenar todos los datos generales.",
					                "Error", JOptionPane.ERROR_MESSAGE);
						}else {
							Date fecha = (Date) spnFecha.getValue();
							int cantRecurSel = 0;
							int cantComiSel = 0;
							for (Recurso obj : GestionEvento.getInstance().getMisRecursos()) {
								if(obj.getSelected()) {
									cantRecurSel++;
								}
							}
							for (Comision obj : GestionEvento.getInstance().getMisComisiones()) {
								if(obj.getSelected()) {
									cantComiSel++;
								}
							}
							if(cantRecurSel == 0 || cantComiSel == 0) {
								JOptionPane.showMessageDialog(null, 
										"Debe seleccionar al menos una comisión y un recurso.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}else {
								if(tieneLocal) {
									evento.setTitulo(txtTitulo.getText().toString());
									evento.setTipo(cmbTipo.getSelectedItem().toString());
									evento.setFecha(fecha);
									evento.getComisiones().clear();
									evento.getRecursos().clear();
									for (Recurso obj : GestionEvento.getInstance().getMisRecursos()) {
										if(obj.getSelected()) {
											evento.getRecursos().add(obj);
											obj.setSelected(false);
										}
									}
									for (Comision obj : GestionEvento.getInstance().getMisComisiones()) {
										if(obj.getSelected()) {
											evento.getComisiones().add(obj);
											obj.setSelected(false);
										}
									}
									JOptionPane.showMessageDialog(null, 
											"Modificación exitosa.",
											"Aviso", JOptionPane.WARNING_MESSAGE);
									dispose();
								}else {
									JOptionPane.showMessageDialog(null, 
							                "Su evento debe tener un local.",
							                "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				});
				okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (Comision comision : GestionEvento.getInstance().getMisComisiones()) {
							comision.setSelected(false);
						}
						for (Recurso recurso : GestionEvento.getInstance().getMisRecursos()) {
							recurso.setSelected(false);
						}
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
		ArrayList<Recurso> aux = GestionEvento.getInstance().getMisRecursos();
		rowRecursoSelected = new Object[tableRecursoS.getColumnCount()];
		for (Recurso obj : aux) {
			if(obj.getDisponibilidad()) {
				if(obj.getSelected()) {
					rowRecursoSelected[0] = obj.getId();
					rowRecursoSelected[1] = obj.getNombre();
					rowRecursoSelected[2] = obj.getTipo();
					modeloRecursoSelected.addRow(rowRecursoSelected);
				}
			}
		}
	}

	private void loadRecursos() {
		modeloRecurso.setRowCount(0);
		ArrayList<Recurso> aux = GestionEvento.getInstance().getMisRecursos();
		rowRecurso = new Object[tableRecurso.getColumnCount()];
		for (Recurso obj : aux) {
			if(obj.getDisponibilidad()) {
				if(!(obj.getSelected())) {
					rowRecurso[0] = obj.getId();
					rowRecurso[1] = obj.getNombre();
					rowRecurso[2] = obj.getTipo();
					modeloRecurso.addRow(rowRecurso);
				}
			}
		}	
	}

	private void loadComisionesSelect() {
		modeloComisionSelected.setRowCount(0);
		ArrayList<Comision> aux = GestionEvento.getInstance().getMisComisiones();
		rowComisionSelected = new Object[tableComisionS.getColumnCount()];
		for (Comision obj : aux) {
			if(obj.getSelected()){
				rowComisionSelected[0] = obj.getCodComision();
				rowComisionSelected[1] = obj.getNombre();
				rowComisionSelected[2] = obj.getArea();
				modeloComisionSelected.addRow(rowComisionSelected);
			}
		}	
	}

	private void loadComisiones() {
		modeloComision.setRowCount(0);
		ArrayList<Comision> aux = GestionEvento.getInstance().getMisComisiones();
		rowComision = new Object[tableComision.getColumnCount()];
		for (Comision obj : aux) {
			if(!(obj.getSelected())){
				rowComision[0] = obj.getCodComision();
				rowComision[1] = obj.getNombre();
				rowComision[2] = obj.getArea();
				modeloComision.addRow(rowComision);
			}
		}
	}
	
	private Boolean existeJurados(Comision obj) {
		for (Comision comision : GestionEvento.getInstance().getMisComisiones()) {
			if(comision.getSelected()) {
				for (Jurado jurado : comision.getJurado()) {
					for (Jurado jurado2 : obj.getJurado()) {
						if(jurado2.getId().equals(jurado.getId())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static Date convertirFecha(String fechaStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        try {
            return sdf.parse(fechaStr);
        } catch (ParseException e) {
            return null;
        }
    }

}
