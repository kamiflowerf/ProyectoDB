package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Comision;
import logico.GestionEvento;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.Font;

public class ListComision extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel modelo;
	private Object row[];
	private JTable table;
	private int index = -1;
	private Comision selected = null;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton cancelButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListComision dialog = new ListComision();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListComision() {
		setTitle("Listar Comisiones");
		setBounds(100, 100, 602, 533);
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
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("InternalFrame.borderShadow")));
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Comisiones registradas", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("FormattedTextField.caretForeground")));
			panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			panel_1.setBounds(10, 11, 556, 429);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel_1.add(scrollPane, BorderLayout.CENTER);
			{
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					index = table.getSelectedRow();
					if(index >= 0) {
						String codigo = table.getValueAt(index, 0).toString();
						selected = GestionEvento.getInstance().buscarComisionID(codigo);
						if(selected != null) {
							btnModificar.setEnabled(true);
							btnEliminar.setEnabled(true);
						}
					}
				}
			});
			modelo = new DefaultTableModel();
			String [] identificadores = {"Código", "Nombre", "Area"};
			modelo.setColumnIdentifiers(identificadores);
			table.setModel(modelo);
			scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Modificar");
				btnModificar.setFont(new Font("Tahoma", Font.BOLD, 13));
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();
				        if(selectedRow >= 0) {
				            String codigo = (String)modelo.getValueAt(selectedRow, 0);
				            Comision comisionSeleccionada = buscarComision(codigo);
				            if(comisionSeleccionada != null) {
				                RegComision regComision = new RegComision(comisionSeleccionada);
				                regComision.setModal(true);
				                regComision.setVisible(true);
				                btnEliminar.setEnabled(false);
								btnModificar.setEnabled(false);
				                loadComisiones(); 
				            }
				        } else {
				            JOptionPane.showMessageDialog(null, 
				                "Debe seleccionar una comisión para modificar.",
				                "Error", JOptionPane.ERROR_MESSAGE);
				        }
					}
				});
				btnModificar.setEnabled(false);
				btnModificar.setActionCommand("Cancel");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
				
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 13));
				btnEliminar.setEnabled(false);
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();
				        if(selectedRow >= 0) {
				        	
				            int option = JOptionPane.showConfirmDialog(null,
				                "¿Está seguro que desea eliminar esta comisión?",
				                "Confirmación", JOptionPane.YES_NO_OPTION);
				            
				            if(option == JOptionPane.YES_OPTION) {
				                String codigo = (String) modelo.getValueAt(selectedRow, 0);
				                eliminarComision(codigo);
				                btnModificar.setEnabled(false);
				                btnEliminar.setEnabled(false);
				                loadComisiones(); 
				            }
				        } else {
				        	btnModificar.setEnabled(false);
			                btnEliminar.setEnabled(false);
				            JOptionPane.showMessageDialog(null, 
				                "Debe seleccionar una comisión para eliminar.",
				                "Error", JOptionPane.ERROR_MESSAGE);
				        }
					}
				});
				buttonPane.add(btnEliminar);
                btnEliminar.setEnabled(false);
                btnEliminar.setActionCommand("Cancel");
			}
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
		loadComisiones();
		
	}
	private void loadComisiones() {
		modelo.setRowCount(0);
		ArrayList<Comision> aux = GestionEvento.getInstance().getMisComisiones();
		row = new Object[table.getColumnCount()];
		for (Comision comision : aux) {
			row[0] = comision.getCodComision();
			row[1] = comision.getNombre();
			row[2] = comision.getArea();
			modelo.addRow(row);
		}
	}
	
	private Comision buscarComision(String codigo) {
	    for(Comision comision : GestionEvento.getInstance().getMisComisiones()) {
	        if(comision.getCodComision().equals(codigo)) {
	            return comision;
	        }
	    }
	    return null;
	}
	
	private void eliminarComision(String codigo) {
	    Comision comisionAEliminar = null;
	    for(Comision comision : GestionEvento.getInstance().getMisComisiones()) {
	        if(comision.getCodComision().equals(codigo)) {
	            comisionAEliminar = comision;
	            break;
	        }
	    }
	    
	    if(comisionAEliminar != null) {
	        GestionEvento.getInstance().getMisComisiones().remove(comisionAEliminar);
	        JOptionPane.showMessageDialog(null, 
	            "Comisión eliminada exitosamente.",
	            "Información", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
}
