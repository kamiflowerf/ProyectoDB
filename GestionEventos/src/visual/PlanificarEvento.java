package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.*;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import java.awt.Font;

public class PlanificarEvento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] headersComision = {"C�digo", "Nombre", "Area"};
	private String[] headersRecurso = {"C�digo", "Nombre", "Tipo"};
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
			
			JLabel lblNewLabel = new JLabel("C\u00F3digo:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(23, 33, 63, 14);
			panel_1.add(lblNewLabel);
			
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setText("E-"+ GeneradorCodigos.generarCodigoUnico(5));
			txtCodigo.setBounds(96, 30, 151, 20);
			panel_1.add(txtCodigo);
			txtCodigo.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("T\u00EDtulo:");
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
			
			cmbTipo = new JComboBox();
			cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Conferencia", "Panel", "Ponencia", "Poster", "Mesa Reonda"}));
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
								"Esta comisi�n tiene jurados en com�n con otra ya seleccionada.",
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
					if(!(tieneLocal) && (selectedRecurso.getIdTipo().toString().equals("Local"))) {
						selectedRecurso.setSelected(true);
						tieneLocal = true;
					}else if(tieneLocal && (selectedRecurso.getIdTipo().toString().equals("Local"))) {
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
					if(selectedRecurso.getIdTipo().toString().equals("Local")) {
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
				JButton okButton = new JButton("Planificar");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
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
										"Debe seleccionar al menos una comisi�n y un recurso.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}else {
								if(tieneLocal) {
									Evento evento = new Evento(txtCodigo.getText().toString(), txtTitulo.getText().toString(), cmbTipo.getSelectedItem().toString(), fecha);
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
									GestionEvento.getInstance().insertarEvento(evento);
									JOptionPane.showMessageDialog(null, 
											"Planificaci�n exitosa.",
											"Aviso", JOptionPane.WARNING_MESSAGE);
									clean();
								}else {
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
		ArrayList<Recurso> aux = GestionEvento.getInstance().getMisRecursos();
		rowRecursoSelected = new Object[tableRecursoS.getColumnCount()];
		for (Recurso obj : aux) {
			if(obj.getDisponibilidad()) {
				if(obj.getSelected()) {
					rowRecursoSelected[0] = obj.getId();
					rowRecursoSelected[1] = obj.getNombre();
					rowRecursoSelected[2] = obj.getIdTipo();
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
					rowRecurso[2] = obj.getIdTipo();
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
						if(jurado2.getIdJurado().equals(jurado.getIdJurado())) {
							return true;
						}
					}
				}
			}
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
