package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.GestionEvento;
import logico.Jurado;
import logico.Participante;
import logico.Persona;
import logico.Recurso;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.Font;

public class ListPersona extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static DefaultTableModel modeloJurd;
	private static Object[] rowJurd;
	private static DefaultTableModel modeloPart;
	private static Object[] rowPart;
	private int indexP = -1;
	private int indexJ = -1;
	private Persona selected = null;
	private JTable tableP;
	private JTable tableJ;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListPersona dialog = new ListPersona();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListPersona() {
		setTitle("Listar Participantes y Jurados");
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
		
		setBounds(100, 100, 669, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		{
			JPanel panel = new JPanel();
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panelPart = new JPanel();
				panelPart.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
				panelPart.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Participantes", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
				panelPart.setBounds(10, 11, 296, 350);
				panel.add(panelPart);
				panelPart.setLayout(new BorderLayout(0, 0));
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane.addMouseListener(new MouseAdapter() {
				});
				panelPart.add(scrollPane, BorderLayout.CENTER);
				
				tableP = new JTable();
				tableP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableP.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						indexP = tableP.getSelectedRow();
						if(indexP >= 0) {
							String cedula = tableP.getValueAt(indexP, 0).toString();
							selected = GestionEvento.getInstance().buscarPersonasCedula(cedula);
							if(selected != null) {
								btnModificar.setEnabled(true);
								btnEliminar.setEnabled(true);
							}
						}
					}
				});
				scrollPane.setViewportView(tableP);
				modeloPart = new DefaultTableModel();
				String[] identificadores1 = {"Cédula", "Nombre", "Teléfono"};
				modeloPart.setColumnIdentifiers(identificadores1);
				tableP.setModel(modeloPart);
				scrollPane.setViewportView(tableP);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Jurados", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.foreground")));
				panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
				panel_1.setBounds(341, 11, 296, 350);
				panel.add(panel_1);
				panel_1.setLayout(new BorderLayout(0, 0));
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel_1.add(scrollPane, BorderLayout.CENTER);
				
				tableJ = new JTable();
				tableJ.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableJ.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						indexJ = tableJ.getSelectedRow();
						if(indexJ >= 0) {
							String cedula = tableJ.getValueAt(indexJ, 0).toString();
							selected = GestionEvento.getInstance().buscarPersonasCedula(cedula);
							if(selected != null) {
								btnEliminar.setEnabled(true);
								btnModificar.setEnabled(true);
							}
						}
					}
				});
				scrollPane.setViewportView(tableJ);
				modeloJurd = new DefaultTableModel();
				String[] identificadores = {"Cédula","Nombre", "Área"};
				modeloJurd.setColumnIdentifiers(identificadores);
				tableJ.setModel(modeloJurd);
				scrollPane.setViewportView(tableJ);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			btnModificar = new JButton("Modificar");
			btnModificar.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ModPersona dialog = new ModPersona(selected);
					dialog.setModal(true);
					dialog.setVisible(true);
					btnEliminar.setEnabled(false);
					btnModificar.setEnabled(false);
					loadParticipante();
					loadJurado();
				}
			});
			btnModificar.setEnabled(false);
			btnModificar.setActionCommand("Cancel");
			buttonPane.add(btnModificar);
			
			btnEliminar = new JButton("Eliminar");
			btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null,
			                "¿Está seguro que desea eliminar este recurso?",
			                "Confirmación", JOptionPane.YES_NO_OPTION);
			            
			            if(option == JOptionPane.YES_OPTION) {
			                GestionEvento.getInstance().eliminarPersona(selected);
			                JOptionPane.showMessageDialog(null, 
					                "Eliminación completada.",
					                "Aviso", JOptionPane.WARNING_MESSAGE);
			                btnModificar.setEnabled(false);
			                btnEliminar.setEnabled(false);
			                loadParticipante();
			                loadJurado();
			            }else {
			            	btnModificar.setEnabled(false);
			                btnEliminar.setEnabled(false);
			            }
				}
			});
			btnEliminar.setEnabled(false);
			btnEliminar.setActionCommand("Cancel");
			buttonPane.add(btnEliminar);
			{
				cancelButton = new JButton("Cancelar");
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
		loadParticipante();
		loadJurado();
	}

	private void loadJurado() {
		modeloJurd.setRowCount(0);
		ArrayList<Persona> aux = GestionEvento.getInstance().getMisPersonas();
		rowJurd = new Object[tableJ.getColumnCount()];
		for (Persona obj : aux) {
			if(obj instanceof Jurado) {
				rowJurd[0] = obj.getCedula();
				rowJurd[1] = obj.getNombre();
				rowJurd[2] = ((Jurado) obj).getArea();
				modeloJurd.addRow(rowJurd);
			}
		}
	}

	private void loadParticipante() {
		modeloPart.setRowCount(0);
		ArrayList<Persona> aux = GestionEvento.getInstance().getMisPersonas();
		rowPart = new Object[tableP.getColumnCount()];
		for (Persona obj : aux) {
			if(obj instanceof Participante){
				rowPart[0] = obj.getCedula();
				rowPart[1] = obj.getNombre() + " " + obj.getApellidos();
				rowPart[2] = obj.getTelefono();
				modeloPart.addRow(rowPart);
			}
		}
	}
}
