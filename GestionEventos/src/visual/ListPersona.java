package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DAO.JuradoDAO;
import DAO.ParticipanteDAO;
import DAO.PersonaDAO;
import logico.GestionEvento;
import logico.Jurado;
import logico.Participante;
import logico.Persona;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
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
	private Jurado selectedJ = null;
	private Participante selectedP = null;
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
							String idPart = tableP.getValueAt(indexP, 0).toString();
                            ParticipanteDAO pDao = new ParticipanteDAO();
                            try {
                                selectedP = pDao.get(idPart);
								if(selectedP != null) {
									btnModificar.setEnabled(true);
									btnEliminar.setEnabled(true);
								}
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
					}
				});
				scrollPane.setViewportView(tableP);
				modeloPart = new DefaultTableModel();
				String[] identificadores1 = {"ID","Cédula", "Nombre", "Teléfono"};
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
							String idJ = tableJ.getValueAt(indexJ, 0).toString();
							JuradoDAO juradoDAO = new JuradoDAO();
                            try {
                                selectedJ = juradoDAO.get(idJ);
								if(selectedJ != null) {
									btnEliminar.setEnabled(true);
									btnModificar.setEnabled(true);
								}
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
						}
					}
				});
				scrollPane.setViewportView(tableJ);
				modeloJurd = new DefaultTableModel();
				String[] identificadores = {"ID","Cédula","Nombre", "Área"};
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
					ModPersona dialog = null;
					if(selectedJ != null) {
                        dialog = new ModPersona(selectedJ);
                    } else if (selectedP != null) {
						dialog = new ModPersona(selectedP);
					}
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
			                "¿Está seguro que desea eliminar esta persona?",
			                "Confirmación", JOptionPane.YES_NO_OPTION);
			            
			            if(option == JOptionPane.YES_OPTION) {
                            try {
                                if(selectedP != null) {
									GestionEvento.getInstance().eliminarPersona(selectedP);
								} else if(selectedJ != null) {
									GestionEvento.getInstance().eliminarPersona(selectedJ);
								}
								JOptionPane.showMessageDialog(null,
										"Eliminación completada.",
										"Aviso", JOptionPane.WARNING_MESSAGE);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
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
		rowJurd = new Object[tableJ.getColumnCount()];

		JuradoDAO juradoDAO = new JuradoDAO();
        ArrayList<Jurado> jurados ;
        try {
            jurados = juradoDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Jurado jurado : jurados) {
			rowJurd[0] = jurado.getIdJurado();
			rowJurd[1] = jurado.getCedula();
			rowJurd[2] = jurado.getNombre() + " " + jurado.getApellidos();
			rowJurd[3] = jurado.getArea() != null ? jurado.getArea().getNombre() : "N/A";
			modeloJurd.addRow(rowJurd);
		}
	}

	private void loadParticipante() {
		modeloPart.setRowCount(0);
		rowPart = new Object[tableP.getColumnCount()];

		ParticipanteDAO pDao = new ParticipanteDAO();
        ArrayList<Participante> participantes ;
        try {
            participantes = pDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Participante par : participantes) {
			rowPart[0] = par.getIdParticipante();
			rowPart[1] = par.getCedula();
			rowPart[2] = par.getNombre() + " " + par.getApellidos();
			rowPart[3] = par.getTelefono();
			modeloPart.addRow(rowPart);
		}
	}
}
