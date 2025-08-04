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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.GestionEvento;
import logico.User;

public class ListUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private int selectedRow = -1;
	private DefaultTableModel modelo;
	private Object row[];
	private JButton btnEliminar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListUsuario dialog = new ListUsuario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListUsuario() {
		setTitle("Lista de usuarios");
		setBounds(100, 100, 616, 527);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
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
			panel_1.setBorder(new TitledBorder(null, "Usuarios registrados", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("InternalFrame.activeTitleForeground")));
			panel_1.setBounds(10, 11, 570, 423);
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
					btnEliminar.setEnabled(true);
				
				}
			});
			modelo = new DefaultTableModel();
			String[] identificadores = {"Nombre", "Nombre de Usuario", "Tipo"};
			modelo.setColumnIdentifiers(identificadores);
			table.setModel(modelo);
			scrollPane.setViewportView(table);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				{
					btnEliminar = new JButton("Eliminar");
					btnEliminar.setEnabled(false);
					btnEliminar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(selectedRow >= 0) {
					            String tipo = (String) modelo.getValueAt(selectedRow, 2);
					            
					            if(tipo.equals("Administrador")) {
					                JOptionPane.showMessageDialog(null, 
					                    "No se pueden eliminar usuarios administradores.",
					                    "Error", 
					                    JOptionPane.ERROR_MESSAGE);
					                btnEliminar.setEnabled(false);
					                return;
					            }
					            
					            int option = JOptionPane.showConfirmDialog(null,
					                "¿Está seguro que desea eliminar este usuario?",
					                "Confirmación", JOptionPane.YES_NO_OPTION);
					            
					            if(option == JOptionPane.YES_OPTION) {
					                String username = (String) modelo.getValueAt(selectedRow, 1);
                                    User usuarioSeleccionado = null;
                                    try {
                                        usuarioSeleccionado = GestionEvento.getInstance().buscarUsuarioUsername(username);
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    try {
                                        GestionEvento.getInstance().eliminarUser(usuarioSeleccionado);
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                    JOptionPane.showMessageDialog(null,
					                    "Eliminación completada.",
					                    "Aviso", 
					                    JOptionPane.INFORMATION_MESSAGE);
					                
					                btnEliminar.setEnabled(false);
					                loadUsuarios();
					            } else {
					                btnEliminar.setEnabled(false);
					            }
					        } 
						}

					});
					
					buttonPane.add(btnEliminar);
					getRootPane().setDefaultButton(cancelButton);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}loadUsuarios();
	}
	private void loadUsuarios() {
		modelo.setRowCount(0);
	    row = new Object[3];
        try {
            for (User usuario : GestionEvento.getInstance().getMisUsuarios()) {
                row[0] = usuario.getNombre()+ " " +usuario.getApellido();
                row[1] = usuario.getUserName();
                row[2] = usuario.getTipo();
                modelo.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
