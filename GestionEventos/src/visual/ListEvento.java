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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Evento;
import logico.GestionEvento;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import java.awt.Font;

public class ListEvento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static DefaultTableModel modelo;
	private static Object[] row;
	private int index = -1;
	private Evento selected = null;
	private JTable table;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnVerReporte;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListEvento dialog = new ListEvento();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListEvento() {
		setTitle("Litar Eventos");
		setBounds(100, 100, 696, 467);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
		
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Eventos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							index = table.getSelectedRow();
							if(index >= 0) {
								String cod = table.getValueAt(index, 0).toString();
								selected = GestionEvento.getInstance().buscarEventoID(cod);
								if(selected != null && selected.getEstado()) {
									btnVerReporte.setEnabled(false);
									btnModificar.setEnabled(true);
									btnEliminar.setEnabled(true);
								}else if(selected != null && !(selected.getEstado())) {
									btnVerReporte.setEnabled(true);
									btnModificar.setEnabled(false);
									btnEliminar.setEnabled(false);
								}
							}
						}
					});
					scrollPane.setViewportView(table);
					modelo = new DefaultTableModel();
					String[] identificadores = {"Código", "Titulo", "Tipo", "Fecha", "Estado"};
					modelo.setColumnIdentifiers(identificadores);
					table.setModel(modelo);
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnEliminar = new JButton("Cancelar");
				btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 13));
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int option = JOptionPane.showConfirmDialog(null,
				                "¿Está seguro que desea cancelar este Evento?",
				                "Confirmación", JOptionPane.YES_NO_OPTION);
				            
				            if(option == JOptionPane.YES_OPTION) {
				                GestionEvento.getInstance().eliminarEvento(selected);;
				                JOptionPane.showMessageDialog(null, 
						                "Cancelación completada.",
						                "Aviso", JOptionPane.WARNING_MESSAGE);
				                loadEvento();
				            }
						btnModificar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnVerReporte.setEnabled(false);
					}
				});
				{
					btnModificar = new JButton("Modificar");
					btnModificar.setFont(new Font("Tahoma", Font.BOLD, 13));
					btnModificar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(selected != null) {
								ModEvento dialog = new ModEvento(selected);
								dialog.setModal(true);
								dialog.setVisible(true);
								loadEvento();
							}
							btnModificar.setEnabled(false);
							btnEliminar.setEnabled(false);
							btnVerReporte.setEnabled(false);
						}
					});
					btnModificar.setEnabled(false);
					btnModificar.setActionCommand("OK");
					buttonPane.add(btnModificar);
					getRootPane().setDefaultButton(btnModificar);
				}
				btnEliminar.setEnabled(false);
				btnEliminar.setActionCommand("OK");
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
				{
					btnVerReporte = new JButton("Ver Reporte");
					btnVerReporte.setFont(new Font("Tahoma", Font.BOLD, 13));
					btnVerReporte.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (selected != null) {
					            ReporteEvento dialog = new ReporteEvento(selected);
					            dialog.setModal(true);
					            dialog.setVisible(true);
					        }
					        btnModificar.setEnabled(false);
					        btnEliminar.setEnabled(false);
					        btnVerReporte.setEnabled(false);
						}
					});
					btnVerReporte.setEnabled(false);
					btnVerReporte.setActionCommand("OK");
					buttonPane.add(btnVerReporte);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		loadEvento();
	}

	private void loadEvento() {
		modelo.setRowCount(0);
		ArrayList<Evento> aux = GestionEvento.getInstance().getMisEventos();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		row = new Object[table.getColumnCount()];
		for (Evento obj : aux) {
			row[0] = obj.getId();
			row[1] = obj.getTitulo();
			row[2] = obj.getTipo();
			row[3] = formato.format(obj.getFecha());
			if(obj.getEstado()) {
				row[4] = "Proximamente";
			}else {
				row[4] = "Finalizado";
			}
			modelo.addRow(row);
		}
	}
}
