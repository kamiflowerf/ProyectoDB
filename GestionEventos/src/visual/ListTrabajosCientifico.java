package visual;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import logico.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListTrabajosCientifico extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private TrabajoCientifico selectedTrabajo = null;
    private JButton btnModificar;
    private JButton btnEliminar;

    public static void main(String[] args) {
        try {
            ListTrabajosCientifico dialog = new ListTrabajosCientifico();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListTrabajosCientifico() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(icon);
        setTitle("Listar Trabajos Cient�ficos");
        setBounds(100, 100, 800, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient"), 
            UIManager.getColor("InternalFrame.activeTitleGradient")));
        contentPanel.add(panel);
        panel.setLayout(null);

        JPanel panelTable = new JPanel();
        panelTable.setForeground(Color.WHITE);
        panelTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
            "Trabajos Cient�ficos", TitledBorder.LEADING, TitledBorder.TOP, null, 
            UIManager.getColor("FormattedTextField.foreground")));
        panelTable.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
        panelTable.setBounds(12, 13, 748, 382);
        panel.add(panelTable);
        panelTable.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headers = {"ID", "Nombre", "�rea", "Autor", "C�dula Autor"};
        model.setColumnIdentifiers(headers);

        table = new JTable();
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table.getSelectedRow();
                if (index >= 0) {
                    btnEliminar.setEnabled(true);
                    btnModificar.setEnabled(true);
                    String trabajoId = table.getValueAt(index, 0).toString();
                    selectedTrabajo = findTrabajoById(trabajoId);
                }
            }
        });
        scrollPane.setViewportView(table);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        cancelButton.addActionListener(e -> dispose());
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (selectedTrabajo != null) {
        			int option = JOptionPane.showConfirmDialog(null,
        					"�Est� seguro que desea eliminar este trabajo cient�fico?",
        					"Confirmaci�n", JOptionPane.YES_NO_OPTION);

        			if (option == JOptionPane.YES_OPTION) {
        				GestionEvento.getInstance().getMisTrabajosCientificos().remove(selectedTrabajo);
        				loadTrabajos();
        				btnEliminar.setEnabled(false);
        				btnModificar.setEnabled(false);
        				selectedTrabajo = null;
        				JOptionPane.showMessageDialog(null, 
        						"Trabajo cient�fico eliminado exitosamente",
        						"Eliminaci�n exitosa",
        						JOptionPane.INFORMATION_MESSAGE);
        			}
        		}
        	}
        });
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(selectedTrabajo != null) {
        			ModTrabajo dialog = new ModTrabajo(selectedTrabajo);
        			dialog.setModal(true);
        			dialog.setVisible(true);
        			btnEliminar.setEnabled(false);
        			btnModificar.setEnabled(false);
        			selectedTrabajo = null;
        			loadTrabajos();
        		}
        	}
        });
        btnModificar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnModificar.setEnabled(false);
        buttonPane.add(btnModificar);
        btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnEliminar.setEnabled(false);
        buttonPane.add(btnEliminar);
        buttonPane.add(cancelButton);

        loadTrabajos();
    }

    private TrabajoCientifico findTrabajoById(String id) {
        for (TrabajoCientifico trabajo : GestionEvento.getInstance().getMisTrabajosCientificos()) {
            if (trabajo.getId().equals(id)) {
                return trabajo;
            }
        }
        return null;
    }

    private void loadTrabajos() {
        model.setRowCount(0); 
        for (TrabajoCientifico trabajo : GestionEvento.getInstance().getMisTrabajosCientificos()) {
            Participante autor = trabajo.getAutor();
            Object[] row = {
                trabajo.getId(),
                trabajo.getNombre(),
                trabajo.getArea(),
                autor.getNombre() + " " + autor.getApellidos(),
                autor.getCedula()
            };
            model.addRow(row);
        }
        selectedTrabajo = null;
    }
}