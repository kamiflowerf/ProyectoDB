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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.GestionEvento;
import logico.Recurso;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.Font;

public class ListRecurso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private int selectedRow = -1;
	private DefaultTableModel modelo;
	private Object row[];
	private JButton btnModificar;
	private JButton btnEliminar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListRecurso dialog = new ListRecurso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListRecurso() {
		setTitle("Lista de recursos");
		setBounds(100, 100, 575, 466);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_1.setBorder(new TitledBorder(null, "Recursos registrados", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("InternalFrame.activeTitleForeground")));
			panel_1.setBounds(10, 11, 529, 362);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel_1.add(scrollPane, BorderLayout.CENTER);
			
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selectedRow = table.getSelectedRow();
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
				
				}
			});
			modelo = new DefaultTableModel();
			String[] identificadores = {"C�digo", "Nombre", "Tipo", "Campus"};
			modelo.setColumnIdentifiers(identificadores);
			table.setModel(modelo);
			scrollPane.setViewportView(table);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Modificar");
				btnModificar.setFont(new Font("Tahoma", Font.BOLD, 13));
				btnModificar.setEnabled(false);
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();
				        if(selectedRow >= 0) {
				            String codigo = (String)modelo.getValueAt(selectedRow, 0);
				            Recurso recursoSeleccionado = GestionEvento.getInstance().buscarRecursoID(codigo);
				            if(recursoSeleccionado != null) {
				                RegRecurso regRecursos = new RegRecurso(recursoSeleccionado);
				                regRecursos.setModal(true);
				                regRecursos.setVisible(true);
				                btnModificar.setEnabled(false);
				                btnEliminar.setEnabled(false);
				                loadRecursos();
				            }
				        } else {
				            JOptionPane.showMessageDialog(null, 
				                "Debe seleccionar un recurso para modificar.",
				                "Error", JOptionPane.ERROR_MESSAGE);
				        }
					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 13));
				btnEliminar.setEnabled(false);
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
				        if(selectedRow >= 0) {
				            int option = JOptionPane.showConfirmDialog(null,
				                "�Est� seguro que desea eliminar este recurso?",
				                "Confirmaci�n", JOptionPane.YES_NO_OPTION);
				            
				            if(option == JOptionPane.YES_OPTION) {
				                String codigo = (String) modelo.getValueAt(selectedRow, 0);
				                Recurso recursoSeleccionado = GestionEvento.getInstance().buscarRecursoID(codigo);
				                GestionEvento.getInstance().eliminarRecurso(recursoSeleccionado);
				                JOptionPane.showMessageDialog(null, 
						                "Eliminaci�n completada.",
						                "Aviso", JOptionPane.ERROR_MESSAGE);
				                btnModificar.setEnabled(false);
				                btnEliminar.setEnabled(false);
				                loadRecursos();
				            }else {
				            	btnModificar.setEnabled(false);
				                btnEliminar.setEnabled(false);
				            }
				        } 
					}
				});
				buttonPane.add(btnEliminar);
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
		loadRecursos();
	}
	
	private void loadRecursos() {
	    modelo.setRowCount(0);
	    row = new Object[table.getColumnCount()];
	    for (Recurso recurso : GestionEvento.getInstance().getMisRecursos()) {
	        row[0] = recurso.getId();
	        row[1] = recurso.getNombre();
	        row[2] = recurso.getIdTipo();
	        if("Local".equals(recurso.getIdTipo()) && recurso.getLocal() != null) {
	            row[3] = recurso.getLocal().getCiudad();
	        } else {
	            row[3] = "N/A";
	        }
	        modelo.addRow(row);
	    }
	}
}
